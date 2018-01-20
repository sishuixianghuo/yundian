package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.Province;
import com.yundian.android.cascade.CascadeActivity;
import com.yundian.android.cascade.OnWheelChangedListener;
import com.yundian.android.cascade.WheelView;
import com.yundian.android.cascade.adapters.ArrayWheelAdapter;
import com.yundian.android.utils.LogUtils;

import java.util.List;

/**
 * 修改我的信息
 * 添加地址
 *
 * @author ShaoZhen-PC
 */
public class Activity_Cascade_Address extends CascadeActivity implements View.OnClickListener, OnWheelChangedListener {

    public static String TAG = "Activity_Cascade_Address";
    public static final String PROVICE_KEY = "provice";
    public static final String CITY_KEY = "city";
    public static final String COUNTY_KEY = "county";


    private int resultCode = 0;

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;

    private View view_return;
    private List<Province> areas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cascade_address);

        areas = BaseApplication.getApp().getCantons();
        setUpViews();
        setUpListener();
        setUpData();
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        view_return = findViewById(R.id.view_return);
    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        mBtnConfirm.setOnClickListener(this);
        view_return.setOnClickListener(this);
    }

    private void setUpData() {
//        initProvinceDatas();

        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(this, areas.toArray(new Province[areas.size()])));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {

//            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
//            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int p = mViewProvince.getCurrentItem();
        int c = mViewCity.getCurrentItem();
        List<Province.Areas> diqu = areas.get(p).getAreas().get(c).getAreas();
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<>(this, diqu.toArray(new Province.Areas[diqu.size()])));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = diqu.get(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int p = mViewProvince.getCurrentItem();
        List<Province.Citys> cities = areas.get(p).getAreas();
        mViewCity.setViewAdapter(new ArrayWheelAdapter<>(this, cities.toArray(new Province.Citys[cities.size()])));
        mViewCity.setCurrentItem(0);
        mCurrentCityName = cities.get(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult();
                break;
            case R.id.view_return:
                finish();
                break;
            default:
                break;
        }
    }

    private void showSelectedResult() {
        Intent mIntent = new Intent();

        mIntent.putExtra(PROVICE_KEY, mViewProvince.getCurrentItem());
        mIntent.putExtra(CITY_KEY, mViewCity.getCurrentItem());
        mIntent.putExtra(COUNTY_KEY, mViewDistrict.getCurrentItem());

        this.setResult(resultCode, mIntent);
        finish();
    }

    public static Intent getIntent_Common(Context context) {
        Intent intent = new Intent(context, Activity_Cascade_Address.class);
        LogUtils.e(TAG, "context : " + context.toString());
        return intent;
    }
}