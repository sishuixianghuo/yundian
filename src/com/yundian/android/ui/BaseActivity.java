package com.yundian.android.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.yundian.android.AppManager;
import com.yundian.android.BaseApplication;
import com.yundian.android.R;
import com.yundian.android.bean.ProductInfo;
import com.yundian.android.net.HttpServer;
import com.yundian.android.task.AsyncCallable;
import com.yundian.android.task.Callback;
import com.yundian.android.task.EMobileTask;
import com.yundian.android.task.ProgressCallable;
import com.yundian.android.utils.CommonTools;

import java.util.Locale;
import java.util.concurrent.Callable;

public abstract class BaseActivity extends AppCompatActivity {

    public String TAG = getClass().getSimpleName();
    protected String httpTag = getClass().getName();
    protected InputMethodManager imm;
    private TelephonyManager tManager;

    public Dialog mWeiboDialog;
    protected int indexPage = 1;

    public void loadImage(String url, ImageView view) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.app_ic)
                .error(R.drawable.app_ic)
                .dontAnimate()
                .centerCrop()
                .into(view);
    }

    public void loadImageNoHost(String url, ImageView view) {
        Glide.with(this)
                .load(String.format("%s%s", HttpServer.HOST_IMG, url))
                .placeholder(R.drawable.app_ic)
                .error(R.drawable.app_ic)
                .dontAnimate()
                .centerCrop()
                .into(view);
    }

    public void loadImageNoHostWithError(String url, ImageView view) {
        Glide.with(this)
                .load(String.format("%s%s", HttpServer.HOST_IMG, url))
                .placeholder(R.drawable.store_icon)
                .error(R.drawable.store_icon)
                .dontAnimate()
                .centerCrop()
                .into(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        getWindowManager().getDefaultDisplay().getMetrics(BaseApplication.getApp().getMetrics());
        AppManager.getInstance().addActivity(this);
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(httpTag);
    }

    /**
     * 绑定控件id
     */
    protected abstract void findViewById();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action启动Activity
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    protected void DisPlay(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载进度条
     */
    public void showProgressDialog() {
        ProgressDialog progressDialog = null;

        if (progressDialog != null) {
            progressDialog.cancel();
        }
        progressDialog = new ProgressDialog(this);
        Drawable drawable = getResources().getDrawable(R.drawable.loading_animation);
        progressDialog.setIndeterminateDrawable(drawable);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("请稍候，正在努力加载。。");
        progressDialog.show();
    }


    public void DisplayToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void DisplayToast(@StringRes int id) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }

    protected void hideOrShowSoftInput(boolean isShowSoft, EditText editText) {
        if (isShowSoft) {
            imm.showSoftInput(editText, 0);
        } else {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    //获得当前程序版本信息
    protected String getVersionName() throws Exception {
        finish();
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
    }


    //獲得設備信息
    protected String getDeviceId() throws Exception {
        String deviceId = tManager.getDeviceId();
        return deviceId;
    }

    /**
     * 获取SIM卡序列号
     *
     * @return
     */
    protected String getToken() {
        return tManager.getSimSerialNumber();
    }

	/*獲得系統版本*/

    protected String getClientOs() {
        return android.os.Build.ID;
    }

    /*獲得系統版本號*/
    protected String getClientOsVer() {
        return android.os.Build.VERSION.RELEASE;
    }

    //獲得系統語言包
    protected String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    protected String getCountry() {
        return Locale.getDefault().getCountry();
    }

    /**
     * @param <T>       模板参数，操作时要返回的内容
     * @param pCallable 需要异步调用的操作
     * @param pCallback 回调
     */
    protected <T> void doAsync(final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback, final boolean showDialog, String message) {
        EMobileTask.doAsync(this, null, message, pCallable, pCallback, pExceptionCallback, false, showDialog);
    }

    protected <T> void doAsync(final CharSequence pTitle, final CharSequence pMessage, final Callable<T> pCallable, final Callback<T> pCallback, final boolean showDialog) {
        EMobileTask.doAsync(this, pTitle, pMessage, pCallable, pCallback, null, false, showDialog);
    }

    /**
     * Performs a task in the background, showing a {@link ProgressDialog},
     * while the {@link Callable} is being processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pMessageResID
     * @param pCallable
     * @param pCallback
     */
    protected <T> void doAsync(final int pTitleResID, final int pMessageResID, final Callable<T> pCallable, final Callback<T> pCallback) {
        this.doAsync(pTitleResID, pMessageResID, pCallable, pCallback, null);
    }

    /**
     * Performs a task in the background, showing a indeterminate
     * {@link ProgressDialog}, while the {@link Callable} is being processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pMessageResID
     * @param pCallable
     * @param pCallback
     * @param pExceptionCallback
     */
    protected <T> void doAsync(final int pTitleResID, final int pMessageResID, final Callable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
        com.yundian.android.task.EMobileTask.doAsync(this, pTitleResID, pMessageResID, pCallable, pCallback, pExceptionCallback);
    }

    /**
     * Performs a task in the background, showing a {@link ProgressDialog} with
     * an ProgressBar, while the {@link AsyncCallable} is being processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pCallback
     */
    protected <T> void doProgressAsync(final int pTitleResID, final ProgressCallable<T> pCallable, final Callback<T> pCallback) {
        this.doProgressAsync(pTitleResID, pCallable, pCallback, null);
    }

    /**
     * Performs a task in the background, showing a {@link ProgressDialog} with
     * a ProgressBar, while the {@link AsyncCallable} is being processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pCallback
     * @param pExceptionCallback
     */
    protected <T> void doProgressAsync(final int pTitleResID, final ProgressCallable<T> pCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
        EMobileTask.doProgressAsync(this, pTitleResID, pCallable, pCallback, pExceptionCallback);
    }

    /**
     * Performs a task in the background, showing an indeterminate
     * {@link ProgressDialog}, while the {@link AsyncCallable} is being
     * processed.
     *
     * @param <T>
     * @param pTitleResID
     * @param pMessageResID
     * @param pAsyncCallable
     * @param pCallback
     * @param pExceptionCallback
     */
    protected <T> void doAsync(final int pTitleResID, final int pMessageResID, final AsyncCallable<T> pAsyncCallable, final Callback<T> pCallback, final Callback<Exception> pExceptionCallback) {
        EMobileTask.doAsync(this, pTitleResID, pMessageResID, pAsyncCallable, pCallback, pExceptionCallback);
    }

    /**
     * 添加商品到购物袋
     *
     * @param pdt
     */
    public void addWithDelPdt2Bag(ProductInfo pdt, boolean isAdd) {
        if (pdt.getG_mPrice() < CommonTools.THRESHOLD_PRICE) {
            DisplayToast(R.string.contact_store);
            return;
        }
        if (BaseApplication.getApp().getShoppingBag().contains(pdt)) {
            for (ProductInfo info : BaseApplication.getApp().getShoppingBag()) {
                if (info.equals(pdt)) {
                    if (isAdd) {
                        info.amount += pdt.amount;
                    } else {
                        info.amount--;
                    }
                    if (info.amount <= 1) {
                        info.amount = 1;
                    }
                    return;
                }
            }
        }
        BaseApplication.getApp().getShoppingBag().add(pdt);
    }
}
