package com.yundian.android.bean;

import java.io.Serializable;

/**
 * @author liushenghan  Created  on 2017/12/28.
 *         订单详情里面使用
 */

public class OrderPdt implements Serializable {

    private String Orders_Goods_Product_Img;
    private String Orders_Goods_Product_Name;
    private int Orders_Goods_Amount;
    private float Orders_Goods_Product_SalePrice;

    public String getOrders_Goods_Product_Img() {
        return Orders_Goods_Product_Img;
    }

    public void setOrders_Goods_Product_Img(String orders_Goods_Product_Img) {
        Orders_Goods_Product_Img = orders_Goods_Product_Img;
    }

    public String getOrders_Goods_Product_Name() {
        return Orders_Goods_Product_Name;
    }

    public void setOrders_Goods_Product_Name(String orders_Goods_Product_Name) {
        Orders_Goods_Product_Name = orders_Goods_Product_Name;
    }

    public int getOrders_Goods_Amount() {
        return Orders_Goods_Amount;
    }

    public void setOrders_Goods_Amount(int orders_Goods_Amount) {
        Orders_Goods_Amount = orders_Goods_Amount;
    }

    public float getOrders_Goods_Product_SalePrice() {
        return Orders_Goods_Product_SalePrice;
    }

    public void setOrders_Goods_Product_SalePrice(float orders_Goods_Product_SalePrice) {
        Orders_Goods_Product_SalePrice = orders_Goods_Product_SalePrice;
    }
}
