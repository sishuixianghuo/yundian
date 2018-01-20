package com.yundian.android.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yundian.android.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonTools {


    public static final float THRESHOLD_PRICE = 2.0f;

    /**
     * 短暂显示Toast消息
     *
     * @param context
     * @param message
     */
    public static void showShortToast(Context context, String message) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast, null);
        TextView text = (TextView) view.findViewById(R.id.toast_message);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 300);
        toast.setView(view);
        toast.show();
    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }

    /**
     * 获取手机状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDate(long time) {
        Date currentTime = new Date(time);
        String dateString = format.format(currentTime);
        return dateString;
    }

    public static String getDate(long time, String fmt) {
        Date currentTime = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        String dateString = format.format(currentTime);
        return dateString;
    }

    public static long getLong(String str) {
        long num = 0;
        int value = 1;
        boolean isInit = false;
        if (TextUtils.isEmpty(str)) {
            return num;
        }

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '-' || chars[i] == '+' && i + 1 < chars.length && chars[i + 1] >= '0' && chars[i + 1] <= '9') {
                if (chars[i] == '-') {
                    value = -1;
                    isInit = true;
                }
            }
            if (chars[i] >= '0' && chars[i] <= '9') {
                num *= 10;
                num += chars[i] - '0';
                isInit = true;
            } else {
                if (isInit) {
                    break;
                }
            }
        }
        return num * value;
    }

    /**
     * 验证手机号码
     * <p>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9])||(17[0-9]))\\d{8}$";
        return check(cellphone, regex);
    }

    /**
     * 验证固话号码
     *
     * @param telephone
     * @return
     */
    public static boolean checkTelephone(String telephone) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        return check(telephone, regex);
    }

    public static boolean checkEmail(String telephone) {
        String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return check(telephone, regex);
    }

    public static boolean checkNickName(String telephone) {
        String regex = "[a-zA-Z0-9\u4e00-\u9fa5\\s]{2,10}";
        return check(telephone, regex);

    }


    public static boolean checkShouhuoren(String name) {
        String reg ="^[\u0391-\uFFE5a-zA-Z·.&\\s]{0,}+$";
        //^([a-zA-Z0-9\u4e00-\u9fa5\·]{1,20})$
//        String regex = "^([a-zA-Z0-9\\u4e00-\\u9fa5\\·]{1,20})$";

        return !check(name,reg);
//        return name.matches(reg2);

    }


    public static boolean check(String num, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(num);
        return matcher.find();
    }


}
