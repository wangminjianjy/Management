package com.example.management.net;

import java.util.List;

/**
 * Description ：网络请求返回结果
 * creator ：但子龙
 * Create time : 2018/4/23
 */

public class HttpResult {

    private int Code;
    private Object Result;
    private Object Result2;
    private Object Result3;
    private Object Result4;
    private int ResultTotle;
    private String ResultMsg;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public Object getResult() {
        return Result;
    }

    public void setResult(Object result) {
        Result = result;
    }

    public Object getResult2() {
        return Result2;
    }

    public void setResult2(Object result2) {
        Result2 = result2;
    }

    public Object getResult3() {
        return Result3;
    }

    public void setResult3(Object result3) {
        Result3 = result3;
    }

    public Object getResult4() {
        return Result4;
    }

    public void setResult4(Object result4) {
        Result4 = result4;
    }

    public int getResultTotle() {
        return ResultTotle;
    }

    public void setResultTotle(int resultTotle) {
        ResultTotle = resultTotle;
    }

    public String getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(String resultMsg) {
        ResultMsg = resultMsg;
    }
}
