package com.example.management.net.okhttp;

/**
 * Created by wangm on 2018/11/22.
 */

public class NetConfig {
    public static final int NETWORK_READ_TIMEOUT = 30 * 1000;       // 读取超时时间
    public static final int NETWORK_CONNECT_TIMEOUT = 30 * 1000;    // 连接超时时间
    public static final int NETWORK_WRITE_TIMEOUT = 30 * 1000;      // 写入超时时间

    public static final int RESULT_STATUS_SUCCESS = 200;    // 返回数据成功
    public static final int RESULT_STATUS_FIAL = 500;       // 返回数据失败
}
