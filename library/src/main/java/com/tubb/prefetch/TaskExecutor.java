package com.tubb.prefetch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.tubb.prefetch.EmptyUtils.checkNotNull;

/**
 * Task executor, RxJava is the execute container
 * Note:
 * Must callback onExecuting(), onExecuteSuccess() and onExecuteError() method.
 * Otherwise registered listeners will not working!
 * Please see DefaultTaskExecutor for detail.
 * Created by tubingbing on 18/3/23.
 */

public abstract class TaskExecutor {

    /**
     * Real execute the task
     * @param task the target task
     * @param <D> data generic
     */
    public abstract <D> void execute(final FetchTask<D> task);

    /**
     * Task is executing
     * @param task target task
     * @param <D> data generic
     */
    protected <D> void onExecuting(@NonNull final FetchTask<D> task) {
        checkNotNull(task);
        Prefetch.instance().taskExecuting(task);
    }

    /**
     * Task execute success
     * @param task target task
     * @param data fetched data
     * @param <D> data generic
     */
    protected <D> void onExecuteSuccess(@NonNull final FetchTask<D> task, @Nullable D data) {
        checkNotNull(task);
        Prefetch.instance().taskExecuteSuccess(task, data);
    }

    /**
     * Task execute fail
     * @param task target task
     * @param throwable task occur exception
     * @param <D> data generic
     */
    protected <D> void onExecuteError(@NonNull final FetchTask<D> task, @Nullable Throwable throwable) {
        checkNotNull(task);
        Prefetch.instance().taskExecuteException(task, throwable);
    }
}
