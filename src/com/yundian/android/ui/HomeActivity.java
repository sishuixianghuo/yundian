package com.yundian.android.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.yundian.android.AppManager;
import com.yundian.android.R;

public class HomeActivity extends TabActivity {

	public static final String TAG = HomeActivity.class.getSimpleName();

	private RadioGroup mTabButtonGroup;
	private TabHost mTabHost;

	public static final String TAB_MAIN = "MAIN_ACTIVITY";
	public static final String TAB_CATEGORY = "CATEGORY_ACTIVITY";
	public static final String TAB_CART = "CART_ACTIVITY";
	public static final String TAB_PERSONAL = "PERSONAL_ACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
		setContentView(R.layout.activity_home);
		findViewById();
		initView();
	}

	private void findViewById() {
		mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
	}

	private void initView() {

		mTabHost = getTabHost();

		Intent i_main = new Intent(this, Activity_HomePage.class);
		Intent i_category = new Intent(this, Activity_Category.class);
		Intent i_cart = new Intent(this, Activity_Cart.class);
		Intent i_personal = new Intent(this, Activity_Personal.class);

		mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
				.setContent(i_main));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CATEGORY)
				.setIndicator(TAB_CATEGORY).setContent(i_category));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CART).setIndicator(TAB_CART)
				.setContent(i_cart));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL)
				.setIndicator(TAB_PERSONAL).setContent(i_personal));

		mTabHost.setCurrentTabByTag(TAB_MAIN);

		mTabButtonGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.home_tab_main:
							mTabHost.setCurrentTabByTag(TAB_MAIN);
							break;

						case R.id.home_tab_category:
							mTabHost.setCurrentTabByTag(TAB_CATEGORY);
							break;

						case R.id.home_tab_cart:
							mTabHost.setCurrentTabByTag(TAB_CART);
							break;

						case R.id.home_tab_personal:
							mTabHost.setCurrentTabByTag(TAB_PERSONAL);
							break;

						default:
							break;
						}
					}
				});
	}

}
