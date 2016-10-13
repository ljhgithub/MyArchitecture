package com.ljh.www.myarchitecture.common.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.ljh.www.myarchitecture.BaseFragment;
import com.ljh.www.myarchitecture.R;
import com.ljh.www.myarchitecture.util.log.LogUtils;
import com.ljh.www.myarchitecture.util.sys.ReflectionUtil;

/**
 * Created by ljh on 2016/5/16.
 */
public class BaseActivity extends AppCompatActivity {
    private boolean destroyed = false;
    private static Handler handler;
    private static final String TAG = LogUtils.makeLogTag(BaseActivity.class.getSimpleName());
    protected TextView tvBarLeft;
    protected TextView tvBarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.LOGD(TAG, "activity:  " + getClass().getName() + "     onCreate");


    }

    public void initToolbar() {
        tvBarLeft = (TextView) findViewById(R.id.tv_bar_left);
        tvBarTitle = (TextView) findViewById(R.id.tv_bar_title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.LOGD(TAG, "activity:  " + getClass().getName() + "    onDestroy");
        destroyed = true;
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            if (null == getCurrentFocus()) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (null != getCurrentFocus()) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }

    /**
     * 延时弹出键盘
     *
     * @param focus ：键盘的焦点项
     */
    protected void showKeyboardDelayed(View focus) {
        final View viewToFocus = focus;
        if (null != focus) {
            viewToFocus.requestFocus();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null == viewToFocus || viewToFocus.isFocused()) {
                    showKeyboard(true);
                }
            }
        }, 200);
    }

    /**
     * 判断页面是否已经被销毁（异步回调时使用）
     */
    protected boolean isDestroyedCompatible() {
        if (Build.VERSION.SDK_INT >= 17) {
            return isDestroyedCompatible17();
        } else {
            return destroyed || super.isFinishing();
        }
    }

    @TargetApi(17)
    private boolean isDestroyedCompatible17() {
        return super.isDestroyed();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void invokeFragmentManagerNoteStateNotSaved() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ReflectionUtil.invokeMethod(getFragmentManager(), "noteStateNotSaved", null);
        }
    }

    protected void switchFragmentContent(BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(fragment.getContainerId(), fragment);
        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }
}
