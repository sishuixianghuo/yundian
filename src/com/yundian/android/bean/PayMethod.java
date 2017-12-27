package com.yundian.android.bean;

import java.io.Serializable;

/**
 * Created by liushenghan on 2017/12/26.
 */

public class PayMethod implements Serializable {

    private String Pay_Way_Name;
    private int Pay_Way_ID;

    public String getPay_Way_Name() {
        return Pay_Way_Name;
    }

    public void setPay_Way_Name(String pay_Way_Name) {
        Pay_Way_Name = pay_Way_Name;
    }

    public int getPay_Way_ID() {
        return Pay_Way_ID;
    }

    public void setPay_Way_ID(int pay_Way_ID) {
        Pay_Way_ID = pay_Way_ID;
    }
}
