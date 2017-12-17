package com.yundian.android.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.widgets.ADInfo;
import com.yundian.android.widgets.BannerViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 云电首页
 *
 * @author ShaoZhen-PC
 */
public class Activity_HomePage extends BaseActivity {
    private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_0912519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                DisPlay("setOnRefreshListener");
                refreshlayout.finishRefresh(1500);
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                DisPlay("setOnLoadmoreListener");
                refreshlayout.finishLoadmore(1500);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (view.getTag() != null) {
                    outRect.top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, BaseApplication.getApp().getMetrics());
                }
            }
        });

        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            int imageWidth = 0;
            int footH = 0;
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 1) {
                    ViewHolder holder = new ViewHolder(LayoutInflater.from(Activity_HomePage.this).
                            inflate(R.layout.activity_index_ad, parent, false));
                    return holder;
                } else {
                    ViewHolder holder = new ViewHolder(LayoutInflater.from(Activity_HomePage.this).
                            inflate(R.layout.home_page_item, parent, false));
                    if (imageWidth == 0) {
                        float value = BaseApplication.getApp().getMetrics().widthPixels *.5f - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,7.5f, BaseApplication.getApp().getMetrics());
                        imageWidth = (int)(value);
                        footH = (int)(value * 197 / 518);
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth, imageWidth);
                    holder.image_1.setLayoutParams(params);
                    holder.image_2.setLayoutParams(params);
                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(imageWidth, footH);
                    holder.foot_1.setLayoutParams(params2);
                    holder.foot_2.setLayoutParams(params2);
                    return holder;
                }
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                // 设置轮播图
                if (getItemViewType(position) == 1) {
                    BannerViewPager viewPager = (BannerViewPager) holder.itemView.findViewById(R.id.common_banner_Layout);
                    if (viewPager.getAdapter() == null) {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, BaseApplication.getApp().getMetrics().widthPixels * 388 / 1080);
                        viewPager.setLayoutParams(params);
                        viewPager.setRootView(holder.itemView);
                        viewPager.setVisibility(View.VISIBLE, 20);
                        viewPager.setPagerAdapter(new BannerViewPager.BannerViewPagerAdapter() {
                            @Override
                            public int bannerImageCount() {
                                return imageUrls.length;
                            }

                            @Override
                            public String bannerImageUrlAtIndex(int index) {
                                return imageUrls[index];
                            }

                            @Override
                            public void bannerDidSelectAtIndex(int index) {

                            }
                        });
                    }

                } else {
//                    ((TextView) holder.itemView).setText(String.valueOf(position));
                    loadImage("http://img.yundian777.com/uploadfile/2017591631131943.jpg", holder.image_1);
                    loadImage("http://img.yundian777.com/uploadfile/2017591633434659.jpg", holder.image_2);
                }

            }

            @Override
            public int getItemViewType(int position) {
                // 返回头部信息
                if (position == 0) {
                    return 1;
                }
                return 0;
            }

            @Override
            public int getItemCount() {
                return 20 + 1;
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.page_1)
        View page_1;
        @BindView(R.id.page_2)
        View page_2;

        @BindView(R.id.image_1)
        ImageView image_1;

        @BindView(R.id.image_2)
        ImageView image_2;

        @BindView(R.id.foot_1)
        RelativeLayout foot_1;

        @BindView(R.id.foot_2)
        RelativeLayout foot_2;
        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView.getTag() != null) {
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
