package com.yundian.android.entity;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/16.
 */

public class BaseResponse<T> implements Serializable {

    private int state;
    private String msg;
    private String name;
    private T info;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
