package com.dy.testcamera;

import java.util.Arrays;
import java.util.Timer;
import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dy.test.MyApplication;

/**
 * @author 作者 段誉 E-mail:dyshandy@yeah.net
 * @version 创建时间：2016-8-3 下午5:25:23 类说明
 */
public class TestActivity extends Activity {

	private final static String TAG = "Camera2testJ";
	private Size mPreviewSize;

	private AutoFitTextureView mTextureView;
	private CameraDevice mCameraDevice;
	private CaptureRequest.Builder mPreviewBuilder;
	private CameraCaptureSession mPreviewSession;

	private MediaRecorder mMediaRecorder;
	private Timer mTimer;
	private final int MAX_TIME = 1500;
	private int mTimeCount;
	private long time;
	private boolean isRecording = false;
	private String fileName;
	private Handler mainHandler = new Handler(MyApplication.getContext()
			.getMainLooper());
	private Runnable sendVideo = new Runnable() {
		@Override
		public void run() {
			// recordStop();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copyofactivity_main);
		mTextureView = (AutoFitTextureView) findViewById(R.id.copytexture);
		mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
		ImageButton copyrecord = (ImageButton) findViewById(R.id.copyrecord);
		copyrecord.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (!isRecording) {
						// time = Calendar.getInstance().getTimeInMillis();
						// mMediaRecorder.start();
						// isRecording = true;
						// mTimer = new Timer();
						// mTimer.schedule(new TimerTask() {
						// @Override
						// public void run() {
						// mTimeCount++;
						// mainHandler.post(updateProgress);
						// if (mTimeCount == MAX_TIME) {
						// mainHandler.post(sendVideo);
						// }
						// }
						// }, 0, 10);
					}
					break;
				case MotionEvent.ACTION_UP:
					// recordStop();
					break;
				default:
					break;
				}
				return true;
			}
		});

	}

	/*-------------分割线－－－－－－－－－－－*/
	private void openCamera() {
		CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
		Log.e(TAG, "openCamera E");
		try {
			String cameraId = manager.getCameraIdList()[0];
			CameraCharacteristics characteristics = manager
					.getCameraCharacteristics(cameraId);
			StreamConfigurationMap map = characteristics
					.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
			mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];
			manager.openCamera(cameraId, mStateCallback, null);
		} catch (CameraAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e(TAG, "openCamera X");
	}

	private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {

		@Override
		public void onSurfaceTextureAvailable(SurfaceTexture surface,
				int width, int height) {
			// TODO Auto-generated method stub
			Log.e(TAG, "onSurfaceTextureAvailable, width=" + width + ",height="
					+ height);
			openCamera();
		}

		@Override
		public void onSurfaceTextureSizeChanged(SurfaceTexture surface,
				int width, int height) {
			// TODO Auto-generated method stub
			Log.e(TAG, "onSurfaceTextureSizeChanged");
		}

		@Override
		public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onSurfaceTextureUpdated(SurfaceTexture surface) {
			// TODO Auto-generated method stub
			Log.e(TAG, "onSurfaceTextureUpdated");
		}

	};

	private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

		@Override
		public void onOpened(CameraDevice camera) {
			// TODO Auto-generated method stub
			Log.e(TAG, "onOpened");
			mCameraDevice = camera;
			startPreview();
		}

		@Override
		public void onDisconnected(CameraDevice camera) {
			// TODO Auto-generated method stub
			Log.e(TAG, "onDisconnected");
			camera.close();
			mCameraDevice = null;
		}

		@Override
		public void onError(CameraDevice camera, int error) {
			// TODO Auto-generated method stub
			Log.e(TAG, "onError");
			camera.close();
			mCameraDevice = null;
		}
	};

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.e(TAG, "onPause");
		super.onPause();
		if (null != mCameraDevice) {
			mCameraDevice.close();
			mCameraDevice = null;
		}
	}

	protected void startPreview() {
		// TODO Auto-generated method stub
		if (null == mCameraDevice || !mTextureView.isAvailable()
				|| null == mPreviewSize) {
			Log.e(TAG, "startPreview fail, return");
		}

		SurfaceTexture texture = mTextureView.getSurfaceTexture();
		if (null == texture) {
			Log.e(TAG, "texture is null, return");
			return;
		}

		texture.setDefaultBufferSize(mPreviewSize.getWidth(),
				mPreviewSize.getHeight());
		Surface surface = new Surface(texture);

		try {
			mPreviewBuilder = mCameraDevice
					.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
		} catch (CameraAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPreviewBuilder.addTarget(surface);

		try {
			mCameraDevice.createCaptureSession(Arrays.asList(surface),
					new CameraCaptureSession.StateCallback() {
						@Override
						public void onConfigured(CameraCaptureSession session) {
							// TODO Auto-generated method stub
							mPreviewSession = session;
							updatePreview();
						}

						@Override
						public void onConfigureFailed(
								CameraCaptureSession session) {
							// TODO Auto-generated method stub
							Toast.makeText(TestActivity.this,
									"onConfigureFailed", Toast.LENGTH_LONG)
									.show();
						}
					}, null);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}

	protected void updatePreview() {
		// TODO Auto-generated method stub
		if (null == mCameraDevice) {
			Log.e(TAG, "updatePreview error, return");
		}
		mPreviewBuilder.set(CaptureRequest.CONTROL_MODE,
				CameraMetadata.CONTROL_MODE_AUTO);
		HandlerThread thread = new HandlerThread("CameraPreview");
		thread.start();
		Handler backgroundHandler = new Handler(thread.getLooper());

		try {
			mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null,
					backgroundHandler);
		} catch (CameraAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}