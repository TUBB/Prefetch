package com.tubb.prefetch;

import android.support.annotation.NonNull;

import static com.tubb.prefetch.CheckUtils.checkNotNull;
import static com.tubb.prefetch.CheckUtils.isNull;

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
     * Observable Task executor
     */
    private ObservableTaskExecutor observableTaskExecutor;
    /**
     * Pure Task executor
     */
    private PureTaskExecutor pureTaskExecutor;

    private PrefetchConfig(Builder builder) {
        if (!isNull(builder.taskIdGenerator)) {
            this.taskIdGenerator = builder.taskIdGenerator;
        } else {
            this.taskIdGenerator = new DefaultTaskIdGenerator();
        }
        if (!isNull(builder.observableTaskExecutor)) {
            this.observableTaskExecutor = builder.observableTaskExecutor;
        } else {
            this.observableTaskExecutor = new DefaultObservableTaskExecutor();
        }
        if (!isNull(builder.pureTaskExecutor)) {
            this.pureTaskExecutor = builder.pureTaskExecutor;
        } else {
            this.pureTaskExecutor = new DefaultPureTaskExecutor();
        }
    }

    TaskIdGenerator getTaskIdGenerator() {
        return taskIdGenerator;
    }

    ObservableTaskExecutor getObservableTaskExecutor() {
        return observableTaskExecutor;
    }

    PureTaskExecutor getPureTaskExecutor() {
        return pureTaskExecutor;
    }

    public final static class Builder {
        private TaskIdGenerator taskIdGenerator;
        private ObservableTaskExecutor observableTaskExecutor;
        private PureTaskExecutor pureTaskExecutor;

        public Builder taskIdGenerator(@NonNull final TaskIdGenerator taskIdGenerator) {
            checkNotNull(taskIdGenerator, "taskIdGenerator = null");
            this.taskIdGenerator = taskIdGenerator;
            return this;
        }

        public Builder observableTaskExecutor(@NonNull final ObservableTaskExecutor observableTaskExecutor) {
            checkNotNull(observableTaskExecutor, "observableTaskExecutor = null");
            this.observableTaskExecutor = observableTaskExecutor;
            return this;
        }

        public Builder pureTaskExecutor(@NonNull final PureTaskExecutor pureTaskExecutor) {
            checkNotNull(pureTaskExecutor, "pureTaskExecutor = null");
            this.pureTaskExecutor = pureTaskExecutor;
            return this;
        }

        public PrefetchConfig build() {
            return new PrefetchConfig(this);
        }
    }
}
