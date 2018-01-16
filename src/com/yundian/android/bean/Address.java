package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/24.
 */

public class Address implements Serializable {


    private String shouhuoren;
    private int provice;
    private int city;
    private int County;
    private String addr;
    private String mobile;
    private String phone;
    private String email;
    private int id;


    public String getShouhuoren() {
        return shouhuoren;
    }

    public void setShouhuoren(String shouhuoren) {
        this.shouhuoren = shouhuoren;
    }

    public int getProvice() {
        return provice < 1 ? 1 : provice;
    }

    public void setProvice(int provice) {
        this.provice = provice;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCounty() {
        return County;
    }

    public void setCounty(int county) {
        County = county;
    }


    @Override
    public String toString() {
        return "Address{" +
                "shouhuoren='" + shouhuoren + '\'' +
                ", provice=" + provice +
                ", city=" + city +
                ", County=" + County +
                ", addr='" + addr + '\'' +
                ", mobile='" + mobile + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
