package com.example.management.bean;

/**
 * Description ：http返回结果
 * creator ：王敏健
 * Create time : 2018/7/13
 */

public class HttpResult<T> {

    private boolean Success;
    private String Message;
    private Object Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
