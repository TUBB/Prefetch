package com.tubb.prefetch;

import io.reactivex.functions.Consumer;

/**
 * Task executor, RxJava is the container
 * Created by tubingbing on 18/3/11.
 */

final class TaskExecutor {
    /**
     * Real execute the task
     * @param task the target task
     * @param <D> data generic
     */
    <D> void execute(final FetchTask<D> task) {
        // notify task is executing
        Prefetch.instance().taskExecuting(task);
        task.execute()
                .subscribeOn(task.subscribeOnScheduler())
                .observeOn(task.observeOnScheduler())
                .subscribe(new Consumer<D>() {
                    @Override
                    public void accept(D data) throws Exception {
                        // notify task execute success
                        Prefetch.instance().taskExecuteSuccess(task, data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // notify task execute fail
                        Prefetch.instance().taskExecuteException(task, throwable);
                    }
                });
    }
}
