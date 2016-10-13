package com.ljh.www.myarchitecture.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.ljh.www.myarchitecture.R;
import com.ljh.www.myarchitecture.common.activity.BaseActivity;
import com.ljh.www.myarchitecture.util.log.LogUtils;

/**
 * Created by ljh on 2016/5/16.
 */
public class WeatherActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initToolbar();
        tvBarLeft.setVisibility(View.VISIBLE);
        tvBarTitle.setText("天气预报");
        tvBarLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_bar_left:
                EditText editText=new EditText(this);
                LogUtils.LOGD("tag","tv_bar_left");
                LogUtils.LOGD("tag",editText.getText().toString().trim());
                break;
        }


    }
}
