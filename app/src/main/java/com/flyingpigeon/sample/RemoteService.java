package com.flyingpigeon.sample;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.flyingpigeon.library.Pigeon;
import com.flyingpigeon.library.ServiceManager;
import com.flyingpigeon.library.annotations.RequestLarge;
import com.flyingpigeon.library.annotations.ResponseLarge;
import com.flyingpigeon.library.annotations.route;

import androidx.annotation.Nullable;

import static com.flyingpigeon.library.Config.PREFIX;

/**
 * @author xiaozhongcen
 * @date 20-6-15
 * @since 1.0.0
 */
public class RemoteService extends Service implements RemoteServiceApi {

    public static final String TAG = PREFIX + RemoteService.class.getSimpleName();

    public static void startService(final Context context) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                context.bindService(new Intent(context, RemoteService.class), new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                    }

                    @Override
                    public void onBindingDied(ComponentName name) {
                    }

                    @Override
                    public void onNullBinding(ComponentName name) {
                    }
                }, Context.BIND_AUTO_CREATE);

            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * There are three ways to publish your service . such as
         */
//        ServiceManager.getInstance().publish(this, RemoteServiceApi.class); //This method does not support route forwarding
        /**
         * or
         */
        ServiceManager.getInstance().publish(this);
        /**
         * or
         */
        ServiceManager.getInstance().publish(mApi);

    }

    private Api mApi = new Api() {
        @Override
        public int createPoster(Poster poster) {
            Log.e(TAG, "poster:" + GsonUtils.toJson(poster));
            Pigeon pigeon = Pigeon.newBuilder(RemoteService.this).setAuthority(MainProcessApi.class).build();
            pigeon.create(MainProcessService.class).login("test", "test");
            return 11;
        }
    };

    @route(value = "/words")
    public void queryWords(Bundle in, Bundle out) {
        Log.e(TAG, "IPC by route, parameter name:" + in.getString("name") + " calling package:" + in.getString("key_calling_package"));
        out.putShort("test", (short) 1);
    }

    @route(value = "/hello")
    public void queryWords() {
        Log.e(TAG, "IPC by route,route=hello");
    }

    @route(value = "/world2")
    public void queryWords2(Bundle in) {
        Log.e(TAG, "IPC by route,route=world, calling package:" + in.getString("key_calling_package"));
    }

    @RequestLarge
    @route(value = "/submit/bitmap")
    public void submitBitmap(String key, byte[] data, int length) {
        Log.e(TAG, "IPC by route,submitBitmap:" + key + " data length:" + data.length + " length:" + length);
    }

    @RequestLarge
    @route(value = "/submit/bitmap2")
    public int submitBitmap2(String key, byte[] data, int length) {
        Log.e(TAG, "IPC by route,submitBitmap2:" + key + " data length:" + data.length + " length:" + length);
        return 1;
    }

    @ResponseLarge
    @route(value = "/query/bitmap")
    public byte[] queryBitmap(String key) {
        Log.e(TAG, "queryBitmap:" + key);
        return new byte[(int) (1.8 * 1024 * 1024)];
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public void queryItems(int id, double score, long timestamp, short gender, float ring, byte b, boolean isABoy) {
        Log.e(TAG, "queryItems");
    }

    @Override
    public byte[] testLargeResponse(String key) {
        Log.e(TAG, "testLargeResponse:" + key);
        return new byte[1024 * 20];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ServiceManager.getInstance().unpublish(this, RemoteServiceApi.class);
        ServiceManager.getInstance().unpublish(mApi);

    }
}
