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

    public void LoginIn(String userName, String password, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("user", userName)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "Login.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 门卫登记
     *
     * @param callback
     */
    public void registerData(String truckNo, String serialNo, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("para1", truckNo)
                .add("para2", serialNo)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "AddqueueForCorn.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 门卫确认
     *
     * @param callback
     */
    public void getGotOutData(String businessNo, StringCallback callback) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
        RequestBody formBody = new FormBody.Builder()
                .add("username", userBean.getUserName())
                .add("yewuhao", businessNo)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "ChuChang.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 获取排队车辆
     *
     * @param callback
     */
    public void getTruckNum(String position, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("address", position)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "GetLineUpInfo.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 获取排队位置
     *
     * @param callback
     */
    public void getPosition(StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "GetLineUpAddressInfo.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 排队车辆
     *
     * @param callback
     */
    public void saveTruckNum(String position, String truckNum, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("address", position)
                .add("number", truckNum)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "AddLineUpInfo.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 获取质检列表信息
     *
     * @param callback
     */
    public void getQualityListData(String water, String water1, String area, String truck, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("shuifen1", water)
                .add("shuifen2", water1)
                .add("chandi", area)
                .add("chexing", truck)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "GetzhijianxinxiList.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 获取库位列表
     *
     * @param callback
     */
    public void getStorageData(String storageNum, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("para1", storageNum)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "GetStorehouse.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 获取计量信息
     *
     * @param callback
     */
    public void getMeasureData(String cardNo, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("para1", cardNo)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "Getjiliangxinxi.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 获取质检信息
     *
     * @param callback
     */
    public void getQualityData(String cardNo, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("para1", cardNo)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "Getzhijianxinxi.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 拒收确认
     *
     * @param callback
     */
    public void tallyingRefuse(String cardNo, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("para1", cardNo)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "ConfirmNoPass.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 加扣计算
     *
     * @param callback
     */
    public void buckleCount(String waterData, String waterDataEx, String waterWeightEx,
                            String zaData, String zaDataEx, String zaWeightEx,
                            String buData, String buDataEx, String buWeightEx,
                            String meiData, String meiDataEx, String meiWeightEx,
                            String roData, String roDataEx, String roWeightEx,
                            String chData, String chDataEx, String chWeightEx,
                            String inData, String smallData, String bugData,
                            String boneData, String stoneData, String focusData,
                            String alienData, String businessNo, String nmDataEx, String nmWeightEx, StringCallback callback) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
        RequestBody formBody = new FormBody.Builder()
                .add("yuanshishuifen", waterData)
                .add("yichangshuifen", waterDataEx)
                .add("yichangshuifenzhongliang", waterWeightEx)
                .add("yuanshizazhi", zaData)
                .add("yichangzazhi", zaDataEx)
                .add("yichangzazhizhongliang", zaWeightEx)
                .add("yuanshibuwanshan", buData)
                .add("yichangbuwanshan", buDataEx)
                .add("yichangbuwanshanzhongliang", buWeightEx)
                .add("yuanshimeibian", meiData)
                .add("yichangmeibian", meiDataEx)
                .add("yichangmeibianzhongliang", meiWeightEx)
                .add("yuanshirongzhong", roData)
                .add("yichangrongzhong", roDataEx)
                .add("yichangrongzhongzhongliang", roWeightEx)
                .add("yuanshichongshi", chData)
                .add("yichangchongshi", chDataEx)
                .add("yichangchongshizhongliang", chWeightEx)
                .add("chexiangjinshui", inData)
                .add("xiaoliyumi", smallData)
                .add("youzi", bugData)
                .add("yumigutou", boneData)
                .add("shizi", stoneData)
                .add("jizhongzazhituan", focusData)
                .add("yizhongliang", alienData)
                .add("username", userBean.getUserName())
                .add("yewuhao", businessNo)
                .add("yichangneimei", nmDataEx)
                .add("yichangneimeizhongliang", nmWeightEx)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "Getkouzhong.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 装车方式
     *
     * @param callback
     */
    public void getTrans(String transName, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("fahuodanwei", transName)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "GetZXXM.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 获取批次号
     *
     * @param callback
     */
    public void getPiCi(String yeWuHao, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("yewuhao", yeWuHao)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "GetPihao.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 上传批次号
     *
     * @param callback
     */
    public void UpdatePiCi(String para1, String para2, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("para1", para1)
                .add("para2", para2)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "UploadPihao.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 物料名称、规格输入检测
     *
     * @param callback
     */
    public void CheckMaterial(String yewuhao, String str, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("yewuhao", yewuhao)
                .add("str", str)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "checking.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 收发货
     *
     * @param callback
     */
    public void tallying(String storageID, String storageName, String CardNo, String UserName, String OperationName,
                         String Count, String jiakou, String jianshu, String beizhu, String xianghao1, String xianghao2,
                         String yuanqianfenghao1, String yuanqianfenghao2, String jinyumiqianfenghao1,
                         String jinyumiqianfenghao2, String daizhong, String pihao, String beiyongpihao, String transName,
                         StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("KuweiId", storageID)
                .add("KuweiMingcheng", storageName)
                .add("CardNo", CardNo)
                .add("UserName", UserName)
                .add("OperationName", OperationName)
                .add("Count", Count)
                .add("jiakou", jiakou)
                .add("jianshu", jianshu)
                .add("beizhu", beizhu)
                .add("xianghao1", xianghao1)
                .add("xianghao2", xianghao2)
                .add("yuanqianfenghao1", yuanqianfenghao1)
                .add("yuanqianfenghao2", yuanqianfenghao2)
                .add("jinyumiqianfenghao1", jinyumiqianfenghao1)
                .add("jinyumiqianfenghao2", jinyumiqianfenghao2)
                .add("daizhong", daizhong)
                .add("picihao", pihao)
                .add("beiyongpicihao", beiyongpihao)
                .add("zhuangchefangshi", transName)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "SendRecive.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 获取淀粉乳测量值信息
     *
     * @param callback
     */
    public void getWeightData(String businessNo, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("yewuhao", businessNo)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "GetDianFenRuCeLiangZhi.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }

    /**
     * 添加淀粉乳测量值信息
     *
     * @param callback
     */
    public void saveWeightData(String businessNo, String date1, String weight1, String temp1, String date2, String weight2,
                               String temp2, String date3, String weight3, String temp3, String date4, String weight4,
                               String temp4, String date5, String weight5, String temp5, StringCallback callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("yewuhao", businessNo)
                .add("Date1", date1)
                .add("BiZhongCeLiang1", weight1)
                .add("WenDuCeLiang1", temp1)
                .add("Date2", date2)
                .add("BiZhongCeLiang2", weight2)
                .add("WenDuCeLiang2", temp2)
                .add("Date3", date3)
                .add("BiZhongCeLiang3", weight3)
                .add("WenDuCeLiang3", temp3)
                .add("Date4", date4)
                .add("BiZhongCeLiang4", weight4)
                .add("WenDuCeLiang4", temp4)
                .add("Date5", date5)
                .add("BiZhongCeLiang5", weight5)
                .add("WenDuCeLiang5", temp5)
                .build();
        Request request = new Request.Builder()
                .url(getInterfaceUrl() + "SaveDianFenRuCeLiangZhi.ashx")
                .post(formBody)
                .build();
        okHttpClientManager.postAsyn(request, callback);
    }
}
