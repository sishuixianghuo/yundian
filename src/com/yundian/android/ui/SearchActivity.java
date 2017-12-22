package com.yundian.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchActivity extends BaseActivity {

    public static final String PID = "pid";
    public static final String KEY_WORD = "key_word";

    @BindView(R.id.empty_image)
    View empty_image;

    @BindView(R.id.edit_seek)
    EditText mEdit_search;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;


    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.layout_login)
    View layout_login;
    private RecyclerView.Adapter<Activity_HomePage.ViewHolder> adapter;
    private List<ProductInfo> productInfos = new CopyOnWriteArrayList<>();
    private String keywrod;
    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        keywrod = getIntent().getStringExtra(KEY_WORD);
        pid = getIntent().getIntExtra(PID, -1);
        empty_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayToast("delete");
            }
        });
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

        recyclerView.setAdapter(adapter = new RecyclerView.Adapter<Activity_HomePage.ViewHolder>() {
            int imageWidth = 0;
            int footH = 0;

            @Override
            public Activity_HomePage.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Activity_HomePage.ViewHolder holder = new Activity_HomePage.ViewHolder(LayoutInflater.from(SearchActivity.this).
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

            @Override
            public void onBindViewHolder(Activity_HomePage.ViewHolder holder, int position) {
                int index = position * 2;
                ProductInfo info = productInfos.get(index);
                Activity_HomePage.setItemData(holder, info, (index + 1) < productInfos.size() ? productInfos.get(index + 1) : null, SearchActivity.this);
            }

            @Override
            public int getItemCount() {
                return (productInfos.size() + 1) / 2;
            }
        });


        mEdit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    indexPage = 1;
                    pid = -1;
                    keywrod = mEdit_search.getText().toString().trim();
                    requestInfo();
                    return true;
                }
                return false;
            }
        });

        empty_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdit_search.setText("");
            }
        });
        mEdit_search.setText(keywrod);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter == null || adapter.getItemCount() == 0) {
            indexPage = 1;
            requestInfo();
        }
    }

    @Override
    protected void findViewById() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
    }

    public void back(View v) {
        finish();
    }

    /*获取数据*/
    private void requestInfo() {
        if (!refreshLayout.isRefreshing() && indexPage == 1) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(SearchActivity.this, "加载中...");
        }

        Type type = new TypeToken<BaseResponse<List<ProductInfo>>>() {
        }.getType();
        HttpServer.getHomePageItem(TAG, pid, indexPage, 0, keywrod, new GenericCallBack<BaseResponse<List<ProductInfo>>>(type) {
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


    public static void startAct(int pid, Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(PID, pid);
        activity.startActivity(intent);
    }

    public static void startAct(int pid, String title, Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(PID, pid);
        intent.putExtra(KEY_WORD, title);
        activity.startActivity(intent);
    }

    public static void startAct(String key, Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(KEY_WORD, key);
        activity.startActivity(intent);
    }
}
