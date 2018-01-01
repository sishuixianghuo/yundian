package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yundian.android.BaseApplication;
import com.yundian.android.R;

/**
 * 我的信息
 *
 * @author ShaoZhen-PC
 */
public class Activity_Main_Info extends BaseActivity implements OnClickListener {

    private ImageView image_return;

    private TextView text_email;
    private TextView text_name;
    private TextView text_phone;
    private RelativeLayout rl_email;
    private RelativeLayout rl_name;
    private RelativeLayout rl_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info);
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {
        image_return = (ImageView) this.findViewById(R.id.image_return);
        text_email = (TextView) this.findViewById(R.id.text_email);
        text_name = (TextView) this.findViewById(R.id.text_name);
        text_phone = (TextView) this.findViewById(R.id.text_phone);
        rl_email = (RelativeLayout) this.findViewById(R.id.rl_email);
        rl_name = (RelativeLayout) this.findViewById(R.id.rl_name);
        rl_phone = (RelativeLayout) this.findViewById(R.id.rl_phone);
    }

    @Override
    protected void initView() {
        image_return.setOnClickListener(this);
        text_email.setOnClickListener(this);
        text_name.setOnClickListener(this);
        text_phone.setOnClickListener(this);
        rl_email.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_phone.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        text_email.setText(BaseApplication.getApp().getInfo().getEmail());
        text_name.setText(BaseApplication.getApp().getInfo().getNickname());
        text_phone.setText(BaseApplication.getApp().getInfo().getPhone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_return:
                finish();
                break;
            case R.id.rl_email:
                startActivity(Activity_Add_Main_Info.getIntent_Common(this, 1));
                break;
            case R.id.rl_name:
                startActivity(Activity_Add_Main_Info.getIntent_Common(this, 2));
                break;
            case R.id.rl_phone:
                startActivity(Activity_Add_Main_Info.getIntent_Common(this, 3));
                break;
            default:
                break;
        }

    }

    public static Intent getIntent_Common(Context context) {
        Intent intent = new Intent(context, Activity_Main_Info.class);
        return intent;
    }

}
       