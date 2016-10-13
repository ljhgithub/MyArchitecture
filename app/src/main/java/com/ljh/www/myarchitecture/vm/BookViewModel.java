package com.ljh.www.myarchitecture.vm;

import android.content.Context;
import android.view.View;

import com.ljh.www.myarchitecture.model.BookModel;

/**
 * Created by ljh on 2016/4/29.
 */
public class BookViewModel {
    private BookModel book;
    private Context context;

    public BookViewModel(Context context, BookModel book) {
        this.context = context;
        this.book = book;
    }

    public String getName() {
        return book.name;
    }

    public String getUrl() {
        return book.url;
    }

    public void onClickBook(View view) {
    }
}
