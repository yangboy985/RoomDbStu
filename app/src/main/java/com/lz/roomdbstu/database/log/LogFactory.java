package com.lz.roomdbstu.database.log;

public class LogFactory {
    private static final String APP_TAG = "APP_LOG";

    public static boolean sIsDebug = true;

    private LogFactory() {
    }

    public static NLogger getLogger(Class<?> clz) {
        if (clz == null) {
            return new NLogger(APP_TAG, "", sIsDebug);
        }
        return new NLogger(APP_TAG, clz.getSimpleName(), sIsDebug);
    }

    public static NLogger getLogger(String tag) {
        return new NLogger(APP_TAG, tag, sIsDebug);
    }
}
