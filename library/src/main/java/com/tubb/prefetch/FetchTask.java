package com.tubb.prefetch;

import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * The abstract fetch data task
 * Created by tubingbing on 18/3/11.
 */

public abstract class FetchTask<D> {
    private static final short INITIALIZED_STATE = 0;
    static final short EXECUTING_STATE = 1;
    static final short SUCCESS_STATE = 2;
    static final short ERROR_STATE = 3;
    /**
     * the task current state
     * */
    private short state = INITIALIZED_STATE;
    /**
     * task id
     * */
    private long taskId;
    /**
     * fetched data
     * */
    private D data;
    /**
     * the task execute occur exception
     * */
    private Throwable exception;

    /**
     * Execute the task
     * @return RxJava observable
     */
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

    /**
     * Fetch task's result listener
     * @param <D> data generic
     */
    public interface Listener<D> {
        /**
         * Fetch task is executing
         */
        void onExecuting();

        /**
         * Fetch task execute success
         * @param data task fetched data
         */
        void onSuccess(@Nullable D data);

        /**
         * Fetch task execute fail
         * @param throwable task occur exception
         */
        void onError(Throwable throwable);
    }
}
