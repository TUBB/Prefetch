package io.github.tubb.prefetch.test;

import io.github.tubb.prefetch.FetchTask;
import io.github.tubb.prefetch.ObservableFetchTask;
import io.github.tubb.prefetch.ObservableTaskExecutor;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * ObservableTask Task executor, just for test
 * Created by tubingbing on 18/3/23.
 */

public class TestObservableTaskExecutor extends ObservableTaskExecutor {
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
