package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/16.
 */

public class BaseResponse<T> implements Serializable {

    private int state;
    private String msg;
    private String name;
    private T info;

    public boolean isOK() {
        return state==1;
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

    @Override
    public String toString() {
        return "BaseResponse{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", name='" + name + '\'' +
                ", info=" + info +
                '}';
    }
}
