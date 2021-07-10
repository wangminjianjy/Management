package com.example.management.ui.person;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.base.BaseActivity;
import com.example.management.bean.CollectBean;
import com.example.management.bean.UserBean;
import com.example.management.net.HttpCallback;
import com.example.management.net.HttpModel;
import com.example.management.net.HttpResult;
import com.example.management.ui.login.LoginActivity;
import com.example.management.util.SharedPreUtil;
import com.example.management.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;

public class PwdActivity extends BaseActivity {

    private TextView pwdBack;
    private TextView pwdTitle;
    private TextView pwdOld;
    private TextView pwdNew;
    private TextView pwdConfirm;
    private Button pwdSubmit;

    @Override
    protected Settings setActivitySettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_pwd;
    }

    @Override
    protected void initView(View contentView) {
        pwdBack = contentView.findViewById(R.id.custom_back);
        pwdTitle = contentView.findViewById(R.id.custom_title);
        pwdOld = contentView.findViewById(R.id.et_pwd_old);
        pwdNew = contentView.findViewById(R.id.et_pwd_new);
        pwdConfirm = contentView.findViewById(R.id.et_pwd_confirm);
        pwdSubmit = contentView.findViewById(R.id.btn_pwd_submit);
    }

    @Override
    protected void bindEvent(View contentView) {
        pwdBack.setOnClickListener(this);
        pwdSubmit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        pwdTitle.setText(getString(R.string.pwd_title));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_back:
                finish();
                break;
            case R.id.btn_pwd_submit:
                ChangePwd();
                break;
            default:
                break;
        }
    }

    private void ChangePwd() {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
        String userID = userBean.getUserId();
        String pwd = userBean.getUserPwd();
        String pwdOldStr = pwdOld.getText().toString();
        String pwdNewStr = pwdNew.getText().toString();
        String pwdConfirmStr = pwdConfirm.getText().toString();
        if (TextUtils.isEmpty(pwdOldStr)) {
            ToastUtil.showSingleToast(R.string.pwd_old_hint, Toast.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(pwdNewStr)) {
            ToastUtil.showSingleToast(R.string.pwd_new_hint, Toast.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(pwdConfirmStr)) {
            ToastUtil.showSingleToast(R.string.pwd_confirm_hint, Toast.LENGTH_SHORT);
        } else if (!(pwd.equals(pwdOldStr))) {
            ToastUtil.showSingleToast(R.string.pwd_old_error, Toast.LENGTH_SHORT);
        } else if (!(pwdNewStr.equals(pwdConfirmStr))) {
            ToastUtil.showSingleToast(R.string.pwd_new_error, Toast.LENGTH_SHORT);
        } else {
            HttpModel.getInstance().ChangePwd(userID, pwdOldStr, pwdNewStr, pwdConfirmStr, new HttpCallback(this) {
                @Override
                public void onSuccessStr(HttpResult httpResult) {
                    ToastUtil.showSingleToast(httpResult.getResultMsg(), Toast.LENGTH_SHORT);
                    LoginOut();
                }
            });
        }
    }

    private void LoginOut() {
        SharedPreUtil.saveUserInfo("");
        SharedPreUtil.saveLogin(false);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}