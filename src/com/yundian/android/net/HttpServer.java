package com.yundian.android.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.yundian.android.bean.Address;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.OrderInfo;
import com.yundian.android.bean.UserInfo;

import java.util.List;

/**
 * Created by liushenghan on 2017/12/16.
 */

public final class HttpServer {
    final static String TAG = "HttpServer";

    public static final String HOST = "http://app.yundian777.com";
    public static final String HOST_IMG = "http://img.yundian777.com";
    public static final String URL_CAT = HOST + "/WebService.asmx/getProductCatogryList";
    public static final String GET_PRODUCT = HOST + "/WebService.asmx/getProductList";
    public static final String GET_PRODUCT_INFO = HOST + "/WebService.asmx/getProductInfo";
    public static final String USER_LOGIN = HOST + "/WebService.asmx/Login";
    public static final String USER_REG = HOST + "/WebService.asmx/Reg";
    public static final String GET_USER_INFO = HOST + "/WebService.asmx/getUserInfo";
    public static final String GET_STORE_INFO = HOST + "/WebService.asmx/getShopInfo";
    public static final String ADD_USER_ADD = HOST + "/WebService.asmx/AddUserAddress";
    public static final String GET_ADDRESS = HOST + "/WebService.asmx/getUserAddress";
    public static final String GET_DELIVERY = HOST + "/WebService.asmx/Get_SysDelivery";
    public static final String GET_PAY_WAY = HOST + "/WebService.asmx/Get_SysPayWay";
    public static final String SUBMIT_ORDER = HOST + "/WebService.asmx/DingDanSave";
    public static final String GET_USER_ORDER = HOST + "/WebService.asmx/getUserOrder";
    public static final String CONTACT_US = "http://www.yundian777.com/HelpCenter/ContactUs.aspx";

    private HttpServer() {
        throw new RuntimeException("禁止创建对象");
    }

    // 获取分类信息
    public static <T> void getCategory(String tag, GenericCallBack<T> callback) {
        OkGo.<T>post(URL_CAT).tag(tag).execute(callback);
    }

    //获取首页条目信息

    /**
     * * @param pid 分类id  不传为全部 ,传了为对应的分类
     *
     * @param page   默认每页20  从 1 开始
     * @param shopid 如果查某个店铺下面的商品。shop_id为商家ID号。ipage为页码，其它可以不填。
     * @param <T>
     */
    public static <T> void getHomePageItem(String tag, int pid, int page, int shopid, String key, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strP", pid < 0 ? "" : String.valueOf(pid));
        params.put("strS", "");
        params.put("strT", "");
        params.put("keyword", TextUtils.isEmpty(key) ? "" : key);
        params.put("IPage", page);
        params.put("shopid", shopid);

        OkGo.<T>post(GET_PRODUCT).params(params).
                tag(tag).execute(callback);
    }

    /**
     * 获取 店铺商品信息 pid 不填为所有  shopid 店铺id
     *
     * @param tag
     * @param pid
     * @param shopid
     * @param callback
     * @param <T>
     */
    public static <T> void getShopItem(String tag, int pid, int shopid, int page, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strP", pid < 0 ? "" : String.valueOf(pid));
        params.put("strS", "");
        params.put("strT", "");
        params.put("keyword", "");
        params.put("IPage", page);
        params.put("shopid", shopid);

        OkGo.<T>post(GET_PRODUCT).params(params).
                tag(tag).execute(callback);
    }

    //获取商品详情
    public static <T> void getProductInfo(String tag, int pid, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strP", pid);
        OkGo.<T>post(GET_PRODUCT_INFO).params(params).
                tag(tag).execute(callback);
    }


    //登陆
    public static <T> void login(String tag, @NonNull String name, @NonNull String pwd, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strUserName", name);
        params.put("strUserPwd", pwd);
        OkGo.<T>post(USER_LOGIN).params(params).
                tag(tag).execute(callback);
    }


    //获取用户信息异步
    public static <T> void getUserinfo(String tag, String token, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("token", token);
        OkGo.<T>post(GET_USER_INFO).params(params).
                tag(tag).execute(callback);
    }

    //获取用户信息同步
    public static BaseResponse<List<UserInfo>> getUserinfo(String token) {
        HttpParams params = new HttpParams();
        params.put("token", token);
        Call<BaseResponse<List<UserInfo>>> call = OkGo.<BaseResponse<List<UserInfo>>>post(GET_USER_INFO).params(params).converter(new GenericConverter<BaseResponse<List<UserInfo>>>(new TypeToken<BaseResponse<List<UserInfo>>>() {
        }.getType())).adapt();
        try {
            Response<BaseResponse<List<UserInfo>>> response = call.execute();
            Log.e(TAG, "body = " + response.body());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


    //获取注册账号
    public static <T> void register(String tag, String email, String nick, String pwd, String phone, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strUserName", email);
        params.put("strUserPwd", pwd);
        params.put("strNickName", nick);
        params.put("strPhone", "");
        OkGo.<T>post(USER_REG).params(params).tag(tag).execute(callback);
    }


    //更新用户信息接口比较错乱
    public static <T> void updateUser(String tag, UserInfo info, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("strUserName", info.getEmail());
//        params.put("strUserPwd", pwd);
//        params.put("strNickName", nick);
        params.put("strPhone", "");
        OkGo.<T>post(USER_REG).params(params).tag(tag).execute(callback);
    }


    //获取店铺信息
    public static <T> void getShopInfo(String tag, int pid, GenericCallBack<T> callback) {
        OkGo.<T>post(GET_STORE_INFO).params("ShopID", pid).tag(tag).execute(callback);
    }

    // 获取用户地址
    public static <T> void getAddress(String tag, GenericCallBack<T> callback) {
        OkGo.<T>post(GET_ADDRESS).tag(tag).execute(callback);
    }

    // 添加用户地址
    public static <T> void addUserAdd(String tag, Address address, GenericCallBack<T> callback) {
        HttpParams params = new HttpParams();
        params.put("shouhuoren", address.getShouhuoren());
        params.put("provice", address.getProvice());
        params.put("city", address.getCity());
        params.put("addr", address.getAddr());
        params.put("mobile", address.getMobile());
        params.put("phone", address.getPhone());
        params.put("email", address.getEmail());
        OkGo.<T>post(ADD_USER_ADD).params(params).tag(tag).execute(callback);
    }


    //获取店铺快递方式
    public static <T> void getDeliveryMethod(String tag, int pid, GenericCallBack<T> callback) {
        OkGo.<T>post(GET_DELIVERY).params("supplierid", pid).tag(tag).execute(callback);
    }

    //获取店铺快递方式
    public static <T> void getPayMethod(String tag, GenericCallBack<T> callback) {
        OkGo.<T>post(GET_PAY_WAY).tag(tag).execute(callback);
    }

    //提交订单
    public static <T> void submitOrder(String tag, OrderInfo order, GenericCallBack<T> callback) {
        /// DingDanSave 添加订单
        /// </summary>
        /// <param name="token"></param>
        /// <param name="Orders_Address_ID">收货地址ID(关联收货地址主键)</param>
        /// <param name="Orders_Note">订单备注</param>
        /// <param name="Orders_Payway">付款方式ID</param>
        /// <param name="Orders_SupplierID">商家ID</param>
        /// <param name="Delivery_Way_ID">配送方式ID</param>
        /// <param name="product_id">购物车产品ID(多个产品以#相连)</param>
        /// <param name="product_amount">产品数量(多个数量以#相连)</param>
        /// <param name="total_price">产品总价</param>
        /// <param name="total_priceFreight">运费总价</param>
        /// <param name="total_allprice">订单总价</param>
        HttpParams params = new HttpParams();
        params.put("Orders_Address_ID", order.getOrders_Address_ID());
        params.put("Orders_Note", order.getOrders_Note());
        params.put("Orders_Payway", order.getOrders_Payway());
        params.put("Orders_SupplierID", order.getOrders_SupplierID());
        params.put("Delivery_Way_ID", order.getDelivery_Way_ID());
        params.put("product_id", order.getProduct_id());
        params.put("product_amount", order.getProduct_amount());
        params.put("total_price", order.getTotal_price());
        params.put("total_priceFreight", order.getTotal_priceFreight());
        params.put("total_allprice", order.getTotal_allprice());
        OkGo.<T>post(SUBMIT_ORDER).tag(tag).params(params).execute(callback);
    }

    /*获取用户订单*/
    public static <T> void getUserOrder(String tag, GenericCallBack<T> callback) {
        OkGo.<T>post(GET_USER_ORDER).tag(tag).execute(callback);
    }
}
