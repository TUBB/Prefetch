package io.github.tubb.prefetch;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Default task executor
 * Created by tubingbing on 18/3/11.
 */

final class DefaultPureTaskExecutor extends PureTaskExecutor {

    @Override
    public <D> void execute(final FetchTask<D, D> task) {
        if (!(task instanceof PureFetchTask)) {
            throw new RuntimeException("Please execute the PureFetchTask<D>");
        }
        final PureFetchTask<D> pureFetchTask = (PureFetchTask<D>)task;
        onExecuting(task);
        Observable.create(new ObservableOnSubscribe<D>() {
            @Override
            public void subscribe(ObservableEmitter<D> emitter) throws Exception {
                emitter.onNext(pureFetchTask.execute());
            }
        }).subscribeOn(SchedulerProvider.io()).observeOn(SchedulerProvider.ui()).subscribe(new Consumer<D>() {
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
