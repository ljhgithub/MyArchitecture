package com.ljh.www.myarchitecture.vm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.ljh.www.myarchitecture.view.adapter.BookAdapter;

/**
 * Created by ljh on 2016/4/28.
 */
public class UserViewModel {
    public final ObservableField<String> firstName = new ObservableField<>();

    public final ObservableField<String> lastName = new ObservableField<>();


}
