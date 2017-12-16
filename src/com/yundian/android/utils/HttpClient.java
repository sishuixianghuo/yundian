package com.yundian.android.utils;

import com.yundian.android.bean.Configer;


public class HttpClient {

	public HttpClient() {
		if (Configer.DEBUG) {
			enableDebug();
		}
	}
	
//啓動調試模式
	private void enableDebug() {
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(
				java.util.logging.Level.FINEST);
		java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(
				java.util.logging.Level.FINER);
		java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(
				java.util.logging.Level.OFF);
		
	}
	
	
}
