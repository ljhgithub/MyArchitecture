package com.ljh.www.myarchitecture.data.source;

/**
 * Created by ljh on 2016/10/13.
 */

public interface DataCallback<T> {
    public void onError(int code,String msg);
    public void onSuccess(T t);
}
