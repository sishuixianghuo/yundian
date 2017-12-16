package com.yundian.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yundian.android.R;
import com.yundian.android.adapter.ImageAndTextListAdapter;
import com.yundian.android.bean.ImageAndText;
import com.yundian.android.utils.LogUtils;
import com.yundian.android.widgets.ADInfo;
import com.yundian.android.widgets.ImageCycleView;
import com.yundian.android.widgets.ImageCycleView.ImageCycleViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 云电首页
 *
 * @author ShaoZhen-PC
 */
public class Activity_HomePage extends BaseActivity implements OnClickListener {
    public static final String TAG = Activity_HomePage.class.getSimpleName();

    private Intent mIntent;
    private ImageView button_seek;
    private EditText edit_seek;
    private GridView gv_commodity;
    private GridView gv_filtration;
    private LinearLayout classify;
    private ImageCycleView ad_view;
    private Button button_classify_brand;
    private Button button_classify_purpose;
    private Button button_classify_rank;
    private RadioGroup rg_filtration_purpose;
    private RadioGroup rg_filtration_sort;

    private RelativeLayout rl_classify_brand;

    private GestureDetector gestureDetector;

    final int TOP = 0;
    final int BOTTOM = 1;

    private ImageCycleView mAdView;
    private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_0912519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        findViewById();
        initView();

        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("top-->" + i);
            infos.add(info);
        }

        mAdView = (ImageCycleView) findViewById(R.id.ad_view);
        mAdView.setImageResources(infos, mAdCycleViewListener);
    }

    @Override
    protected void findViewById() {
        button_seek = (ImageView) findViewById(R.id.button_seek);
        edit_seek = (EditText) findViewById(R.id.edit_seek);
        gv_commodity = (GridView) findViewById(R.id.gv_commodity);
        gv_filtration = (GridView) findViewById(R.id.gv_filtration);
        classify = (LinearLayout) findViewById(R.id.classify);
        ad_view = (ImageCycleView) findViewById(R.id.ad_view);
        button_classify_brand = (Button) findViewById(R.id.button_classify_brand);
        button_classify_purpose = (Button) findViewById(R.id.button_classify_purpose);
        button_classify_rank = (Button) findViewById(R.id.button_classify_rank);
        rl_classify_brand = (RelativeLayout) findViewById(R.id.rl_classify_brand);
        rg_filtration_purpose = (RadioGroup) findViewById(R.id.rg_filtration_purpose);
        rg_filtration_sort = (RadioGroup) findViewById(R.id.rg_filtration_sort);

        button_seek.setOnClickListener(this);
        button_classify_brand.setOnClickListener(this);
        button_classify_purpose.setOnClickListener(this);
        button_classify_rank.setOnClickListener(this);
        rl_classify_brand.setOnClickListener(this);
        rg_filtration_purpose.setOnClickListener(this);
        rg_filtration_sort.setOnClickListener(this);

        List<ImageAndText> list = new ArrayList<ImageAndText>();

        for (int i = 0; i < 100; i++) {
            list.add(new ImageAndText(
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490266580150&di=af41066883a9bf5554a226afd7fec1fe&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2F908fa0ec08fa513dde11606e3d6d55fbb3fbd9c3.jpg",
                    "挖掘技术哪,中国山东找蓝翔!"));
        }

        gv_commodity.setAdapter(new ImageAndTextListAdapter(this, list, gv_commodity));
        ArrayList<String> list2 = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list2.add("参数" + i);
        }
        gv_filtration.setAdapter(new MyAdapter(this, list2));
//		rl_classify_brand.setOnTouchListener();
//				setOutsideTouchable

        gv_commodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long id) {
//              Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_LONG).show();
                Intent intent = Activity_Product_Info.getIntent_Common(Activity_HomePage.this);
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        private ArrayList<String> list;
        private Activity activity;

        public MyAdapter(Activity activity,
                         ArrayList<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewFiltration viewFiltration = null;
            if (rowView == null) {
                viewFiltration = new ViewFiltration();
                LayoutInflater inflater = activity.getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_filtration, null);
                viewFiltration.text_filtration = (TextView) rowView.findViewById(R.id.text_filtration);
                rowView.setTag(viewFiltration);
            } else {
                viewFiltration = (ViewFiltration) rowView.getTag();
            }

            viewFiltration.text_filtration.setText(list.get(position));
            return rowView;
        }
    }

    static class ViewFiltration {
        TextView text_filtration;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        Drawable rightDrawable_on = getResources().getDrawable(R.drawable.home_ic_sifter_pre);
        rightDrawable_on.setBounds(0, 0, rightDrawable_on.getMinimumWidth(), rightDrawable_on.getMinimumHeight());
        Drawable rightDrawable_off = getResources().getDrawable(R.drawable.home_ic_sifter);
        rightDrawable_off.setBounds(0, 0, rightDrawable_off.getMinimumWidth(), rightDrawable_off.getMinimumHeight());
        switch (v.getId()) {
            case R.id.rl_classify_brand:
                rl_classify_brand.setVisibility(View.GONE);
                break;
            case R.id.rg_filtration_purpose:
                rg_filtration_purpose.setVisibility(View.VISIBLE);
                rg_filtration_sort.setVisibility(View.GONE);
                gv_filtration.setVisibility(View.GONE);
                break;
            case R.id.rg_filtration_sort:
                break;
            case R.id.edit_seek:
                break;
            case R.id.button_classify_brand:
            case R.id.button_classify_purpose:
            case R.id.button_classify_rank:
                setFiltrationButton(v.getId());
                break;
            default:
                break;
        }
    }

    private void setFiltrationButton(int view) {
        Drawable rightDrawable_on = getResources().getDrawable(R.drawable.home_ic_sifter_pre);
        rightDrawable_on.setBounds(0, 0, rightDrawable_on.getMinimumWidth(), rightDrawable_on.getMinimumHeight());
        Drawable rightDrawable_off = getResources().getDrawable(R.drawable.home_ic_sifter);
        rightDrawable_off.setBounds(0, 0, rightDrawable_off.getMinimumWidth(), rightDrawable_off.getMinimumHeight());

        button_classify_brand.setTextColor(getResources().getColor(R.color.c262626));
        button_classify_purpose.setTextColor(getResources().getColor(R.color.c262626));
        button_classify_rank.setTextColor(getResources().getColor(R.color.c262626));
        button_classify_brand.setCompoundDrawables(null, null, rightDrawable_off, null);
        button_classify_purpose.setCompoundDrawables(null, null, rightDrawable_off, null);
        button_classify_rank.setCompoundDrawables(null, null, rightDrawable_off, null);
        if (rl_classify_brand.getVisibility() == View.VISIBLE) {
            rl_classify_brand.setVisibility(View.GONE);
        } else {
            switch (view) {
                case R.id.button_classify_brand:
                    LogUtils.e(TAG, "品牌");
                    if (rl_classify_brand.getVisibility() == View.GONE) {
                        rl_classify_brand.setVisibility(View.VISIBLE);
                        rg_filtration_purpose.setVisibility(View.GONE);
                        rg_filtration_sort.setVisibility(View.GONE);
                        gv_filtration.setVisibility(View.VISIBLE);
                        button_classify_brand.setTextColor(getResources().getColor(R.color.e60012));
                        button_classify_brand.setCompoundDrawables(null, null, rightDrawable_on, null);
                    }
                    break;
                case R.id.button_classify_purpose:
                    LogUtils.e(TAG, "用途");
                    if (rl_classify_brand.getVisibility() == View.GONE) {
                        rl_classify_brand.setVisibility(View.VISIBLE);
                        gv_filtration.setVisibility(View.GONE);
                        rg_filtration_purpose.setVisibility(View.VISIBLE);
                        rg_filtration_sort.setVisibility(View.GONE);
                        button_classify_purpose.setTextColor(getResources().getColor(R.color.e60012));
                        button_classify_purpose.setCompoundDrawables(null, null, rightDrawable_on, null);
                    }
                    break;
                case R.id.button_classify_rank:
                    LogUtils.e(TAG, "排序");
                    if (rl_classify_brand.getVisibility() == View.GONE) {
                        rl_classify_brand.setVisibility(View.VISIBLE);
                        gv_filtration.setVisibility(View.GONE);
                        rg_filtration_purpose.setVisibility(View.GONE);
                        rg_filtration_sort.setVisibility(View.VISIBLE);
                        button_classify_rank.setTextColor(getResources().getColor(R.color.e60012));
                        button_classify_rank.setCompoundDrawables(null, null, rightDrawable_on, null);
                    }
                    break;
            }
        }
    }

    private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            Toast.makeText(Activity_HomePage.this, "content->" + info.getContent(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
//            ImageLoader.getInstance().displayImage(imageURL, imageView);// 使用ImageLoader对图片进行加装！
            loadImage(imageURL,imageView);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.startImageCycle();
    }

    ;

    @Override
    protected void onPause() {
        super.onPause();
        mAdView.pushImageCycle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdView.pushImageCycle();
    }

}
