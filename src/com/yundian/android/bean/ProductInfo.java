package com.yundian.android.bean;

import java.io.Serializable;

/**
 * @author liushenghan Created on 2017/12/18.
 */

public class ProductInfo implements Serializable {

    private String shop_name;

    private float Product_Weight;

    private int Product_SupplierID;
    private float Product_Tiji;

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

    public int amount = 1;
    // 正常情况下
    public boolean isSelected = false;
    // 编辑模式下 应该是 remove 移除
    public boolean isMove = false;


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


    public float getProduct_Weight() {
        return Product_Weight;
    }

    public void setProduct_Weight(float product_Weight) {
        Product_Weight = product_Weight;
    }

    public int getProduct_SupplierID() {
        return Product_SupplierID;
    }

    public void setProduct_SupplierID(int product_SupplierID) {
        Product_SupplierID = product_SupplierID;
    }

    public float getProduct_Tiji() {
        return Product_Tiji;
    }

    public void setProduct_Tiji(float product_Tiji) {
        Product_Tiji = product_Tiji;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductInfo that = (ProductInfo) o;

        return g_ID == that.g_ID;
    }


    @Override
    public int hashCode() {
        return g_ID;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "shop_name='" + shop_name + '\'' +
                ", Product_Weight=" + Product_Weight +
                ", Product_SupplierID=" + Product_SupplierID +
                ", Product_Tiji=" + Product_Tiji +
                ", g_ID=" + g_ID +
                ", g_mPrice=" + g_mPrice +
                ", g_parent=" + g_parent +
                ", g_photo='" + g_photo + '\'' +
                ", g_mc='" + g_mc + '\'' +
                ", g_time='" + g_time + '\'' +
                ", amount=" + amount +
                ", isSelected=" + isSelected +
                ", isMove=" + isMove +
                '}';
    }
}

