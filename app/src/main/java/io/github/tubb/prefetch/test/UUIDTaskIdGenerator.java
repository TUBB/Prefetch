package io.github.tubb.prefetch.test;

import io.github.tubb.prefetch.FetchTask;
import io.github.tubb.prefetch.TaskIdGenerator;

import java.util.UUID;

/**
 * Generate task id by UUID
 * Created by tubingbing on 18/3/23.
 */

public class UUIDTaskIdGenerator implements TaskIdGenerator {
    @Override
    public <D, E> long generateTaskId(FetchTask<D, E> task) {
        return UUID.randomUUID().toString().hashCode();
    }
}
