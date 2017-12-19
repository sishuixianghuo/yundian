package com.yundian.android.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.UserInfo;

import java.util.List;

/**
 * Created by liushenghan on 2017/12/16.
 */

public final class HttpServer {
    final static String TAG = "HttpServer";

    public static final String HOST = "http://app.yundian777.com";
    public static final String HOST_IMG = "http://img.yundian777.com";
    public static final String URL_CAT = HOST + "/WebService.asmx/getProductCatogryList";
    public static final String GET_PRODUCT = HOST + "/WebService.asmx/getProductList";
    public static final String GET_PRODUCT_INFO = HOST + "/WebService.asmx/getProductInfo";
    public static final String USER_LOGIN = HOST + "/WebService.asmx/Login";
    public static final String USER_REG = HOST + "/WebService.asmx/Reg";
    public static final String GET_USER_INFO = HOST + "/WebService.asmx/getUserInfo";

    private HttpServer() {
        throw new RuntimeException("禁止创建对象");
    }

    // 获取分类信息
    public static <T> void getCategory(String tag, GenericCallBack<T> callback) {
        OkGo.<T>post(URL_CAT).tag(tag).execute(callback);
    }

    //获取首页条目信息

    /**
     * * @param pid 分类id  不传为全部 ,传了为对应的分类
     *
     * @param page   默认每页20  从 1 开始
     * @param shopid 如果查某个店铺下面的商品。shop_id为商家ID号。ipage为页码，其它可以不填。
     * @param <T>
     */
    public static <T> void getHomePageItem(String tag, String pid, int page, int shopid, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strP", TextUtils.isEmpty(pid) ? "" : pid);
        params.put("strS", "");
        params.put("strT", "");
        params.put("keyword", "");
        params.put("IPage", page);
        params.put("shopid", shopid);

        OkGo.<T>post(GET_PRODUCT).params(params).
                tag(tag).execute(callback);
    }

    //获取商品详情
    public static <T> void getProductInfo(String tag, int pid, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strP", pid);
        params.put("strS", "");
        params.put("strT", "");
        params.put("keyword", "");
        params.put("IPage", 0);
        params.put("shopid", 0);

        OkGo.<T>post(GET_PRODUCT).params(params).
                tag(tag).execute(callback);
    }


    //登陆
    public static <T> void login(String tag, @NonNull String name, @NonNull String pwd, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strUserName", name);
        params.put("strUserPwd", pwd);
        OkGo.<T>post(USER_LOGIN).params(params).
                tag(tag).execute(callback);
    }


    //获取用户信息异步
    public static <T> void getUserinfo(String tag, String token, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("token", token);
        OkGo.<T>post(GET_USER_INFO).params(params).
                tag(tag).execute(callback);
    }

    //获取用户信息同步
    public static BaseResponse<List<UserInfo>> getUserinfo(String token) {
        HttpParams params = new HttpParams();
        params.put("token", token);
        Call<BaseResponse<List<UserInfo>>> call = OkGo.<BaseResponse<List<UserInfo>>>post(GET_USER_INFO).params(params).converter(new GenericConverter<BaseResponse<List<UserInfo>>>(new TypeToken<BaseResponse<List<UserInfo>>>() {
        }.getType())).adapt();
        try {
            Response<BaseResponse<List<UserInfo>>> response = call.execute();
            Log.e(TAG, "body = " + response.body());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


    //获取用户信息同步
    public static <T> void register(String tag, String email, String nick, String pwd, String phone, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strUserName", email);
        params.put("strUserPwd", pwd);
        params.put("strNickName", nick);
        params.put("strPhone", phone);
        OkGo.<T>post(USER_REG).params(params).tag(tag).execute(callback);
    }

}
