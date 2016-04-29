package com.ljh.www.myarchitecture.util;

import android.content.Context;

/**
 * Created by ljh on 2016/4/25.
 */
public class UIUtils {
    public static Context context;
    public static void init(Context ctx){
        context=ctx;
    }
    public static Context getContext(){
        return context;
    }
}
