package com.example.management.ui.collect;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.base.BaseActivity;
import com.example.management.base.BaseConfig;
import com.example.management.bean.CategoryBean;
import com.example.management.bean.CollectBean;
import com.example.management.bean.UserBean;
import com.example.management.net.HttpCallback;
import com.example.management.net.HttpModel;
import com.example.management.net.HttpResult;
import com.example.management.ui.adapter.CollectAdapter;
import com.example.management.ui.adapter.CollectDetailAdapter;
import com.example.management.util.SharedPreUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends BaseActivity {

    private TextView collectSumBack;
    private TextView collectSumTitle;
    private TextView collectSumNoData;
    private ListView lvCollectSum;

    private List<CollectBean> collectList;
    private CollectDetailAdapter collectDetailAdapter;

    @Override
    protected Settings setActivitySettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initView(View contentView) {
        collectSumBack = contentView.findViewById(R.id.custom_back);
        collectSumTitle = contentView.findViewById(R.id.custom_title);
        collectSumNoData = contentView.findViewById(R.id.tv_collect_data_null);
        lvCollectSum = contentView.findViewById(R.id.lv_collect_sum);
    }

    @Override
    protected void bindEvent(View contentView) {
        lvCollectSum.setOnItemClickListener(onItemClick);
        collectSumBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        collectSumTitle.setText(getString(R.string.collect_sum_title));

        collectList = new ArrayList<>();
        collectDetailAdapter = new CollectDetailAdapter(this);
        lvCollectSum.setAdapter(collectDetailAdapter);

        getCollectSumData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_back:
                finish();
            default:
                break;
        }
    }

    private void getCollectSumData() {
        String flowName = getIntent().getStringExtra(BaseConfig.PARAM_COLLECT);
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
        String companyID = userBean.getFactoryCode();
        HttpModel.getInstance().CollectByTypeData(flowName, companyID, new HttpCallback(this) {
            @Override
            public void onSuccessStr(HttpResult httpResult) {
                Gson gson = new Gson();
                collectList = gson.fromJson(gson.toJson(httpResult.getResult()), new TypeToken<List<CollectBean>>() {
                }.getType());
                collectDetailAdapter.setData(collectList);
                collectDetailAdapter.notifyDataSetChanged();
            }
        });
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CollectDetail(collectList.get(position).getID());
        }
    };

    private void CollectDetail(String ID) {
        Intent intent = new Intent(this, CollectDetailActivity.class);
        intent.putExtra(BaseConfig.PARAM_COLLECT_DETAIL, ID);
        startActivity(intent);
    }
}