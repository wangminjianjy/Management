package com.example.management.base;

import android.app.Application;
import android.content.Context;

import com.example.management.util.SharedPreUtil;

/**
 * Created by wangm on 2018/11/19.
 */

public class CustomApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        SharedPreUtil.saveSerial(1);
//        RetrofitManage.getInstance().init(BaseConfig.CONFIGIP);
    }

    public static Context getContext() {
        return context;
    }
}
