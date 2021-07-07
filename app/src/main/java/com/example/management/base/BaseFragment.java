package com.example.management.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.management.R;
import com.example.management.util.ProgressUtil;

/**
 * Created by wangm on 2018/11/19.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected View contentView;
    protected Toolbar toolBar;
    protected Settings fragmentSettings;

    private ProgressUtil progressUtil;

    protected abstract Settings setFragmentSettings(View contentView);

    protected abstract int setLayoutResourceID();

    protected abstract void initView(View contentView);

    protected abstract void bindEvent(View mContentView);

    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(setLayoutResourceID(), container, false);
        fixSettings();
        initView(contentView);
        bindEvent(contentView);
        initData();
        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void fixSettings() {
        fragmentSettings = setFragmentSettings(contentView);
        if (fragmentSettings != null) {
            if (fragmentSettings.enableToolbar) {
                toolBar = fragmentSettings.toolbar;
                if (toolBar != null) {
                    ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
                }
            }

            if (fragmentSettings.isDeepStatusBar) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
            if (fragmentSettings.isFarAwayStatusBar) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    contentView.setPadding(contentView.getPaddingLeft(), WindowUtil.getStatusBarHeight(getContext()),
//                            contentView.getPaddingRight(), contentView.getPaddingBottom());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    protected void showNetProgress(boolean isCancel) {
        Activity activity = getActivity();
        if (!getActivity().isFinishing()) {
            if (progressUtil == null) {
                progressUtil = new ProgressUtil();
            }
            progressUtil.showProgress(getContext(), getString(R.string.hint_waiting), isCancel);
        }
    }

    public void dismissNetDialog() {
        if (!getActivity().isFinishing()) {
            if (progressUtil != null) {
                progressUtil.hideProgress(getContext());
            }
        } else {
            progressUtil = null;
        }
    }

    public class Settings {
        private boolean enableToolbar = false;
        private Toolbar toolbar;
        public boolean isDeepStatusBar = false;//是否深色状态栏
        public boolean isFarAwayStatusBar = false;//是否paddingTop状态栏,LOLLIPOP 版本上有效

        public void setToolbar(Toolbar toolbar) {
            this.enableToolbar = true;
            this.toolbar = toolbar;
        }

        /**
         * 设置是否深色状态栏
         *
         * @param deepStatusBar
         */
        public void setDeepStatusBar(boolean deepStatusBar) {
            isDeepStatusBar = deepStatusBar;
        }

        /**
         * 当状态栏透明时，是否距离状态栏有padding top
         *
         * @param isFarAwayStatusBar
         */
        public void setFarAwayStatusBar(boolean isFarAwayStatusBar) {
            this.isFarAwayStatusBar = isFarAwayStatusBar;
        }
    }
}
