package com.yundian.android.ui;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.yundian.android.R;
import com.yundian.android.entity.BaseResponse;
import com.yundian.android.entity.CategoryInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分类
 *
 * @author ShaoZhen-PC
 */
public class Activity_Category extends BaseActivity {

    @BindView(R.id.top_class)
    RecyclerView recyclerView;
    @BindView(R.id.sub_class)
    RecyclerView subRecycleView;
    private Dialog mWeiboDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                Paint paint = new Paint();
                paint.setColor(getResources().getColor(R.color.d4d4d4));
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View v = parent.getChildAt(i);
                    c.drawLine(0, v.getY() + v.getHeight(), parent.getWidth(), v.getY() + v.getHeight(), paint);
                }
            }
        });
        up();
    }

    private void up() {

        Type type = new TypeToken<BaseResponse<List<CategoryInfo>>>() {
        }.getType();

        HttpServer.getCategory(httpTag, new GenericCallBack<BaseResponse<List<CategoryInfo>>>(type) {

            @Override
            public void onStart(Request<BaseResponse<List<CategoryInfo>>, ? extends Request> request) {
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(Activity_Category.this, "加载中...");
            }

            @Override
            public void onSuccess(Response<BaseResponse<List<CategoryInfo>>> response) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                setData(response.body());
            }

            @Override
            public void onError(Response<BaseResponse<List<CategoryInfo>>> response) {
                super.onError(response);
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                DisPlay(response.getException().getMessage());
            }
        });
    }


    private void setData(final BaseResponse<List<CategoryInfo>> datas) {
        if (datas == null) {
            DisplayToast("数据为空 >!<");
            return;
        }

        recyclerView.setAdapter(new RecyclerView.Adapter<ItemHolder>() {
            View view;
            @Override
            public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                ItemHolder holder = new ItemHolder(LayoutInflater.from(Activity_Category.this).
                        inflate(R.layout.category_item, parent, false));
                return holder;
            }

            @Override
            public void onBindViewHolder(ItemHolder holder, final int position) {
                ((TextView) holder.itemView).setText(datas.getInfo().get(position).getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view != null ) {
                            view.setSelected(false);
                        }
                        v.setSelected(true);
                        setSubClassData(datas.getInfo().get(position));
                        view = v;
                    }
                });
                if (view == null) {
                    holder.itemView.performClick();
                }
            }

            @Override
            public int getItemCount() {
                return datas.getInfo().size();
            }
        });

    }


    private void setSubClassData(CategoryInfo subClassData) {

    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    protected void findViewById() {


    }

    @Override
    protected void initView() {


    }


}
