package com.dy.test;

import android.app.Application;
import android.content.Context;

/**
 * @author 作者 段誉 E-mail:dyshandy@yeah.net	
 * @version 创建时间：2016-8-4 下午3:55:41
 * 类说明 
 */
/**
 * 全局Application
 */
public class MyApplication extends Application {

	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();

	}

	public static Context getContext() {
		return context;
	}

}