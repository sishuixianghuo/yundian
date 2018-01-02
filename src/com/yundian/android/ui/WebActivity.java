package com.yundian.android.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.yundian.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liushenghan on 2018/1/2.
 */

public class WebActivity extends BaseActivity {


    private WebSettings settings;

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

    @BindView(R.id.web_view)
    WebView web_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.web_view);
        ButterKnife.bind(this);
        title.setVisibility(View.GONE);
        more.setVisibility(View.GONE);
        sub_title.setText("联系我们");
        settings = web_view.getSettings();
//        settings.setSavePassword(false);
//        settings.setAllowFileAccess(false);
//        settings.setAllowFileAccessFromFileURLs(false);
//        settings.setJavaScriptEnabled(false);
        web_view.loadUrl("https://www.baidu.com");
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

    }


}
