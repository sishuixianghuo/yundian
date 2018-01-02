package com.yundian.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.ProductDetail;
import com.yundian.android.bean.ProductInfo;
import com.yundian.android.bean.StoreInfo;
import com.yundian.android.fragment.EmptyFragment;
import com.yundian.android.fragment.ProducdAppraiseFragment;
import com.yundian.android.fragment.ProductFragment;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.utils.CommonTools;
import com.yundian.android.utils.NetWorkUtil;
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

    @BindView(R.id.number)
    TextView number;


    private List<Fragment> fragments;
    private String[] titleList = new String[]{"商品", "评价", "厂家", "售后"};
    public ProductDetail detail;
    private ProductFragment pay;
    private int amount = 1;
    private StoreInfo info;
    private int shop_id;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        ButterKnife.bind(this);
        pid = getIntent().getIntExtra(SearchActivity.PID, -1);
        shop_id = getIntent().getIntExtra(SearchActivity.SHOP_ID, -1);
        if (pid <= 0 || shop_id <= 0) {
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
            ProducdAppraiseFragment appraise = new ProducdAppraiseFragment();
            appraise.setArguments(args);
            EmptyFragment selling = new EmptyFragment();
            selling.setArguments(args);
            EmptyFragment shouhou = new EmptyFragment();
            shouhou.setArguments(args);
            fragments.add(pay);
            fragments.add(appraise);
//            fragments.add(selling);
//            fragments.add(shouhou);
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
            viewPager.setOffscreenPageLimit(fragments.size());
            scrollview.setViewPager(viewPager);
            scrollview.setTextSize(50);
            scrollview.setTabPaddingLeftRight(24);
        }

        for (ProductInfo info : BaseApplication.getApp().getShoppingBag()) {
            count += info.amount;
        }
        number.setText(count > 99 ? "99+" : String.valueOf(count));
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {
    }


    @Override
    public void addWithDelPdt2Bag(ProductInfo pdt, boolean isAdd) {
        super.addWithDelPdt2Bag(pdt, isAdd);
        if (pdt.getG_mPrice() > CommonTools.THRESHOLD_PRICE) {
            number.setText(++count > 99 ? "99+" : String.valueOf(count));
        }
    }

    public void back(View v) {
        finish();
    }

    private void request() {
        if (!NetWorkUtil.isNetConnected(this)) {
            DisplayToast(R.string.no_net_work);
            return;
        }
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中");
        HttpServer.getShopInfo(TAG, shop_id, new GenericCallBack<BaseResponse<List<StoreInfo>>>(new TypeToken<BaseResponse<List<StoreInfo>>>() {
        }.getType()) {
            @Override
            public void onSuccess(Response<BaseResponse<List<StoreInfo>>> response) {
                if (response.body().isOK() && response.body().getInfo().size() > 0) {
                    // 设置信息
                    info = response.body().getInfo().get(0);
                } else {
                }
            }
        });

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
        if (info == null || (TextUtils.isEmpty(info.getSupplier_Phone()) && TextUtils.isEmpty(info.getSupplier_mobile()))) {
            DisplayToast("数据错误");
            return;
        }

        final String msg = !TextUtils.isEmpty(info.getSupplier_Phone()) ? info.getSupplier_Phone() : info.getSupplier_mobile();

        WeiboDialogUtils.CallPhone(this, msg);
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

    /**
     * @param v
     * @see HomeActivity#onNewIntent(Intent)
     */
    public void cart(View v) {
//        DisplayToast("去购物车");
        // openActivity(HomeActivity.class); 在
        Activity_Cart.startActivity(TAG, this);
    }

    /**
     * 商品id 店铺id
     *
     * @param pid
     * @param shopid
     * @param activity
     */
    public static void startActivity(int pid, int shopid, Activity activity) {
        Intent intent = new Intent(activity, ActivityPdtDetail.class);
        intent.putExtra(SearchActivity.PID, pid);
        intent.putExtra(SearchActivity.SHOP_ID, shopid);
        activity.startActivity(intent);
    }
}
       