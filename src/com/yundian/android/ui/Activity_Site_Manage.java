package com.yundian.android.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.Address;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.Province;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地址管理
 *
 * @author ShaoZhen-PC
 */
public class Activity_Site_Manage extends BaseActivity {
    public static final String OK_CODE = "ok_code";
    public static final String INDEX_OF_ADDRESS = "index_of_address";


    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private RecyclerView.Adapter<ViewHolder> adapter;
    View.OnClickListener listener;


    @OnClick(R.id.image_return)
    public void back() {
        finish();
    }

    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    View more;

    int code = -1;
    private List<Province> areas = BaseApplication.getApp().getCantons();

    @OnClick(R.id.button_add_dizhi)
    public void addAddress() {
        openActivity(Activity_Add_Site.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_manage);

        code = getIntent().getIntExtra(OK_CODE, -1);

        ButterKnife.bind(this);
        title.setVisibility(View.GONE);
        more.setVisibility(View.GONE);
        sub_title.setText("地址管理");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.parseColor("#dcdcdc"));
                paint.setStrokeWidth(1);
                for (int i = 0; i < parent.getChildCount(); i++) {
                    if (i > 0) {
                        View v = parent.getChildAt(i);
                        c.drawLine(v.getX(), v.getY(), v.getX() + v.getWidth(), v.getY(), paint);
                    }
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.top = 30;
                }

            }
        });
        Log.e(TAG, " Address size = " + BaseApplication.getApp().getAddresses().size());
        recyclerView.setAdapter(adapter = new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder holder = new ViewHolder(LayoutInflater.from(Activity_Site_Manage.this).
                        inflate(R.layout.item_site_manage, parent, false));
                return holder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                setData(holder, position);

            }

            @Override
            public int getItemCount() {
                return BaseApplication.getApp().getAddresses().size();
            }
        });
    }

    private void setData(final ViewHolder holder, final int postion) {
        Address address = BaseApplication.getApp().getAddresses().get(postion);
        holder.text_name.setText(address.getShouhuoren());
        holder.text_phone.setText(address.getMobile());
        holder.text_site.setText(areas.get(address.getProvice() - 1).getCity());

        END:
        for (Province.Citys item : areas.get(address.getProvice() - 1).getAreas()) {
            if (item.getCity_id() == address.getCity()) {
                holder.text_site.append(item.getCity());
                for (Province.Areas area : item.getAreas()) {
                    if (area.getArea_id() == address.getCounty()) {
                        holder.text_site.append(area.getArea());
                        break END;
                    }
                }
            }
        }


        holder.text_site.append(TextUtils.isEmpty(address.getAddr()) ? "" : address.getAddr());

        if (listener == null) {
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DisplayToast(R.string.function_not_imp);
                }
            };
        }
        holder.text_delete.setOnClickListener(listener);
        holder.text_compile.setOnClickListener(listener);
        holder.rb_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rb_default.setChecked(false);
                DisplayToast(R.string.function_not_imp);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code != -1) {
                    Intent mIntent = new Intent();
                    mIntent.putExtra(INDEX_OF_ADDRESS, postion);
                    // 设置结果，并进行传送
                    Activity_Site_Manage.this.setResult(code, mIntent);
                    finish();
                }
            }
        });
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
        if (BaseApplication.getApp().getAddresses().isEmpty()) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, getString(R.string.loading));

            Type type = new TypeToken<BaseResponse<List<Address>>>() {
            }.getType();
            HttpServer.getAddress(this.getClass().getName(), new GenericCallBack<BaseResponse<List<Address>>>(type) {
                @Override
                public void onSuccess(Response<BaseResponse<List<Address>>> response) {
                    if (response.body().isOK()) {
                        BaseApplication.getApp().getAddresses().addAll(response.body().getInfo());
                        adapter.notifyDataSetChanged();

                    }
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }

                @Override
                public void onError(Response<BaseResponse<List<Address>>> response) {
                    super.onError(response);
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }
            });

        }
        adapter.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button button_reduce;
        @BindView(R.id.text_name)
        public TextView text_name;

        @BindView(R.id.text_phone)
        public TextView text_phone;

        @BindView(R.id.text_site)
        public TextView text_site;

        @BindView(R.id.text_compile)
        public TextView text_compile;

        @BindView(R.id.text_delete)
        public TextView text_delete;

        @BindView(R.id.rb_default)
        public RadioButton rb_default;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 获取选择的地址
     *
     * @param activity
     */
    public static void startActiviyForResult(BaseActivity activity, int requestCode) {
        Intent intent = new Intent(activity, Activity_Site_Manage.class);
        intent.putExtra(OK_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
    }
}
