package com.yundian.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.yundian.android.R;
import com.yundian.android.cascade.CascadeActivity;
import com.yundian.android.cascade.OnWheelChangedListener;
import com.yundian.android.cascade.WheelView;
import com.yundian.android.cascade.adapters.ArrayWheelAdapter;
import com.yundian.android.utils.LogUtils;

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


    private int resultCode = 0;

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;

    private View view_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cascade_address);
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
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(Activity_Cascade_Address.this, mProvinceDatas));
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
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = areas[0];
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<>(this, cities));
        mViewCity.setCurrentItem(0);
        mCurrentCityName= cities[0];
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
        mIntent.putExtra(PROVICE_KEY, mCurrentProviceName);
        mIntent.putExtra(CITY_KEY, mCurrentCityName + "" + mCurrentDistrictName);
        this.setResult(resultCode, mIntent);
        finish();
    }

    public static Intent getIntent_Common(Context context) {
        Intent intent = new Intent(context, Activity_Cascade_Address.class);
        LogUtils.e(TAG, "context : " + context.toString());
        return intent;
    }
}