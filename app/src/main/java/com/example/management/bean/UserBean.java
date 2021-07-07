package com.example.management.bean;

import java.io.Serializable;

/**
 * Created by wangm on 2018/11/19.
 */

public class UserBean implements Serializable {

    private String UserId;
    private String UserName;
    private String UserPwd;
    private String LastLoginDate;
    private String LastChangePwdDate;
    private String State;
    private String CreateDate;
    private String UserCode;
    private String FactoryCode;
    private String PicName;
    private String TelNo;
    private String Email;
    private String Role;
    private String TeamName;
    private String ThemeSkin;
    private String ThemeSize;
    private String Sex;
    private String Signature;
    private String Remark;

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

    public String getUserPwd() {
        return UserPwd;
    }

    public void setUserPwd(String userPwd) {
        UserPwd = userPwd;
    }

    public String getLastLoginDate() {
        return LastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        LastLoginDate = lastLoginDate;
    }

    public String getLastChangePwdDate() {
        return LastChangePwdDate;
    }

    public void setLastChangePwdDate(String lastChangePwdDate) {
        LastChangePwdDate = lastChangePwdDate;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getFactoryCode() {
        return FactoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        FactoryCode = factoryCode;
    }

    public String getPicName() {
        return PicName;
    }

    public void setPicName(String picName) {
        PicName = picName;
    }

    public String getTelNo() {
        return TelNo;
    }

    public void setTelNo(String telNo) {
        TelNo = telNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getThemeSkin() {
        return ThemeSkin;
    }

    public void setThemeSkin(String themeSkin) {
        ThemeSkin = themeSkin;
    }

    public String getThemeSize() {
        return ThemeSize;
    }

    public void setThemeSize(String themeSize) {
        ThemeSize = themeSize;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
