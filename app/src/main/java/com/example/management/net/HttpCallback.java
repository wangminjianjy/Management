package com.example.management.net;

import android.app.Activity;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.net.okhttp.NetConfig;
import com.example.management.net.okhttp.callback.StringCallback;
import com.example.management.util.ProgressUtil;
import com.example.management.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description:
 * Creator: wangminjian
 * Create time: 2018/4/10.
 */

public abstract class HttpCallback implements StringCallback {

    private static final String TAG = "HttpCallback";
    protected Activity context;
    private ProgressUtil progressUtil;

    public HttpCallback(Activity context) {
        this.context = context;
    }

    public boolean isShowProgressDialog() {
        return true;
    }

    public boolean isCanceledOnTouchOutsideDialog() {
        return false;
    }

    public void onStart(Request request) {
        if (isShowProgressDialog() && context != null && !context.isFinishing()) {
            progressUtil = new ProgressUtil();
            progressUtil.showProgress(context, context.getString(R.string.hint_waiting), isCanceledOnTouchOutsideDialog());
        }
    }

    public int parseResponseCode(HttpResult httpResult) {
        int code = httpResult.getCode();
        String msg = httpResult.getResultMsg();
        ToastUtil.showSingleToast(msg, Toast.LENGTH_LONG);
        return code;
    }

    @Override
    public void onResponse(Call call, Response response, String text) {
        HttpResult httpResult = new Gson().fromJson(text, HttpResult.class);
        int code = httpResult.getCode();
        if (code == NetConfig.RESULT_STATUS_SUCCESS) {
            onSuccessStr(httpResult);
        } else {
            onErrorStr(httpResult);
        }
        onFinish();
    }

    @Override
    public void onResponseError(Call call, Response response, IOException e) {

    }

    @Override
    public void onFailure(Call call, Request request, IOException e) {
        if (progressUtil != null) {
            progressUtil.hideProgress(context);
        }
        ToastUtil.showSingleToast(context.getString(R.string.hint_net_error), Toast.LENGTH_LONG);
    }

    public abstract void onSuccessStr(HttpResult httpResult);

    public void onErrorStr(HttpResult httpResult) {
        parseResponseCode(httpResult);
    }

    public void onFinish() {
        if (progressUtil != null) {
            progressUtil.hideProgress(context);
        }
    }
}
