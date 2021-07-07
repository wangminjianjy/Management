package com.example.management.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.example.management.R;
import com.example.management.util.ProgressUtil;

/**
 * Created by wangm on 2018/11/19.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected View contentView;
    protected Toolbar toolBar;
    protected boolean isForeground;
    protected Settings activitySettings;
    private ProgressUtil progressUtil;

    // 设置当前activity的一些设置或组件
    protected abstract Settings setActivitySettings(View contentView);

    // 设置布局
    protected abstract int setLayoutResourceID();

    // 初始化视图
    protected abstract void initView(View contentView);

    // 绑定事件
    protected abstract void bindEvent(View contentView);

    // 初始化数据
    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentView = LayoutInflater.from(this).inflate(setLayoutResourceID(), null);
        fixSettings();
        setContentView(contentView);
        initView(contentView);
        bindEvent(contentView);
        initData();
    }

    private void fixSettings() {
        activitySettings = setActivitySettings(contentView);
        if (activitySettings != null) {
            if (activitySettings.isOrientationFix) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            }

            if (activitySettings.toolbar != null) {
                toolBar = activitySettings.toolbar;
                if (toolBar != null) {
                    setSupportActionBar(toolBar);
                }
            }

            if (activitySettings.isDeepStatusBar) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contentView = null;
    }

    protected void startActivity(Class activity, boolean finish) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    protected void showNetProgress(boolean isCancel) {
        if (!isFinishing()) {
            if (progressUtil == null) {
                progressUtil = new ProgressUtil();
            }
            progressUtil.showProgress(this, getString(R.string.hint_waiting), isCancel);
        }
    }

    public void dismissNetDialog() {
        if (!isFinishing()) {
            if (progressUtil != null) {
                progressUtil.hideProgress(this);
            }
        } else {
            progressUtil = null;
        }
    }

    // 将 Fragment添加到Activity
    protected void replaceFragmentToActivity(@NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public boolean isForeground() {
        return isForeground;
    }

    public Toolbar getToolBar() {
        return toolBar;
    }

    public class Settings {
        public static final int GROUP_DEFAULT = 1000;

        public boolean isOrientationFix = true;//是否固定不转屏
        public boolean isDeepStatusBar = false;//是否深色状态栏
        public int activityGroup = GROUP_DEFAULT;//activity组，用于标识不同组的activity
        public Toolbar toolbar;

        /**
         * 设置toolbar
         *
         * @param toolbar
         */
        public void setToolbar(Toolbar toolbar) {
            this.toolbar = toolbar;
        }

        /**
         * 设置不能横屏
         */
        public void setOrientationFix(boolean isFix) {
            isOrientationFix = isFix;
        }

        /**
         * 设置是否深色状态栏
         *
         * @param deepStatusBar
         */
        public void setDeepStatusBar(boolean deepStatusBar) {
            isDeepStatusBar = deepStatusBar;
        }
    }
}
