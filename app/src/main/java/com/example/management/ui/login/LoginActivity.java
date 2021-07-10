package com.example.management.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.base.BaseActivity;
import com.example.management.base.BaseConfig;
import com.example.management.bean.UserBean;
import com.example.management.net.HttpCallback;
import com.example.management.net.HttpModel;
import com.example.management.net.HttpResult;
import com.example.management.ui.main.MainActivity;
import com.example.management.util.SharedPreUtil;
import com.example.management.util.ToastUtil;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity {

    private LinearLayout tvLoginIP;
    private EditText etUserName;
    private EditText etPwd;
    private Button btnLogin;
    private CheckBox cbPwdSee;
    private CheckBox cbRemember;

    private String userName;
    private String userPwd;

    @Override
    protected Settings setActivitySettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(View contentView) {
        tvLoginIP = contentView.findViewById(R.id.tv_login_logo);
        etUserName = contentView.findViewById(R.id.et_login_username);
        etPwd = contentView.findViewById(R.id.et_login_pwd);
        btnLogin = contentView.findViewById(R.id.btn_login);
        cbPwdSee = contentView.findViewById(R.id.cb_login_pwd_see);
        cbRemember = contentView.findViewById(R.id.cb_login_remember);
        cbPwdSee.setChecked(false);
        cbRemember.setChecked(true);
    }

    @Override
    protected void bindEvent(View contentView) {
        cbPwdSee.setOnCheckedChangeListener(onCheckedChange);
        cbRemember.setOnCheckedChangeListener(onCheckedChange);
        tvLoginIP.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_logo:
                Intent intent = new Intent(this, IpActivity.class);
                intent.putExtra(BaseConfig.PARAM_IP, BaseConfig.RETURN_RESULT_LOGIN);
                startActivityForResult(intent, BaseConfig.PARAM_LOGIN_IP);
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(etUserName.getText())) {
                    ToastUtil.showSingleToast(R.string.login_name_hint, Toast.LENGTH_SHORT);
                } else if (TextUtils.isEmpty(etPwd.getText())) {
                    ToastUtil.showSingleToast(R.string.login_pwd_hint, Toast.LENGTH_SHORT);
                } else {
                    loginMethod();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    CompoundButton.OnCheckedChangeListener onCheckedChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.cb_login_pwd_see:
                    if (isChecked) {
                        etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        etPwd.setSelection(etPwd.getText().length());
                    } else {
                        etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        etPwd.setSelection(etPwd.getText().length());
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void loginMethod() {
        userName = etUserName.getText().toString();
        userPwd = etPwd.getText().toString();
        HttpModel.getInstance().LoginIn(userName, userPwd, new HttpCallback(this) {
            @Override
            public void onSuccessStr(HttpResult httpResult) {
                if (cbRemember.isChecked()) {
                    SharedPreUtil.saveLogin(true);
                }
                UserBean userBean = new UserBean();
                userBean.setUserId(httpResult.getResult().toString());
                userBean.setUserName(httpResult.getResult2().toString());
                userBean.setUserPwd(userPwd);
                userBean.setFactoryCode(httpResult.getResult4().toString());
                userBean.setFactoryName(httpResult.getResult3().toString());
                SharedPreUtil.saveUserInfo(new Gson().toJson(userBean));
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
}
