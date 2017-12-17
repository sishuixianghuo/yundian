package com.yundian.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.yundian.android.BuildConfig;
import com.yundian.android.R;


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
    }

    @Override
    protected void initView() {
    }

}
