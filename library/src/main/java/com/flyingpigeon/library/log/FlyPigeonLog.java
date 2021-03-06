package com.flyingpigeon.library.log;

import android.util.Log;

import com.flyingpigeon.library.BuildConfig;

/**
 * @author xiaozhongcen
 * @date 20-7-3
 * @since 1.0.0
 */
public class FlyPigeonLog {

    public static boolean DEBUG = BuildConfig.DEBUG;

    private FlyPigeonLog() {
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }
}
