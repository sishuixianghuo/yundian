package com.yundian.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.UserInfo;
import com.yundian.android.utils.CommonTools;
import com.yundian.android.utils.ExitView;

/**
 * 我的
 *
 * @author ShaoZhen-PC
 */
public class Activity_Personal extends BaseActivity implements OnClickListener {

    private Button mLoginButton;
    private Intent mIntent = null;
    private ExitView exit;
    private LinearLayout Ly_login;
    private int LOGIN_CODE = 100;
    private RelativeLayout rl_site_manage;
    private RelativeLayout rl_wodexinxi;
    private RelativeLayout rl_xiugaimima;
    private RelativeLayout rl_dingdan;

    private TextView text_daifukuan;
    private TextView text_daishouhuo;
    private TextView text_daiziti;
    private TextView text_daipingjia;
    private TextView user_name;
    private UserInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        findViewById();
    }

    @Override
    protected void findViewById() {
        user_name = (TextView) findViewById(R.id.user_name);
        mLoginButton = (Button) findViewById(R.id.personal_login_button);
        Ly_login = (LinearLayout) findViewById(R.id.login);
        rl_site_manage = (RelativeLayout) findViewById(R.id.rl_site_manage);
        rl_wodexinxi = (RelativeLayout) findViewById(R.id.rl_wodexinxi);
        rl_xiugaimima = (RelativeLayout) findViewById(R.id.rl_xiugaimima);
        rl_dingdan = (RelativeLayout) findViewById(R.id.rl_dingdan);

        text_daifukuan = (TextView) findViewById(R.id.text_daifukuan);
        text_daishouhuo = (TextView) findViewById(R.id.text_daishouhuo);
        text_daiziti = (TextView) findViewById(R.id.text_daiziti);
        text_daipingjia = (TextView) findViewById(R.id.text_daipingjia);
    }

    @Override
    protected void initView() {

        mLoginButton.setOnClickListener(this);
        rl_site_manage.setOnClickListener(this);
        rl_wodexinxi.setOnClickListener(this);
        rl_xiugaimima.setOnClickListener(this);
        rl_dingdan.setOnClickListener(this);

        text_daifukuan.setOnClickListener(this);
        text_daishouhuo.setOnClickListener(this);
        text_daiziti.setOnClickListener(this);
        text_daipingjia.setOnClickListener(this);
        info = BaseApplication.getApp().getInfo();
        if (info != null) {
        	user_name.setText(info.getNickname());
            mLoginButton.setText(info.getEmail());
            mLoginButton.setBackground(null);
        } else {
            user_name.setText(R.string.personal_welcome);
            mLoginButton.setText(R.string.personal_login);
            mLoginButton.setBackgroundResource(R.drawable.my_personal_click_login);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onClick(View v) {
        //CommonTools.showShortToast(PersonalActivity.this, "稍后开放");
        switch (v.getId()) {
            case R.id.personal_login_button:
                if (info == null) {
                    mIntent = new Intent(Activity_Personal.this, Activity_Login.class);
                    startActivityForResult(mIntent, LOGIN_CODE);
                }
                break;
            case R.id.rl_site_manage:
                mIntent = new Intent(Activity_Personal.this, Activity_Site_Manage.class);
                startActivity(mIntent);
                break;
            case R.id.rl_wodexinxi:
                startActivity(Activity_Main_Info.getIntent_Common(this));
                break;
            case R.id.rl_xiugaimima:
                startActivity(Activity_Revise_Password.getIntent_Common(this));
                break;
            case R.id.text_daifukuan:   //代付款
                startActivity(Activity_Cascade_Address.getIntent_Common(this));
                break;
            case R.id.text_daishouhuo:  //待收货
                startActivity(Activity_Pay_Receive.getIntent_Common(this, 2));
                break;
            case R.id.text_daiziti:
                break;
            case R.id.text_daipingjia:
                break;
            case R.id.rl_dingdan:
                startActivity(Activity_My_Order.getIntent_Common(this));
                break;

            default:
                break;
        }

    }


    public void logout(View v) {
        DisplayToast("tuichu ");
        BaseApplication.getApp().setToken(null);
        BaseApplication.getApp().setInfo(null);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (resultCode == 20) {
//			String name=data.getExtras().getString("username");
//			Log.i("name", name);
//			username.setText(name);
            if (Ly_login.isShown()) {
                Ly_login.setVisibility(View.GONE);
            }
            Ly_login.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //为弹出窗口实现监听类
    private OnClickListener itemsOnClick = new OnClickListener() {

        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_exit:
                    CommonTools.showShortToast(Activity_Personal.this, "退出程序");

                    break;
                case R.id.btn_cancel:
                    Activity_Personal.this.dismissDialog(R.id.btn_cancel);

                    break;
                default:
                    break;
            }
        }
    };

}
