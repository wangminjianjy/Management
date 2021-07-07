package com.example.management.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.example.management.R;
import com.maning.mndialoglibrary.MProgressDialog;

/**
 * Created by wangminjian on 2018/8/8.
 */

public class ProgressUtil {

    private MProgressDialog mProgressDialog;

    public ProgressUtil() {

    }

    public void showProgress(Context context, String msg, boolean cancelFlag) {
        if (mProgressDialog == null) {
            int color = Color.TRANSPARENT;
            int bgColor = context.getResources().getColor(R.color.colorTextGray);
            mProgressDialog = new MProgressDialog.Builder(context)
                    //点击外部是否可以取消
                    .isCanceledOnTouchOutside(cancelFlag)
                    .setBackgroundWindowColor(color)
                    .setBackgroundViewColor(bgColor)
                    .build();
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show(msg);
        }
    }

    public void hideProgress(Context context) {
        if (context != null && !((Activity) context).isDestroyed() && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
