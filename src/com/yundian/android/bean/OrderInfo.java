package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/27.
 * <p>
 * /// <summary>
 * /// DingDanSave 添加订单
 * /// </summary>
 * /// <param name="token"></param>
 * /// <param name="Orders_Address_ID">收货地址ID(关联收货地址主键)</param>
 * /// <param name="Orders_Note">订单备注</param>
 * /// <param name="Orders_Payway">付款方式ID</param>
 * /// <param name="Orders_SupplierID">商家ID</param>
 * /// <param name="Delivery_Way_ID">配送方式ID</param>
 * /// <param name="product_id">购物车产品ID(多个产品以#相连)</param>
 * /// <param name="product_amount">产品数量(多个数量以#相连)</param>
 * /// <param name="total_price">产品总价</param>
 * /// <param name="total_priceFreight">运费总价</param>
 * /// <param name="total_allprice">订单总价</param>
 */

public class OrderInfo implements Serializable {

    private int Orders_Address_ID;
    private String Orders_Note;
    private int Orders_Payway;
    private int Orders_SupplierID;
    private int Delivery_Way_ID;
    private String product_id;
    private String product_amount;
    private float total_price;
    private float total_priceFreight;
    private float total_allprice;

    public OrderInfo() {
    }

    public OrderInfo(ShopCart cart, Address address) {
        this.Orders_Address_ID = address.getId();
        this.Orders_Note = "#";
        this.Orders_Payway = cart.getPay().getPay_Way_ID();
        this.Orders_SupplierID = cart.getSupplierID();
        this.Delivery_Way_ID = cart.getDelivery().getDelivery_Way_ID();
        // 产品id
        StringBuilder ids = new StringBuilder();
        // 产品个数
        StringBuilder nums = new StringBuilder();
        for (ProductInfo info : cart.getPdts()) {
            ids.append(info.getG_ID()).append("#");
            nums.append(info.amount).append("#");
        }
        product_id = ids.deleteCharAt(ids.length() - 1).toString();
        product_amount = nums.deleteCharAt(nums.length() - 1).toString();

        this.total_price = cart.getOrder_total();
        this.total_priceFreight = cart.getFreight();
        this.total_allprice = total_price + total_priceFreight;

    }

    public int getOrders_Address_ID() {
        return Orders_Address_ID;
    }

    public void setOrders_Address_ID(int orders_Address_ID) {
        Orders_Address_ID = orders_Address_ID;
    }

    public String getOrders_Note() {
        return Orders_Note;
    }

    public void setOrders_Note(String orders_Note) {
        Orders_Note = orders_Note;
    }

    public int getOrders_Payway() {
        return Orders_Payway;
    }

    public void setOrders_Payway(int orders_Payway) {
        Orders_Payway = orders_Payway;
    }

    public int getOrders_SupplierID() {
        return Orders_SupplierID;
    }

    public void setOrders_SupplierID(int orders_SupplierID) {
        Orders_SupplierID = orders_SupplierID;
    }

    public int getDelivery_Way_ID() {
        return Delivery_Way_ID;
    }

    public void setDelivery_Way_ID(int delivery_Way_ID) {
        Delivery_Way_ID = delivery_Way_ID;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(String product_amount) {
        this.product_amount = product_amount;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public float getTotal_priceFreight() {
        return total_priceFreight;
    }

    public void setTotal_priceFreight(float total_priceFreight) {
        this.total_priceFreight = total_priceFreight;
    }

    public float getTotal_allprice() {
        return total_allprice;
    }

    public void setTotal_allprice(float total_allprice) {
        this.total_allprice = total_allprice;
    }


    @Override
    public String toString() {
        return "OrderInfo{" +
                "Orders_Address_ID=" + Orders_Address_ID +
                ", Orders_Note='" + Orders_Note + '\'' +
                ", Orders_Payway=" + Orders_Payway +
                ", Orders_SupplierID=" + Orders_SupplierID +
                ", Delivery_Way_ID=" + Delivery_Way_ID +
                ", product_id='" + product_id + '\'' +
                ", product_amount='" + product_amount + '\'' +
                ", total_price=" + total_price +
                ", total_priceFreight=" + total_priceFreight +
                ", total_allprice=" + total_allprice +
                '}';
    }
}



