package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/18.
 */

public class ProductInfo implements Serializable {
    // 商品id
    private int g_ID;
    //价格
    private float g_mPrice;
    // 分类id
    private int g_parent;
    //图片
    private String g_photo;
    // 商品名称
    private String g_mc;
    //时间
    private String g_time;


    public int getG_ID() {
        return g_ID;
    }

    public void setG_ID(int g_ID) {
        this.g_ID = g_ID;
    }

    public float getG_mPrice() {
        return g_mPrice;
    }

    public void setG_mPrice(float g_mPrice) {
        this.g_mPrice = g_mPrice;
    }

    public int getG_parent() {
        return g_parent;
    }

    public void setG_parent(int g_parent) {
        this.g_parent = g_parent;
    }

    public String getG_photo() {
        return g_photo;
    }

    public void setG_photo(String g_photo) {
        this.g_photo = g_photo;
    }

    public String getG_mc() {
        return g_mc;
    }

    public void setG_mc(String g_mc) {
        this.g_mc = g_mc;
    }

    public String getG_time() {
        return g_time;
    }

    public void setG_time(String g_time) {
        this.g_time = g_time;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "g_ID=" + g_ID +
                ", g_mPrice=" + g_mPrice +
                ", g_parent=" + g_parent +
                ", g_photo='" + g_photo + '\'' +
                ", g_mc='" + g_mc + '\'' +
                ", g_time='" + g_time + '\'' +
                '}';
    }
}

