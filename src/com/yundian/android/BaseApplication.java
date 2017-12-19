package com.yundian.android;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.yundian.android.bean.UserInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.RestApi;
import com.yundian.android.utils.SettingUtils;


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
        RestApi.initOkGO(this);
        token = SettingUtils.get(SettingUtils.TOKEN, null);

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
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }
}
