package com.yundian.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.android.R;
import com.yundian.android.bean.ProductDetail;
import com.yundian.android.ui.BaseActivity;
import com.yundian.android.utils.CommonTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liushenghan on 2017/12/20.
 * 用户详情
 */

public class ProductFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.pdt_name)
    TextView pdt_name;

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.stock)
    TextView stock;

    @BindView(R.id.pdt_args)
    TextView pdt_args;
    @BindView(R.id.pdt_intro)
    ImageView pdt_intro;

    @BindView(R.id.number)
    TextView number;

    @BindView(R.id.reduce)
    View reduce;

    @BindView(R.id.increase)
    View increase;

    public int count = 1;
    private ProductDetail data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (data != null) setData(data);
    }

    public void setData(final ProductDetail info) {
        data = info;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) image.getLayoutParams();
        params.weight = image.getWidth();
        params.height = image.getWidth();
        image.setLayoutParams(params);
        ((BaseActivity) getActivity()).loadImageNoHost(info.getProduct_Img1(), image);
        pdt_name.setText(info.getProduct_Name());
        time.setText(String.format("更新时间 :  %s  |  浏览: %d", CommonTools.getDate(CommonTools.getLong(info.getProduct_Addtime())), info.getProduct_Hits()));
        stock.setText(String.format("库存: %d件", info.getProduct_StockAmount()));
        if (info.getProduct_Price() < CommonTools.THRESHOLD_PRICE) {
            price.setText(R.string.price_negotiable);
        } else {
            price.setText(String.format("￥%.2f", info.getProduct_Price()));
        }

        StringBuilder sb = new StringBuilder("<big> <font color=#9f9f9f >商品名称 : </font> <font color=#3a3a3a >" + info.getProduct_Name() + "</font>  <br>");
        sb.append(" <font color=#9f9f9f >商品品牌 : </font> <font color=#3a3a3a >" + info.getBrand_Name() + "</font> <br>");
        sb.append(" <font color=#9f9f9f >商品型号 : </font> <font color=#3a3a3a >" + info.getProduct_Xh() + "</font> <br>");
        sb.append(" <font color=#9f9f9f >商品用途 : </font> <font color=#3a3a3a >" + info.getProduct_Yongtu() + "</font> <br>");
        sb.append(" <font color=#9f9f9f >商品编号 : </font> <font color=#3a3a3a >" + info.getProduct_Code() + "</font> <br>");
        sb.append(" <font color=#9f9f9f >商品体积 : </font> <font color=#3a3a3a >" + info.getProduct_Tiji() + "</font> <br>");
        sb.append(" <font color=#9f9f9f >商品重量 : </font> <font color=#3a3a3a >" + info.getProduct_Weight() + "</font> <br> </big>");
        pdt_args.setText(Html.fromHtml(sb.toString()));
        if (getActivity() instanceof BaseActivity) {
            String str = info.getProduct_Intro();
            if (str.contains("<img")) {
                int index = str.indexOf("http");
                int index2 = str.indexOf("alt");
                Log.e("TAG URL", str.substring(index, index2 - 2));
                ((BaseActivity) getActivity()).loadImage(str.substring(index, index2 - 2), pdt_intro);
            } else {
                pdt_intro.setVisibility(View.GONE);
            }
        }
        if (info.getProduct_StockAmount() > 0) {
            reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (--count < 1) {
                        count = 1;
                    }
                    number.setText(String.valueOf(count));
                }
            });

            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (++count > info.getProduct_StockAmount()) {
                        count = info.getProduct_StockAmount();
                    }
                    number.setText(String.valueOf(count));
                }
            });
        } else {
            reduce.setEnabled(false);
            increase.setEnabled(false);
            number.setText("0");
            count = 0;
        }
    }
}
