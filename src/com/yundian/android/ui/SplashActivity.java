package com.yundian.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.BaseApplication;
import com.yundian.android.BuildConfig;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.UserInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;

import java.util.List;


public class SplashActivity extends BaseActivity {

    public static final String TAG = SplashActivity.class.getSimpleName();

    private ImageView mSplashItem_iv = null;

    @Override
    protected void findViewById() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        HomeActivity.class); // 从启动动画ui跳转到主ui
                startActivity(intent);
                finish(); // 结束启动动画界面
            }
        }, BuildConfig.DEBUG ? 0 : 3000); // 启动动画持续3秒钟

        // 获取用户的信息
        if (!TextUtils.isEmpty(BaseApplication.getApp().getToken())) {
            HttpServer.getUserinfo(TAG, BaseApplication.getApp().getToken(), new GenericCallBack<BaseResponse<List<UserInfo>>>(
                    new TypeToken<BaseResponse<List<UserInfo>>>() {
                    }.getType()) {
                @Override
                public void onSuccess(Response<BaseResponse<List<UserInfo>>> response) {
                    if (response.body().isOK() && response.body().getInfo().size() == 1) {
                        BaseApplication.getApp().setInfo(response.body().getInfo().get(0));
                    }
                }
            });
        }
    }

    @Override
    protected void initView() {
    }

}
