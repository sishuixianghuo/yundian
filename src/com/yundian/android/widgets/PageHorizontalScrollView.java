package com.yundian.android.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.android.R;

/**
 * Created by liushenghan on 2017/11/8.`
 */

public class PageHorizontalScrollView extends HorizontalScrollView {

    private final Paint dividerPaint;
    private String TAG = PageHorizontalScrollView.class.getSimpleName();
    private final LinearLayout tabsContainer;
    private final LinearLayout.LayoutParams expandedTabLayoutParams;
    private ViewPager pager;
    private int tabTextSize = 12;
    private float strokeWidth = 20;
    private int currentPosition;
    private int preCurrentPosition;
    private float currentPositionOffset;
    private int tabPadding = 24;

    public PageHorizontalScrollView(Context context) {
        this(context, null);
    }

    public PageHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);


        dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dividerPaint.setColor(getResources().getColor(R.color.e42626));
        dividerPaint.setStrokeWidth(strokeWidth);

        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

    }


    public void setViewPager(final ViewPager pager) {
        this.pager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
                currentPositionOffset = positionOffset;
                invalidate();
            }

            @Override
            public void onPageSelected(int position) {
                tabsContainer.getChildAt(preCurrentPosition).setSelected(false);
                tabsContainer.getChildAt(position).setSelected(true);
                preCurrentPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        tabsContainer.removeAllViews();

        int tabCount = pager.getAdapter().getCount();

        for (int i = 0; i < tabCount; i++) {

            TextView tab = new TextView(getContext());
            tab.setFocusable(true);
            tab.setTag(i);
            tab.setGravity(Gravity.CENTER);
            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
            tab.setTypeface(null, Typeface.BOLD);
            tab.setPadding(tabPadding, 0, tabPadding, 0);
            tab.setText(pager.getAdapter().getPageTitle(i));
            tab.setTextColor(new ColorStateList(new int[][]{
                    {android.R.attr.state_selected},
                    {android.R.attr.state_pressed},
                    {}
            }, new int[]{
                    getContext().getResources().getColor(R.color.black),
                    getContext().getResources().getColor(R.color.black),
                    getContext().getResources().getColor(R.color.c9c9c9c)
            }));
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem((int) v.getTag(), true);
                }
            });
            if (i == 0) tab.setSelected(true);
            tabsContainer.addView(tab, expandedTabLayoutParams);
        }


        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                currentPosition = PageHorizontalScrollView.this.pager.getCurrentItem();
            }
        });
        invalidate();
    }


    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    private void updateTabStyles() {
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tab = tabsContainer.getChildAt(i);
            tab.setPadding(tabPadding, 0, tabPadding, 0);
            if (tab instanceof TextView) {
                ((TextView) tab).setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
            }
        }
    }

    float lineW = 200;
    float startx = -1;
    float tmpy;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 啥也不干绘制进度条
        if (tabsContainer.getChildCount() == 0) return;
        if (startx == -1) {
            strokeWidth = getResources().getDimensionPixelOffset(R.dimen.default_margin_min);
            TextView tv = (TextView) tabsContainer.getChildAt(0);
            lineW = tv.getPaint().measureText(tv.getText().toString()) + tabPadding * 2;
            startx = (getWidth() / tabsContainer.getChildCount() - lineW) / 2;
            tmpy = getHeight() - strokeWidth;
            dividerPaint.setStrokeWidth(strokeWidth);
        }
        // default: line below current tab
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();

        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabsContainer.getChildCount() - 1) {

            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();
            lineLeft = currentPositionOffset * nextTabLeft + lineLeft - currentPositionOffset * lineLeft;
            lineRight = currentPositionOffset * nextTabRight + lineRight - currentPositionOffset * lineRight;
        }
        canvas.drawLine(lineLeft + startx, tmpy, lineRight - startx, tmpy, dividerPaint);
    }


    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }
}
