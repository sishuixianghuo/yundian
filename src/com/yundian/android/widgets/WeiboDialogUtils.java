package com.yundian.android.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.android.R;

public class WeiboDialogUtils {

    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.dialog_loading_view);// 加载布局
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        loadingDialog.show();

        return loadingDialog;
    }

    /**
     * 关闭dialog
     * <p>
     * http://blog.csdn.net/qq_21376985
     *
     * @param mDialogUtils
     */
    public static void closeDialog(Dialog mDialogUtils) {
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
            mDialogUtils.dismiss();
        }
    }


    /**
     * 拨打电话
     *
     * @param activity
     * @param msg
     */
    public static void CallPhone(final Activity activity, final String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomDialog);

        SpannableString title = new SpannableString("服务热线");
        title.setSpan(new ForegroundColorSpan(Color.parseColor("#3a3a3a")), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new AbsoluteSizeSpan(20, true), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString message = new SpannableString(msg);
        message.setSpan(new ForegroundColorSpan(Color.parseColor("#3a3a3a")), 0, msg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        message.setSpan(new AbsoluteSizeSpan(20, true), 0, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String confirm = "确定";

        String cancle = "取消";

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + msg);
                        intent.setData(data);
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton(cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.show();
        Button con = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        con.setTextColor(Color.parseColor("#e60012"));
        con.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        Button can = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        can.setTextColor(Color.parseColor("#3a3a3a"));
        can.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    }
}