package com.yundian.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.ProductDetail;
import com.yundian.android.bean.ProductInfo;
import com.yundian.android.fragment.EmptyFragment;
import com.yundian.android.fragment.ProductFragment;
import com.yundian.android.fragment.TestFragment;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.widgets.PageHorizontalScrollView;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 产品详情
 *
 * @author ShaoZhen-PC
 */
public class ActivityPdtDetail extends BaseActivity {


    private int pid;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.user_scrollview)
    PageHorizontalScrollView scrollview;


    private List<Fragment> fragments;
    private String[] titleList = new String[]{"商品", "评价", "厂家", "售后"};
    public ProductDetail detail;
    private ProductFragment pay;
    private int amount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        ButterKnife.bind(this);
        pid = getIntent().getIntExtra(SearchActivity.PID, -1);
        if (pid <= 0) {
            finish();
        } else {
            request();
        }

        if (fragments == null || fragments.isEmpty()) {
            fragments = new ArrayList<>();
            Bundle args = new Bundle();
            args.putInt(SearchActivity.PID, pid);
            pay = new ProductFragment();
            pay.setArguments(args);
            TestFragment free = new TestFragment();
            free.setArguments(args);
            EmptyFragment selling = new EmptyFragment();
            selling.setArguments(args);
            EmptyFragment shouhou = new EmptyFragment();
            shouhou.setArguments(args);
            fragments.add(pay);
            fragments.add(free);
            fragments.add(selling);
            fragments.add(shouhou);
            viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragments.get(position);
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return titleList[position];
                }

                @Override
                public int getCount() {
                    return fragments.size();
                }
            });
            viewPager.setOffscreenPageLimit(4);
            scrollview.setViewPager(viewPager);
            scrollview.setTextSize(50);
            scrollview.setTabPaddingLeftRight(24);
        }
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {
    }

    public void back(View v) {
        finish();
    }

    private void request() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中");
        Type type = new TypeToken<BaseResponse<List<ProductDetail>>>() {
        }.getType();
        HttpServer.getProductInfo(TAG, pid, new GenericCallBack<BaseResponse<List<ProductDetail>>>(type) {
            @Override
            public void onSuccess(Response<BaseResponse<List<ProductDetail>>> response) {
                Log.e(TAG, response.body().getInfo().get(0).toString());
                if (response.body().isOK()) {
                    detail = response.body().getInfo().get(0);
                    pay.setData(detail);
                } else {
                    DisplayToast(response.body().getMsg());
                }
                mWeiboDialog.dismiss();
            }

            @Override
            public void onError(Response<BaseResponse<List<ProductDetail>>> response) {
                super.onError(response);
                DisplayToast(response.getException().getMessage());
                mWeiboDialog.dismiss();
            }
        });

    }

    public void contact(View v) {
        DisplayToast("拔打电话");
    }

    public void shop(View v) {
//        DisplayToast("跳转店铺");
        ActivityStore.startAct(detail.getProduct_SupplierID(), this);
    }

    public void buy(View v) {
        ProductInfo info = detail.getPdt();
        info.amount = amount;
        addWithDelPdt2Bag(info, true);
    }


    public void cart(View v) {
//        DisplayToast("去购物车");
        openActivity(Activity_Cart.class);
    }

    public static void startActivity(int pid, Activity activity) {
        Intent intent = new Intent(activity, ActivityPdtDetail.class);
        intent.putExtra(SearchActivity.PID, pid);
        activity.startActivity(intent);
    }
}
       