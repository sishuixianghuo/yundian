package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.widgets.WeiboDialogUtils;

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
                changePwd();
                break;
            default:
                break;
        }

    }

    private void changePwd() {

        String oldPwd = edit_jiu_pw.getText().toString().trim();
        String newPwd = edit_xin_pw1.getText().toString().trim();
        String confirm = edit_xin_pw2.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(confirm)) {
            DisPlay("密码不能为空");
            return;
        }
        if (!newPwd.equals(confirm)) {
            DisPlay("两次密码不一致");
            return;
        }

        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, getString(R.string.updating));

        HttpServer.updatePwd(TAG, oldPwd, newPwd, new GenericCallBack<BaseResponse<Object>>(new TypeToken<BaseResponse<Object>>() {
        }.getType()) {

            @Override
            public void onSuccess(Response<BaseResponse<Object>> response) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                if (response.body().isOK()) {
                    DisplayToast("密码修改成功");
                    finish();
                } else {
                    DisplayToast("密码修改失败");
                }
            }

            @Override
            public void onError(Response<BaseResponse<Object>> response) {
                super.onError(response);
                DisplayToast(response.message());
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });

    }

    public static Intent getIntent_Common(Context context) {
        Intent intent = new Intent(context, Activity_Revise_Password.class);
        return intent;
    }

}
       