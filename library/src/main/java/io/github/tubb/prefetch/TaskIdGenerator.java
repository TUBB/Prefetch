package io.github.tubb.prefetch;

/**
 * Task id generator
 * Created by tubingbing on 18/3/23.
 */

public interface TaskIdGenerator {
    /**
     * Generate task id for ObservableFetchTask
     * @param task the target task
     * @param <D> data generic
     * @return task id
     */
    <D, E> long generateTaskId(final FetchTask<D, E> task);
}
