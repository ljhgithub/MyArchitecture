package com.ljh.www.myarchitecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ljh.www.myarchitecture.ui.activity.RetrofitActivity;
import com.ljh.www.myarchitecture.ui.activity.RxJavaActivity;
import com.ljh.www.myarchitecture.view.BooksActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_retrofit).setOnClickListener(this);
        findViewById(R.id.btn_rxjava).setOnClickListener(this);
        findViewById(R.id.btn_binding_data).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_retrofit:
                RetrofitActivity.start(this);
                break;
            case R.id.btn_rxjava:
                RxJavaActivity.start(this);

                break;
            case R.id.btn_binding_data:
                BooksActivity.start(this);
                break;
            default:
                break;
        }
    }

}
