package com.yundian.android.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.OrderInfoDetail;
import com.yundian.android.bean.OrderPdt;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.utils.CommonTools;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liushenghan  Created  on 2017/12/28.
 *         <p>
 *         订单详情
 */

public class ActivityOrderDetail extends BaseActivity {

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

    }


    @OnClick(R.id.image_return)
    public void back() {
        finish();
    }

    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    View more;

    @BindView(R.id.order_id)
    TextView order_id;


    @BindView(R.id.status)
    TextView status;


    @BindView(R.id.name)
    TextView name;


    @BindView(R.id.address)
    TextView address;


    @BindView(R.id.store_name)
    TextView store_name;

    @BindView(R.id.view_contain)
    LinearLayout view_contain;

    @BindView(R.id.pay_method)
    TextView pay_method;


    @BindView(R.id.pay_method_name)
    TextView pay_method_name;


    @BindView(R.id.total_price)
    TextView total_price;


    @BindView(R.id.time)
    TextView time;

    List<OrderPdt> pdts = new CopyOnWriteArrayList<>();

    private static OrderInfoDetail data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        more.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        sub_title.setText("订单详情");
        if (data == null) {
            DisplayToast("数据错误请重试");
            finish();
        }
        setStaticInfo();
        request();
    }


    private void request() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, getString(R.string.loading));
        Type type = new TypeToken<BaseResponse<List<OrderPdt>>>() {
        }.getType();
        HttpServer.getOrderDetail(TAG, data.getOrders_ID(), new GenericCallBack<BaseResponse<List<OrderPdt>>>(type) {
            @Override
            public void onSuccess(Response<BaseResponse<List<OrderPdt>>> response) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                if (response.body().isOK() && !response.body().getInfo().isEmpty()) {
                    pdts.clear();
                    pdts.addAll(response.body().getInfo());

                    setData();

                } else {
                    DisPlay(response.body().getMsg());
                }
            }


            @Override
            public void onError(Response<BaseResponse<List<OrderPdt>>> response) {
                super.onError(response);
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                DisPlay(response.message());
            }
        });
    }


    private void setStaticInfo() {
        order_id.setText(String.format("订单号：%s", data.getOrders_SN()));
        status.setText("待付款");
        name.setText(data.getOrders_Address_Name());
        address.setText(data.getOrders_Address_StreetAddress());
        pay_method_name.setText(data.getOrders_Payway_Name());
        total_price.setText(Html.fromHtml(String.format("<font color=#3a3a3a>合计金额 :</font><font size='10' color=#e60012>￥%.2f</font>", data.getOrders_Total_AllPrice())));
        title.setText(String.format("下单时间：", CommonTools.getDate(CommonTools.getLong(data.getOrders_Addtime()))));
        store_name.setText(data.getShop_Name());
        store_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStore.startAct(data.getOrders_SupplierID(), ActivityOrderDetail.this);
            }
        });
    }

    private void setData() {
        view_contain.removeAllViews();
        for (OrderPdt pdt : pdts) {
            View view = LayoutInflater.from(ActivityOrderDetail.this).
                    inflate(R.layout.order_pdt_item, null, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            loadImageNoHost(pdt.getOrders_Goods_Product_Img(), imageView);
            TextView pdt_name = (TextView) view.findViewById(R.id.pdt_name);
            pdt_name.setText(pdt.getOrders_Goods_Product_Name());
            TextView price = (TextView) view.findViewById(R.id.price);
            price.setText(String.format("x%d", pdt.getOrders_Goods_Amount()));
            TextView amount = (TextView) view.findViewById(R.id.amount);
            amount.setText(String.format("￥ %.2f", pdt.getOrders_Goods_Product_SalePrice() * pdt.getOrders_Goods_Amount()));
            view_contain.addView(view);
        }

    }

    // 确认收货
    public void confirm(View v) {
        DisplayToast(R.string.function_not_imp);
    }

    // 评价
    public void pingjia(View v) {
        DisplayToast(R.string.function_not_imp);
    }

    // 联系卖家
    public void lianxi(View v) {
        // 直接跳转 打你妹的电话啊

        DisplayToast(R.string.function_not_imp);
    }

    // 删除订单
    public void del(View v) {
        DisplayToast(R.string.function_not_imp);
    }


    public static void startActivity(OrderInfoDetail info, BaseActivity activity) {
        data = info;
        activity.openActivity(ActivityOrderDetail.class);
    }

}
