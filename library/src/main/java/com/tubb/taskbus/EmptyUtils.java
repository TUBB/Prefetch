package com.tubb.taskbus;

/**
 * Created by tubingbing on 2017/12/18.
 */

final class EmptyUtils {
    private EmptyUtils() {}
    static <T> T checkNotNull(T ref) {
        if (ref == null) {
            throw new NullPointerException();
        }
        return ref;
    }

    static <T> boolean isNull(T ref) {
        return ref == null;
    }
}
