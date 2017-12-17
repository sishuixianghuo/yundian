package com.yundian.android.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 轮播图
 * 作者：gassion on 17/8/28 11:38
 * 最可怕的敌人，就是没有坚强的信念。
 */
public class BannerViewPager extends ViewPager {

    private ViewPagerAdapter viewPagerAdapter;

    public interface BannerViewPagerAdapter {

        int bannerImageCount();

        String bannerImageUrlAtIndex(int index);

        void bannerDidSelectAtIndex(int index);

    }

    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private final int SCROLL_BORDER = 2, TIMER_UPDATE = 3;
    private BannerViewPagerAdapter adapter;
    private Timer timer;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIMER_UPDATE: {
                    int count = adapter.bannerImageCount();
                    int index = getCurrentItem();
                    index = (index + 1) % (count + 2);
                    setCurrentItem(index);
                    break;
                }
                case SCROLL_BORDER: {
                    int count = adapter.bannerImageCount();
                    int current = getCurrentItem();
                    if (current == 0) {
                        setCurrentItem(count, false);
                    } else if (current == count + 1) {
                        setCurrentItem(1, false);
                    }
                    break;
                }
            }
        }
    };

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRootView(View view) {
        this.linearLayout = (LinearLayout) view.findViewById(R.id.common_banner_linear_layout);
        this.relativeLayout = (RelativeLayout) view.findViewById(R.id.common_banner_relayout_layout);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void setPagerAdapter(BannerViewPagerAdapter adapter) {
        this.stopTimer();
        this.adapter = adapter;
        if (this.adapter != null) {
            viewPagerAdapter = new ViewPagerAdapter();
            super.setAdapter(viewPagerAdapter);
            super.setOnPageChangeListener(viewPagerAdapter);
            final int count = adapter.bannerImageCount();
            if (count > 1) {
                //底部展示的点
                linearLayout.setVisibility(VISIBLE);
                linearLayout.removeAllViews();
                for (int i = 0; i < count; i++) {
                    ImageView imageView = new ImageView(getContext());
                    int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,7, BaseApplication.getApp().getMetrics());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(radius, radius);
                    layoutParams.leftMargin = 7;
                    linearLayout.addView(imageView, layoutParams);
                }
                this.setCurrentItem(1);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(TIMER_UPDATE);
                    }
                }, 5000, 5000);
            } else {
                linearLayout.setVisibility(GONE);
            }
        }
    }


    public void setVisibility(int visibility, float height) {
        if (visibility == View.GONE) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
        super.setVisibility(visibility);
    }


    public class ViewPagerAdapter extends PagerAdapter implements OnPageChangeListener {

        private Map<Integer, ImageView> cache = new HashMap<Integer, ImageView>();

        public String urlAtIndex(int index) {
            return adapter.bannerImageUrlAtIndex(index);
        }

        @Override
        public void onPageSelected(int var1) {
            int count = adapter.bannerImageCount();
            if (count > 1) {
                handler.sendEmptyMessageDelayed(SCROLL_BORDER, 500);
            }
            int index = indexForPosition(var1);
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                ImageView imageView = (ImageView) linearLayout.getChildAt(i);
                if (i == index) {
                    imageView.setBackgroundResource(R.drawable.banner_point_normal);
                } else {
                    imageView.setBackgroundResource(R.drawable.banner_point_pressed);
                }
            }
        }

        @Override
        public void onPageScrolled(int var1, float var2, int var3) {

        }

        @Override
        public void onPageScrollStateChanged(int var1) {

        }

        @Override
        public int getCount() {
            int count = 0;
            if (adapter != null) {
                count = adapter.bannerImageCount();
                if (count > 1) {
                    return count + 2;
                }
            }
            return count;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final int index = indexForPosition(position);
            ImageView imageView = cache.get(position);
            if (imageView == null) {
                imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                cache.put(position, imageView);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.bannerDidSelectAtIndex(index);
                    }
                });
            }
            Object parent = imageView.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(imageView);
            }
            container.addView(imageView);
            Glide.with(container.getContext())
                    .load(urlAtIndex(index))
                    .placeholder(R.drawable.banner_default)
                    .error(R.drawable.banner_default)
                    .dontAnimate()
                    .centerCrop()
                    .into(imageView);

            return imageView;
        }

        private int indexForPosition(int position) {
            int index = position;
            if (adapter.bannerImageCount() > 1) {
                if (position == 0) {
                    index = adapter.bannerImageCount() - 1;
                } else if (position == adapter.bannerImageCount() + 1) {
                    index = 0;
                } else {
                    index = position - 1;
                }
            }
            return index;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            cache.remove(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }
    }
}
