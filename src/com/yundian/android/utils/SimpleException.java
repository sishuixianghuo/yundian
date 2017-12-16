package com.yundian.android.utils;

import com.google.gson.internal.$Gson$Types;
import com.yundian.android.bean.BaseBean;

import java.lang.reflect.Type;

public class SimpleException extends Exception {
	/**
	 * 简单请求异常处理
	 */
	private static final long serialVersionUID = 1L;

	public SimpleException() {
		super();
	}

	public SimpleException(String msg) {
		super(msg);
	}

	public static SimpleException newInstance(String msg, Throwable cause) {
		StackTraceElement element = cause.getStackTrace()[1];
		String className = element.getClassName();
		className = className.substring(className.lastIndexOf(".") + 1);
		String methodName = element.getMethodName();
		msg = "--" + className + "----" + methodName + "----:" + msg;
		LogUtils.i("exception", msg);
		return new SimpleException(msg, cause);
	}

	public static SimpleException newInstance(String msg) {
		Exception exception = new Exception();
		return SimpleException.newInstance(msg, exception);
	}

	private SimpleException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SimpleException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		String message = super.getMessage();
		String[] split = message.split(":");
		if (split.length >= 2) {
			message = split[1];
		}
		return message;

	}

	public <T extends BaseBean> T convertToBean(Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
			t.setmException(this);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

	public BaseBean convertToBean(Type type) {
		BaseBean t = null;
		LogUtils.i("exception", type.toString());

		// String sdf = null;
		// switch (sdf){
		// case "sdf":
		// break;
		// }

		try {
			Class<?> clazz = $Gson$Types.getRawType(type);
			t = (BaseBean) clazz.newInstance();
			t.setmException(this);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

}
