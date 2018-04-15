package com.tubb.prefetch.test;

import android.app.Application;

import com.tubb.prefetch.Prefetch;
import com.tubb.prefetch.PrefetchConfig;

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
