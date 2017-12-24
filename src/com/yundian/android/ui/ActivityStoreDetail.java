package com.yundian.android.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.StoreInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by liushenghan on 2017/12/24.
 */

public class ActivityStoreDetail extends BaseActivity {


    private int storeId;

    @OnClick(R.id.image_return)
    public void back() {
        finish();
    }


    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.title)
    TextView title;

    @OnClick(R.id.more)
    public void more() {
        DisplayToast(R.string.function_not_imp);
    }

    @OnClick(R.id.qualification)
    public void qualification() {
        DisplayToast(R.string.function_not_imp);
    }

    @OnClick(R.id.hot_line_layout)
    public void dialing() {
        if (info == null || (TextUtils.isEmpty(info.getSupplier_Phone()) && TextUtils.isEmpty(info.getSupplier_mobile()))) {
            DisplayToast("没有有效信息");
            return;
        }

        final String msg = !TextUtils.isEmpty(info.getSupplier_Phone()) ? info.getSupplier_Phone() : info.getSupplier_mobile();

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityStoreDetail.this, R.style.CustomDialog);

        SpannableString title = new SpannableString("服务热线");
        title.setSpan(new ForegroundColorSpan(Color.parseColor("#3a3a3a")), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new AbsoluteSizeSpan(20, true), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString message = new SpannableString(msg);
        message.setSpan(new ForegroundColorSpan(Color.parseColor("#3a3a3a")), 0, msg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        message.setSpan(new AbsoluteSizeSpan(20, true), 0, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String confirm = "确定";

        String cancle = "取消";

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + msg);
                        intent.setData(data);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog dialog = builder.show();
        Button con = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        con.setTextColor(Color.parseColor("#e60012"));
        con.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        Button can = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        can.setTextColor(Color.parseColor("#3a3a3a"));
        can.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    }

    @BindView(R.id.image)
    ImageView image;


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.hot_line)
    TextView hot_line;
    @BindView(R.id.store_name)
    TextView store_name;
    @BindView(R.id.address)
    TextView address;

    private StoreInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_dsc);
        ButterKnife.bind(this);

        storeId = getIntent().getIntExtra(ActivityStore.STORE_ID, -1);
        if (storeId < 0) {
            finish();
        }
        title.setVisibility(View.GONE);
        sub_title.setText("店铺简介");

    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (info == null) {
            request();
        }
    }

    private void request() {
        if (mWeiboDialog == null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, getString(R.string.loading));
        } else {
            mWeiboDialog.show();
        }
        HttpServer.getShopInfo(TAG, storeId, new GenericCallBack<BaseResponse<List<StoreInfo>>>(new TypeToken<BaseResponse<List<StoreInfo>>>() {
        }.getType()) {
            @Override
            public void onSuccess(Response<BaseResponse<List<StoreInfo>>> response) {
                if (response.body().isOK() && response.body().getInfo().size() > 0) {
                    // 设置信息
                    info = response.body().getInfo().get(0);
                    loadImageNoHostWithError(info.getShop_Img(), image);
                    name.setText(info.getShop_Name());
                    store_name.setText(info.getSupplier_CompanyName());
                    hot_line.setText(!TextUtils.isEmpty(info.getSupplier_Phone()) ? info.getSupplier_Phone() : info.getSupplier_mobile());
                    address.setText(info.getSupplier_Address());
                } else {
                    showDialog(response.body().getMsg());
                }
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }

            @Override
            public void onError(Response<BaseResponse<List<StoreInfo>>> response) {
                super.onError(response);
                showDialog(response.message());
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    private void showDialog(String infos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityStoreDetail.this, R.style.CustomDialog);

        SpannableString title = new SpannableString(getString(R.string.app_name));
        title.setSpan(new ForegroundColorSpan(Color.parseColor("#3a3a3a")), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new AbsoluteSizeSpan(20, true), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String msg = "获取信息失败，请重试！\r错误信息:" + infos;
        SpannableString message = new SpannableString(msg);
        message.setSpan(new ForegroundColorSpan(Color.parseColor("#3a3a3a")), 0, msg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        message.setSpan(new AbsoluteSizeSpan(20, true), 0, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String confirm = "确定";

        String cancle = "取消";

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request();
                    }
                })
                .setNegativeButton(cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog dialog = builder.show();
        Button con = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        con.setTextColor(Color.parseColor("#3a3a3a"));
        con.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        Button can = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        can.setTextColor(Color.parseColor("#3a3a3a"));
        can.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

    }

    public static void startAct(int pid, Activity activity) {
        Intent intent = new Intent(activity, ActivityStoreDetail.class);
        intent.putExtra(ActivityStore.STORE_ID, pid);
        activity.startActivity(intent);
    }
}
