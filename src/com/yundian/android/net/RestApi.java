package com.yundian.android.net;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.yundian.android.BaseApplication;
import com.yundian.android.BuildConfig;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;


/**
 * @author liushenghan
 *         <p>
 *         <p>
 *         OKGO 初始化
 *         https://github.com/jeasonlzy/okhttp-OkGo/wiki/Init
 */

public class RestApi {

    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();

    static {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
//        loggingInterceptor.setPrintLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    private RestApi() {
    }

    public static void initOkGO(Application context) {
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", "application/json;charset=utf-8");
        //header不支持中文
        //应用版本号
        headers.put("app-version", BuildConfig.VERSION_NAME);
        //系统版本号
        headers.put("os-version", String.valueOf(Build.VERSION.SDK_INT));
        headers.put("device-model", Build.MODEL);
        headers.put("device-brand", Build.BRAND);
        headers.put("device-SERIAL", Build.SERIAL);
        headers.put("package-name", context.getPackageName());
        headers.put("version", BuildConfig.VERSION_NAME);
        headers.put("resource", "android");
        HttpParams params = new HttpParams();
        if (!TextUtils.isEmpty(BaseApplication.getApp().getToken())) {
            params.put("token", BaseApplication.getApp().getToken());
        }

        //必须调用初始化
        OkGo.getInstance().init(context);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    .setCacheMode(CacheMode.NO_CACHE)
                    .setOkHttpClient(builder.build())
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    // 设置重连次数具体看源码
                    .setRetryCount(0)
                    .addCommonHeaders(headers)
                    .addCommonParams(params);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setToken(String token) {
        OkGo.getInstance().addCommonParams(new HttpParams("token", token));
    }


}
