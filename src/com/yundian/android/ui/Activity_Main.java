package com.yundian.android.ui;

import java.util.ArrayList;
import java.util.List;

import com.yundian.android.R;
import com.yundian.android.widgets.jazzviewpager.JazzyViewPager;
import com.yundian.android.widgets.jazzviewpager.JazzyViewPager.TransitionEffect;
import com.yundian.android.widgets.jazzviewpager.OutlineContainer;
import com.yundian.android.widgets.viewpager.CirclePageIndicator;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;


public class Activity_Main extends BaseActivity {

	public static final String TAG = Activity_Main.class.getSimpleName();

	private JazzyViewPager mViewPager = null;
	private List<View> mPageViews = new ArrayList<View>();
	private CirclePageIndicator mIndicator = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		mViewPager = (JazzyViewPager) findViewById(R.id.main_container);
		mIndicator = (CirclePageIndicator) findViewById(R.id.main_indicator);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		View view1 = new View(Activity_Main.this);
		view1.setBackgroundColor(getResources().getColor(R.color.blue));
		mPageViews.add(view1);

		View view2 = new View(Activity_Main.this);
		view2.setBackgroundColor(getResources().getColor(R.color.red));
		mPageViews.add(view2);

		View view3 = new View(Activity_Main.this);
		view3.setBackgroundColor(getResources().getColor(R.color.green));
		mPageViews.add(view3);

		View view4 = new View(Activity_Main.this);
		view4.setBackgroundColor(getResources().getColor(R.color.yellow));
		mPageViews.add(view4);

		mViewPager.setTransitionEffect(TransitionEffect.FlipHorizontal);
		mViewPager.setAdapter(new MainAdapter());

		mIndicator.setCentered(true);
		mIndicator.setRadius(8);
		mIndicator.setViewPager(mViewPager);
	}

	private class MainAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			container.addView(mPageViews.get(position),
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			mViewPager.setObjectForPosition(mPageViews.get(position), position);
			return mPageViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object obj) {
			container.removeView(mViewPager.findViewFromObject(position));
		}

		@Override
		public int getCount() {
			return mPageViews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}
	}

}
