package com.tubb.prefetch;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava Scheduler provider
 * Created by tubingbing on 2017/12/18.
 */

final class SchedulerProvider {
    private SchedulerProvider() {}

    static Scheduler io() {
        return Schedulers.io();
    }

    static Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
