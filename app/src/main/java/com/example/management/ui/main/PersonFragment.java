package com.example.management.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.base.BaseConfig;
import com.example.management.base.BaseFragment;
import com.example.management.bean.UserBean;
import com.example.management.ui.login.LoginActivity;
import com.example.management.util.AlertDialogUtil;
import com.example.management.util.SharedPreUtil;
import com.google.gson.Gson;

public class PersonFragment extends BaseFragment {

    private TextView personBack;
    private TextView personTitle;
    private TextView personNo;
    private TextView personName;
    private TextView personSex;
    private TextView personPhone;
    private TextView personEmail;
    private TextView personRole;
    private TextView personTeamName;
    private TextView personContent;
    private Button loginOut;

    private AlertDialogUtil alertDialog;

    public static PersonFragment newInstance() {
        Bundle args = new Bundle();
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Settings setFragmentSettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initView(View contentView) {
        personBack = contentView.findViewById(R.id.custom_back);
        personTitle = contentView.findViewById(R.id.custom_title);
        personNo = contentView.findViewById(R.id.tv_person_no);
        personName = contentView.findViewById(R.id.tv_person_name);
        personSex = contentView.findViewById(R.id.tv_person_sex);
        personPhone = contentView.findViewById(R.id.tv_person_phone);
        personEmail = contentView.findViewById(R.id.tv_person_email);
        personRole = contentView.findViewById(R.id.tv_person_role);
        personTeamName = contentView.findViewById(R.id.tv_person_company);
        personContent = contentView.findViewById(R.id.tv_person_content);
        loginOut = contentView.findViewById(R.id.btn_login_out);
    }

    @Override
    protected void bindEvent(View mContentView) {
        loginOut.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        personTitle.setText(getString(R.string.person_title));
        personBack.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_out:
                alertDialog = new AlertDialogUtil(getContext(), getString(R.string.person_login_out_hint), 0,
                        false, BaseConfig.REQUEST_CODE_FIRST, onDialogClick);
                alertDialog.show();
                break;
            default:
                break;
        }
    }

    public void setPersonData() {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
        if (!TextUtils.isEmpty(userBean.getUserCode())) {
            personNo.setText(userBean.getUserCode());
        }
        if (!TextUtils.isEmpty(userBean.getUserName())) {
            personName.setText(userBean.getUserName());
        }
        if (!TextUtils.isEmpty(userBean.getSex())) {
            personSex.setText(userBean.getSex());
        }
        if (!TextUtils.isEmpty(userBean.getTelNo())) {
            personPhone.setText(userBean.getTelNo());
        }
        if (!TextUtils.isEmpty(userBean.getEmail())) {
            personEmail.setText(userBean.getEmail());
        }
        if (!TextUtils.isEmpty(userBean.getRole())) {
            personRole.setText(userBean.getRole());
        }
        if (!TextUtils.isEmpty(userBean.getTeamName())) {
            personTeamName.setText(userBean.getTeamName());
        }
        if (!TextUtils.isEmpty(userBean.getRemark())) {
            personContent.setText(userBean.getRemark());
        }
    }

    AlertDialogUtil.OnDialogClickListener onDialogClick = new AlertDialogUtil.OnDialogClickListener() {
        @Override
        public void onDialogClick(int requestCode, boolean isPositive) {
            if (isPositive) {
                switch (requestCode) {
                    case BaseConfig.REQUEST_CODE_FIRST:
                        LoginOut();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private void LoginOut() {
        SharedPreUtil.saveUserInfo("");
        SharedPreUtil.saveLogin(false);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
