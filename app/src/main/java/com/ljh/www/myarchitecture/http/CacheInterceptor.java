package com.ljh.www.myarchitecture.http;

import android.os.Environment;
import android.util.Log;

import com.ljh.www.myarchitecture.util.AppUtils;
import com.ljh.www.myarchitecture.util.UIUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ljh on 2016/4/25.
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!AppUtils.isNetworkReachable(UIUtils.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        Log.d("tag","暂无网络");
        }

        Response response = chain.proceed(request);
        if (AppUtils.isNetworkReachable(UIUtils.getContext())) {
            int maxAge = 60 * 60; // read from cache for 1 minute
         response=   response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
          response=response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }









}
