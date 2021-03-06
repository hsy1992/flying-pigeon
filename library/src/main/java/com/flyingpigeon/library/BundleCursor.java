package com.flyingpigeon.library;

import android.database.MatrixCursor;
import android.os.Bundle;

/**
 * @author xiaozhongcen
 * @date 20-6-24
 * @since 1.0.0
 */
public final class BundleCursor extends MatrixCursor {
    private Bundle mBundle;
    private static final String TAG = Config.PREFIX + BundleCursor.class.getSimpleName();

    public BundleCursor(Bundle extras, String[] data) {
        super(data, 1);
        mBundle = extras;
    }

    @Override
    public Bundle getExtras() {
        return mBundle;
    }

    @Override
    public Bundle respond(Bundle extras) {
        mBundle = extras;
        return mBundle;
    }
}