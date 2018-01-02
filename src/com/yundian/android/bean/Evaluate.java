package com.yundian.android.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by liushenghan on 2018/1/2.
 */

public class Evaluate implements Serializable {

    private int Shop_Evaluate_ID;
    private int Shop_Evaluate_SupplierID;
    private int Shop_Evaluate_OrderID;
    private int Shop_Evaluate_ProductID;
    private int Shop_Evaluate_MemberID;
    private int Shop_Evaluate_Product;
    private int Shop_Evaluate_Service;
    private int Shop_Evaluate_Delivery;
    private String Shop_Evaluate_Note;
    private String Shop_Evaluate_Addtime;
    private String Member_Nickname;


    public int getShop_Evaluate_ID() {
        return Shop_Evaluate_ID;
    }

    public void setShop_Evaluate_ID(int shop_Evaluate_ID) {
        Shop_Evaluate_ID = shop_Evaluate_ID;
    }

    public int getShop_Evaluate_SupplierID() {
        return Shop_Evaluate_SupplierID;
    }

    public void setShop_Evaluate_SupplierID(int shop_Evaluate_SupplierID) {
        Shop_Evaluate_SupplierID = shop_Evaluate_SupplierID;
    }

    public int getShop_Evaluate_OrderID() {
        return Shop_Evaluate_OrderID;
    }

    public void setShop_Evaluate_OrderID(int shop_Evaluate_OrderID) {
        Shop_Evaluate_OrderID = shop_Evaluate_OrderID;
    }

    public int getShop_Evaluate_ProductID() {
        return Shop_Evaluate_ProductID;
    }

    public void setShop_Evaluate_ProductID(int shop_Evaluate_ProductID) {
        Shop_Evaluate_ProductID = shop_Evaluate_ProductID;
    }

    public int getShop_Evaluate_MemberID() {
        return Shop_Evaluate_MemberID;
    }

    public void setShop_Evaluate_MemberID(int shop_Evaluate_MemberID) {
        Shop_Evaluate_MemberID = shop_Evaluate_MemberID;
    }

    public int getShop_Evaluate_Product() {
        return Shop_Evaluate_Product;
    }

    public void setShop_Evaluate_Product(int shop_Evaluate_Product) {
        Shop_Evaluate_Product = shop_Evaluate_Product;
    }

    public int getShop_Evaluate_Service() {
        return Shop_Evaluate_Service;
    }

    public void setShop_Evaluate_Service(int shop_Evaluate_Service) {
        Shop_Evaluate_Service = shop_Evaluate_Service;
    }

    public int getShop_Evaluate_Delivery() {
        return Shop_Evaluate_Delivery;
    }

    public void setShop_Evaluate_Delivery(int shop_Evaluate_Delivery) {
        Shop_Evaluate_Delivery = shop_Evaluate_Delivery;
    }

    public String getShop_Evaluate_Note() {
        return Shop_Evaluate_Note;
    }

    public void setShop_Evaluate_Note(String shop_Evaluate_Note) {
        Shop_Evaluate_Note = shop_Evaluate_Note;
    }

    public String getShop_Evaluate_Addtime() {
        return Shop_Evaluate_Addtime;
    }

    public void setShop_Evaluate_Addtime(String shop_Evaluate_Addtime) {
        Shop_Evaluate_Addtime = shop_Evaluate_Addtime;
    }

    public String getMember_Nickname() {
        return TextUtils.isEmpty(Member_Nickname) ? "匿名用户" : Member_Nickname;
    }

    public void setMember_Nickname(String member_Nickname) {
        Member_Nickname = member_Nickname;
    }
}
