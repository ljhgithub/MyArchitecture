package com.ljh.www.myarchitecture.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.ljh.www.myarchitecture.R;
import com.ljh.www.myarchitecture.databinding.ActivityTestBinding;
import com.ljh.www.myarchitecture.model.BookModel;
import com.ljh.www.myarchitecture.view.adapter.BookAdapter;
import com.ljh.www.myarchitecture.vm.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private RecyclerView rvBook;
    private LinearLayoutManager llm;
    private BookAdapter bookAdapter;

    public static void start(AppCompatActivity appCompatActivity) {
        Intent it = new Intent(appCompatActivity, TestActivity.class);
        appCompatActivity.startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
//        binding=DataBindingUtil.inflate(getLayoutInflater(),R.layout.activity_test,null,false);
        llm = new LinearLayoutManager(this);
        binding.setTitle("Books");
        rvBook = binding.rvBook;
        rvBook.setLayoutManager(llm);
        rvBook.setHasFixedSize(true);
        bookAdapter = new BookAdapter(this);
        List<BookModel> bookModels = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BookModel bookModel=new BookModel("name" + i, "url" + i);
            bookModel.url="https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=4046719105,2434242014&fm=58";
            if (i%2==0){
                bookModel.url="https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1816610462,1343069403&fm=58";
            }
            bookModels.add(bookModel);
        }
        bookAdapter.setBooks(bookModels);
        rvBook.setAdapter(bookAdapter);


    }

}
