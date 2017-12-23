package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/23.
 */

public class StoreInfo implements Serializable {


//                  "Shop_Name": "乌鲁木齐鸿博鑫业商贸有限公司",
//                  "Supplier_Address": "天津南路西五巷58号嘉盛园13号楼",
//                  "Supplier_CompanyName": "乌鲁木齐鸿博鑫业商贸有限公司",
//                  "Supplier_Phone": "",
//                  "Supplier_QQ": "342350130",
//                  "Shop_Img": "",
//                  "supplier_mobile": "18999218413"


    private String Shop_Name;
    private String Supplier_Address;
    private String Supplier_CompanyName;
    private String Supplier_Phone;
    private String Supplier_QQ;
    private String Shop_Img;
    private String supplier_mobile;
    private int shop_id;

    public String getShop_Name() {
        return Shop_Name;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }

    public String getSupplier_Address() {
        return Supplier_Address;
    }

    public void setSupplier_Address(String supplier_Address) {
        Supplier_Address = supplier_Address;
    }

    public String getSupplier_CompanyName() {
        return Supplier_CompanyName;
    }

    public void setSupplier_CompanyName(String supplier_CompanyName) {
        Supplier_CompanyName = supplier_CompanyName;
    }

    public String getSupplier_Phone() {
        return Supplier_Phone;
    }

    public void setSupplier_Phone(String supplier_Phone) {
        Supplier_Phone = supplier_Phone;
    }

    public String getSupplier_QQ() {
        return Supplier_QQ;
    }

    public void setSupplier_QQ(String supplier_QQ) {
        Supplier_QQ = supplier_QQ;
    }

    public String getShop_Img() {
        return Shop_Img;
    }

    public void setShop_Img(String shop_Img) {
        Shop_Img = shop_Img;
    }

    public String getSupplier_mobile() {
        return supplier_mobile;
    }

    public void setSupplier_mobile(String supplier_mobile) {
        this.supplier_mobile = supplier_mobile;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public String toString() {
        return "StoreInfo{" +
                "Shop_Name='" + Shop_Name + '\'' +
                ", Supplier_Address='" + Supplier_Address + '\'' +
                ", Supplier_CompanyName='" + Supplier_CompanyName + '\'' +
                ", Supplier_Phone='" + Supplier_Phone + '\'' +
                ", Supplier_QQ='" + Supplier_QQ + '\'' +
                ", Shop_Img='" + Shop_Img + '\'' +
                ", supplier_mobile='" + supplier_mobile + '\'' +
                ", shop_id=" + shop_id +
                '}';
    }
}
