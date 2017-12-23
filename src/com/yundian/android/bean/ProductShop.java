package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/22.
 *
 * @author liushenghan
 *         <p>
 *         店铺里面商品信息
 */

public class ProductShop implements Serializable {

    private int g_ID;
    private int g_mPrice;
    private int g_parent;
    private String g_photo;
    private String g_mc;
    private String g_time;


    public int getG_ID() {
        return g_ID;
    }

    public void setG_ID(int g_ID) {
        this.g_ID = g_ID;
    }

    public int getG_mPrice() {
        return g_mPrice;
    }

    public void setG_mPrice(int g_mPrice) {
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
}
