package com.example.management.net;

import com.example.management.base.BaseConfig;
import com.example.management.bean.UserBean;
import com.example.management.net.okhttp.OkHttpClientManager;
import com.example.management.net.okhttp.callback.StringCallback;
import com.example.management.util.SharedPreUtil;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dzl on 2017/5/27.
 */

public class HttpModel {

    private static HttpModel mInstance;
    private OkHttpClientManager okHttpClientManager;

    public HttpModel() {
        okHttpClientManager = OkHttpClientManager.getInstance();
    }

    public static HttpModel getInstance() {
        if (mInstance == null) {
            synchronized (HttpModel.class) {
                if (mInstance == null) {
                    mInstance = new HttpModel();
                }
            }
        }
        return mInstance;
    }

    public String getInterfaceUrl() {
        String ip = SharedPreUtil.getIp();
        ip = BaseConfig.CONFIG_HTTP + ip + "/";
        return ip;
    }

    /**
     * 登录
     *
     * @param callback
     */
    public void LoginIn(String userName, String password, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("UserName", userName)
                .add("Password", password)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "DataInterface/LoginInterface.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 首页数据
     *
     * @param callback
     */
    public void MainData(String companyNo, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("CompanyNumber", companyNo)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "DataInterface/HomePageInterface.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 汇总数据
     *
     * @param callback
     */
    public void CollectData(String companyNo, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("CompanyNumber", companyNo)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "DataInterface/SummaryPageInterface.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 汇总列表
     *
     * @param callback
     */
    public void CollectByTypeData(String flowName, String companyNo, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("FlowName", flowName)
                .add("CompanyNumber", companyNo)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "DataInterface/ListInterface.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 汇总详情
     *
     * @param callback
     */
    public void CollectDetailData(String ID, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("WorkMainID", ID)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "DataInterface/DetailsInterface.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }
}
