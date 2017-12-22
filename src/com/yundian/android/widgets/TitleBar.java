package com.yundian.android.widgets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.yundian.android.R;
import com.yundian.android.ui.BaseActivity;
import com.yundian.android.ui.SearchActivity;

/**
 * Created by liushenghan on 2017/12/20.
 */

public class TitleBar extends RelativeLayout {
    private EditText edit;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //
        edit = (EditText) findViewById(R.id.edit_seek);
        findViewById(R.id.button_seek).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof BaseActivity) {
                    if (TextUtils.isEmpty(edit.getText().toString().trim())) {
                        ((BaseActivity) getContext()).DisplayToast("请输入关键字");
                    } else {
                        SearchActivity.startAct(edit.getText().toString().trim(), (BaseActivity) getContext());
                    }
                }
            }
        });
    }
}
