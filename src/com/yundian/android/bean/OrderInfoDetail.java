package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/28.
 * 订单详情
 * <p>
 * "Orders_ID": 35,
 * "Orders_SN": "17122861601",
 * "Orders_Total_Price": 6970.0000,
 * "Orders_Total_AllPrice": 7100.0000,
 * "Orders_Addtime": "\/Date(1514425595000+0800)\/",
 * "Orders_Status": 0,
 * "Orders_PaymentStatus": 0,
 * "Orders_DeliveryStatus": 0,
 * "Orders_Total_Freight": 130.0000,
 * "Orders_Address_StreetAddress": "changpin",
 * "Orders_Address_Name": "刘胜寒",
 * "Orders_Address_Phone_Number": "18500637478",
 * "Orders_Address_Mobile": "\"\"",
 * "Orders_Delivery_Name": "空运",
 * "Orders_Payway_Name": "支付宝支付",
 * "Orders_SupplierID": 10,
 * "Shop_Name": "乌鲁木齐鸿博鑫业商贸有限公司",
 * "product_img": "美孚机油",
 * "product_name": "/uploadfile/2017472236481264.jpg",
 * "product_count": "9",
 * "Supplier_Mobile": "18999218413"
 */

public class OrderInfoDetail implements Serializable {
    private int Orders_ID;
    private String Orders_SN;
    private float Orders_Total_Price;
    private float Orders_Total_AllPrice;
    private String Orders_Addtime;
    private int Orders_Status;
    private int Orders_PaymentStatus;
    private int Orders_DeliveryStatus;
    private float Orders_Total_Freight;
    private String Orders_Address_StreetAddress;
    private String Orders_Address_Name;
    private String Orders_Address_Phone_Number;
    private String Orders_Address_Mobile;
    private String Orders_Delivery_Name;
    private String Orders_Payway_Name;
    private int Orders_SupplierID;
    private String Shop_Name;
    private String product_img;
    private String product_name;
    private int product_count;
    private String Supplier_Mobile;

    public int getOrders_ID() {
        return Orders_ID;
    }

    public void setOrders_ID(int orders_ID) {
        Orders_ID = orders_ID;
    }

    public String getOrders_SN() {
        return Orders_SN;
    }

    public void setOrders_SN(String orders_SN) {
        Orders_SN = orders_SN;
    }

    public float getOrders_Total_Price() {
        return Orders_Total_Price;
    }

    public void setOrders_Total_Price(float orders_Total_Price) {
        Orders_Total_Price = orders_Total_Price;
    }

    public float getOrders_Total_AllPrice() {
        return Orders_Total_AllPrice;
    }

    public void setOrders_Total_AllPrice(float orders_Total_AllPrice) {
        Orders_Total_AllPrice = orders_Total_AllPrice;
    }

    public String getOrders_Addtime() {
        return Orders_Addtime;
    }

    public void setOrders_Addtime(String orders_Addtime) {
        Orders_Addtime = orders_Addtime;
    }

    public int getOrders_Status() {
        return Orders_Status;
    }

    public void setOrders_Status(int orders_Status) {
        Orders_Status = orders_Status;
    }

    public int getOrders_PaymentStatus() {
        return Orders_PaymentStatus;
    }

    public void setOrders_PaymentStatus(int orders_PaymentStatus) {
        Orders_PaymentStatus = orders_PaymentStatus;
    }

    public int getOrders_DeliveryStatus() {
        return Orders_DeliveryStatus;
    }

    public void setOrders_DeliveryStatus(int orders_DeliveryStatus) {
        Orders_DeliveryStatus = orders_DeliveryStatus;
    }

    public float getOrders_Total_Freight() {
        return Orders_Total_Freight;
    }

    public void setOrders_Total_Freight(float orders_Total_Freight) {
        Orders_Total_Freight = orders_Total_Freight;
    }

    public String getOrders_Address_StreetAddress() {
        return Orders_Address_StreetAddress;
    }

    public void setOrders_Address_StreetAddress(String orders_Address_StreetAddress) {
        Orders_Address_StreetAddress = orders_Address_StreetAddress;
    }

    public String getOrders_Address_Name() {
        return Orders_Address_Name;
    }

    public void setOrders_Address_Name(String orders_Address_Name) {
        Orders_Address_Name = orders_Address_Name;
    }

    public String getOrders_Address_Phone_Number() {
        return Orders_Address_Phone_Number;
    }

    public void setOrders_Address_Phone_Number(String orders_Address_Phone_Number) {
        Orders_Address_Phone_Number = orders_Address_Phone_Number;
    }

    public String getOrders_Address_Mobile() {
        return Orders_Address_Mobile;
    }

    public void setOrders_Address_Mobile(String orders_Address_Mobile) {
        Orders_Address_Mobile = orders_Address_Mobile;
    }

    public String getOrders_Delivery_Name() {
        return Orders_Delivery_Name;
    }

    public void setOrders_Delivery_Name(String orders_Delivery_Name) {
        Orders_Delivery_Name = orders_Delivery_Name;
    }

    public String getOrders_Payway_Name() {
        return Orders_Payway_Name;
    }

    public void setOrders_Payway_Name(String orders_Payway_Name) {
        Orders_Payway_Name = orders_Payway_Name;
    }

    public int getOrders_SupplierID() {
        return Orders_SupplierID;
    }

    public void setOrders_SupplierID(int orders_SupplierID) {
        Orders_SupplierID = orders_SupplierID;
    }

    public String getShop_Name() {
        return Shop_Name;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }

    public String getSupplier_Mobile() {
        return Supplier_Mobile;
    }

    public void setSupplier_Mobile(String supplier_Mobile) {
        Supplier_Mobile = supplier_Mobile;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderInfoDetail)) return false;

        OrderInfoDetail that = (OrderInfoDetail) o;

        return getOrders_ID() == that.getOrders_ID();
    }

    @Override
    public int hashCode() {
        return getOrders_ID();
    }
}
