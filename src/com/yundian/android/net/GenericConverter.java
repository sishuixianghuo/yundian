package com.yundian.android.net;

import com.lzy.okgo.convert.Converter;
import com.yundian.android.utils.JsonUtil;

import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by liushenghan on 2017/12/11.
 */

public class GenericConverter<T> implements Converter<T> {

    private String TAG = this.getClass().getSimpleName();
    private Class<T> tClass;


    private Type type;

    GenericConverter(Class<T> clazz) {
        tClass = clazz;
    }

    GenericConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        if (!response.isSuccessful()) {
            if (response.code() >= 400 && response.code() < 500) {
                throw new RuntimeException("接口未找到");
            }
            if (response.code() >= 500) {
                throw new RuntimeException("服务器错误");
            }
            return null;
        }
        String json = response.body().string();
        if (tClass != null) {
            return JsonUtil.jsonToBean(json, tClass);
        }
        if (type != null) {
            return JsonUtil.jsonToBean(json, type);
        }
        throw new IllegalAccessException("请传入正确的class或者type信息");
    }

}
