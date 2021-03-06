package com.flyingpigeon.sample;

import android.os.Bundle;
import android.util.Log;

import com.flyingpigeon.library.ServiceContentProvider;
import com.flyingpigeon.library.annotations.route;

import java.util.ArrayList;

import static com.flyingpigeon.library.Config.PREFIX;

/**
 * @author xiaozhongcen
 * @date 20-6-10
 * @since 1.0.0
 */
public class ServiceApiImpl extends ServiceContentProvider implements IServiceApi {
    private static final String TAG = PREFIX + ServiceApiImpl.class.getSimpleName();

    @Override
    public boolean onCreate() {
        return super.onCreate();
    }

    @Override
    public void queryTest(int id) {
        Log.e(TAG, "queryTest:" + id);
    }

    @Override
    public void queryItems(int id, double score, long idcard, short gender, float ring, byte b, boolean isABoy) {
        Log.e(TAG, "queryItems method call id:" + id + " score:" + score + " idcard:" + idcard + " gender:" + gender + " ring:" + ring + " b:" + b + " isABoy:" + isABoy);
    }

    @Override
    public void submitInformation(String uuid, int hash, Information information) {
        Log.e(TAG, "Information:" + GsonUtils.toJson(information) + " uuid:" + uuid + " hash:" + hash);
    }

    @Override
    public int createPoster(Poster poster) {
        Log.e(TAG, "poster:" + GsonUtils.toJson(poster));
        return 1999;
    }

    @Override
    public Poster queryPoster(String posterId) {
        Poster poster = new Poster("Justson", "just", 119, 11111000L, (short) 23, 1.15646F, 'h', (byte) 4, 123456.415D);
        return poster;
    }

    @Override
    public double testDouble() {
        return 1.1D;
    }

    @Override
    public long testLong() {
        return 512313L;
    }

    @Override
    public short testShort() {
        return 12;
    }

    @Override
    public float testFloat() {
        return 1.001F;
    }

    @Override
    public byte testByte() {
        return 9;
    }

    @Override
    public boolean testBoolean() {
        return true;
    }

    @Override
    public Information testParcelable() {
        Information information = new Information("Justson", "just", 110, (short) 1, 'c', 1.22F, (byte) 14, 8989123.111D, 100000L);
        return information;
    }

    @Override
    public String testLargeBlock(String param, byte[] data) {
        Log.e(TAG, "testLargeBlock , param:" + param + " data:" + data.length);
        Information information = new Information("Justson", "just", 110, (short) 1, 'c', 1.22F, (byte) 14, 8989123.111D, 100000L);
        return GsonUtils.toJson(information);
    }

    int testSize = 1024 * 20;

    @Override
    public byte[] testLargeBlock(String param, int hash) {
        return new byte[testSize];
    }

    @route(value = "/words")
    public void queryWords(Bundle in, Bundle out) {
        Log.e(TAG, "IPC by route");
    }

    @Override
    public void testArrayList(ArrayList<String> items) {
        Log.e(TAG, "testArrayList:" + GsonUtils.toJson(items));
    }
}
