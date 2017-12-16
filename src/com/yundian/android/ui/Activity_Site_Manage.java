package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yundian.android.R;
import com.yundian.android.URLs;
import com.yundian.android.bean.Site_Bean;
import com.yundian.android.utils.JsonUtil;
import com.yundian.android.utils.LogUtils;
import com.yundian.android.utils.SettingUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 地址管理
 *
 * @author ShaoZhen-PC
 */
public class Activity_Site_Manage extends BaseActivity implements OnClickListener {

    private ImageView image_return;
    private Intent mIntent;
    private ListView list_dizhi;
    private Button button_add_dizhi;
    private List<Site_Bean> site_been;
    private myAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_manage);
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {
        image_return = (ImageView) this.findViewById(R.id.image_return);
        list_dizhi = (ListView) findViewById(R.id.list_dizhi);
        button_add_dizhi = (Button) findViewById(R.id.button_add_dizhi);
    }

    @Override
    protected void initView() {
        image_return.setOnClickListener(this);
        button_add_dizhi.setOnClickListener(this);
        site_been = new ArrayList<Site_Bean>();
//        for (int i=0;i<10;i++) {
//            Site_Bean sb = new Site_Bean();
//            sb.setName("邵震");
//            sb.setPhone("1851500222" + i);
//            sb.setRegion("北京市朝阳区" + i);
//            sb.setSite("建外SOHO西区16号楼308" + i);
//            sb.setTelephone("10010" + i);
//            sb.setMailbox("123" + i + "@qq.com");
//            site_been.add(sb);
//        }
            site_been = new Gson().fromJson(SettingUtils.get(URLs.LIST_SITE_JSON,"[]"), new TypeToken<List<Site_Bean>>() {}.getType());
        LogUtils.e(TAG,"URLs.LIST_SITE_JSON : " + SettingUtils.get(URLs.LIST_SITE_JSON,""));
        myAdapter = new myAdapter(this,site_been);
        list_dizhi.setAdapter(myAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        site_been = new Gson().fromJson(SettingUtils.get(URLs.LIST_SITE_JSON,"[]"), new TypeToken<List<Site_Bean>>() {}.getType());
        LogUtils.e(TAG,"URLs.LIST_SITE_JSON : " + SettingUtils.get(URLs.LIST_SITE_JSON,""));
        myAdapter.setList(site_been);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_return:
                finish();
                break;
            case R.id.button_add_dizhi:
               startActivity(new Intent(this,Activity_Add_Site.class));
                break;
            default:
                break;
        }

    }

    public final class ViewHolder {
        public Button button_reduce;
        public TextView text_name;
        public TextView text_phone;
        public TextView text_site;
        public TextView text_compile;
        public TextView text_delete;
        public RadioButton rb_default;
        public Button viewBtn;
    }

    public class myAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<Site_Bean> site_been;
        HashMap<String, Boolean> states = new HashMap<String, Boolean>();//用于记录每个RadioButton的状态，并保证只可选一个

        public myAdapter(Context context,List<Site_Bean> site_beens) {
            this.mInflater = LayoutInflater.from(context);
            this.site_been = site_beens;
        }

        @Override
        public int getCount() {
            return site_been.size();
        }

        public void setList(List<Site_Bean> site_been){
            this.site_been = site_been;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.item_site_manage, null);
                holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
                holder.text_phone = (TextView) convertView.findViewById(R.id.text_phone);
                holder.text_site = (TextView) convertView.findViewById(R.id.text_site);
                holder.text_compile = (TextView) convertView.findViewById(R.id.text_compile);
                holder.text_delete = (TextView) convertView.findViewById(R.id.text_delete);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final RadioButton radio = (RadioButton) convertView.findViewById(R.id.rb_default);
            holder.rb_default = radio;
            holder.text_name.setText(site_been.get(position).getName());
            holder.text_phone.setText(site_been.get(position).getPhone());
            holder.text_site.setText(site_been.get(position).getSite());
            //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
            holder.rb_default.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //重置，确保最多只有一项被选中
                    for (String key : states.keySet()) {
                        states.put(key, false);
                    }
                    states.put(String.valueOf(position), radio.isChecked());
                    myAdapter.this.notifyDataSetChanged();
                }
            });
            holder.text_compile.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LogUtils.e(TAG,"position : " + position );
                    startActivity(Activity_Add_Site.getIntent_Common(getApplicationContext(),site_been,position));
                }
            });
            holder.text_delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LogUtils.e(TAG,"position : " + position);
                    site_been.remove(position);
                    SettingUtils.set(URLs.LIST_SITE_JSON, JsonUtil.toJson(site_been));
                    LogUtils.e(TAG,"URLs.LIST_SITE_JSON : " + SettingUtils.get(URLs.LIST_SITE_JSON,""));
                    myAdapter.notifyDataSetChanged();
                }
            });

            boolean res = false;
            if (states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false) {
                res = false;
                states.put(String.valueOf(position), false);
            } else {
                res = true;
            }

            holder.rb_default.setChecked(res);
            return convertView;
        }
    }
}
