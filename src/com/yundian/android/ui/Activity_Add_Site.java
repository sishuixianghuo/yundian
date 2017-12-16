package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yundian.android.R;
import com.yundian.android.URLs;
import com.yundian.android.bean.Site_Bean;
import com.yundian.android.utils.JsonUtil;
import com.yundian.android.utils.SettingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加地址
 *
 * @author ShaoZhen-PC
 */
public class Activity_Add_Site extends BaseActivity implements OnClickListener {

    private ImageView image_return;
    private Intent mIntent;
    private TextView text_title;
    private EditText edit_name;
    private EditText edit_phone;
    private TextView tv_diqu;
    private EditText edit_site;
    private EditText edit_telephone;
    private EditText edit_mailbox;
    private Button button_add_dizhi;
    private List<Site_Bean> site_been;
    public static int position  = 0;

    private static List<Site_Bean> sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {
        image_return = (ImageView) findViewById(R.id.image_return);
        text_title = (TextView) findViewById(R.id.text_title);
        button_add_dizhi = (Button) findViewById(R.id.button_add_dizhi);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        tv_diqu = (TextView) findViewById(R.id.tv_diqu);
        edit_site = (EditText) findViewById(R.id.edit_site);
        edit_telephone = (EditText) findViewById(R.id.edit_telephone);
        edit_mailbox = (EditText) findViewById(R.id.edit_mailbox);
        edit_site.setSingleLine(false);
        if (sb != null) {
            text_title.setText("编辑");
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
        image_return.setOnClickListener(this);
        button_add_dizhi.setOnClickListener(this);
        edit_name.setOnClickListener(this);
        edit_phone.setOnClickListener(this);
        tv_diqu.setOnClickListener(this);
        edit_site.setOnClickListener(this);
        edit_telephone.setOnClickListener(this);
        edit_mailbox.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_return:
                finish();
                break;
            case R.id.button_add_dizhi:
                site_been = new Gson().fromJson(SettingUtils.get(URLs.LIST_SITE_JSON, ""), new TypeToken<List<Site_Bean>>() {
                }.getType());
                if(site_been == null)
                    site_been = new ArrayList<Site_Bean>();
                Site_Bean sb = new Site_Bean();
                sb.setName(edit_name.getText().toString()+"");
                sb.setPhone(edit_phone.getText().toString()+"");
                sb.setRegion(tv_diqu.getText().toString()+"");
                sb.setSite(edit_site.getText().toString()+"");
                sb.setTelephone(edit_telephone.getText().toString()+"");
                sb.setMailbox(edit_mailbox.getText().toString()+"");
                site_been.add(sb);
                SettingUtils.set(URLs.LIST_SITE_JSON, JsonUtil.toJson(site_been));
                finish();
            case R.id.tv_diqu:
                Intent intent=new Intent();
                intent.setClass(this, Activity_Cascade_Address.class);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String address = data.getStringExtra("address");
        tv_diqu.setText(address);
    }

    public static Intent getIntent_Common(Context context, List<Site_Bean> site_bean, int p) {
        Intent intent = new Intent(context, Activity_Add_Site.class);
        position = p;
        sb = site_bean;
        return intent;
    }
}
