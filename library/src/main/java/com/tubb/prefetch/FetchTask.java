package com.tubb.prefetch;

import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by tubingbing on 18/3/11.
 */

public abstract class FetchTask<D> {
    private static final short INITIALIZED_STATE = 0;
    static final short EXECUTING_STATE = 1;
    static final short SUCCESS_STATE = 2;
    static final short ERROR_STATE = 3;
    private short state = INITIALIZED_STATE;
    private long taskId;
    private D data;
    private Throwable exception;

    public abstract Observable<D> execute();

    protected Scheduler subscribeOnScheduler() {
        return SchedulerProvider.io();
    }

    protected Scheduler observeOnScheduler() {
        return SchedulerProvider.ui();
    }

    void setData(D data) {
        this.data = data;
    }

    D getData() {
        return this.data;
    }

    void setException(Throwable exception) {
        this.exception = exception;
    }

    Throwable getException() {
        return exception;
    }

    void setState(short state) {
        this.state = state;
    }

    short getState() {
        return this.state;
    }

    void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    long getTaskId() {
        return taskId;
    }

    void reset() {
        setTaskId(0L);
        setState(INITIALIZED_STATE);
        setData(null);
        setException(null);
    }

    public interface Listener<D> {
        void onExecuting();
        void onSuccess(@Nullable D data);
        void onError(Throwable throwable);
    }
}
