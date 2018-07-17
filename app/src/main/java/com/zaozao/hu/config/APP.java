package com.zaozao.hu.config;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

/**
 * Created by 胡章孝
 * Date:2018/7/9
 * Describle:
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(getApplicationContext(), Constants.APP_KEY, "", UMConfigure.DEVICE_TYPE_PHONE, null);
    }
}
