package io.github.tubb.prefetch;

/**
 * Default task id generator
 * Created by tubingbing on 18/3/23.
 */

final class DefaultTaskIdGenerator implements TaskIdGenerator {
    @Override
    public <D, E> long generateTaskId(final FetchTask<D, E> task) {
        return System.nanoTime();
    }
}
