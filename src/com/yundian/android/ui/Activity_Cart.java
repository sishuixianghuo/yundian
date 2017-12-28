package com.yundian.android.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.ProductInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购物车
 *
 * @author ShaoZhen-PC
 */
public class Activity_Cart extends BaseActivity {

    static final String KEY_TAG = "key_tag";

    @OnClick(R.id.image_return)
    public void close() {
        finish();
    }

    @BindView(R.id.text_compile)
    TextView text_compile;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.status)
    ImageView status;
    @BindView(R.id.total)
    TextView total;

    @BindView(R.id.order)
    View order;

    @BindView(R.id.delete)
    View delete;

    @BindView(R.id.image_return)
    View image_return;


    @BindView(R.id.total_contain)
    View total_contain;
    private float value = 0;
    private RecyclerView.Adapter<ViewHolder> adapter;


    public void payOrder(View v) {

        if (BaseApplication.getApp().getInfo() == null) {
            DisplayToast(R.string.please_login);
            return;
        }

        if (value == 0) {
            DisplayToast(R.string.select_pdts);
            return;
        }
        openActivity(ActivityOrder.class);

    }

    // 编辑模式状态
    volatile boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        recycler.setLayoutManager(new LinearLayoutManager(Activity_Cart.this));
        adapter = new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder holder = new ViewHolder(LayoutInflater.from(Activity_Cart.this).
                        inflate(R.layout.shop_cart_item, parent, false));
                return holder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                // 获取拖慢 但是没啥事
                setDate(holder, position);
            }

            @Override
            public int getItemCount() {
                return BaseApplication.getApp().getShoppingBag().size();
            }
        };


        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                Paint paint = new Paint();
                paint.setColor(getResources().getColor(R.color.d4d4d4));
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View v = parent.getChildAt(i);
                    float value = v.getY() + v.getHeight();
                    c.drawLine(0, value, parent.getWidth(), value, paint);
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            }
        });
        recycler.setAdapter(adapter);

        if (TextUtils.isEmpty(getIntent().getStringExtra(KEY_TAG))) {
            image_return.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        computeValue();
    }

    @Override
    protected void findViewById() {
    }

    @Override
    protected void initView() {

    }


    private void computeValue() {
        value = 0f;
        for (ProductInfo info : BaseApplication.getApp().getShoppingBag()) {
            if (info.isSelected) {
                value += info.amount * info.getG_mPrice();
            }
        }
        total.setText(String.format("￥ %.2f", value));
    }

    public void delete(View v) {
        if (isEdit && BaseApplication.getApp().getShoppingBag().size() > 0) {
            ProductInfo[] arrays = BaseApplication.getApp().getShoppingBag().toArray(new ProductInfo[BaseApplication.getApp().getShoppingBag().size()]);
            for (ProductInfo info : arrays) {
                if (info.isMove) {
                    BaseApplication.getApp().getShoppingBag().remove(info);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void editBag(View v) {
        isEdit = !isEdit;
        status.setSelected(false);
        //  编辑模式
        if (isEdit) {
            text_compile.setText("完成");
            // 合计部分隐藏 去结算变成 删除
            total_contain.setVisibility(View.INVISIBLE);
            order.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.VISIBLE);
        } else { // 正常模式
            text_compile.setText("编辑");
            total_contain.setVisibility(View.VISIBLE);
            order.setVisibility(View.VISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    public void selectAll(View v) {
        boolean flag = !status.isSelected();
        status.setSelected(flag);
        if (isEdit) {
            for (ProductInfo info : BaseApplication.getApp().getShoppingBag()) {
                info.isMove = flag;
            }
        } else {
            for (ProductInfo info : BaseApplication.getApp().getShoppingBag()) {
                info.isSelected = flag;
            }
        }
        adapter.notifyDataSetChanged();
        computeValue();
    }

    private void setDate(final ViewHolder holder, int position) {
        final ProductInfo info = BaseApplication.getApp().getShoppingBag().get(position);
        loadImageNoHost(info.getG_photo(), holder.image);
        holder.name.setText(info.getG_mc());
        holder.price.setText(String.valueOf(info.getG_mPrice()));
        holder.number.setText(String.valueOf(info.amount));
        if (isEdit) {
            holder.select.setSelected(info.isMove);
        } else {
            holder.select.setSelected(info.isSelected);
        }

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEdit) {
                    holder.select.setSelected(info.isMove = !holder.select.isSelected());
                } else {
                    holder.select.setSelected(info.isSelected = !holder.select.isSelected());
                }
                computeValue();

            }
        });
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.number.setText(String.valueOf(++info.amount));
                info.isSelected = true;
                computeValue();
                adapter.notifyDataSetChanged();
            }
        });
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.amount--;
                if (info.amount <= 1) {
                    info.amount = 1;
                }
                holder.number.setText(String.valueOf(info.amount));
                info.isSelected = true;
                computeValue();
                adapter.notifyDataSetChanged();

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Cart.this, R.style.CustomDialog);

                SpannableString title = new SpannableString("删除");
                title.setSpan(new ForegroundColorSpan(Color.parseColor("#3a3a3a")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                title.setSpan(new AbsoluteSizeSpan(20, true), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                title.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                String msg = "确定要删除此商品吗？";
                SpannableString message = new SpannableString(msg);
                message.setSpan(new ForegroundColorSpan(Color.parseColor("#3a3a3a")), 0, msg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                message.setSpan(new AbsoluteSizeSpan(20, true), 0, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                String confirm = "确定";

                String cancle = "取消";

                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BaseApplication.getApp().getShoppingBag().remove(info);
                                adapter.notifyDataSetChanged();
                                computeValue();
                            }
                        })
                        .setNegativeButton(cancle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.show();
                Button con = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                con.setTextColor(Color.parseColor("#e60012"));
                con.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                Button can = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                can.setTextColor(Color.parseColor("#3a3a3a"));
                can.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                return false;
            }
        });
    }


    public static void startActivity(String tag, Activity activity) {
        Intent intent = new Intent(activity, Activity_Cart.class);
        intent.putExtra(KEY_TAG, tag);
        activity.startActivity(intent);
    }


    public final class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.select)
        ImageView select;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.reduce)
        View reduce;
        @BindView(R.id.increase)
        View increase;
        @BindView(R.id.number)
        TextView number;


        public ViewHolder(View itemView) {
            super(itemView);
            Log.e("Activity_Cart", "ViewHolder");
            ButterKnife.bind(this, itemView);
        }
    }

}
