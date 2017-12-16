package com.yundian.android.bean;


import com.yundian.android.utils.SimpleException;

/**
 * 所有java bean 的父类
 * @author niuql
 */
public class BaseBean {
	public static final String TAG = "BaseBean.java";
	protected SimpleException mException;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public SimpleException getmException() {
		return mException;
	}

	public void setmException(SimpleException mException) {
		this.mException = mException;
	}
}
