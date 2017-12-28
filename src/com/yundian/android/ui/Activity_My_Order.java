package com.yundian.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yundian.android.R;
import com.yundian.android.fragment.EmptyFragment;
import com.yundian.android.fragment.OrderListFragment;
import com.yundian.android.fragment.TestFragment;
import com.yundian.android.widgets.PageHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 *
 * @author ShaoZhen-PC
 */
public class Activity_My_Order extends BaseActivity {


    @OnClick(R.id.image_return)
    public void back() {
        finish();
    }

    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    View more;

    @BindView(R.id.user_scrollview)
    PageHorizontalScrollView scrollview;

    @BindView(R.id.view_page)
    ViewPager view_page;

    private List<Fragment> fragments;
    private String[] titleList = new String[]{"全部", "待付款", "待收货", "待自提", "待评价"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        title.setVisibility(View.GONE);
        more.setVisibility(View.GONE);
        sub_title.setText("我的订单");

        fragments = new ArrayList<>();
        Bundle args = new Bundle();
        OrderListFragment all = new OrderListFragment();
        all.setArguments(args);
        TestFragment other = new TestFragment();
        all.setArguments(args);
        TestFragment free = new TestFragment();
        free.setArguments(args);
        EmptyFragment selling = new EmptyFragment();
        selling.setArguments(args);
        EmptyFragment shouhou = new EmptyFragment();
        shouhou.setArguments(args);
        fragments.add(all);
//        fragments.add(other);
//        fragments.add(free);
//        fragments.add(selling);
//        fragments.add(shouhou);
        view_page.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleList[position];
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        view_page.setOffscreenPageLimit(fragments.size());
        scrollview.setViewPager(view_page);
        scrollview.setTextSize(40);

    }

    @Override
    protected void findViewById() {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


}
       