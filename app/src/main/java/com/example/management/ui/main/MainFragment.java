package com.example.management.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.base.BaseFragment;
import com.example.management.bean.CategoryBean;
import com.example.management.bean.MainBean;
import com.example.management.bean.UserBean;
import com.example.management.net.HttpCallback;
import com.example.management.net.HttpModel;
import com.example.management.net.HttpResult;
import com.example.management.ui.adapter.CategoryAdapter;
import com.example.management.ui.login.LoginActivity;
import com.example.management.util.SharedPreUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {

    private TextView mainBack;
    private TextView mainTitle;
    private TextView mainCompany;
    private TextView mainWeigh;
    private TextView mainWeighDay;
    private TextView mainWeighMonth;
    private ListView lvMainData;

    private List<CategoryBean> categoryList;
    private CategoryAdapter categoryAdapter;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Settings setFragmentSettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View contentView) {
        mainBack = contentView.findViewById(R.id.custom_back);
        mainTitle = contentView.findViewById(R.id.custom_title);
        mainCompany = contentView.findViewById(R.id.main_company);
        mainWeigh = contentView.findViewById(R.id.main_weigh);
        mainWeighDay = contentView.findViewById(R.id.main_weigh_day);
        mainWeighMonth = contentView.findViewById(R.id.main_weigh_month);
        lvMainData = contentView.findViewById(R.id.lv_main_data);
    }

    @Override
    protected void bindEvent(View mContentView) {

    }

    @Override
    protected void initData() {
        mainTitle.setText(getString(R.string.main_title));
        mainBack.setVisibility(View.GONE);

        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext());
        lvMainData.setAdapter(categoryAdapter);

        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
        mainCompany.setText(userBean.getFactoryName());
//        getMainData(companyID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.custom_back:
//                break;
            default:
                break;
        }
    }

    public void getMainData() {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
        String companyID = userBean.getFactoryCode();
        HttpModel.getInstance().MainData(companyID, new HttpCallback(getActivity()) {
            @Override
            public void onSuccessStr(HttpResult httpResult) {
                Gson gson = new Gson();
                MainBean mainBean = gson.fromJson(gson.toJson(httpResult.getResult()), MainBean.class);
                mainWeigh.setText(String.valueOf(mainBean.getCumulativeCount()));
                mainWeighDay.setText(String.valueOf(mainBean.getTodayCount()));
                mainWeighMonth.setText(String.valueOf(mainBean.getThisMonthCount()));
                categoryList = mainBean.getCategoryStatistics();
                categoryAdapter.setData(categoryList);
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }
}
