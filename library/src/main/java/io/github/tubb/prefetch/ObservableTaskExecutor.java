package io.github.tubb.prefetch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;

import static io.github.tubb.prefetch.CheckUtils.checkNotNull;

/**
 * Task executor, RxJava is the execute container
 * Note:
 * Must callback onExecuting(), onExecuteSuccess() and onExecuteError() method.
 * Otherwise registered listeners will not working!
 * Please see DefaultObservableTaskExecutor for detail.
 * Created by tubingbing on 18/3/23.
 */

public abstract class ObservableTaskExecutor {

    /**
     * Real execute the task
     * @param task the target task
     * @param <D> data generic
     */
    public abstract <D> void execute(final FetchTask<D, Observable<D>> task);

    /**
     * Task is executing
     * @param task target task
     */
    protected <D> void onExecuting(@NonNull final FetchTask<D, Observable<D>> task) {
        checkNotNull(task, "task = null");
        Prefetch.instance().taskExecuting(task);
    }

    /**
     * Task execute success
     * @param task target task
     * @param data fetched data
     */
    protected <D> void onExecuteSuccess(@NonNull final FetchTask<D, Observable<D>> task, @Nullable D data) {
        checkNotNull(task, "task = null");
        Prefetch.instance().taskExecuteSuccess(task, data);
    }

    /**
     * Task execute fail
     * @param task target task
     * @param throwable task occur exception
     */
    protected <D> void onExecuteError(@NonNull final FetchTask<D, Observable<D>> task, @Nullable Throwable throwable) {
        checkNotNull(task, "task = null");
        Prefetch.instance().taskExecuteException(task, throwable);
    }
}
