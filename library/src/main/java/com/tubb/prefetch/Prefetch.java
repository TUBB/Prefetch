package com.tubb.prefetch;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import static com.tubb.prefetch.EmptyUtils.checkNotNull;
import static com.tubb.prefetch.EmptyUtils.isNull;

/**
 * Created by tubingbing on 18/3/11.
 */

public final class Prefetch {
    private static final String TAG = "Prefetch";
    private static final Prefetch INSTANCE = new Prefetch();
    private TaskExecutor mTaskExecutor = new TaskExecutor();
    private ArrayMap<Long, FetchTask> mTaskMap = new ArrayMap<>(8);
    private ArrayMap<Long, FetchTask.Listener> mListenerMap = new ArrayMap<>(8);

    private Prefetch() {}

    public static Prefetch instance() {
        return INSTANCE;
    }

    public synchronized <D> void executeTask(final long taskId, final FetchTask<D> task) {
        checkNotNull(task);
        if (mTaskMap.containsKey(task.getTaskId())) {
            throw new RuntimeException("You maybe execute the same task again, a task can only execute once!");
        }
        execute(taskId, task);
    }

    public synchronized <D> long executeTask(final FetchTask<D> task) {
        checkNotNull(task);
        if (mTaskMap.containsKey(task.getTaskId())) {
            throw new RuntimeException("A task can only execute once, you should finish the task first!");
        }
        long taskId = System.nanoTime();
        execute(taskId, task);
        return taskId;
    }

    private <D> void execute(final long taskId, final FetchTask<D> task) {
        task.setTaskId(taskId);
        mTaskMap.put(taskId, task);
        mTaskExecutor.execute(task);
    }

    public synchronized void finishTask(final long taskId) {
        if (!mTaskMap.containsKey(taskId)) {
            throw new RuntimeException(String.format("Not find the target task of %s", taskId));
        }
        FetchTask task = mTaskMap.remove(taskId);
        if (!isNull(task)) // the task maybe null
            task.reset();
        mListenerMap.remove(taskId);
    }

    public synchronized void registerListener(final long taskId, final FetchTask.Listener listener) {
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
        FetchTask.Listener listener = mListenerMap.get(taskId);
        if (isNull(listener)) {
            Log.d(TAG, String.format("Not found %s task listener!", taskId));
            return;
        }
        FetchTask task = mTaskMap.get(taskId);
        if (isNull(task)) {
            Log.d(TAG, String.format("Not found %s task!", taskId));
            return;
        }
        short state = task.getState();
        switch (state) {
            case FetchTask.EXECUTING_STATE:
                listener.onExecuting();
                break;
            case FetchTask.SUCCESS_STATE:
                listener.onSuccess(task.getData());
                break;
            case FetchTask.ERROR_STATE:
                listener.onError(task.getException());
                break;
            default:
                Log.d(TAG, String.format("%s task is INITIALIZED_STATE!", taskId));
                break;
        }
    }

    synchronized <D> void taskExecuting(final FetchTask<D> task) {
        task.setState(FetchTask.EXECUTING_STATE);
        notifyListener(task.getTaskId());
    }

    synchronized <D> void taskExecuteException(final FetchTask<D> task, Throwable exception) {
        task.setState(FetchTask.ERROR_STATE);
        task.setException(exception);
        notifyListener(task.getTaskId());
    }

    synchronized <D> void taskExecuteSuccess(final FetchTask<D> task, @Nullable final D data) {
        task.setState(FetchTask.SUCCESS_STATE);
        task.setData(data);
        notifyListener(task.getTaskId());
    }
}
