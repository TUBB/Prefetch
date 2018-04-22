package io.github.tubb.prefetch.test;

import android.app.Application;

import io.github.tubb.prefetch.Prefetch;
import io.github.tubb.prefetch.PrefetchConfig;

/**
 * App
 * Created by tubingbing on 18/3/23.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // init Prefetch
        Prefetch.instance().init(new PrefetchConfig.Builder()
                .taskIdGenerator(new UUIDTaskIdGenerator())
                .observableTaskExecutor(new TestObservableTaskExecutor())
                .pureTaskExecutor(new TestPureTaskExecutor())
                .build());
    }
}
