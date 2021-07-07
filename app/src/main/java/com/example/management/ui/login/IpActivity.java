package com.example.management.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.base.BaseActivity;
import com.example.management.base.BaseConfig;
import com.example.management.util.SharedPreUtil;
import com.example.management.util.ToastUtil;

public class IpActivity extends BaseActivity {

    private TextView ipSetBack;
    private TextView ipSetTitle;
    private TextView ipSetAction;
    private EditText mIpEt1;
    private EditText mIpEt2;
    private EditText mIpEt3;
    private EditText mIpEt4;
    private EditText mPortEt;

    private int paramType;

    @Override
    protected Settings setActivitySettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_ip;
    }

    @Override
    protected void initView(View contentView) {
        ipSetBack = contentView.findViewById(R.id.custom_back);
        ipSetTitle = contentView.findViewById(R.id.custom_title);
        ipSetAction = contentView.findViewById(R.id.custom_action);
        mIpEt1 = contentView.findViewById(R.id.et_ip_1);
        mIpEt2 = contentView.findViewById(R.id.et_ip_2);
        mIpEt3 = contentView.findViewById(R.id.et_ip_3);
        mIpEt4 = contentView.findViewById(R.id.et_ip_4);
        mPortEt = contentView.findViewById(R.id.et_ip_port);
    }

    @Override
    protected void bindEvent(View contentView) {
        ipSetBack.setOnClickListener(this);
        ipSetAction.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        ipSetTitle.setText(getString(R.string.ip_set_title));
        ipSetAction.setText(getString(R.string.btn_submit));

        paramType = getIntent().getIntExtra(BaseConfig.PARAM_IP, BaseConfig.RETURN_RESULT_LOGIN);
        if (paramType == BaseConfig.RETURN_RESULT_SPLASH) {
            ipSetBack.setVisibility(View.GONE);
        }

        String ip = SharedPreUtil.getIp();
        if (!TextUtils.isEmpty(ip)) {
            String[] ipArray = ip.split("\\.");
            mIpEt1.setText(ipArray[0]);
            mIpEt2.setText(ipArray[1]);
            mIpEt3.setText(ipArray[2]);
            String[] ipPort = ipArray[3].split(":");
            mIpEt4.setText(ipPort[0]);
            mPortEt.setText(ipPort[1]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_back:
                finish();
                break;
            case R.id.custom_action:
                setIP();
                break;
            default:
                break;
        }
    }

    private void setIP() {
        if (TextUtils.isEmpty(mIpEt1.getText()) || TextUtils.isEmpty(mIpEt2.getText())
                || TextUtils.isEmpty(mIpEt3.getText()) || TextUtils.isEmpty(mIpEt4.getText())
                || TextUtils.isEmpty(mPortEt.getText())) {
            ToastUtil.showSingleToast(getString(R.string.ip_set_error), Toast.LENGTH_SHORT);
            return;
        }
        String ip = mIpEt1.getText().toString().concat(".")
                .concat(mIpEt2.getText().toString()).concat(".")
                .concat(mIpEt3.getText().toString()).concat(".")
                .concat(mIpEt4.getText().toString()).concat(":")
                .concat(mPortEt.getText().toString());
        SharedPreUtil.saveIp(ip);
        if (paramType == BaseConfig.RETURN_RESULT_SPLASH) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Intent intent = new Intent();
            intent.putExtra(BaseConfig.PARAM_CONFIG_IP, ip);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
