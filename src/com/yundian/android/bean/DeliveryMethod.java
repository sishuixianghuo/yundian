package com.yundian.android.bean;

import java.io.Serializable;

/**
 * @author liushenghan Created  on 2017/12/26.
 */

public class DeliveryMethod implements Serializable {

    // id
    private int Delivery_Way_ID;
    // 快递方式
    private String Delivery_Way_Name;
    // 首重
    private float Delivery_Way_InitialWeight;
    // 续重
    private float Delivery_Way_UpWeight;
    // 首重体积
    private float Delivery_Way_InitialFee;
    // 续重提价
    private float Delivery_Way_UpFee;

    public int getDelivery_Way_ID() {
        return Delivery_Way_ID;
    }

    public void setDelivery_Way_ID(int delivery_Way_ID) {
        Delivery_Way_ID = delivery_Way_ID;
    }

    public String getDelivery_Way_Name() {
        return Delivery_Way_Name;
    }

    public void setDelivery_Way_Name(String delivery_Way_Name) {
        Delivery_Way_Name = delivery_Way_Name;
    }

    public float getDelivery_Way_InitialWeight() {
        return Delivery_Way_InitialWeight;
    }

    public void setDelivery_Way_InitialWeight(float delivery_Way_InitialWeight) {
        Delivery_Way_InitialWeight = delivery_Way_InitialWeight;
    }

    public float getDelivery_Way_UpWeight() {
        return Delivery_Way_UpWeight;
    }

    public void setDelivery_Way_UpWeight(float delivery_Way_UpWeight) {
        Delivery_Way_UpWeight = delivery_Way_UpWeight;
    }

    public float getDelivery_Way_InitialFee() {
        return Delivery_Way_InitialFee;
    }

    public void setDelivery_Way_InitialFee(float delivery_Way_InitialFee) {
        Delivery_Way_InitialFee = delivery_Way_InitialFee;
    }

    public float getDelivery_Way_UpFee() {
        return Delivery_Way_UpFee;
    }

    public void setDelivery_Way_UpFee(float delivery_Way_UpFee) {
        Delivery_Way_UpFee = delivery_Way_UpFee;
    }
}

