package com.ljh.www.myarchitecture;

import android.app.Application;

import com.ljh.www.myarchitecture.util.UIUtils;

/**
 * Created by ljh on 2016/4/25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.init(getApplicationContext());
    }
}
