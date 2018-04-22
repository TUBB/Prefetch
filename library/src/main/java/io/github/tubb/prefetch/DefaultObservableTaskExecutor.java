package io.github.tubb.prefetch;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Default task executor
 * Created by tubingbing on 18/3/11.
 */

final class DefaultObservableTaskExecutor extends ObservableTaskExecutor {

    @Override
    public <D> void execute(final FetchTask<D, Observable<D>> task) {
        if (!(task instanceof ObservableFetchTask)) {
            throw new RuntimeException("Please execute the ObservableFetchTask<D>");
        }
        ObservableFetchTask<D> observableFetchTask = (ObservableFetchTask<D>)task;
        onExecuting(task);
        task.execute()
                .subscribeOn(observableFetchTask.subscribeOnScheduler())
                .observeOn(observableFetchTask.observeOnScheduler())
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
