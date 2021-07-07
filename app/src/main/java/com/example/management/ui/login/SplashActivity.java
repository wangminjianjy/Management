package com.example.management.ui.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.base.BaseActivity;
import com.example.management.base.BaseConfig;
import com.example.management.ui.main.MainActivity;
import com.example.management.util.SharedPreUtil;

public class SplashActivity extends BaseActivity {

    private TextView mSplashTv;

    @Override
    protected Settings setActivitySettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(View contentView) {
        mSplashTv = contentView.findViewById(R.id.tv_second);
    }

    @Override
    protected void bindEvent(View contentView) {
        mSplashTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        countDownTimer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_second:
                if (!mSplashTv.getText().equals("0s")) {
                    countDownTimer.onFinish();
                }
                break;
            default:
                break;
        }
    }

    CountDownTimer countDownTimer = new CountDownTimer(1000 * 4, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mSplashTv.setText(getString(R.string.hint_splash_time, (millisUntilFinished / 1000) + "s"));
            if (millisUntilFinished / 1000 < 1) {
                mSplashTv.setClickable(false);
            }
        }

        @Override
        public void onFinish() {
            if (TextUtils.isEmpty(SharedPreUtil.getIp())) {
                Intent intent = new Intent(SplashActivity.this, IpActivity.class);
                intent.putExtra(BaseConfig.PARAM_IP, BaseConfig.RETURN_RESULT_SPLASH);
                startActivity(intent);
            } else if (SharedPreUtil.getLogin()) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            cancel();
        }
    };
}
