package com.yundian.android.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.BaseApplication;
import com.yundian.android.BuildConfig;
import com.yundian.android.R;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.Token;
import com.yundian.android.bean.UserInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.utils.RxSchedulers;
import com.yundian.android.widgets.WeiboDialogUtils;

import org.reactivestreams.Subscription;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 登陆
 *
 * @author ShaoZhen-PC
 */
public class Activity_Login extends BaseActivity implements OnClickListener {

    private Button button_register;
    private Button button_login;

    private EditText edit_username;
    private EditText edit_password;

    private ImageView image_edit_username_delete;
    private ImageView image_edit_password_delete;

//    private TextView text_edit_password_delete;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        button_register = (Button) findViewById(R.id.button_register);
        button_login = (Button) findViewById(R.id.button_login);
        image_edit_username_delete = (ImageView) findViewById(R.id.image_edit_username_delete);
        image_edit_password_delete = (ImageView) findViewById(R.id.image_edit_password_delete);
//        text_edit_password_delete = (TextView) findViewById(R.id.text_edit_password_delete);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_password = (EditText) findViewById(R.id.edit_password);

        button_register.setOnClickListener(this);
        button_login.setOnClickListener(this);
        image_edit_username_delete.setOnClickListener(this);
        image_edit_password_delete.setOnClickListener(this);
//        text_edit_password_delete.setOnClickListener(this);
        edit_username.setOnClickListener(this);
        edit_password.setOnClickListener(this);

        findViewById(R.id.image_return).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (BuildConfig.DEBUG) {
            edit_password.setText("1249324345");
            edit_username.setText("123");
        }
    }

    @Override
    protected void initView() {
        edit_username.addTextChangedListener(new EditChangedListener(1));
        edit_password.addTextChangedListener(new EditChangedListener(2));
        btnBg();
    }

    // 获取用户信息
    private void getUserInfo(final String userName, final String pwd) {

        dialog = WeiboDialogUtils.createLoadingDialog(this, "正在登陆");
        Flowable.create(new FlowableOnSubscribe<Token>() {
            @Override
            public void subscribe(final FlowableEmitter<Token> e) throws Exception {
                Type type = new TypeToken<BaseResponse<Token>>() {
                }.getType();
                HttpServer.login(TAG, userName, pwd, new GenericCallBack<BaseResponse<Token>>(type) {
                    @Override
                    public void onSuccess(Response<BaseResponse<Token>> response) {
                        if (response.body().isOK()) {
                            BaseApplication.getApp().setToken(response.body().getInfo().getToken());
                            e.onNext(response.body().getInfo());
                        } else {
                            e.onError(new Throwable(response.body().getMsg()));
                        }
                        e.onComplete();
                    }
                });
            }
        }, BackpressureStrategy.ERROR).observeOn(Schedulers.io())
                .map(new Function<Token, BaseResponse<List<UserInfo>>>() {
                    @Override
                    public BaseResponse<List<UserInfo>> apply(Token token) throws Exception {
                        Log.e(TAG, "BaseResponse Token = " + token.getToken() + " Thread = " + Thread.currentThread().getName());
                        return HttpServer.getUserinfo(token.getToken());
                    }
                }).compose(RxSchedulers.<BaseResponse<List<UserInfo>>>fIo_main())
                .subscribe(new FlowableSubscriber<BaseResponse<List<UserInfo>>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(BaseResponse<List<UserInfo>> userInfo) {
                        Log.e(TAG, "BaseResponse = " + userInfo.getInfo());
                        if (userInfo.isOK() && userInfo.getInfo().size() == 1) {
                            BaseApplication.getApp().setInfo(userInfo.getInfo().get(0));
                            finish();
                        } else {
                            DisPlay(userInfo.getMsg());
                        }
                        WeiboDialogUtils.closeDialog(dialog);
                    }

                    @Override
                    public void onError(Throwable t) {
                        DisPlay(t.getMessage());
                        WeiboDialogUtils.closeDialog(dialog);
                    }
                    @Override
                    public void onComplete() {
                        WeiboDialogUtils.closeDialog(dialog);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_register:
                startActivity(new Intent(this, Activity_Register.class));

                // “00” – 银联正式环境
                // “01” – 银联测试环境，该环境中不发生真实交易
                // String serverMode = "01";
                // UPPayAssistEx.startPay (this, null, null, tn, serverMode);
                break;
            case R.id.image_edit_username_delete:
                edit_username.setText("");
                break;
            case R.id.image_edit_password_delete:
                edit_password.setText("");
                break;
            case R.id.text_edit_password_delete:
                startActivity(new Intent(this, Activity_Forget_Password.class));
                break;
            case R.id.button_login:
                if (!TextUtils.isEmpty(edit_password.getText().toString().trim()) || !TextUtils.isEmpty(edit_username.getText().toString().trim())) {
                    getUserInfo(edit_username.getText().toString().trim(), edit_password.getText().toString().trim());
                }
                break;
            default:
                break;
        }

    }

    class EditChangedListener implements TextWatcher {
        private int editStart; // 光标开始位置
        private int id;

        public EditChangedListener(int id) {
            this.id = id;
        }

        // 输入文本之前的状态
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // 输入文字中的状态，count是一次性输入字符数
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        // 输入文字后的状态
        @Override
        public void afterTextChanged(Editable s) {
            // 如果邮箱有数据 显示删除按钮   密码有数据显示 删除 没数据显示忘记密码
            if (!TextUtils.isEmpty(edit_username.getText().toString().trim())) {
                image_edit_username_delete.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(edit_password.getText().toString().trim())) {
//                    text_edit_password_delete.setVisibility(View.VISIBLE);
                    image_edit_password_delete.setVisibility(View.GONE);
                } else {
//                    text_edit_password_delete.setVisibility(View.GONE);
                    image_edit_password_delete.setVisibility(View.VISIBLE);
                }
            } else {
                image_edit_username_delete.setVisibility(View.GONE);
                image_edit_password_delete.setVisibility(View.GONE);
//                text_edit_password_delete.setVisibility(View.GONE);
            }
            btnBg();
        }
    }

    private void btnBg() {
        if (!TextUtils.isEmpty(edit_password.getText().toString().trim()) && !TextUtils.isEmpty(edit_username.getText().toString().trim())) {
            button_login.setTextColor(Color.WHITE);
            button_login.setBackgroundResource(R.drawable.style_login_button_on);
        } else {
            button_login.setTextColor(getColor(R.color.b4b4b4));
            button_login.setBackgroundResource(R.drawable.style_login_button_off);
        }
    }

}
