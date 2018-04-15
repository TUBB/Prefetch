package com.tubb.prefetch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by tubingbing on 18/4/15.
 */

public abstract class FetchTask<D, E> {

    private static final short INITIALIZED_STATE = 0;
    protected static final short EXECUTING_STATE = 1;
    protected static final short SUCCESS_STATE = 2;
    protected static final short ERROR_STATE = 3;
    /**
     * the task current state
     * */
    protected short state = INITIALIZED_STATE;
    /**
     * task id
     * */
    protected long taskId;
    /**
     * fetched data
     * */
    protected D data;
    /**
     * the task execute occur exception
     * */
    protected Throwable exception;

    @NonNull
    public abstract E execute();

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
