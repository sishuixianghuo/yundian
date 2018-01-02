package com.yundian.android.fragment;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.OrderInfoDetail;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.ui.ActivityOrderDetail;
import com.yundian.android.ui.ActivityStoreDetail;
import com.yundian.android.ui.BaseActivity;
import com.yundian.android.utils.CommonTools;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 订单列表
 *
 * @author liushenghan   on 2017/12/28.
 */

public class OrderListFragment extends Fragment {

    String TAG = this.getClass().getSimpleName();

    private Unbinder unbinder;

    @BindView(R.id.sub_title_layout)
    View sub_title_layout;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    BaseActivity activity;

    List<OrderInfoDetail> orders = new CopyOnWriteArrayList<>();

    RecyclerView.Adapter<OrderHolder> adapter;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_view_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity = (BaseActivity) getActivity();
        sub_title_layout.setVisibility(View.GONE);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 15;
            }
        });

        recycler.setAdapter(adapter = new RecyclerView.Adapter<OrderHolder>() {
            @Override
            public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                OrderHolder holder = new OrderHolder(LayoutInflater.from(getActivity()).
                        inflate(R.layout.order_list_item, null, false));
                return holder;
            }

            @Override
            public void onBindViewHolder(OrderHolder holder, int position) {
                setData(holder, position);
            }

            @Override
            public int getItemCount() {
                return orders.size();
            }
        });

        if (orders.isEmpty()) {
            request();
        }
    }

    private void request() {
        dialog = WeiboDialogUtils.createLoadingDialog(activity, getString(R.string.loading));
        Type type = new TypeToken<BaseResponse<List<OrderInfoDetail>>>() {
        }.getType();
        HttpServer.getUserOrder(TAG, new GenericCallBack<BaseResponse<List<OrderInfoDetail>>>(type) {

            @Override
            public void onSuccess(Response<BaseResponse<List<OrderInfoDetail>>> response) {
                WeiboDialogUtils.closeDialog(dialog);
                if (response.body().isOK() && !response.body().getInfo().isEmpty()) {
                    orders.clear();
                    orders.addAll(response.body().getInfo());

                    adapter.notifyDataSetChanged();
                } else {
                    activity.DisplayToast(response.body().getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResponse<List<OrderInfoDetail>>> response) {
                super.onError(response);
                WeiboDialogUtils.closeDialog(dialog);
                activity.DisplayToast(response.message());
            }
        });

    }


    private void setData(OrderHolder holder, int postion) {
        final OrderInfoDetail info = orders.get(postion);
        holder.store_name.setText(info.getShop_Name());
        holder.delivery_status.setText("正在发货");
        activity.loadImageNoHost(info.getProduct_img(), holder.imageView);
        holder.pdt_name.setText(info.getProduct_name());
        holder.price.setText(CommonTools.getDate(CommonTools.getLong(info.getOrders_Addtime())));
        holder.summary.setText(String.format("共 %d件商品 合计：￥%.2f(含运费￥%.2f)", info.getProduct_count(), info.getOrders_Total_AllPrice(), info.getOrders_Total_Freight()));
        holder.order_status.setText("待付款");
        holder.order_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.DisplayToast(R.string.function_not_imp);
            }
        });

        holder.store_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStoreDetail.startAct(info.getOrders_SupplierID(), activity);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity.DisplayToast("跳转订单详情");
                ActivityOrderDetail.startActivity(info, activity);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkGo.getInstance().cancelTag(TAG);
    }


    static class OrderHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.store_name)
        TextView store_name;
        @BindView(R.id.delivery_status)
        TextView delivery_status;

        @BindView(R.id.summary)
        TextView summary;

        @BindView(R.id.order_status)
        TextView order_status;

        @BindView(R.id.view_contain)
        LinearLayout view_contain;

        /*----- 商品信息---------*/
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.pdt_name)
        TextView pdt_name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.amount)
        TextView amount;

        public OrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            amount.setVisibility(View.GONE);
        }
    }

}
