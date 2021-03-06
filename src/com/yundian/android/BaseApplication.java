package com.yundian.android;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.bean.Address;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.PayMethod;
import com.yundian.android.bean.ProductInfo;
import com.yundian.android.bean.Province;
import com.yundian.android.bean.UserInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.net.RestApi;
import com.yundian.android.utils.SettingUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class BaseApplication extends Application {
    private static BaseApplication mAppApplication;
    private DisplayMetrics metrics = new DisplayMetrics();
    /**
     * 判断用户存在 应该判断info是否为空，每次启动获取用户信息 在
     *
     * @see com.yundian.android.ui.SplashActivity#onCreate(Bundle)
     * @see com.yundian.android.net.HttpServer#getUserinfo(String, String, GenericCallBack)
     */
    private String token;
    private UserInfo info;
    // 存放所有的购物信息
    private List<ProductInfo> shoppingBag = new CopyOnWriteArrayList<>();
    // 存放用户的地址信息
    private List<Address> addresses = new CopyOnWriteArrayList<>();
    // 存放系统支付方式
    private List<PayMethod> payMethods = new CopyOnWriteArrayList<>();
    // 存放 行政区
    private List<Province> cantons = new ArrayList<>();

    /**
     * 获取Application
     */
    public static BaseApplication getApp() {
        return mAppApplication;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mAppApplication = this;
        token = SettingUtils.get(SettingUtils.TOKEN, null);
        RestApi.initOkGO(this);

        // 读取信息初始化购物袋
        if (!TextUtils.isEmpty(token)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getAddress();
                }
            }).start();
        }

        if (payMethods.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getPayMethod();
                }
            }).start();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Type type = new TypeToken<List<Province>>() {
                }.getType();
                HttpServer.getCantons(this.getClass().getName(), new GenericCallBack<List<Province>>(type) {
                    @Override
                    public void onSuccess(Response<List<Province>> response) {
                        cantons = response.body();
                    }

                    @Override
                    public void onError(Response<List<Province>> response) {
                        super.onError(response);
                    }
                });
            }
        }).start();
    }


    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public DisplayMetrics getMetrics() {
        return metrics;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        RestApi.setToken(token);
        if (TextUtils.isEmpty(token)) {
            SettingUtils.remove(SettingUtils.TOKEN);
        } else {
            SettingUtils.set(SettingUtils.TOKEN, token);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getAddress();
                }
            }).start();
        }

    }

    private void getAddress() {

        Type type = new TypeToken<BaseResponse<List<Address>>>() {
        }.getType();
        HttpServer.getAddress(this.getClass().getName(), new GenericCallBack<BaseResponse<List<Address>>>(type) {
            @Override
            public void onSuccess(Response<BaseResponse<List<Address>>> response) {
                if (response.body().isOK()) {
                    addresses.clear();
                    addresses.addAll(response.body().getInfo());
                }
            }
        });

        HttpServer.getUserinfo(this.getClass().getName(), BaseApplication.getApp().getToken(), new GenericCallBack<BaseResponse<List<UserInfo>>>(
                new TypeToken<BaseResponse<List<UserInfo>>>() {
                }.getType()) {
            @Override
            public void onSuccess(Response<BaseResponse<List<UserInfo>>> response) {
                if (response.body().isOK() && response.body().getInfo().size() == 1) {
                    info = response.body().getInfo().get(0);
                }
            }
        });

        if (payMethods.isEmpty()) {
            getPayMethod();
        }
    }

    private void getPayMethod() {
        HttpServer.getPayMethod(this.getClass().getName(), new GenericCallBack<BaseResponse<List<PayMethod>>>(
                new TypeToken<BaseResponse<List<PayMethod>>>() {
                }.getType()) {
            @Override
            public void onSuccess(Response<BaseResponse<List<PayMethod>>> response) {
                if (response.body().isOK() && !response.body().getInfo().isEmpty()) {
                    payMethods.clear();
                    payMethods.addAll(response.body().getInfo());
                }
            }
        });
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public List<ProductInfo> getShoppingBag() {
        return shoppingBag;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public List<PayMethod> getPayMethods() {
        return payMethods;
    }


    public List<Province> getCantons() {
        return cantons;
    }
}
