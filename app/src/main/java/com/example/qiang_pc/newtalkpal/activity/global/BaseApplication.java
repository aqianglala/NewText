package com.example.qiang_pc.newtalkpal.activity.global;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2016/1/26.
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        OkHttpUtils instance = OkHttpUtils.getInstance();
        instance.setConnectTimeout(10000, TimeUnit.MILLISECONDS);

    }
}
