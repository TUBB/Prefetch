package com.tubb.prefetch;

/**
 * Check null utils
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
}
