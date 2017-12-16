package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.android.R;
import com.yundian.android.bean.Site_Bean;
import com.yundian.android.utils.LogUtils;

import java.util.List;

/**
 * 待支付-待收货
 *
 * @author ShaoZhen-PC
 */
public class Activity_Pay_Receive extends BaseActivity implements  OnClickListener{

    private ImageView image_return;
    private TextView text_title;
    private List<Site_Bean> site_been;
    public static int mType  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_receive);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        image_return = (ImageView) findViewById(R.id.image_return);
        text_title = (TextView) findViewById(R.id.text_title);

    }

    @Override
    protected void initView() {

        image_return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.image_return:
//                finish();
//                break;
//            default:
//                break;
        }
    }

    public static Intent getIntent_Common(Context context,int type) {
        Intent intent = new Intent(context, Activity_Pay_Receive.class);
        LogUtils.e(TAG,"context : " + context.toString());
        mType = type;
        return intent;
    }
}
