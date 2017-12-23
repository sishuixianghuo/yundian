package com.yundian.android.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.lzy.okgo.request.base.Request;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.CategoryInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    RecyclerView.Adapter topClassAdapter;
    RecyclerView.Adapter subClassAdapter;

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
        subRecycleView.setLayoutManager(new GridLayoutManager(this, 1));
        subRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (view instanceof LinearLayout) {
                    outRect.top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, BaseApplication.getApp().getMetrics());
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (topClassAdapter == null || topClassAdapter.getItemCount() == 0) {
            up();
        }
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

                if (response.body().isOK()) {
                    // 处理数据之后在set 一级标题
                    for (CategoryInfo info : response.body().getInfo()) {
                        // 二级
                        List<List<CategoryInfo.SubCategory>> datas = new ArrayList<>();
                        for (CategoryInfo.SubCategory item : info.getInfo()) {
                            List<CategoryInfo.SubCategory> subCategories = new ArrayList<>();
                            subCategories.add(item);
                            datas.add(subCategories);
                            // 三级分类
                            if (item.getInfo() != null) {
                                int count = 0;
                                do {
                                    datas.add(item.getInfo().subList(count, count + 3 < item.getInfo().size() ? count + 3 : item.getInfo().size()));
                                    count += 3;
                                } while (count < item.getInfo().size());
                            }
                        }
                        info.setDirs(datas);
                    }
                    setData(response.body());
                    setSubClassData(response.body().getInfo().get(0));
                } else {
                    DisPlay(response.body().getMsg());
                }
                WeiboDialogUtils.closeDialog(mWeiboDialog);
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

        recyclerView.setAdapter(topClassAdapter = new RecyclerView.Adapter<ItemHolder>() {
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
                        if (view != null) {
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

    private void setSubClassData(final CategoryInfo subClassData) {
        subRecycleView.setAdapter(subClassAdapter = new RecyclerView.Adapter<ItemHolder>() {

            @Override
            public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // 三级条目
                if (viewType == 0) {
                    ItemHolder holder = new ItemHolder(LayoutInflater.from(Activity_Category.this).
                            inflate(R.layout.category_sub_item, parent, false));
                    return holder;
                }
                // 二级标题
                View view = LayoutInflater.from(Activity_Category.this).
                        inflate(R.layout.category_sub_item_title, parent, false);
                view.setMinimumWidth(4000);
                ItemHolder holder = new ItemHolder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(ItemHolder holder, final int position) {
                Log.e(TAG, "setSubClassData onBindViewHolder ");
                if (subClassData.getDirs().get(position).get(0).getInfo() == null) {
                    holder.item1.setText(subClassData.getDirs().get(position).get(0).getName());
                    holder.item1.setOnClickListener(new CategoryClick(subClassData.getDirs().get(position).get(0), Activity_Category.this));

                    if (subClassData.getDirs().get(position).size() >= 2) {
                        holder.item2.setVisibility(View.VISIBLE);
                        holder.item2.setText(subClassData.getDirs().get(position).get(1).getName());
                        holder.item2.setOnClickListener(new CategoryClick(subClassData.getDirs().get(position).get(1), Activity_Category.this));

                    } else {
                        holder.item2.setVisibility(View.INVISIBLE);
                        holder.item2.setOnClickListener(null);
                    }
                    if (subClassData.getDirs().get(position).size() == 3) {
                        holder.item3.setVisibility(View.VISIBLE);
                        holder.item3.setText(subClassData.getDirs().get(position).get(2).getName());
                        holder.item3.setOnClickListener(new CategoryClick(subClassData.getDirs().get(position).get(2), Activity_Category.this));

                    } else {
                        holder.item3.setVisibility(View.INVISIBLE);
                        holder.item3.setOnClickListener(null);
                    }
                } else {
                    ((TextView) holder.itemView).setText(subClassData.getDirs().get(position).get(0).getName()+"1231");
                    holder.itemView.setOnClickListener(new CategoryClick(subClassData.getDirs().get(position).get(0), Activity_Category.this));
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (subClassData.getDirs().get(position).get(0).getInfo() == null) {
                    return 0;
                }
                return 1;
            }

            @Override
            public int getItemCount() {
                return subClassData.getDirs().size();
            }
        });
    }


    static class CategoryClick implements View.OnClickListener {
        CategoryInfo.SubCategory info;
        BaseActivity activity;

        public CategoryClick(CategoryInfo.SubCategory info, BaseActivity activity) {
            this.info = info;
            this.activity = activity;
        }

        @Override
        public void onClick(View v) {
            SearchActivity.startAct(info.getId(), info.getName(), activity);
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item1)
        TextView item1;
        @BindView(R.id.item2)
        TextView item2;
        @BindView(R.id.item3)
        TextView item3;

        public ItemHolder(View itemView) {
            super(itemView);
            if (itemView instanceof LinearLayout) {
                ButterKnife.bind(this, itemView);
            }
        }
    }


    @Override
    protected void findViewById() {
    }

    @Override
    protected void initView() {
    }


}
