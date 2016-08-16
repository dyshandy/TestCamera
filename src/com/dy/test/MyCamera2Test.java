package com.dy.test;


import com.dy.testcamera.R;
import com.dy.testcamera.R.id;
import com.dy.testcamera.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * @author 作者 段誉 E-mail:dyshandy@yeah.net
 * @version 创建时间：2016-8-4 下午3:52:55 类说明
 */
public class MyCamera2Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FrameLayout	content=(FrameLayout)findViewById(R.id.content);
		
//		FragmentActivity fragmentActivity = (FragmentActivity) getContext();
//			// VideoInputDialog.show(fragmentActivity.getSupportFragmentManager());
//			VideoInputDialog.show(fragmentActivity.getSupportFragmentManager());
	}

}
