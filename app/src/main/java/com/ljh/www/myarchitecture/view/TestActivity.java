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
import com.ljh.www.myarchitecture.view.adapter.BookAdapter;
import com.ljh.www.myarchitecture.vm.UserViewModel;

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
        llm=new LinearLayoutManager(this);
        binding.setTitle("Books");
        rvBook=binding.rvBook;
        rvBook.setLayoutManager(llm);
        rvBook.setHasFixedSize(true);
        bookAdapter=new BookAdapter(this);
        rvBook.setAdapter(bookAdapter);


    }

}
