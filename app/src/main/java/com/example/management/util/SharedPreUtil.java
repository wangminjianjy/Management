package com.example.management.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.management.base.CustomApplication;

/**
 * Created by wangm on 2018/11/19.
 */

public class SharedPreUtil {

    public static String COMMON_SETTING = "COMMON_SETTING";

    /**
     * 保存接口ip
     *
     * @param ip
     */
    public static void saveIp(String ip) {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("common_ip", ip).commit();
    }

    /**
     * 获取接口ip
     */
    public static String getIp() {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        return sharedPreferences.getString("common_ip", "");
    }

    /**
     * 保存登录状态
     *
     * @param loginInfo
     */
    public static void saveLogin(boolean loginInfo) {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("common_login", loginInfo).commit();
    }

    /**
     * 获取登录状态
     */
    public static boolean getLogin() {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("common_login", false);
    }

    /**
     * 保存用户信息
     *
     * @param userInfo
     */
    public static void saveUserInfo(String userInfo) {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("common_user", userInfo).commit();
    }

    /**
     * 获取用户信息
     */
    public static String getUserInfo() {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        return sharedPreferences.getString("common_user", "");
    }

    /**
     * 保存进厂序号
     *
     * @param serialNum
     */
    public static void saveSerial(int serialNum) {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("common_serial", serialNum).commit();
    }

    /**
     * 获取进厂序号
     */
    public static int getSerial() {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("common_serial", 1);
    }

    /**
     * 保存位置
     *
     * @param position
     */
    public static void savePosition(String position) {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("common_position", position).commit();
    }

    /**
     * 获取位置
     */
    public static String getPosition() {
        SharedPreferences sharedPreferences = CustomApplication.getContext().getSharedPreferences(COMMON_SETTING, Context.MODE_PRIVATE);
        return sharedPreferences.getString("common_position", "");
    }
}
