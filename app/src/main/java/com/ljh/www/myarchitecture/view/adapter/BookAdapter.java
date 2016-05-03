package com.ljh.www.myarchitecture.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljh.www.myarchitecture.R;
import com.ljh.www.myarchitecture.databinding.ItemBookBinding;
import com.ljh.www.myarchitecture.model.BookModel;
import com.ljh.www.myarchitecture.vm.BookViewModel;

import java.util.List;


/**
 * Created by ljh on 2016/4/28.
 */
public class BookAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mInflate;
    private Context context;
    private List<BookModel> books;


    public BookAdapter(Context context) {
        this.context = context;
        mInflate = LayoutInflater.from(context);
    }

    public void setBooks(List<BookModel> books){
        this.books=books;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBookBinding bookBinding = DataBindingUtil.inflate(mInflate, R.layout.item_book, parent, false);
        BookViewHolder bh = new BookViewHolder(bookBinding.getRoot());
        bh.setBookBinding(bookBinding);
        return bh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookViewHolder bookViewHolder = (BookViewHolder) holder;
        ItemBookBinding itemBookBinding = bookViewHolder.bookBinding;
        itemBookBinding.setBook(new BookViewModel(context, books.get(position)));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {

        private ItemBookBinding bookBinding;

        public ItemBookBinding getBookBinding() {
            return bookBinding;
        }

        public void setBookBinding(ItemBookBinding bookBinding) {
            this.bookBinding = bookBinding;
        }

        public BookViewHolder(View itemView) {

            super(itemView);
        }
    }
}
