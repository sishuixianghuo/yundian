package com.yundian.android.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.ProductInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.utils.CommonTools;
import com.yundian.android.widgets.ADInfo;
import com.yundian.android.widgets.BannerViewPager;
import com.yundian.android.widgets.HomePageSelectView;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private PopupWindow popupWindow;
    RecyclerView.Adapter adapter;
    private List<ProductInfo> productInfos = new CopyOnWriteArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        findViewById();
        initView();
        requestInfo();
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                indexPage = 1;
                requestInfo();
            }
        });

        // 去xml 放开
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                indexPage++;
                requestInfo();
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

        recyclerView.setAdapter(adapter = new RecyclerView.Adapter<ViewHolder>() {
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
                        float value = BaseApplication.getApp().getMetrics().widthPixels * .5f - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7.5f, BaseApplication.getApp().getMetrics());
                        imageWidth = (int) (value);
                        footH = (int) (value * 197 / 518);
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
                    initHeadView(holder);
                } else {
                    int index = position * 2 - 2;
                    ProductInfo info = productInfos.get(index);
                    setItemData(holder, info, (index + 1) < productInfos.size() ? productInfos.get(index + 1) : null, Activity_HomePage.this);
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
                return (productInfos.size() + 1) / 2 + 1;
            }
        });


    }

    private void initHeadView(final ViewHolder holder) {
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
            final View page_contain = holder.itemView.findViewById(R.id.page_contain);
            final HomePageSelectView first = (HomePageSelectView) holder.itemView.findViewById(R.id.page_first);
            first.getSelect().setText("品牌");
            final HomePageSelectView second = (HomePageSelectView) holder.itemView.findViewById(R.id.page_second);
            second.getSelect().setText("用途");
            final HomePageSelectView third = (HomePageSelectView) holder.itemView.findViewById(R.id.page_third);
            third.getSelect().setText("排序");
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    first.setSelected(false);
                    second.setSelected(false);
                    third.setSelected(false);
                    switch (v.getId()) {
                        case R.id.page_first:
                            first.setSelected(true);
                            break;
                        case R.id.page_second:
                            second.setSelected(true);
                            break;
                        case R.id.page_third:
                            third.setSelected(true);
                            break;
                    }

                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(holder.itemView.getContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        RecyclerView recyclerView = new RecyclerView(holder.itemView.getContext());
                        recyclerView.setLayoutParams(params);
                        recyclerView.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 4));
                        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                            Paint paint = null;

                            @Override
                            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                                super.onDrawOver(c, parent, state);
                                if (paint == null) {
                                    paint = new Paint();
                                    paint.setStyle(Paint.Style.STROKE);
                                    paint.setColor(Color.parseColor("#ffd4d4d4"));
                                }
                                c.drawRect(1, 1, parent.getWidth() - 1, parent.getHeight() - 1, paint);
                                for (int i = 0; i < parent.getChildCount(); i++) {
                                    View child = parent.getChildAt(i);
                                    c.drawRect(child.getX(), child.getY(), child.getX() + child.getWidth(), child.getY() + child.getHeight(), paint);
                                }
                            }
                        });
                        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
                            @Override
                            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                ViewHolder mHolder = new ViewHolder(LayoutInflater.from(holder.itemView.getContext()).
                                        inflate(R.layout.home_page_pop_item, parent, false));
                                return mHolder;
                            }

                            @Override
                            public void onBindViewHolder(ViewHolder holder, int position) {
                                ((TextView) holder.itemView).setText(String.valueOf(position));
                            }

                            @Override
                            public int getItemCount() {
                                return 19;
                            }
                        });
                        popupWindow.setContentView(recyclerView);
                        popupWindow.setWidth(page_contain.getWidth());
                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                        popupWindow.setOutsideTouchable(false);
                        popupWindow.setFocusable(true);
                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                first.setSelected(false);
                                second.setSelected(false);
                                third.setSelected(false);
                            }
                        });
                        popupWindow.showAsDropDown(page_contain);
                    } else {
                        popupWindow.showAsDropDown(page_contain);
                    }
                }
            };
            first.setOnClickListener(listener);
            second.setOnClickListener(listener);
            third.setOnClickListener(listener);
        }
    }

    /*获取数据*/
    private void requestInfo() {
        if (!refreshLayout.isRefreshing() && indexPage == 1) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(Activity_HomePage.this, "加载中...");
        }

        Type type = new TypeToken<BaseResponse<List<ProductInfo>>>() {
        }.getType();
        HttpServer.getHomePageItem(TAG, 0, indexPage, 0, null, new GenericCallBack<BaseResponse<List<ProductInfo>>>(type) {
            @Override
            public void onSuccess(Response<BaseResponse<List<ProductInfo>>> response) {
                if (response.body().isOK()) {
                    if (indexPage == 1) {
                        productInfos.clear();
                    }
                    productInfos.addAll(response.body().getInfo());
                    adapter.notifyDataSetChanged();
                } else {
                    DisPlay(response.body().getMsg());
                }
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadmore();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }

            @Override
            public void onError(Response<BaseResponse<List<ProductInfo>>> response) {
                super.onError(response);
                DisPlay(response.getException().getMessage());
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadmore();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    public static void setItemData(ViewHolder holder, final ProductInfo info, final ProductInfo info2, final BaseActivity activity) {
        activity.loadImage(String.format("%s%s", HttpServer.HOST_IMG, info.getG_photo()), holder.image_1);
        if (info.getG_mPrice() < CommonTools.THRESHOLD_PRICE) {
            holder.pdc_price_1.setText(R.string.price_negotiable);
        } else {
            holder.pdc_name_1.setText(info.getG_mc());
        }

        holder.pdc_price_1.setText(String.valueOf(info.getG_mPrice()));
        holder.pdc_cart_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addWithDelPdt2Bag(info, true);
            }
        });
        holder.page_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityPdtDetail.startActivity(info.getG_ID(), activity);
            }
        });
        if (info2 != null) {
            holder.image_2.setVisibility(View.VISIBLE);
            holder.foot_2.setVisibility(View.VISIBLE);
            activity.loadImage(String.format("%s%s", HttpServer.HOST_IMG, info2.getG_photo()), holder.image_2);
            holder.pdc_name_2.setText(info2.getG_mc());
            if (info2.getG_mPrice() < CommonTools.THRESHOLD_PRICE) {
                holder.pdc_price_2.setText(R.string.price_negotiable);
            } else {
                holder.pdc_price_2.setText(String.valueOf(info2.getG_mPrice()));
            }
            holder.pdc_cart_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.addWithDelPdt2Bag(info2, true);
                }
            });
            holder.page_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityPdtDetail.startActivity(info2.getG_ID(), activity);
                }
            });
        } else {
            // 全部隐藏
            holder.image_2.setVisibility(View.INVISIBLE);
            holder.foot_2.setVisibility(View.INVISIBLE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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

        @BindView(R.id.pdc_name_1)
        TextView pdc_name_1;
        @BindView(R.id.pdc_name_2)
        TextView pdc_name_2;
        @BindView(R.id.pdc_price_1)
        TextView pdc_price_1;
        @BindView(R.id.pdc_price_2)
        TextView pdc_price_2;
        @BindView(R.id.pdc_cart_1)
        View pdc_cart_1;
        @BindView(R.id.pdc_cart_2)
        View pdc_cart_2;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView.getTag() != null) {
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
