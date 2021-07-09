package com.example.management.bean;

import java.io.Serializable;

/**
 * Created by wangm on 2018/11/19.
 */

public class UserBean implements Serializable {

    private String UserId;
    private String UserName;
    private String FactoryCode;
    private String FactoryName;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFactoryCode() {
        return FactoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        FactoryCode = factoryCode;
    }

    public String getFactoryName() {
        return FactoryName;
    }

    public void setFactoryName(String factoryName) {
        FactoryName = factoryName;
    }
}
