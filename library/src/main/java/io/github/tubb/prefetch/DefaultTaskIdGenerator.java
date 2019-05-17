package io.github.tubb.prefetch;

import java.util.UUID;

/**
 * Default task id generator(UUID)
 * Created by tubingbing on 18/3/23.
 */

final class DefaultTaskIdGenerator implements TaskIdGenerator {
    @Override
    public <D, E> long generateTaskId(final FetchTask<D, E> task) {
        return UUID.randomUUID().hashCode();
    }
}
