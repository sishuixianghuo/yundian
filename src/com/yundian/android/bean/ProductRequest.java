package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/18.
 */

public class ProductRequest implements Serializable {
    /**
     * 1、	产品分类三个都为空的情况下是查询全部产品；
     * 2、	一级分类不为空，二、三级分类为空查询一级分类产品；
     * 3、	一、二级分类不为空，三级分类为空查询二级分类产品
     * 4、	一、二、三级分类都不为空查询三级分类产品
     */
    //一级分类编号
    private String strP;

    //二级产品分类编号
    private String strS;

    //三级产品分类编号
    private String strT;

    private int iPage;
    private int shopid;
    private String keyword;

    public String getStrP() {
        return strP;
    }

    public void setStrP(String strP) {
        this.strP = strP;
    }

    public String getStrS() {
        return strS;
    }

    public void setStrS(String strS) {
        this.strS = strS;
    }

    public String getStrT() {
        return strT;
    }

    public void setStrT(String strT) {
        this.strT = strT;
    }

    public int getiPage() {
        return iPage;
    }

    public void setiPage(int iPage) {
        this.iPage = iPage;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "strP='" + strP + '\'' +
                ", strS='" + strS + '\'' +
                ", strT='" + strT + '\'' +
                ", iPage=" + iPage +
                ", shopid=" + shopid +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
