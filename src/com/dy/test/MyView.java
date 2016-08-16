package com.dy.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 作者 段誉 E-mail:dyshandy@yeah.net
 * @version 创建时间：2016-8-11 下午8:25:59 类说明
 */
public class MyView extends View {

	boolean touchOn;
	boolean mDownTouch = false;

	public MyView(Context context) {
		super(context);
		init();
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		touchOn = false;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (touchOn) {
			canvas.drawColor(Color.RED);
		} else {
			canvas.drawColor(Color.GRAY);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

//		int action = event.getAction();
//		if (action == MotionEvent.ACTION_DOWN) {
//			touchOn = !touchOn;
//			invalidate();
//			return true;
//		}
//
//		return false;

		// Listening for the down and up touch events
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			touchOn = !touchOn;
			invalidate();

			mDownTouch = true;
			return true;

		case MotionEvent.ACTION_UP:
			if (mDownTouch) {
				mDownTouch = false;
				performClick(); // Call this method to handle the response, and
				// thereby enable accessibility services to
				// perform this action for a user who cannot
				// click the touchscreen.
				return true;
			}
		}
		return false; // Return false for other touch events
	}

	@Override
	public boolean performClick() {
		// Calls the super implementation, which generates an AccessibilityEvent
		// and calls the onClick() listener on the view, if any
		super.performClick();

		// Handle the action for the custom click here

		return true;
	}

}