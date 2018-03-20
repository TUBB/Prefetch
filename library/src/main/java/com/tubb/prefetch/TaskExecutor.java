package com.tubb.prefetch;

import io.reactivex.functions.Consumer;

/**
 * Created by tubingbing on 18/3/11.
 */

final class TaskExecutor {
    <D> void execute(final FetchTask<D> task) {
        Prefetch.instance().taskExecuting(task);
        task.execute()
                .subscribeOn(task.subscribeOnScheduler())
                .observeOn(task.observeOnScheduler())
                .subscribe(new Consumer<D>() {
                    @Override
                    public void accept(D data) throws Exception {
                        Prefetch.instance().taskExecuteSuccess(task, data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Prefetch.instance().taskExecuteException(task, throwable);
                    }
                });
    }
}
