package com.yundian.android.utils;

import android.text.TextUtils;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *  @ClassName: JsonUtil  
 * 
 *  @Description: Json的工具类
 * 
 *  @author ShaoZhen  
 * 
 *  @date 2015-2-25 上午11:10:41      
 */
public class JsonUtil {
	public static final String TAG = "JsonUtil.java";
	private static Gson gson = null;
	
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}
	private JsonUtil(){
		
	}
	/**
	 * 解析集合
	 * 
	 * @param responseBody
	 * @param t
	 * @return 返回一个List集合
	 */
	public static <T> List<T> parseList(String responseBody,
                                        TypeToken<ArrayList<T>> typeToken) {
		LogUtils.d(TAG, "JSON  parseList() responseBody = " + responseBody);
		if (TextUtils.isEmpty(responseBody)) {
			return null;
		}
		Gson gson = new Gson();
		Type listType = typeToken.getType();
		List<T> beans = gson.fromJson(responseBody, listType);
		return beans;
	}

	/**
	 * 解析对象
	 * 
	 * @param responseBody
	 * @param t
	 * @return
	 */
	public static <T> T parseObject(String responseBody, TypeToken<T> typeToken) {
		LogUtils.d(TAG, "JSON  parseObject() responseBody = " + responseBody);
		if (TextUtils.isEmpty(responseBody)) {
			return null;
		}
		Gson gson = new Gson();
		Type type = typeToken.getType();
		T bean = gson.fromJson(responseBody, type);
		return bean;
	}

	/**
	 * 将对象转为json
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if (null != obj) {
			Gson gson = new Gson();
			return gson.toJson(obj);
		} else {
			return "";
		}
	}
	
	/**
	 * 将json格式转换成list对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr) {
		List<?> objList = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
			}.getType();
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}

	/**
	 * 将json转换成bean对象
	 *
	 * @param jsonStr
	 * @return
	 * @throws SimpleException
	 */
	public static <T> T jsonToBean(String jsonStr, Class<T> cl)
			throws SimpleException {
		T obj = null;
		if (gson != null) {
			try {
				obj = gson.fromJson(jsonStr, cl);
			} catch (JsonSyntaxException e) {
				throw SimpleException.newInstance("服务器异常", e);
			}
		}
		return obj;
	}

	public static Object jsonToBean(String jsonStr, java.lang.reflect.Type type)
			throws SimpleException {
		Object obj = null;
		if (gson != null) {
			try {
				obj = gson.fromJson(jsonStr, type);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				throw SimpleException.newInstance("服务器异常", e);
			}
		}
		return obj;
	}
	
}
