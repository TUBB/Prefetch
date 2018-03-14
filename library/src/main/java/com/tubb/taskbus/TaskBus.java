package com.tubb.taskbus;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import static com.tubb.taskbus.EmptyUtils.checkNotNull;
import static com.tubb.taskbus.EmptyUtils.isNull;

/**
 * Created by tubingbing on 18/3/11.
 */

public final class TaskBus {
    private static final String TAG = "TaskBus";
    private static final TaskBus INSTANCE = new TaskBus();
    private TaskExecutor mTaskExecutor = new TaskExecutor();
    private ArrayMap<Long, AdvanceTask> mTaskMap = new ArrayMap<>(8);
    private ArrayMap<Long, AdvanceTask.Listener> mListenerMap = new ArrayMap<>(8);

    private TaskBus() {}

    public static TaskBus instance() {
        return INSTANCE;
    }

    public synchronized <Data> long executeTask(final AdvanceTask<Data> task) {
        checkNotNull(task);
        if (mTaskMap.containsKey(task.getTaskId())) {
            throw new RuntimeException("A task can only execute once, you should finish the task first!");
        }
        long taskId = System.nanoTime();
        task.setTaskId(taskId);
        mTaskMap.put(taskId, task);
        mTaskExecutor.execute(task);
        return taskId;
    }

    public synchronized void finishTask(final long taskId) {
        if (!mTaskMap.containsKey(taskId)) {
            throw new RuntimeException(String.format("Not find the target task of %s", taskId));
        }
        AdvanceTask task = mTaskMap.remove(taskId);
        task.reset();
        mListenerMap.remove(taskId);
    }

    public synchronized void registerListener(final long taskId, final AdvanceTask.Listener listener) {
        checkNotNull(listener);
        if (!mTaskMap.containsKey(taskId)) {
            throw new RuntimeException(String.format("The %s task not execute yet!", taskId));
        }
        mListenerMap.put(taskId, listener);
        notifyListener(taskId);
    }

    public synchronized void unregisterListener(final long taskId) {
        mListenerMap.remove(taskId);
    }

    private void notifyListener(final long taskId) {
        AdvanceTask.Listener listener = mListenerMap.get(taskId);
        if (isNull(listener)) {
            Log.d(TAG, String.format("Not found %s task listener!", taskId));
            return;
        }
        AdvanceTask task = mTaskMap.get(taskId);
        if (isNull(task)) {
            Log.d(TAG, String.format("Not found %s task!", taskId));
            return;
        }
        short state = task.getState();
        switch (state) {
            case AdvanceTask.EXECUTING_STATE:
                listener.onExecuting();
                break;
            case AdvanceTask.SUCCESS_STATE:
                listener.onSuccess(task.getData());
                break;
            case AdvanceTask.ERROR_STATE:
                listener.onError(task.getException());
                break;
            default:
                Log.d(TAG, String.format("%s task is INITIALIZED_STATE!", taskId));
                break;
        }
    }

    synchronized <Data> void taskExecuting(final AdvanceTask<Data> task) {
        task.setState(AdvanceTask.EXECUTING_STATE);
        notifyListener(task.getTaskId());
    }

    synchronized <Data> void taskExecuteException(final AdvanceTask<Data> task, Throwable exception) {
        task.setState(AdvanceTask.ERROR_STATE);
        task.setException(exception);
        notifyListener(task.getTaskId());
    }

    synchronized <Data> void taskExecuteSuccess(final AdvanceTask<Data> task, @Nullable final Data data) {
        task.setState(AdvanceTask.SUCCESS_STATE);
        task.setData(data);
        notifyListener(task.getTaskId());
    }
}
