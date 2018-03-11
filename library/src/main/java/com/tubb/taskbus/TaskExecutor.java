package com.tubb.taskbus;

import io.reactivex.functions.Consumer;

/**
 * Created by tubingbing on 18/3/11.
 */

final class TaskExecutor {
    <Data> void execute(final AdvanceTask<Data> task) {
        TaskBus.instance().taskExecuting(task);
        task.execute()
                .subscribeOn(task.subscribeOnScheduler())
                .observeOn(task.observeOnScheduler())
                .subscribe(new Consumer<Data>() {
                    @Override
                    public void accept(Data data) throws Exception {
                        TaskBus.instance().taskExecuteSuccess(task, data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        TaskBus.instance().taskExecuteException(task, throwable);
                    }
                });
    }
}
