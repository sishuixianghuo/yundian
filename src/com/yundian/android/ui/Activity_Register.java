package com.yundian.android.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.utils.NetWorkUtil;
import com.yundian.android.widgets.WeiboDialogUtils;

/**
 * 注册
 *
 * @author ShaoZhen-PC
 */
public class Activity_Register extends BaseActivity implements OnClickListener {
    private Button mButtonRegister;
    private Dialog mWeiboDialog;
    private EditText edit_username;
    private EditText edit_nickname;
    private EditText edit_set_password_1;
    private EditText edit_set_password_2;

    private View view_1;
    private View view_2;
    private View view_3;
    private View view_4;

    private boolean edit1, edit2, edit3, edit4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mButtonRegister = (Button) findViewById(R.id.button_register);

        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_nickname = (EditText) findViewById(R.id.edit_nickname);
        edit_set_password_1 = (EditText) findViewById(R.id.edit_set_password_1);
        edit_set_password_2 = (EditText) findViewById(R.id.edit_set_password_2);

        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        view_3 = findViewById(R.id.view_3);
        view_4 = findViewById(R.id.view_4);

        findViewById(R.id.image_return).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mButtonRegister.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        edit_username.addTextChangedListener(new TextWatcher() {
            //文本变化之前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //文本变化中
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            //文本变化之后
            @Override
            public void afterTextChanged(Editable s) {
                // 有中文的正则匹配邮箱
                String str1 = "^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
                edit1 = false;
                if (s.toString().length() == 0) {
                    view_1.setBackgroundResource(R.color.e6e6e6);
                } else if (!edit_username.getText().toString().trim().matches(str1)) {
                    view_1.setBackgroundResource(R.color.e60012);
                } else {
                    view_1.setBackgroundResource(R.color.c76c626);
                    edit1 = true;
                }
                if (edit1 && edit2 && edit3 && edit4) {
                    mButtonRegister.setBackgroundResource(R.drawable.style_register_button);
                    mButtonRegister.setTextColor(getResources().getColor(R.color.white));
                    mButtonRegister.setEnabled(true);
                } else {
                    mButtonRegister.setBackgroundResource(R.drawable.style_login_button_off);
                    mButtonRegister.setTextColor(getResources().getColor(R.color.b4b4b4));
                    mButtonRegister.setEnabled(false);
                }
            }
        });

        edit_nickname.addTextChangedListener(new TextWatcher() {
            //文本变化之前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //文本变化中
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            //文本变化之后
            @Override
            public void afterTextChanged(Editable s) {
                String str = "[a-zA-Z0-9\u4e00-\u9fa5\\s]{2,10}";
                edit2 = false;
                if (s.toString().length() == 0) {
                    view_2.setBackgroundResource(R.color.e6e6e6);
                } else if (!edit_nickname.getText().toString().trim().matches(str)) {
                    view_2.setBackgroundResource(R.color.e60012);
                } else {
                    view_2.setBackgroundResource(R.color.c76c626);
                    edit2 = true;
                }
                if (edit1 && edit2 && edit3 && edit4) {
                    mButtonRegister.setBackgroundResource(R.drawable.style_register_button);
                    mButtonRegister.setTextColor(getResources().getColor(R.color.white));
                    mButtonRegister.setEnabled(true);
                } else {
                    mButtonRegister.setBackgroundResource(R.drawable.style_login_button_off);
                    mButtonRegister.setTextColor(getResources().getColor(R.color.b4b4b4));
                    mButtonRegister.setEnabled(false);
                }
            }
        });

        edit_set_password_1.addTextChangedListener(new TextWatcher() {
            //文本变化之前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //文本变化中
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            //文本变化之后
            @Override
            public void afterTextChanged(Editable s) {
                String str = "[a-zA-Z0-9\u4e00-\u9fa5\\s]{6,20}";
                edit3 = false;
                if (s.toString().length() == 0) {
                    view_3.setBackgroundResource(R.color.e6e6e6);
                } else if (!edit_set_password_1.getText().toString().trim().matches(str)) {
                    view_3.setBackgroundResource(R.color.e60012);
                } else {
                    view_3.setBackgroundResource(R.color.c76c626);
                    edit3 = true;
                }
                if (edit1 && edit2 && edit3 && edit4) {
                    mButtonRegister.setBackgroundResource(R.drawable.style_register_button);
                    mButtonRegister.setTextColor(getResources().getColor(R.color.white));
                    mButtonRegister.setEnabled(true);
                } else {
                    mButtonRegister.setBackgroundResource(R.drawable.style_login_button_off);
                    mButtonRegister.setTextColor(getResources().getColor(R.color.b4b4b4));
                    mButtonRegister.setEnabled(false);
                }
            }
        });

        edit_set_password_2.addTextChangedListener(new TextWatcher() {
            //文本变化之前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //文本变化中
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            //文本变化之后
            @Override
            public void afterTextChanged(Editable s) {
                String str = "[a-zA-Z0-9\u4e00-\u9fa5\\s]{6,20}";
                edit4 = false;
                if (s.toString().length() == 0) {
                    view_4.setBackgroundResource(R.color.e6e6e6);
                } else if (!edit_set_password_2.getText().toString().trim().matches(str)) {
                    view_4.setBackgroundResource(R.color.e60012);
                } else {
                    if (edit_set_password_1.getText().toString().trim().equals(edit_set_password_2.getText().toString().trim()))
                        view_4.setBackgroundResource(R.color.c76c626);
                    edit4 = true;

                }
                if (edit1 && edit2 && edit3 && edit4) {
                    mButtonRegister.setBackgroundResource(R.drawable.style_register_button);
                    mButtonRegister.setTextColor(getResources().getColor(R.color.white));
                    mButtonRegister.setEnabled(true);
                } else {
                    mButtonRegister.setBackgroundResource(R.drawable.style_login_button_off);
                    mButtonRegister.setTextColor(getResources().getColor(R.color.b4b4b4));
                    mButtonRegister.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                if (!NetWorkUtil.isNetConnected(this)) {
                    DisplayToast(R.string.no_net_work);
                    return;
                }
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
                mWeiboDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                HttpServer.register(TAG, edit_username.getText().toString().trim(), edit_nickname.getText().toString().trim(),
                        edit_set_password_1.getText().toString().trim(), edit_set_password_2.getText().toString().trim(), new GenericCallBack<BaseResponse<Object>>(new TypeToken<BaseResponse<Object>>() {
                        }.getType()) {
                            @Override
                            public void onSuccess(Response<BaseResponse<Object>> response) {
                                WeiboDialogUtils.closeDialog(mWeiboDialog);
                                if (response.body().isOK()) {
                                    DisplayToast("请登录");
                                    finish();
                                } else {
                                    DisplayToast(response.body().getMsg());
                                }
                            }

                            @Override
                            public void onError(Response<BaseResponse<Object>> response) {
                                super.onError(response);
                                DisPlay(response.getException().getMessage());
                                WeiboDialogUtils.closeDialog(mWeiboDialog);
                            }
                        });
//                OkGo.<String>post(URLs.URL_REGISTER)
//                        .tag(this)
//                        .params("strUserName", "paramValue1")
//                        .params("strUserPwd", "paramValue1")
//                        .params("strNickName", "paramValue1")
//                        .params("strPhone", "paramValue1")
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(Response<String> response) {
//                                LogUtils.e(TAG, "response code: " + response.code() + " , message: " + response.message() + " , isSuccessful: " + response.isSuccessful());
//                                WeiboDialogUtils.closeDialog(mWeiboDialog);
//                                finish();
//                            }
//
//                            @Override
//                            public void onError(Response<String> response) {
//                                super.onError(response);
//                                WeiboDialogUtils.closeDialog(mWeiboDialog);
//                            }
//                        });
                break;
            default:
                break;
        }
    }

}
