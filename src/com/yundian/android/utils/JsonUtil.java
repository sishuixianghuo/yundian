package com.yundian.android.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 *  @ClassName: JsonUtil  
 * <p>
 *  @Description: Json的工具类
 * <p>
 *  @author ShaoZhen  
 * <p>
 *  @date 2015-2-25 上午11:10:41      
 */
public class JsonUtil {
    public static final String TAG = "JsonUtil.java";
    private static Gson gson = new Gson();

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private JsonUtil() {

    }

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }


    public static String toJson(@NonNull Object obj) {
        if (gson == null) gson = new Gson();
        return gson.toJson(obj);

    }

    public static <T> T jsonToBean(String jsonStr, Class<T> cl) {
        if (gson == null) gson = new Gson();
        return gson.fromJson(jsonStr, cl);
    }

    public static <T> T jsonToBean(String jsonStr, java.lang.reflect.Type type) {
        if (gson == null) gson = new Gson();
        return gson.fromJson(jsonStr, type);
    }


    public static <T> List<T> jsonList(String jsonStr, Class<T> cl) {
        if (gson == null) gson = new Gson();
        return gson.fromJson(jsonStr, new TypeToken<List<T>>() {
        }.getType());
    }


}
