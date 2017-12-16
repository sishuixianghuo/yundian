package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.android.R;

/**
 * 修改密码
 *
 * @author ShaoZhen-PC
 */
public class Activity_Revise_Password extends BaseActivity implements OnClickListener {

    private ImageView image_return;

    private TextView text_queren;
    private EditText edit_jiu_pw;
    private EditText edit_xin_pw1;
    private EditText edit_xin_pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_password);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        image_return = (ImageView) this.findViewById(R.id.image_return);
        text_queren = (TextView) this.findViewById(R.id.text_queren);
        edit_jiu_pw = (EditText) this.findViewById(R.id.edit_jiu_pw);
        edit_xin_pw1 = (EditText) this.findViewById(R.id.edit_xin_pw1);
        edit_xin_pw2 = (EditText) this.findViewById(R.id.edit_xin_pw2);
    }

    @Override
    protected void initView() {
        image_return.setOnClickListener(this);
        text_queren.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_return:
                finish();
                break;
            case R.id.text_queren:
                finish();
                break;
            default:
                break;
        }

    }

    public static Intent getIntent_Common(Context context) {
        Intent intent = new Intent(context, Activity_Revise_Password.class);
        return intent;
    }

}
       