package com.yundian.android.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liushenghan  Created  on 2017/12/26.
 */

public class ShopCart implements Serializable {
    // 店铺id
    private int supplierID;
    //支付方式
    private PayMethod pay;
    //物流方式
    private DeliveryMethod delivery;
    private List<ProductInfo> pdts;
    // 订单总价
    private float order_total;
    //运费
    private float freight;
    private float tiji;
    private float weight;


    public ShopCart(int supplierID) {
        pdts = new ArrayList<>();
        this.supplierID = supplierID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public PayMethod getPay() {
        return pay;
    }

    public void setPay(PayMethod pay) {
        this.pay = pay;
    }

    public DeliveryMethod getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryMethod delivery) {

        // 计算运费
        tiji = 0;
        weight = 0;
        order_total = 0;
        for (ProductInfo info : pdts) {
            order_total += info.getG_mPrice() * info.amount;
            weight += info.getProduct_Weight() * info.amount;
            tiji += info.getProduct_Tiji() * info.amount;
        }


        this.delivery = delivery;
        freight = delivery.getDelivery_Way_InitialFee();
        if (weight > delivery.getDelivery_Way_InitialWeight()) {
            freight += (weight - delivery.getDelivery_Way_InitialWeight() + delivery.getDelivery_Way_UpWeight() - 1) / delivery.getDelivery_Way_UpWeight() * delivery.getDelivery_Way_UpFee();
        }
    }

    public List<ProductInfo> getPdts() {
        return pdts;
    }

//    public void setPdts(List<ProductInfo> pdts) {
//        this.pdts = pdts;
//        // 计算运费
//        tiji = 0;
//        weight = 0;
//        order_total = 0;
//        for (ProductInfo info : pdts) {
//            order_total = info.getG_mPrice() * info.amount;
//            weight += info.getProduct_Weight() * info.amount;
//            tiji += info.getProduct_Tiji() * info.amount;
//        }
//    }


    public float getOrder_total() {
        return order_total;
    }


    public float getFreight() {
        return freight;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopCart shopCart = (ShopCart) o;

        return supplierID == shopCart.supplierID;
    }

    @Override
    public int hashCode() {
        return supplierID;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "supplierID=" + supplierID +
                ", pay=" + pay +
                ", delivery=" + delivery +
                ", pdts=" + pdts +
                '}';
    }
}
