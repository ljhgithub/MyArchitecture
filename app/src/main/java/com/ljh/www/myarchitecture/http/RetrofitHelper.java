package com.ljh.www.myarchitecture.http;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.ljh.www.myarchitecture.http.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ljh on 2016/4/20.
 */
public class RetrofitHelper {
    public static final String BASE_URL = "http://t.assistant.120yibao.com";
    //    public static final String API_URL = "http://10.0.0.77:8080";
    private static Retrofit retrofit;
    private static OkHttpClient client;

    public static Retrofit getRetrofit() {
        if (null == retrofit) {
            OkHttpClient client = buildClient();
            Retrofit instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return instance;
        }
        return retrofit;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        if (null == client) {
            return new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor())
                    .build();
        } else {
            return client;
        }

    }

    public static Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(buildClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getRetrofitRxJava(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(buildClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static Retrofit getRetrofitRxJava() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(buildClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
