package com.yundian.android.net;

import com.lzy.okgo.OkGo;

/**
 * Created by liushenghan on 2017/12/16.
 */

public final class HttpServer {

    public static final String HOST = "http://app.yundian777.com";
    public static final String URL_CAT = HOST + "/WebService.asmx/getProductCatogryList";

    private HttpServer() {
        throw new RuntimeException("禁止创建对象");
    }

    public static <T> void getCategory(String tag, GenericCallBack<T> callback) {
        OkGo.<T>post(URL_CAT).tag(tag).execute(callback);
    }


}
