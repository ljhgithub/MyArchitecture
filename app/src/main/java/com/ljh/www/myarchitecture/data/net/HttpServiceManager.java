package com.ljh.www.myarchitecture.data.net;

import com.ljh.www.myarchitecture.http.RetrofitProvider;
import com.ljh.www.myarchitecture.util.log.LogUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ljh on 2016/9/21.
 */

public class HttpServiceManager {
    private HttpService httpService;

    private static String TAG = LogUtils.makeLogTag(HttpServiceManager.class.getSimpleName());

    public static HttpServiceManager getInstance() {
        return InstanceHolder.instance;
    }

    private HttpServiceManager() {
        httpService = RetrofitProvider.getRetrofitRxJava().create(HttpService.class);
    }

    private static class InstanceHolder {
        final static HttpServiceManager instance = new HttpServiceManager();
    }

    public Observable<ResponseBody> github() {
        return httpService.github()
                .subscribeOn(Schedulers.io());

    }
}
