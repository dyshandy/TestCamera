package com.dy.testcamera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * @author 作者 段誉 E-mail:dyshandy@yeah.net
 * @version 创建时间：2016-8-2 下午2:12:21 摄像头预览界面控件
 */
public class AutoFitTextureView extends TextureView {
	private int mRatioWidth = 0;
	private int mRatioHeight = 0;

	public AutoFitTextureView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setAspectRatio(int width, int height) {
		mRatioWidth = width;
		mRatioHeight = height;
		requestLayout();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		if (0 == mRatioWidth || 0 == mRatioHeight) {
			setMeasuredDimension(width, height);
		} else {
			if (width < height * mRatioWidth / mRatioHeight) {
				setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
			} else {
				setMeasuredDimension(height * mRatioWidth / mRatioHeight,
						height);
			}
		}
	}
}