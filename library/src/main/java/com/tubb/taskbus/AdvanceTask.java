package com.tubb.taskbus;

import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by tubingbing on 18/3/11.
 */

public abstract class AdvanceTask<Data> {
    private static final short INITIALIZED_STATE = 0;
    static final short EXECUTING_STATE = 1;
    static final short SUCCESS_STATE = 2;
    static final short ERROR_STATE = 3;
    private short state = INITIALIZED_STATE;
    private long taskId;
    private Data data;
    private Throwable exception;

    public abstract Observable<Data> execute();

    public Scheduler subscribeOnScheduler() {
        return SchedulerProvider.io();
    }

    public Scheduler observeOnScheduler() {
        return SchedulerProvider.ui();
    }

    void setData(Data data) {
        this.data = data;
    }

    Data getData() {
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

    public interface Listener<Data> {
        void onExecuting();
        void onSuccess(@Nullable Data data);
        void onError(Throwable throwable);
    }
}
