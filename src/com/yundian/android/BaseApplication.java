package com.yundian.android;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.yundian.android.net.RestApi;


public class BaseApplication extends Application {
	private static BaseApplication mAppApplication;
	private DisplayMetrics metrics = new DisplayMetrics();

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

}
