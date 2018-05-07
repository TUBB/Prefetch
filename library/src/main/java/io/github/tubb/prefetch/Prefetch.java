package io.github.tubb.prefetch;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import static io.github.tubb.prefetch.CheckUtils.checkNotNull;
import static io.github.tubb.prefetch.CheckUtils.checkOnMainThread;
import static io.github.tubb.prefetch.CheckUtils.isNull;

/**
 * Prefetch data universal library
 * Created by tubingbing on 18/3/11.
 */

public final class Prefetch {
    private static final String TAG = "Prefetch";
    private static final Prefetch INSTANCE = new Prefetch();
    /**
     * Prefetch config
     */
    private PrefetchConfig mConfig;
    /**
     * Current task map, key is the task id and value is the task
     */
    private ArrayMap<Long, FetchTask> mTaskMap = new ArrayMap<>(8);
    /**
     * Task result listener map, key is the task id and value is the listener
     */
    private ArrayMap<Long, FetchTask.Listener> mListenerMap = new ArrayMap<>(8);

    private Prefetch() {
        mConfig = new PrefetchConfig.Builder().build();
    }

    public static Prefetch instance() {
        return INSTANCE;
    }

    /**
     * Init Prefetch with custom config
     * @param config custom PrefetchConfig
     * @throws NullPointerException if config is null
     */
    public void init(@NonNull PrefetchConfig config) {
        checkNotNull(config, "config = null");
        mConfig = config;
    }

    /**
     * Execute the PureFetchTask
     * @param task your custom task
     * @param <D> data generic
     * @return task id
     * @throws NullPointerException if task is null
     * @throws RuntimeException if not execute on main thread
     * @throws RuntimeException if task execute twice
     */
    @MainThread
    public <D> long executeTask(@NonNull final PureFetchTask<D> task) {
        checkNotNull(task, "task = null");
        checkOnMainThread();
        if (mTaskMap.containsKey(task.getTaskId())) {
            throw new RuntimeException("A task can only execute once, you should finish the task first!");
        }
        long taskId = mConfig.getTaskIdGenerator().generateTaskId(task);
        execute(taskId, task);
        return taskId;
    }

    private <D> void execute(final long taskId, final PureFetchTask<D> task) {
        task.setTaskId(taskId);
        mTaskMap.put(taskId, task);
        mConfig.getPureTaskExecutor().execute(task);
    }

    /**
     * Execute the ObservableFetchTask
     * @param task your custom task
     * @param <D> data generic
     * @return task id
     * @throws NullPointerException if task is null
     * @throws RuntimeException if not execute on main thread
     * @throws RuntimeException if task execute twice
     *
     */
    @MainThread
    public <D> long executeTask(@NonNull final ObservableFetchTask<D> task) {
        checkNotNull(task, "task = null");
        checkOnMainThread();
        if (mTaskMap.containsKey(task.getTaskId())) {
            throw new RuntimeException("A task can only execute once, you should finish the task first!");
        }
        long taskId = mConfig.getTaskIdGenerator().generateTaskId(task);
        execute(taskId, task);
        return taskId;
    }

    private <D> void execute(final long taskId, final ObservableFetchTask<D> task) {
        task.setTaskId(taskId);
        mTaskMap.put(taskId, task);
        mConfig.getObservableTaskExecutor().execute(task);
    }

    /**
     * Finished the task, then you can execute the same task again
     * @param taskId task id
     * @throws RuntimeException if not execute the task yet
     */
    public void finishTask(final long taskId) {
        if (!mTaskMap.containsKey(taskId)) {
            throw new RuntimeException(String.format("Not find the target task of %s", taskId));
        }
        synchronized (this) {
            FetchTask task = mTaskMap.remove(taskId);
            if (!isNull(task)) // the task maybe null
                task.reset();
            mListenerMap.remove(taskId);
        }
    }

    /**
     * Register listener to listen the task's result
     * @param taskId task id
     * @param listener ObservableFetchTask.Listener
     * @throws NullPointerException if listener is null
     * @throws RuntimeException if not execute the task yet
     */
    public void registerListener(final long taskId, @NonNull final FetchTask.Listener listener) {
        checkNotNull(listener, "listener = null");
        if (!mTaskMap.containsKey(taskId)) {
            throw new RuntimeException(String.format("The %s task not execute yet!", taskId));
        }
        synchronized (this) {
            mListenerMap.put(taskId, listener);
        }
        notifyListener(taskId);
    }

    /**
     * Unregister listener of task's result
     * @param taskId task id
     */
    public void unregisterListener(final long taskId) {
        synchronized (this) {
            mListenerMap.remove(taskId);
        }
    }

    @SuppressWarnings("unchecked")
    private void notifyListener(final long taskId) {
        FetchTask.Listener listener = mListenerMap.get(taskId);
        if (isNull(listener)) {
            Log.w(TAG, String.format("Not found %s task listener!", taskId));
            return;
        }
        FetchTask task = mTaskMap.get(taskId);
        if (isNull(task)) {
            Log.w(TAG, String.format("Not found %s task!", taskId));
            return;
        }
        short state = task.getState();
        switch (state) {
            case ObservableFetchTask.EXECUTING_STATE:
                listener.onExecuting();
                break;
            case ObservableFetchTask.SUCCESS_STATE:
                // Type-unsafe
                // When FetchTask<D> type is not equals FetchTask.Listener<D> type
                // Maybe throws ClassCastException
                // Please noticed that!!!
                try {
                    listener.onSuccess(task.getData());
                } catch (ClassCastException e) {
                    listener.onError(e);
                }
                break;
            case ObservableFetchTask.ERROR_STATE:
                listener.onError(task.getException());
                break;
            default:
                Log.w(TAG, String.format("%s task is INITIALIZED_STATE!", taskId));
                break;
        }
    }

    <D, E> void taskExecuting(final FetchTask<D, E> task) {
        task.setState(FetchTask.EXECUTING_STATE);
        notifyListener(task.getTaskId());
    }

    <D, E> void taskExecuteException(final FetchTask<D, E> task, Throwable exception) {
        task.setState(FetchTask.ERROR_STATE);
        task.setException(exception);
        notifyListener(task.getTaskId());
    }

    <D, E> void taskExecuteSuccess(final FetchTask<D, E> task, @Nullable final D data) {
        task.setState(FetchTask.SUCCESS_STATE);
        task.setData(data);
        notifyListener(task.getTaskId());
    }
}
