package com.yundian.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.ProductInfo;
import com.yundian.android.bean.StoreInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.utils.NetWorkUtil;
import com.yundian.android.utils.RxSchedulers;
import com.yundian.android.widgets.WeiboDialogUtils;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liushenghan on 2017/12/23.
 */

public class ActivityStore extends BaseActivity {
    public static final String STORE_ID = "store_id";
    public static final String STORE_NAME = "store_NAME";
    public static final int HEAD_TYPE = 1;
    public static final int ITEM_TYPE = 0;


    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private RecyclerView.Adapter<Activity_HomePage.ViewHolder> adapter;

    private List<ProductInfo> productInfos = new CopyOnWriteArrayList<>();
    private StoreInfo storeInfo = null;
    private int storeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
        storeId = getIntent().getIntExtra(STORE_ID, -1);
        storeInfo = (StoreInfo) getIntent().getSerializableExtra(STORE_NAME);
        if (storeId < 0) {
            finish();
        }

        setContentView(R.layout.activity_store_home);
        ButterKnife.bind(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                indexPage = 1;
                refresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                indexPage++;
                loadMore();
            }
        });

        //底部边框显示
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int up = 0;
            int down = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (view.getTag() != null) {
                    outRect.top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, BaseApplication.getApp().getMetrics());
                }
            }
        });
        recycler.setAdapter(adapter = new RecyclerView.Adapter<Activity_HomePage.ViewHolder>() {
            int imageWidth = 0;
            int footH = 0;

            @Override
            public Activity_HomePage.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == HEAD_TYPE) {
                    Activity_HomePage.ViewHolder holder = new Activity_HomePage.ViewHolder(LayoutInflater.from(ActivityStore.this).
                            inflate(R.layout.store_head, parent, false));
                    return holder;
                } else {
                    Activity_HomePage.ViewHolder holder = new Activity_HomePage.ViewHolder(LayoutInflater.from(ActivityStore.this).
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
            public void onBindViewHolder(Activity_HomePage.ViewHolder holder, int position) {
                if (position == 0) {
                    initHead(holder);
                    return;
                } else {
                    int index = position * 2 - 2;
                    ProductInfo info = productInfos.get(index);
                    Activity_HomePage.setItemData(holder, info, (index + 1) < productInfos.size() ? productInfos.get(index + 1) : null, ActivityStore.this);
                }
            }


            @Override
            public int getItemViewType(int position) {
                if (position == 0) {
                    return HEAD_TYPE;
                }
                return ITEM_TYPE;
            }

            @Override
            public int getItemCount() {
                return (productInfos.size() + 1) / 2 + 1;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        storeId = getIntent().getIntExtra(STORE_ID, -1);
        if (storeId < 0) {
            finish();
        }
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
        if (storeInfo == null || productInfos.size() == 0) {
            refresh();
        }
    }


    public void goStore(View v) {
        ActivityStoreDetail.startAct(storeId, this);
    }

    public void callPhone(View v) {
        if (storeInfo == null) {
            DisplayToast("数据错误");
            return;
        }
        String msg = !TextUtils.isEmpty(storeInfo.getSupplier_Phone()) ? storeInfo.getSupplier_Phone() : storeInfo.getSupplier_mobile();
        WeiboDialogUtils.CallPhone(this, msg);
    }

    private void initHead(Activity_HomePage.ViewHolder holder) {
        holder.itemView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        holder.itemView.findViewById(R.id.store_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayToast(R.string.function_not_imp);
            }
        });

        holder.itemView.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayToast(R.string.function_not_imp);
            }
        });
        EditText editText = (EditText) holder.itemView.findViewById(R.id.edit_seek);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // 跳转
                    if (!TextUtils.isEmpty(v.getText().toString().trim())) {
                        SearchActivity.startAct(v.getText().toString().trim(), ActivityStore.this);
                    }
                    return true;
                }
                return false;
            }
        });
        if (storeInfo != null) {
            ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.image);
            loadImageNoHostWithError(storeInfo.getShop_Img(), imageView);
            TextView name = (TextView) holder.itemView.findViewById(R.id.name);
            name.setText(storeInfo.getShop_Name());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转店铺详情页
                ActivityStoreDetail.startAct(storeInfo.getShop_id(), ActivityStore.this);
            }
        });


    }

    private void refresh() {
        if (!NetWorkUtil.isNetConnected(this)) {
            DisplayToast(R.string.no_net_work);
            return;
        }
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(ActivityStore.this, getString(R.string.loading));

        Flowable.create(new FlowableOnSubscribe<StoreInfo>() {
            @Override
            public void subscribe(final FlowableEmitter<StoreInfo> e) throws Exception {

                HttpServer.getShopInfo(TAG, storeId, new GenericCallBack<BaseResponse<List<StoreInfo>>>(new TypeToken<BaseResponse<List<StoreInfo>>>() {
                }.getType()) {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<StoreInfo>>> response) {
                        if (response.body().isOK() && response.body().getInfo().size() > 0) {
                            storeInfo = response.body().getInfo().get(0);
                            storeInfo.setShop_id(storeId);
                            e.onNext(storeInfo);
                        } else {
                            e.onError(new Throwable(response.body().getMsg()));
                        }
                        e.onComplete();
                    }

                    @Override
                    public void onError(Response<BaseResponse<List<StoreInfo>>> response) {
                        super.onError(response);
                        e.onError(response.getException());
                    }
                });
            }
        }, BackpressureStrategy.ERROR).observeOn(Schedulers.io())
                .flatMap(new Function<StoreInfo, Publisher<BaseResponse<List<ProductInfo>>>>() {
                    @Override
                    public Publisher<BaseResponse<List<ProductInfo>>> apply(StoreInfo storeInfo) throws Exception {
                        return new Publisher<BaseResponse<List<ProductInfo>>>() {
                            @Override
                            public void subscribe(final Subscriber<? super BaseResponse<List<ProductInfo>>> s) {
                                HttpServer.getHomePageItem(TAG, -1, indexPage, storeId, null, new GenericCallBack<BaseResponse<List<ProductInfo>>>(new TypeToken<BaseResponse<List<ProductInfo>>>() {
                                }.getType()) {
                                    @Override
                                    public void onSuccess(Response<BaseResponse<List<ProductInfo>>> response) {
                                        if (response.body().isOK()) {
                                            s.onNext(response.body());
                                        } else {
                                            s.onError(new Throwable(response.body().getMsg()));
                                        }
                                        s.onComplete();
                                    }

                                    @Override
                                    public void onError(Response<BaseResponse<List<ProductInfo>>> response) {
                                        super.onError(response);
                                        s.onError(response.getException());
                                    }
                                });
                            }
                        };
                    }
                })
                .compose(RxSchedulers.<BaseResponse<List<ProductInfo>>>fIo_main())
                .subscribe(new FlowableSubscriber<BaseResponse<List<ProductInfo>>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(BaseResponse<List<ProductInfo>> userInfo) {
                        Log.e(TAG, "BaseResponse = " + userInfo.getInfo());
                        if (userInfo.isOK()) {
                            if (indexPage <= 1) {
                                productInfos.clear();
                            }
                            productInfos.addAll(userInfo.getInfo());
                            adapter.notifyDataSetChanged();
                        } else {
                            DisPlay(userInfo.getMsg());
                        }
                        compelete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        DisPlay(t.getMessage());
                        compelete();
                    }

                    @Override
                    public void onComplete() {
                        compelete();
                    }
                });


    }

    private void loadMore() {
        if (!NetWorkUtil.isNetConnected(this)) {
            DisplayToast(R.string.no_net_work);
            return;
        }
        HttpServer.getHomePageItem(TAG, -1, indexPage, storeId, null, new GenericCallBack<BaseResponse<List<ProductInfo>>>(
                new TypeToken<BaseResponse<List<ProductInfo>>>() {
                }.getType()) {
            @Override
            public void onSuccess(Response<BaseResponse<List<ProductInfo>>> response) {
                if (response.body().isOK()) {

                    productInfos.addAll(response.body().getInfo());
                    adapter.notifyDataSetChanged();
                } else {
                    DisPlay(response.body().getMsg());
                }
                compelete();
            }

            @Override
            public void onError(Response<BaseResponse<List<ProductInfo>>> response) {
                super.onError(response);
                DisPlay(response.message());
                compelete();
            }
        });
    }

    private void compelete() {
        WeiboDialogUtils.closeDialog(mWeiboDialog);
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();

    }


    public static void startAct(int pid, Activity activity) {
        Intent intent = new Intent(activity, ActivityStore.class);
        intent.putExtra(STORE_ID, pid);
        activity.startActivity(intent);
    }
}
