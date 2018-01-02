package com.yundian.android.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.yundian.android.bean.Evaluate;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.ui.BaseActivity;
import com.yundian.android.ui.SearchActivity;
import com.yundian.android.utils.CommonTools;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author liushenghan  Created  on 2018/1/2.
 */

public class ProducdAppraiseFragment extends Fragment {
    String TAG = getClass().getSimpleName();

    private Unbinder unbinder;


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.personal_top_layout)
    View personal_top_layout;
    @BindView(R.id.empty)
    View empty;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    RecyclerView.Adapter<EvaluteHolder> adapter;
    private int pid;

    BaseActivity activity = null;
    List<Evaluate> datas = new CopyOnWriteArrayList<>();

    int index = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_index, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
        pid = getArguments().getInt(SearchActivity.PID);
        Log.e(TAG, "pid = " + pid);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        personal_top_layout.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, BaseApplication.getApp().getMetrics());
                }
            }
        });


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                index = 1;
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                index++;
                refreshlayout.finishLoadmore();
            }
        });
        recyclerView.setAdapter(adapter = new RecyclerView.Adapter<EvaluteHolder>() {
            @Override
            public EvaluteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.evaluate_item, parent, false);
                return new EvaluteHolder(view);
            }

            @Override
            public void onBindViewHolder(EvaluteHolder holder, int position) {
                setData(holder, position);
            }

            @Override
            public int getItemCount() {
                return datas.size();
            }
        });
    }


    private void setData(EvaluteHolder holder, int postion) {
        Evaluate item = datas.get(postion);
        holder.name.setText(item.getMember_Nickname());
        holder.time.setText(CommonTools.getDate(CommonTools.getLong(item.getShop_Evaluate_Addtime()), "yyyy-MM-dd"));
        holder.evaluate.setText(item.getShop_Evaluate_Note());

    }

    @Override
    public void onResume() {
        super.onResume();

        if (datas.isEmpty()) {
            doRequest();
        }
    }

    private void doRequest() {

        HttpServer.getEvaluate(TAG, pid, index, new GenericCallBack<BaseResponse<List<Evaluate>>>(new TypeToken<BaseResponse<List<Evaluate>>>() {
        }.getType()) {
            @Override
            public void onSuccess(Response<BaseResponse<List<Evaluate>>> response) {
                if (response.body().isOK()) {
                    if (index <= 1) {
                        datas.clear();
                    }
                    datas.addAll(response.body().getInfo());
                } else {
                    activity.DisplayToast(response.body().getMsg());
                }
                if (adapter.getItemCount() == 0) {
                    empty.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response<BaseResponse<List<Evaluate>>> response) {
                super.onError(response);
                activity.DisplayToast(response.message());
            }
        });


    }


    static class EvaluteHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.evaluate)
        TextView evaluate;
        @BindView(R.id.images)
        LinearLayout images;


        public EvaluteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
