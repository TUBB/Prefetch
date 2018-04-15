package com.tubb.prefetch.test;

import com.tubb.prefetch.FetchTask;
import com.tubb.prefetch.PureFetchTask;
import com.tubb.prefetch.PureTaskExecutor;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Pure task executor, just for test
 * Created by tubingbing on 18/3/11.
 */

final class TestPureTaskExecutor extends PureTaskExecutor {

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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<D>() {
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
