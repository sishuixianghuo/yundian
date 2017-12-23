package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/20.
 * <href >https://www.zybuluo.com/homeplay/note/975293</href>
 */

public class ProductDetail implements Serializable {

//    Product_SupplierID - 店铺ID号，
//    Product_Code - 商品编号，
//    Product_Addtime - 更新时间，
//    Product_Intro - 产品详情，
//    Product_Weight - 商品重量，
//    Product_Hits - 浏览量，
//    Product_Img - 缩略图，
//    Product_IsInsale - 是否上架，
//    Product_StockAmount - 库存数量，
//    Product_Name - 产品名称，
//    Product_Brand - 商品品牌，
//    Product_Price - 产品价格，
//    Product_Img1 - 产品详情图，
//    Product_Yongtu - 商品用途，
//    Product_Tiji - 商品体积，
//    Product_Xh - 商品型号，
//    Product_Phone - 商家电话

    private int Product_ID;
    private String Product_Code;
    private int Product_CateID;
    private int Product_BrandID;
    private String Brand_Name;
    private String Supplier_Phone;
    // 店铺id
    private int Product_SupplierID;
    private String Product_Name;
    private float Product_Price;
    private String Product_Note;
    private int Product_Weight;
    private String Product_Img;
    private int Product_StockAmount;
    private int Product_SaleAmount;
    private int Product_Review_Count;
    private int Product_Review_ValidCount;
    private int Product_Review_Average;
    private int Product_IsInsale;
    // 详情
    private String Product_Intro;
    private int Product_Hits;
    private int Product_Sort;
    private String Product_Addtime;
    private String Product_Img1;
    private String Product_Yongtu;
    private String Product_Tiji;
    private String Product_Xh;

    public int getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(int product_ID) {
        Product_ID = product_ID;
    }

    public String getProduct_Code() {
        return Product_Code;
    }

    public void setProduct_Code(String product_Code) {
        Product_Code = product_Code;
    }

    public int getProduct_CateID() {
        return Product_CateID;
    }

    public void setProduct_CateID(int product_CateID) {
        Product_CateID = product_CateID;
    }

    public int getProduct_BrandID() {
        return Product_BrandID;
    }

    public void setProduct_BrandID(int product_BrandID) {
        Product_BrandID = product_BrandID;
    }

    public String getBrand_Name() {
        return Brand_Name;
    }

    public void setBrand_Name(String brand_Name) {
        Brand_Name = brand_Name;
    }

    public String getSupplier_Phone() {
        return Supplier_Phone;
    }

    public void setSupplier_Phone(String supplier_Phone) {
        Supplier_Phone = supplier_Phone;
    }

    public int getProduct_SupplierID() {
        return Product_SupplierID;
    }

    public void setProduct_SupplierID(int product_SupplierID) {
        Product_SupplierID = product_SupplierID;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public float getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(float product_Price) {
        Product_Price = product_Price;
    }

    public String getProduct_Note() {
        return Product_Note;
    }

    public void setProduct_Note(String product_Note) {
        Product_Note = product_Note;
    }

    public int getProduct_Weight() {
        return Product_Weight;
    }

    public void setProduct_Weight(int product_Weight) {
        Product_Weight = product_Weight;
    }

    public String getProduct_Img() {
        return Product_Img;
    }

    public void setProduct_Img(String product_Img) {
        Product_Img = product_Img;
    }

    public int getProduct_StockAmount() {
        return Product_StockAmount;
    }

    public void setProduct_StockAmount(int product_StockAmount) {
        Product_StockAmount = product_StockAmount;
    }

    public int getProduct_SaleAmount() {
        return Product_SaleAmount;
    }

    public void setProduct_SaleAmount(int product_SaleAmount) {
        Product_SaleAmount = product_SaleAmount;
    }

    public int getProduct_Review_Count() {
        return Product_Review_Count;
    }

    public void setProduct_Review_Count(int product_Review_Count) {
        Product_Review_Count = product_Review_Count;
    }

    public int getProduct_Review_ValidCount() {
        return Product_Review_ValidCount;
    }

    public void setProduct_Review_ValidCount(int product_Review_ValidCount) {
        Product_Review_ValidCount = product_Review_ValidCount;
    }

    public int getProduct_Review_Average() {
        return Product_Review_Average;
    }

    public void setProduct_Review_Average(int product_Review_Average) {
        Product_Review_Average = product_Review_Average;
    }

    public int getProduct_IsInsale() {
        return Product_IsInsale;
    }

    public void setProduct_IsInsale(int product_IsInsale) {
        Product_IsInsale = product_IsInsale;
    }

    public String getProduct_Intro() {
        return Product_Intro;
    }

    public void setProduct_Intro(String product_Intro) {
        Product_Intro = product_Intro;
    }

    public int getProduct_Hits() {
        return Product_Hits;
    }

    public void setProduct_Hits(int product_Hits) {
        Product_Hits = product_Hits;
    }

    public int getProduct_Sort() {
        return Product_Sort;
    }

    public void setProduct_Sort(int product_Sort) {
        Product_Sort = product_Sort;
    }

    public String getProduct_Addtime() {
        return Product_Addtime;
    }

    public void setProduct_Addtime(String product_Addtime) {
        Product_Addtime = product_Addtime;
    }

    public String getProduct_Img1() {
        return Product_Img1;
    }

    public void setProduct_Img1(String product_Img1) {
        Product_Img1 = product_Img1;
    }

    public String getProduct_Yongtu() {
        return Product_Yongtu;
    }

    public void setProduct_Yongtu(String product_Yongtu) {
        Product_Yongtu = product_Yongtu;
    }

    public String getProduct_Tiji() {
        return Product_Tiji;
    }

    public void setProduct_Tiji(String product_Tiji) {
        Product_Tiji = product_Tiji;
    }

    public String getProduct_Xh() {
        return Product_Xh;
    }

    public void setProduct_Xh(String product_Xh) {
        Product_Xh = product_Xh;
    }


    public ProductInfo getPdt() {
        ProductInfo info = new ProductInfo();
        info.setG_ID(getProduct_ID());
        info.setG_mc(getProduct_Name());
        info.setG_mPrice(getProduct_Price());
        return info;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "Product_ID=" + Product_ID +
                ", Product_Code='" + Product_Code + '\'' +
                ", Product_CateID=" + Product_CateID +
                ", Product_BrandID=" + Product_BrandID +
                ", Brand_Name='" + Brand_Name + '\'' +
                ", Supplier_Phone='" + Supplier_Phone + '\'' +
                ", Product_SupplierID=" + Product_SupplierID +
                ", Product_Name='" + Product_Name + '\'' +
                ", Product_Price=" + Product_Price +
                ", Product_Note='" + Product_Note + '\'' +
                ", Product_Weight=" + Product_Weight +
                ", Product_Img='" + Product_Img + '\'' +
                ", Product_StockAmount=" + Product_StockAmount +
                ", Product_SaleAmount=" + Product_SaleAmount +
                ", Product_Review_Count=" + Product_Review_Count +
                ", Product_Review_ValidCount=" + Product_Review_ValidCount +
                ", Product_Review_Average=" + Product_Review_Average +
                ", Product_IsInsale=" + Product_IsInsale +
                ", Product_Intro='" + Product_Intro + '\'' +
                ", Product_Hits=" + Product_Hits +
                ", Product_Sort=" + Product_Sort +
                ", Product_Addtime='" + Product_Addtime + '\'' +
                ", Product_Img1='" + Product_Img1 + '\'' +
                ", Product_Yongtu='" + Product_Yongtu + '\'' +
                ", Product_Tiji='" + Product_Tiji + '\'' +
                ", Product_Xh='" + Product_Xh + '\'' +
                '}';
    }
}
