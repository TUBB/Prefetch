package io.github.tubb.prefetch;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

/**
 * The abstract fetch data task
 * Created by tubingbing on 18/3/11.
 */

public abstract class ObservableFetchTask<D> extends FetchTask<D, Observable<D>> {
    private Disposable disposable;
    /**
     * Execute the task
     * @return RxJava observable
     */
    @NonNull
    @Override
    public abstract Observable<D> execute();

    /**
     * @return subscribe on Scheduler
     */
    public Scheduler subscribeOnScheduler() {
        return SchedulerProvider.io();
    }

    /**
     * @return observe on Scheduler
     */
    public Scheduler observeOnScheduler() {
        return SchedulerProvider.ui();
    }

    void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    Disposable getDisposable() {
        return disposable;
    }

    void dispose() {
        if (this.disposable != null && !this.disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
    }
}
