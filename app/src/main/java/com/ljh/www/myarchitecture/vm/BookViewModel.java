package com.ljh.www.myarchitecture.vm;

import android.databinding.ObservableField;

import com.ljh.www.myarchitecture.model.BookModel;

/**
 * Created by ljh on 2016/4/29.
 */
public class BookViewModel {
    public final ObservableField<BookModel> book = new ObservableField<>();
}
