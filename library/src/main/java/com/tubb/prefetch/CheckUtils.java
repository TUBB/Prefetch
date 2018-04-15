package com.tubb.prefetch;

import android.os.Looper;

/**
 * Check utils
 * Created by tubingbing on 2017/12/18.
 */

final class CheckUtils {
    private CheckUtils() {}
    static <T> T checkNotNull(T ref, String msg) {
        if (ref == null) {
            throw new NullPointerException(msg);
        }
        return ref;
    }

    static <T> boolean isNull(T ref) {
        return ref == null;
    }

    static void checkOnMainThread() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new RuntimeException("Please execute on main thread!");
        }
    }
}
