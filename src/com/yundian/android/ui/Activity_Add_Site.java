package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.model.Response;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.Address;
import com.yundian.android.bean.BaseResponse;
import com.yundian.android.bean.Site_Bean;
import com.yundian.android.net.GenericCallBack;
import com.yundian.android.net.HttpServer;
import com.yundian.android.utils.NetWorkUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加地址
 *
 * @author ShaoZhen-PC
 */
public class Activity_Add_Site extends BaseActivity {


    ImageView image_return;
    private String provice;
    private String city;


    @OnClick(R.id.image_return)
    public void back() {
        finish();
    }

    @OnClick(R.id.button_add_dizhi)
    public void addAddress() {
        if (!NetWorkUtil.isNetConnected(this)) {
            DisplayToast(R.string.no_net_work);
            return;
        }
        String mobile = edit_phone.getText().toString().trim();
        String phone = edit_telephone.getText().toString().trim();
        String person = edit_name.getText().toString().trim();
        String email = edit_mailbox.getText().toString().trim();
        String addr = edit_site.getText().toString().trim();
        final Address address = new Address();
        address.setMobile(mobile);
        address.setPhone(phone);
        address.setProvice(provice);
        address.setCity(city);
        address.setShouhuoren(person);
        address.setEmail(email);
        address.setAddr(addr);

        HttpServer.addUserAdd(TAG, address, new GenericCallBack<BaseResponse<Object>>(BaseResponse.class) {
            @Override
            public void onSuccess(Response<BaseResponse<Object>> response) {
                if (response.body().isOK()) {
                    BaseApplication.getApp().getAddresses().add(address);
                    finish();
                } else {
                    DisplayToast(R.string.retry);
                }
            }

            @Override
            public void onError(Response<BaseResponse<Object>> response) {
                super.onError(response);
                DisplayToast(R.string.retry);
            }
        });
    }

    @OnClick(R.id.tv_diqu)
    public void seletDiqu() {
        Intent intent = new Intent();
        intent.setClass(this, Activity_Cascade_Address.class);
        startActivityForResult(intent, 0);
    }


    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    View more;


    @BindView(R.id.edit_name)
    EditText edit_name;

    @BindView(R.id.edit_phone)
    EditText edit_phone;

    @BindView(R.id.tv_diqu)
    TextView tv_diqu;

    @BindView(R.id.edit_site)
    EditText edit_site;

    @BindView(R.id.edit_telephone)
    EditText edit_telephone;

    @BindView(R.id.edit_mailbox)

    EditText edit_mailbox;


    public static int position = 0;

    private static List<Site_Bean> sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);
        ButterKnife.bind(this);
        title.setVisibility(View.GONE);
        more.setVisibility(View.GONE);
        sub_title.setText("新建地址");
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {


        edit_site.setSingleLine(false);
        if (sb != null) {
            edit_name.setText(sb.get(position).getName());
            edit_phone.setText(sb.get(position).getPhone());
            tv_diqu.setText(sb.get(position).getRegion());
            edit_site.setText(sb.get(position).getSite());
            edit_telephone.setText(sb.get(position).getTelephone());
            edit_mailbox.setText(sb.get(position).getMailbox());
        }
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        provice = data.getStringExtra(Activity_Cascade_Address.PROVICE_KEY);
        city = data.getStringExtra(Activity_Cascade_Address.CITY_KEY);
        tv_diqu.setText(provice);
        tv_diqu.append(city);
    }

    public static Intent getIntent_Common(Context context, List<Site_Bean> site_bean, int p) {
        Intent intent = new Intent(context, Activity_Add_Site.class);
        position = p;
        sb = site_bean;
        return intent;
    }
}
