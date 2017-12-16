package com.yundian.android;

import android.app.Application;
import android.content.res.Configuration;

import com.lzy.okgo.OkGo;
import com.yundian.android.config.Constants;
import com.yundian.android.image.ImageLoaderConfig;


public class BaseApplication extends Application {
	private static BaseApplication mAppApplication;
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
		ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);

		OkGo.getInstance().init(this);
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
}
