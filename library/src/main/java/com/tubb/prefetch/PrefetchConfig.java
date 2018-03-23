package com.tubb.prefetch;

import static com.tubb.prefetch.EmptyUtils.checkNotNull;
import static com.tubb.prefetch.EmptyUtils.isNull;

/**
 * Prefetch global config
 * Created by tubingbing on 18/3/23.
 */

public final class PrefetchConfig {
    /**
     * Task id generator
     */
    private TaskIdGenerator taskIdGenerator;
    /**
     * Task executor
     */
    private TaskExecutor taskExecutor;

    private PrefetchConfig(Builder builder) {
        if (!isNull(builder.taskIdGenerator)) {
            this.taskIdGenerator = builder.taskIdGenerator;
        } else {
            this.taskIdGenerator = new DefaultTaskIdGenerator();
        }
        if (!isNull(builder.taskExecutor)) {
            this.taskExecutor = builder.taskExecutor;
        } else {
            this.taskExecutor = new DefaultTaskExecutor();
        }
    }

    TaskIdGenerator getTaskIdGenerator() {
        return taskIdGenerator;
    }

    TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public final static class Builder {
        private TaskIdGenerator taskIdGenerator;
        private TaskExecutor taskExecutor;

        public Builder taskIdGenerator(final TaskIdGenerator taskIdGenerator) {
            checkNotNull(taskIdGenerator);
            this.taskIdGenerator = taskIdGenerator;
            return this;
        }

        public Builder taskExecutor(final TaskExecutor taskExecutor) {
            checkNotNull(taskExecutor);
            this.taskExecutor = taskExecutor;
            return this;
        }

        public PrefetchConfig build() {
            return new PrefetchConfig(this);
        }
    }
}
