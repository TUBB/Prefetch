package com.tubb.prefetch;

/**
 * Task id generator
 * Created by tubingbing on 18/3/23.
 */

public interface TaskIdGenerator {
    /**
     * Generate task id for FetchTask
     * @param task the target task
     * @param <D> data generic
     * @return task id
     */
    <D> long generateTaskId(final FetchTask<D> task);
}
