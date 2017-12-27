package com.yundian.android.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.Address;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.DeliveryMethod;
import com.yundian.android.bean.OrderInfo;
import com.yundian.android.bean.PayMethod;
import com.yundian.android.bean.ProductInfo;
import com.yundian.android.bean.ShopCart;
import com.yundian.android.bean.UserInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.utils.NetWorkUtil;
import com.yundian.android.widgets.WeiboDialogUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by liushenghan on 2017/12/25.
 */

public class ActivityOrder extends BaseActivity {

    public static final int ADD_REQ_CODE = 200;

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

    }


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

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.price)
    TextView price;

    public final int TYPE_HEAD = 0;
    public final int TYPE_ITEM = 1;
    private Map<Integer, ShopCart> shops = new HashMap<>();
    private List<Integer> sets = new ArrayList<>();

    private RecyclerView.Adapter<ViewHolder> adapter;
    // 默认收货地址为第一个
    private int index_of_address = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        title.setVisibility(View.GONE);
        more.setVisibility(View.GONE);
        sub_title.setText("填写订单");
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.top = 30;
                }
            }
        });

        // 处理 购车的内容
        shops.clear();
        for (ProductInfo info : BaseApplication.getApp().getShoppingBag()) {
            if (info.isSelected) {
                ShopCart cart = shops.get(info.getProduct_SupplierID());
                if (cart == null) {
                    cart = new ShopCart(info.getProduct_SupplierID());
                    shops.put(info.getProduct_SupplierID(), cart);
                    sets.add(info.getProduct_SupplierID());
                }
                cart.getPdts().add(info);
            }
        }

        recycler.setAdapter(adapter = new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == TYPE_HEAD) {
                    ViewHolder holder = new ViewHolder(LayoutInflater.from(ActivityOrder.this).
                            inflate(R.layout.order_head, parent, false));
                    return holder;
                } else {
                    ViewHolder holder = new ViewHolder(LayoutInflater.from(ActivityOrder.this).
                            inflate(R.layout.order_foot, parent, false));
                    return holder;
                }
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                if (getItemViewType(position) == TYPE_HEAD) {
                    setAddress(holder);
                } else {
                    setPdts(holder, position);
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 0) {
                    return TYPE_HEAD;
                }
                return TYPE_ITEM;
            }

            @Override
            public int getItemCount() {
                return shops.size() + 1;
            }
        });

    }

    /**
     * 设置收货人信息
     *
     * @param holder
     */
    private void setAddress(ViewHolder holder) {
        UserInfo info = BaseApplication.getApp().getInfo();
        Address address = BaseApplication.getApp().getAddresses().get(index_of_address);
        TextView add = (TextView) holder.itemView.findViewById(R.id.address);
        add.setText(String.format("收货地址：%s%s%s", address.getProvice(), address.getCity(), address.getAddr()));
        TextView shr = (TextView) holder.itemView.findViewById(R.id.shr);
        shr.setText(String.format("收货人： %s", address.getShouhuoren()));
        TextView name = (TextView) holder.itemView.findViewById(R.id.name);
        name.setText(String.format("%s  %s", info.getNickname(), TextUtils.isEmpty(info.getEmail()) ? "" : info.getEmail()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Site_Manage.startActiviyForResult(ActivityOrder.this, ADD_REQ_CODE);
            }
        });
    }

    /**
     * 设置单个订单的信息
     *
     * @param position
     */
    private void setPdts(ViewHolder holder, int position) {
        final ShopCart cart = shops.get(sets.get(position - 1));
        if (cart == null) {
            return;
        }
        holder.store_name.setText(cart.getPdts().get(0).getShop_name());
        holder.view_contain.removeAllViews();
        for (ProductInfo info : cart.getPdts()) {
            View view = LayoutInflater.from(ActivityOrder.this).
                    inflate(R.layout.order_pdt_item, null, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            loadImageNoHost(info.getG_photo(), imageView);
            TextView pdt_name = (TextView) view.findViewById(R.id.pdt_name);
            pdt_name.setText(info.getG_mc());
            TextView price = (TextView) view.findViewById(R.id.price);
            price.setText(String.format("￥ %.2f", info.getG_mPrice()));
            TextView amount = (TextView) view.findViewById(R.id.amount);
            amount.setText(String.format("x%d", info.amount));
            holder.view_contain.addView(view);
        }
        holder.select_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 选择货运方式
                selectFreightMethod(cart);
            }
        });

        holder.select_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // 选择付款方式
                selectPayMethod(cart);
            }
        });

        if (cart.getDelivery() == null) {
            holder.computer_total.setText("请选择快递方式");
        } else { // 计算总价
            holder.logistics.setText(cart.getDelivery().getDelivery_Way_Name());
            String value = String.format("<font color=#808080>小计 :<big> <font size='10' color=#e60012 > %.2f </font> </big> </font>", cart.getOrder_total() + cart.getFreight());
            holder.computer_total.setText(Html.fromHtml(value));
        }

        if (cart.getPay() != null) {
            holder.pay_method.setText(cart.getPay().getPay_Way_Name());
        } else {
            holder.pay_method.setText("请选择付款方式");
        }

        holder.submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.getDelivery() == null) {
                    DisPlay("请选择货运方式");
                    return;
                }
                if (cart.getPay() == null) {
                    DisPlay("请选择支付方式");
                    return;
                }

                // 提交订单
                submitOrder(cart);

            }
        });

    }

    // 提交订单
    private void submitOrder(final ShopCart cart) {
        if (!NetWorkUtil.isNetConnected(this)) {
            DisplayToast(R.string.no_net_work);
            return;
        }
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(ActivityOrder.this, "正在提交订单");

        HttpServer.submitOrder(TAG, new OrderInfo(cart, BaseApplication.getApp().getAddresses().get(index_of_address)), new GenericCallBack<BaseResponse<Object>>(BaseResponse.class) {
            @Override
            public void onSuccess(Response<BaseResponse<Object>> response) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                if (response.body().isOK()) {
                    DisplayToast("删除数据从购物车 和  shops");

                    shops.remove(cart.getSupplierID());
                    for (ProductInfo info : cart.getPdts()) {
                        BaseApplication.getApp().getShoppingBag().remove(info);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    DisplayToast(response.body().getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResponse<Object>> response) {
                super.onError(response);
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                DisplayToast(response.message());
            }
        });
    }

    /**
     * 选择货运方式
     * 先去 获取店铺的提供的货运方式 然后再选择
     */
    private void selectFreightMethod(final ShopCart cart) {
        if (!NetWorkUtil.isNetConnected(this)) {
            DisplayToast(R.string.no_net_work);
            return;
        }
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(ActivityOrder.this, "获取商家货运方式");
        Type type = new TypeToken<BaseResponse<List<DeliveryMethod>>>() {
        }.getType();
        HttpServer.getDeliveryMethod(TAG, cart.getSupplierID(), new GenericCallBack<BaseResponse<List<DeliveryMethod>>>(type) {
            @Override
            public void onSuccess(final Response<BaseResponse<List<DeliveryMethod>>> response) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                if (response.body().isOK() && !response.body().getInfo().isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder.this, R.style.CustomDialog);
                    builder.setTitle("选择货运方式")
                            .setAdapter(new BaseAdapter() {
                                @Override
                                public int getCount() {
                                    return response.body().getInfo().size();
                                }

                                @Override
                                public Object getItem(int position) {
                                    return null;
                                }

                                @Override
                                public long getItemId(int position) {
                                    return 0;
                                }

                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    DeliveryMethod method = response.body().getInfo().get(position);
                                    View view = LayoutInflater.from(ActivityOrder.this).
                                            inflate(R.layout.delivery_info_item, parent, false);
                                    TextView name = (TextView) view.findViewById(R.id.name);
                                    name.setText(method.getDelivery_Way_Name());
                                    TextView dsc = (TextView) view.findViewById(R.id.dsc);

                                    String str = String.format("<font color=#3a3a3a>首重 :</font>  <big> <font size='10' color=#e60012>￥%.2f</font> </big> <font color=#3a3a3a> ； </font>" +
                                                    " <big> <font size='10' color=#e60012>%.2f</font> </big> <font color=#3a3a3a>克</font>" +
                                                    "<br>" +
                                                    "<font color=#3a3a3a>续重 :</font>  <big> <font size='10' color=#e60012>￥%.2f</font> </big> <font color=#3a3a3a> ； </font>" +
                                                    "<big> <font size='10' color=#e60012>%.2f</font></big> <font color=#3a3a3a>克</font>", method.getDelivery_Way_InitialFee(), method.getDelivery_Way_InitialWeight(),
                                            method.getDelivery_Way_UpFee(), method.getDelivery_Way_UpWeight());
                                    StringBuilder sb = new StringBuilder("<font color=#3a3a3a>首重 :<font color=#e60012 >" + String.format("￥ %.2f, %.2f", method.getDelivery_Way_InitialFee(), method.getDelivery_Way_InitialWeight()) + " </font> 克</font> ");
                                    sb.append("<font color=#3a3a3a>续重 :<font color=#e60012 >" + String.format("￥ %.2f, %.2f", method.getDelivery_Way_UpFee(), method.getDelivery_Way_UpWeight()) + " </font> 克</font> ");
                                    dsc.setText(Html.fromHtml(str));
                                    return view;
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cart.setDelivery(response.body().getInfo().get(which));
                                    adapter.notifyDataSetChanged();
                                }
                            }).show();

                } else {
                    DisplayToast(response.body().getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResponse<List<DeliveryMethod>>> response) {
                super.onError(response);
                DisplayToast(response.message());
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }

    /**
     * 选择付款方式 系统付款方式目前就四种
     */
    private void selectPayMethod(final ShopCart cart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrder.this, R.style.CustomDialog);
        builder.setTitle("付款方式")
                .setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        List<PayMethod> methods = BaseApplication.getApp().getPayMethods();
                        return methods.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return null;
                    }

                    @Override
                    public long getItemId(int position) {
                        return 0;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        PayMethod method = BaseApplication.getApp().getPayMethods().get(position);
                        View view = LayoutInflater.from(ActivityOrder.this).
                                inflate(R.layout.pay_info_item, parent, false);
                        ImageView icon = (ImageView) view.findViewById(R.id.icon);
                        if (method.getPay_Way_Name().contains("微信")) {
                            icon.setImageResource(R.drawable.purchase_ic_wechat);
                        } else if (method.getPay_Way_Name().contains("支付宝")) {
                            icon.setImageResource(R.drawable.purchase_ic_alipay);
                        } else if (method.getPay_Way_Name().contains("银联")) {
                            icon.setImageResource(R.drawable.purchase_ic_unionpay);
                        } else if (method.getPay_Way_Name().contains("线下")) {
                            icon.setImageDrawable(new ColorDrawable(Color.WHITE));
                        }
                        TextView name = (TextView) view.findViewById(R.id.name);
                        name.setText(method.getPay_Way_Name());
                        return view;
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cart.setPay(BaseApplication.getApp().getPayMethods().get(which));
                        adapter.notifyDataSetChanged();
                    }
                }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 获取 不同商家对应的快递方式  支付方式 收货地址
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, String.format("requestCode =  %d  requestCode = %d", requestCode, resultCode));
        if (requestCode == ADD_REQ_CODE && data != null) {
            index_of_address = data.getIntExtra(Activity_Site_Manage.INDEX_OF_ADDRESS, 0);
            Log.e(TAG, "index " + index_of_address);
        }
        adapter.notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        // 店铺名称
        @BindView(R.id.store_name)
        TextView store_name;

        // 存放商品容器
        @BindView(R.id.view_contain)
        LinearLayout view_contain;

        // 物流
        @BindView(R.id.select_logistics)
        View select_logistics;

        @BindView(R.id.logistics_method)
        TextView logistics;

        // 支付
        @BindView(R.id.select_pay)
        View select_pay;

        @BindView(R.id.pay_method)
        TextView pay_method;


        // 计算的价格
        @BindView(R.id.computer_total)
        TextView computer_total;
        // 提交订单
        @BindView(R.id.submit_order)
        View submit_order;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView instanceof LinearLayout) {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
