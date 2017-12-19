package com.yundian.android.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;

import com.yundian.android.BaseApplication;

import java.lang.reflect.Method;

@SuppressLint("CommitPrefEdits")
public class SettingUtils {
    /**
     * 用户token
     */
    public static final String TOKEN = "user_token";

    private SettingUtils() {
    }

    public static boolean contains(int resId) {
        return contains(BaseApplication.getApp().getString(resId));
    }

    public static boolean contains(String key) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        return prefs.contains(key);
    }

    public static void remove(int resId) {
        remove(BaseApplication.getApp().getString(resId));
    }

    public static void remove(String key) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        Editor editor = prefs.edit();
        editor.remove(key);
        commitOrApply(editor);
    }

    public static void set(int resId, boolean value) {
        set(BaseApplication.getApp().getString(resId), value);
    }

    public static void set(String key, boolean value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        commitOrApply(editor);
    }

    public static void set(int resId, float value) {
        set(BaseApplication.getApp().getString(resId), value);
    }

    public static void set(String key, float value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        Editor editor = prefs.edit();
        editor.putFloat(key, value);
        commitOrApply(editor);
    }

    public static void set(int resId, int value) {
        set(BaseApplication.getApp().getString(resId), value);
    }

    public static void set(String key, int value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        Editor editor = prefs.edit();
        editor.putInt(key, value);
        commitOrApply(editor);
    }

    public static void set(int resId, long value) {
        set(BaseApplication.getApp().getString(resId), value);
    }

    public static void set(String key, long value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        Editor editor = prefs.edit();
        editor.putLong(key, value);
        commitOrApply(editor);
    }

    public static void set(int resId, String value) {
        set(BaseApplication.getApp().getString(resId), value);
    }

    public static void set(String key, String value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        Editor editor = prefs.edit();
        editor.putString(key, value);
        commitOrApply(editor);
    }

    public static boolean get(int resId, boolean defValue) {
        return get(BaseApplication.getApp().getString(resId), defValue);
    }

    public static boolean get(String key, boolean defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        return prefs.getBoolean(key, defValue);
    }

    public static float get(int resId, float defValue) {
        return get(BaseApplication.getApp().getString(resId), defValue);
    }

    public static float get(String key, float defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        return prefs.getFloat(key, defValue);
    }

    public static int get(int resId, int defValue) {
        return get(BaseApplication.getApp().getString(resId), defValue);
    }

    public static int get(String key, int defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        return prefs.getInt(key, defValue);
    }

    public static long get(int resId, long defValue) {
        return get(BaseApplication.getApp().getString(resId), defValue);
    }

    public static long get(String key, long defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        return prefs.getLong(key, defValue);
    }

    public static String get(int resId, String defValue) {
        return get(BaseApplication.getApp().getString(resId), defValue);
    }

    public static String get(String key, String defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(BaseApplication.getApp());
        return prefs.getString(key, defValue);
    }

    public static Editor getEditor() {
        return PreferenceManager.getDefaultSharedPreferences(BaseApplication.getApp()).edit();
    }

    // ////////////////////////////////////////////////////////////////////////
    // Apply method via reflection
    private static final Method APPLY_METHOD = findApplyMethod();

    /**
     * 这种方式判断当前是否有apply()方法，效率会低
     */
    private static Method findApplyMethod() {
        try {
            Class<Editor> cls = Editor.class;
            return cls.getMethod("apply");
        } catch (NoSuchMethodException unused) {
            LogUtils.i(
                    "",
                    "Failed to retrieve Editor.apply(); probably doesn't exist on this phone's OS.  Using Editor.commit() instead.");
            return null;
        }
    }

    /**
     * 应用/提交 修改 <br>
     * <dd>apply() Added in API level 9 <br><dd><dd>apply()方法是API
     * 9增加的，commit()执行的时候是开启一个异步去修改配置文件，而apply()方法是先修改内存中的配置然后再开启一个异步去修改配置文件
     *
     * @param editor
     */
    @TargetApi(9)
    private static void commitOrApply(Editor editor) {
        if (Build.VERSION.SDK_INT > 8) {
            editor.apply();
        } else {
            editor.commit();
        }
        /*
		 * if (APPLY_METHOD != null) { try { APPLY_METHOD.invoke(editor);
		 * return; } catch (InvocationTargetException e) { L.d("",
		 * "Failed while using Editor.apply().  Using Editor.commit() instead.",
		 * e); } catch (IllegalAccessException e) { L.d("",
		 * "Failed while using Editor.apply().  Using Editor.commit() instead.",
		 * e); } } editor.commit();
		 */
    }
}
