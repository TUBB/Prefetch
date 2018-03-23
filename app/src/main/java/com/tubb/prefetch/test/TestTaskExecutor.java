package com.tubb.prefetch.test;

import com.tubb.prefetch.FetchTask;
import com.tubb.prefetch.TaskExecutor;

import io.reactivex.functions.Consumer;

/**
 * Task executor, just for test
 * Created by tubingbing on 18/3/23.
 */

public class TestTaskExecutor extends TaskExecutor {
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
