package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.URLs;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.UserInfo;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.utils.CommonTools;
import com.yundian.android.utils.SettingUtils;
import com.yundian.android.widgets.WeiboDialogUtils;

/**
 * 修改我的信息
 *
 * @author ShaoZhen-PC
 */
public class Activity_Add_Main_Info extends BaseActivity implements OnClickListener {

    private ImageView image_return;
    private ImageView image_delete;

    private EditText edit_test;
    private TextView text_title;
    private TextView text_hint;
    private TextView text_queren;
    public static int category;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_main_info);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        image_return = (ImageView) this.findViewById(R.id.image_return);
        image_delete = (ImageView) this.findViewById(R.id.image_delete);
        text_title = (TextView) this.findViewById(R.id.text_title);
        text_hint = (TextView) this.findViewById(R.id.text_hint);
        text_queren = (TextView) this.findViewById(R.id.text_queren);
        edit_test = (EditText) this.findViewById(R.id.edit_test);
    }

    @Override
    protected void initView() {
        image_return.setOnClickListener(this);
        image_delete.setOnClickListener(this);
        text_queren.setOnClickListener(this);
        edit_test.setOnClickListener(this);

        switch (category) {
            case 1:
                text_title.setText("修改电子邮箱");
                text_hint.setText("4-20个字符, (可由中英文, 数字, _, -) 组成");
                url = URLs.MAIN_INFO_EMAIL;
                edit_test.setText(SettingUtils.get(URLs.MAIN_INFO_EMAIL, ""));
                break;
            case 2:
                text_title.setText("修改真实姓名");
                text_hint.setText("2-20个中文组成");
                url = URLs.MAIN_INFO_NAME;
                edit_test.setText(SettingUtils.get(URLs.MAIN_INFO_NAME, ""));
                break;
            case 3:
                text_title.setText("修改联系电话");
                text_hint.setText("11位纯数字或区号+固话");
                url = URLs.MAIN_INFO_PHONE;
                edit_test.setText(SettingUtils.get(URLs.MAIN_INFO_PHONE, ""));
                edit_test.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                edit_test.setText(BaseApplication.getApp().getInfo().getPhone());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_return:
                finish();
                break;
            case R.id.image_delete:
                edit_test.setText("");
                break;
            case R.id.text_queren:
                String num = edit_test.getText().toString().trim();
                if (CommonTools.checkCellphone(num) || CommonTools.checkTelephone(num)) {
                    // 更新用户手机号码
                    updateInfo(num);
                } else {
                    DisPlay("请输入正确的号码");
                }
                break;
            default:
                break;
        }

    }

    private void updateInfo(final String phone) {
        UserInfo info = null;
        try {
            info = (UserInfo) BaseApplication.getApp().getInfo().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        if (info != null) {
            info.setPhone(phone);
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, getString(R.string.updating));
            HttpServer.updateUser(TAG, info, new GenericCallBack<BaseResponse<Object>>(new TypeToken<BaseResponse<Object>>() {
            }.getType()) {
                @Override
                public void onSuccess(Response<BaseResponse<Object>> response) {
                    if (response.body().isOK()) {
                        DisplayToast("更新成功");
                        BaseApplication.getApp().getInfo().setPhone(phone);
                        finish();
                    } else {
                        DisplayToast("更新失败");
                    }
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }

                @Override
                public void onError(Response<BaseResponse<Object>> response) {
                    super.onError(response);
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    DisPlay(response.message());
                }
            });

        } else {
            DisplayToast("未知错误");
        }

    }

    public static Intent getIntent_Common(Context context, int i) {
        Intent intent = new Intent(context, Activity_Add_Main_Info.class);
        category = i;
        return intent;
    }

}
       