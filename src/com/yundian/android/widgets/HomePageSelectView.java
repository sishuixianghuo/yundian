package com.yundian.android.widgets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yundian.android.R;

/**
 * Created by liushenghan on 2017/12/18.
 */

public class HomePageSelectView extends RelativeLayout {


    private TextView select;

    public HomePageSelectView(Context context) {
        super(context);
    }

    public HomePageSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomePageSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HomePageSelectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.home_page_select, this);
        select = (TextView) findViewById(R.id.select);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setSelected(selected);
        }
    }


    public TextView getSelect() {
        return select;
    }

}
