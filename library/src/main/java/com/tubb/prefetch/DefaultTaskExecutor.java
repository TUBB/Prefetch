package com.tubb.prefetch;

import io.reactivex.functions.Consumer;

/**
 * Default task executor
 * Created by tubingbing on 18/3/11.
 */

final class DefaultTaskExecutor extends TaskExecutor {

    @Override
    public <D> void execute(final FetchTask<D> task) {
        onExecuting(task);
        task.execute()
                .subscribeOn(task.subscribeOnScheduler())
                .observeOn(task.observeOnScheduler())
                .subscribe(new Consumer<D>() {
                    @Override
                    public void accept(D data) throws Exception {
                        onExecuteSuccess(task, data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onExecuteError(task, throwable);
                    }
                });
    }
}
