package com.ljh.www.myarchitecture.data.source.remote;

import android.text.TextUtils;

import com.ljh.www.myarchitecture.data.net.HttpServiceManager;
import com.ljh.www.myarchitecture.data.source.DataCallback;
import com.ljh.www.myarchitecture.data.source.GithubDataSource;
import com.ljh.www.myarchitecture.model.Github;
import com.ljh.www.myarchitecture.util.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by ljh on 2016/10/12.
 */

public class RemoteGithubDataSource implements GithubDataSource {

    @Override
    public void githubList(final DataCallback<List<Github>> callback) {
        final String[] result = {"-1", ""};
        HttpServiceManager.getInstance().github()
                .flatMap(new Func1<ResponseBody, Observable<List<Github>>>() {
                    @Override
                    public Observable<List<Github>> call(ResponseBody responseBody) {
                        final ResponseBody rb = responseBody;
                        return Observable.create(new Observable.OnSubscribe<List<Github>>() {
                            @Override
                            public void call(Subscriber<? super List<Github>> subscriber) {
                                try {
                                    String content = rb.string();
                                    List<Github> githubs = new ArrayList<Github>();
                                    subscriber.onNext(githubs);
                                    subscriber.onCompleted();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    result[0] = "-2";
                                    result[1] = "解析数据错误";
                                    subscriber.onError(e);
                                }

                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Github>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        int c = Integer.valueOf(result[0]);
                        String m = result[1];
                        callback.onError(c, TextUtils.isEmpty(m) ? e.getMessage() : m);
                        result[0] = "-1";
                        result[1] = "";
                    }

                    @Override
                    public void onNext(List<Github> githubs) {
                        callback.onSuccess(githubs);
                    }
                });
    }
}
