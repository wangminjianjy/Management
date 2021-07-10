package com.example.management.ui.query;

import android.view.View;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.base.BaseActivity;
import com.example.management.base.BaseConfig;
import com.example.management.bean.CollectDetailBean;
import com.example.management.net.HttpCallback;
import com.example.management.net.HttpModel;
import com.example.management.net.HttpResult;
import com.google.gson.Gson;

public class QueryDetailActivity extends BaseActivity {

    private TextView detailBack;
    private TextView detailTitle;
    private TextView detailTruck;
    private TextView detailDriver;
    private TextView detailIDNumber;
    private TextView detailFlow;
    private TextView detailMaterial;
    private TextView detailNet;
    private TextView detailGross;
    private TextView detailTare;
    private TextView detailDeliver;
    private TextView detailReceive;
    private TextView detailCreate;
    private TextView detailYi;
    private TextView detailEr;

    @Override
    protected BaseActivity.Settings setActivitySettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_query_detail;
    }

    @Override
    protected void initView(View contentView) {
        detailBack = contentView.findViewById(R.id.custom_back);
        detailTitle = contentView.findViewById(R.id.custom_title);
        detailTruck = contentView.findViewById(R.id.tv_query_detail_truck);
        detailDriver = contentView.findViewById(R.id.tv_query_detail_driver);
        detailIDNumber = contentView.findViewById(R.id.tv_query_detail_number);
        detailFlow = contentView.findViewById(R.id.tv_query_detail_flow);
        detailMaterial = contentView.findViewById(R.id.tv_query_detail_material);
        detailNet = contentView.findViewById(R.id.tv_query_detail_net);
        detailGross = contentView.findViewById(R.id.tv_query_detail_gross);
        detailTare = contentView.findViewById(R.id.tv_query_detail_tare);
        detailDeliver = contentView.findViewById(R.id.tv_query_detail_deliver);
        detailReceive = contentView.findViewById(R.id.tv_query_detail_receive);
        detailCreate = contentView.findViewById(R.id.tv_query_detail_create);
        detailYi = contentView.findViewById(R.id.tv_query_detail_yi);
        detailEr = contentView.findViewById(R.id.tv_query_detail_er);
    }

    @Override
    protected void bindEvent(View contentView) {
        detailBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        detailTitle.setText(getString(R.string.query_detail_title));

        getDetailData();
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

    private void getDetailData() {
        String ID = getIntent().getStringExtra(BaseConfig.PARAM_QUERY_DETAIL);
        HttpModel.getInstance().CollectDetailData(ID, new HttpCallback(this) {
            @Override
            public void onSuccessStr(HttpResult httpResult) {
                Gson gson = new Gson();
                CollectDetailBean detailBean = gson.fromJson(gson.toJson(httpResult.getResult()), CollectDetailBean.class);
                setDetailData(detailBean);
            }
        });
    }

    private void setDetailData(CollectDetailBean detailBean) {
        detailTruck.setText(detailBean.getLicenseNumber());
        detailDriver.setText(detailBean.getDrivername());
        detailIDNumber.setText(detailBean.getDriverNumberID());
        detailFlow.setText(detailBean.getFlow());
        detailMaterial.setText(detailBean.getMaterielName());
        detailNet.setText(String.valueOf(detailBean.getNetWeight()));
        detailGross.setText(String.valueOf(detailBean.getGrossWeight()));
        detailTare.setText(String.valueOf(detailBean.getTareWeight()));
        detailDeliver.setText(detailBean.getDeliveName());
        detailReceive.setText(detailBean.getReceivingName());
        detailCreate.setText(detailBean.getCreatDate().replace("T", " ").substring(0, 19));
        detailYi.setText(detailBean.getFirstdatetime().replace("T", " ").substring(0, 19));
        detailEr.setText(detailBean.getSeconddatetime().replace("T", " ").substring(0, 19));
    }
}