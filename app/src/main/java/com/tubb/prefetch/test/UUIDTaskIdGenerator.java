package com.tubb.prefetch.test;

import com.tubb.prefetch.FetchTask;
import com.tubb.prefetch.TaskIdGenerator;

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
