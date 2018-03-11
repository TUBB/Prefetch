package com.tubb.taskbus;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
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
