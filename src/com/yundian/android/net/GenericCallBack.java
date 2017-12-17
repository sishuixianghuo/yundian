package com.yundian.android.net;

import android.util.Log;

import com.lzy.okgo.callback.AbsCallback;
import com.yundian.android.utils.JsonUtil;

import java.lang.reflect.Type;

/**
 * Created by liushenghan on 2017/12/16.
 */

public abstract class GenericCallBack<T> extends AbsCallback<T> {


    private Class<T> tClass;

    private Type type;

    public GenericCallBack(Class<T> clazz) {
        tClass = clazz;
    }

    public GenericCallBack(Type type) {
        this.type = type;
    }


    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        String body = response.body().string();
        Log.e("GenericCallBack", " body = " + body);
        if (tClass != null) {
            return JsonUtil.jsonToBean(body, tClass);
        }
        if (type != null) {
            return JsonUtil.jsonToBean(body, type);
        }
        return null;
    }
}
