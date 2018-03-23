package com.tubb.prefetch;

/**
 * Default task id generator
 * Created by tubingbing on 18/3/23.
 */

final class DefaultTaskIdGenerator implements TaskIdGenerator {
    @Override
    public <D> long generateTaskId(final FetchTask<D> task) {
        return System.nanoTime();
    }
}
