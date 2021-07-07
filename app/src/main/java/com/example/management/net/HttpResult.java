package com.example.management.net;

import java.util.List;

/**
 * Description ：网络请求返回结果
 * creator ：但子龙
 * Create time : 2018/4/23
 */

public class HttpResult {
    private int Code;
    private String Message;
    private List<Object> Date;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<Object> getDate() {
        return Date;
    }

    public void setDate(List<Object> Date) {
        Date = Date;
    }
}
