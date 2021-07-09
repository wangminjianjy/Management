package com.example.management.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.base.BaseConfig;
import com.example.management.base.BaseFragment;
import com.example.management.bean.CategoryBean;
import com.example.management.bean.UserBean;
import com.example.management.net.HttpCallback;
import com.example.management.net.HttpModel;
import com.example.management.net.HttpResult;
import com.example.management.ui.adapter.CategoryAdapter;
import com.example.management.ui.adapter.CollectAdapter;
import com.example.management.ui.collect.CollectActivity;
import com.example.management.util.SharedPreUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CollectFragment extends BaseFragment {

    private TextView collectBack;
    private TextView collectTitle;
    private TextView collectWeigh;
    private ListView lvCollectData;

    private List<CategoryBean> categoryList;
    private CollectAdapter collectAdapter;

    public static CollectFragment newInstance() {
        Bundle args = new Bundle();
        CollectFragment fragment = new CollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Settings setFragmentSettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initView(View contentView) {
        collectBack = contentView.findViewById(R.id.custom_back);
        collectTitle = contentView.findViewById(R.id.custom_title);
        collectWeigh = contentView.findViewById(R.id.collect_weigh);
        lvCollectData = contentView.findViewById(R.id.lv_collect_data);
    }

    @Override
    protected void bindEvent(View mContentView) {
        lvCollectData.setOnItemClickListener(onItemClick);
    }

    @Override
    protected void initData() {
        collectTitle.setText(getString(R.string.collect_title));
        collectBack.setVisibility(View.GONE);

        categoryList = new ArrayList<>();
        collectAdapter = new CollectAdapter(getContext());
        lvCollectData.setAdapter(collectAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.img_collect_sale:
//                Collect(1);
//                break;
//            case R.id.img_collect_purchase:
//                Collect(2);
//                break;
            default:
                break;
        }
    }

    public void getCollectData() {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
        String companyID = userBean.getFactoryCode();
        HttpModel.getInstance().CollectData(companyID, new HttpCallback(getActivity()) {
            @Override
            public void onSuccessStr(HttpResult httpResult) {
                collectWeigh.setText(String.valueOf(httpResult.getResult()));
                Gson gson = new Gson();
                categoryList = gson.fromJson(gson.toJson(httpResult.getResult2()), new TypeToken<List<CategoryBean>>() {
                }.getType());
                collectAdapter.setData(categoryList);
                collectAdapter.notifyDataSetChanged();
            }
        });
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Collect(categoryList.get(position).getCategoryName());
        }
    };

    private void Collect(String type) {
        Intent intent = new Intent(getActivity(), CollectActivity.class);
        intent.putExtra(BaseConfig.PARAM_COLLECT, type);
        startActivity(intent);
    }
}
