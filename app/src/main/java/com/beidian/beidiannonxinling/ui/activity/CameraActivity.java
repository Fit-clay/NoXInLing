package com.beidian.beidiannonxinling.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.widget.DowntiltSurfaceView;
import com.beidian.beidiannonxinling.ui.widget.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 拍照界面
 *
 * @author liuzhiguo cameraView
 *
 */
@SuppressWarnings("deprecation")
public class CameraActivity extends Activity implements SensorEventListener, View.OnClickListener {
	private static final int           STATE_PHOTO_CAN_TAKE     = 0;
	private static final int           STATE_PHOTO_CAN_NOT_TAKE = 1;
	// 拍照按钮
	private              Button        mBtCapture               = null;
	// 相机引用
	private              Camera        camera                   = null;
	// 预览界面
	private              DowntiltSurfaceView mySurfaceView            = null;
	// 方向角
	private TextView              orientation;
	// 方向角传感器管理器
	private SensorManager         sensorManagerOri;
	private SensorManager         sensorManagerCiganrao;// 磁干扰传感器管理
	private MySensorEventListener mySensorEventListenerORI;
	private Intent                getIntent;
	private String                myCameraPhotoPath;
	private float                 saveAzumith;
	/**
	 * 当前照片位置
	 */
	private int                   myCameraPhotoPos;
	/**
	 * 当前方向角
	 */
	public  float                 nowAzumith;
	/**
	 * 磁干扰指数
	 */
	private double                magneticNum;
	private TextView tvEmiRead = null;
	boolean isPause =false; //是否暂停记录数据
	private String mWhoCallMe;
	private View mBt_record_mechanical;
	private View mBt_cancel;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_record_mechanical:// 记录数据
				if (mWhoCallMe!=null&&mWhoCallMe.equals(Const.IntentTransfer.MAIN_ACTIVITY_TOOLS)) {
					isPause = !isPause;
					((TextView) v).setText(!isPause?"锁定":"解锁");
					return;
				}
				Intent data = new Intent(CameraActivity.this,BaseInfoCollectionActivity.class);
				data.putExtra(Const.IntentTransfer.resultCode_CameraActivity, orientation.getText().toString().replace("°",""));
				setResult(BaseInfoCollectionActivity.Mechanical_RESULT_OK, data);
				finish();
				break;
			case R.id.bt_cancel:// 取消
				finish();
				break;
			default:
				break;
		}

	}
	private final class MySensorEventListener implements SensorEventListener {

		@Override
		// 可以得到传感器实时测量出来的变化值
		public void onSensorChanged(SensorEvent event) {
//			if (photoType.equals("antenna")) {
//				orientation.setVisibility(View.GONE);
//				tvRemind.setVisibility(View.GONE);
//				return;
//			}
			switch (myCameraPhotoPos) {
			// 0
			case 1:
				setPhotoTakeState(STATE_PHOTO_CAN_TAKE);
				break;
//			// 45
//			case 2:
//				checkDrgreeIfTakePic(Globle.direction45);
//				break;
//			// 90
//			case 3:
//				checkDrgreeIfTakePic(Globle.direction90);
//				break;
//			// 135
//			case 4:
//				checkDrgreeIfTakePic(Globle.direction135);
//				break;
//			// 180
//			case 5:
//				checkDrgreeIfTakePic(Globle.direction180);
//				break;
//			// 225
//			case 6:
//				checkDrgreeIfTakePic(Globle.direction225);
//				break;
//			// 270
//			case 7:
//				checkDrgreeIfTakePic(Globle.direction270);
//				break;
			// 315
			case 8:
//				checkDrgreeIfTakePic(Globle.direction315);
				break;
			default:
//				orientation.setVisibility(View.GONE);
//				tvRemind.setVisibility(View.GONE);
				break;
			}
			// 方向传感器
			if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
				// x表示手机指向的方位，0表示北,90表示东，180表示南，270表示西
				float x = event.values[SensorManager.DATA_X];
				// float y = event.values[SensorManager.DATA_Y];
				// float z = event.values[SensorManager.DATA_Z];
				// tv_orientation是界面上的一个TextView标签，不再赘述
				nowAzumith = x;
				// tv_orientation.setText("Orientation:"+x+","+y+","+z);

				if (orientation != null && !CapturePic&&!isPause) {
					orientation.setText(Tools.formatDouble(x) + "°");
				}
			}

		}

		/**
		 * 检查角度是否可以拍摄
		 *
		 * @param standardDir
		 */
		private void checkDrgreeIfTakePic(float standardDir) {
			if (nowAzumith > standardDir - Const.ABOUT_DEGREE
					&& nowAzumith < standardDir + Const.ABOUT_DEGREE) {
				setPhotoTakeState(STATE_PHOTO_CAN_TAKE);
			} else {
				setPhotoTakeState(STATE_PHOTO_CAN_NOT_TAKE);
			}
		}

		private void setPhotoTakeState(int state) {
			switch (state) {
			case STATE_PHOTO_CAN_TAKE:
				mBtCapture
						.setBackgroundResource(R.drawable.shutter_available);
				orientation.setTextColor(Color.parseColor("#174b07"));
				mBtCapture.setEnabled(true);
				break;
			case STATE_PHOTO_CAN_NOT_TAKE:
				mBtCapture
						.setBackgroundResource(R.drawable.shutter_disabled);
				orientation.setTextColor(Color.parseColor("#861806"));
				mBtCapture.setEnabled(false);
				break;
			default:
				break;
			}

		}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	}

	private   PictureCallback pictureCallback = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			if (null != data && data.length > 0) {
				File tempFile = new File(myCameraPhotoPath);
				if(tempFile.exists()){
					tempFile.delete();
				}
				try {
					FileOutputStream fos = new FileOutputStream(tempFile);
					fos.write(data);
					fos.flush();
					fos.close();
//					if (myCameraPhotoPos == 1) {
//						Globle.testZeroAzumith = saveAzumith;
//						Globle.surveyBasic
//								.setPhoto0Angel(Globle.testZeroAzumith);
//						setAllPhotoAngel();
//					}
//					CameraResultActivity.start(CameraActivity.this,
//							myCameraPhotoPath, myCameraPhotoPos);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	protected boolean         CapturePic      = false;
	private TextView    tvRemind;
	/**
	 * 照片类型，天馈or基础
	 */
	private String      photoType;
	private FrameLayout preview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycamera_layout);
		//设置屏幕为竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getIntent = getIntent();
		myCameraPhotoPath = getIntent.getStringExtra("myCameraPhotoPath");
		myCameraPhotoPos = getIntent.getIntExtra("myCameraPhotoPos", 0);
		photoType = getIntent.getStringExtra("photoType");
		mWhoCallMe = getIntent.getStringExtra(Const.IntentTransfer.MAIN_ACTIVITY_TOOLS);

		orientation = (TextView) findViewById(R.id.tv_oritation_dirscrtion);
		tvRemind = (TextView) findViewById(R.id.tv_photo_remind);
		tvEmiRead = (TextView) findViewById(R.id.tv_ciganrao);

		sensorManagerOri = (SensorManager) getSystemService(
				Context.SENSOR_SERVICE);
		sensorManagerCiganrao = (SensorManager) getSystemService(
				Context.SENSOR_SERVICE);
		mySensorEventListenerORI = new MySensorEventListener();
		mBt_record_mechanical = findViewById(R.id.bt_record_mechanical);
		mBt_record_mechanical.setOnClickListener(this);
		mBt_cancel = findViewById(R.id.bt_cancel);
		mBt_cancel.setOnClickListener(this);
		if (mWhoCallMe!=null&&mWhoCallMe.equals(Const.IntentTransfer.MAIN_ACTIVITY_TOOLS)) {
			((TextView) mBt_record_mechanical).setText(!isPause?"锁定":"解锁");
		}

		// add by liuren
		// if (camera == null) {
		// camera = getCameraInstance();
		//
		// }
		// mySurfaceView = new MySurfaceView(getApplicationContext(), camera);
		preview = (FrameLayout) findViewById(R.id.camera_preview);
		// preview.addView(mySurfaceView);
		// camera.setDisplayOrientation(90);
		initCameraAndPreview();
	}

	@Override
	protected void onStop() {
		if (null != mySurfaceView) {// 移除预览
			preview.removeView(mySurfaceView);
			mySurfaceView = null;
		}
		if (null != camera) {
			camera.release();
		}
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		CapturePic = false;
//		mBtCapture.setVisibility(View.VISIBLE);
		initCameraAndPreview();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Sensor sensor_orientation = sensorManagerOri
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);

		sensorManagerOri.registerListener(mySensorEventListenerORI,
				sensor_orientation, SensorManager.SENSOR_DELAY_UI);
		Sensor sensor_ciganrao = sensorManagerCiganrao
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManagerCiganrao.registerListener(this, sensor_ciganrao,
				SensorManager.SENSOR_DELAY_UI);
	}

	/**
	 * 初始化相机和相机预览
	 */
	private void initCameraAndPreview() {
		camera = getCameraInstance();
		if (null != camera) {
			camera.setDisplayOrientation(90);
			mySurfaceView = new DowntiltSurfaceView(getApplicationContext(), camera);
			preview.addView(mySurfaceView);
		} else {
			// 提示无法链接相机
			showDialog();
		}
	}

	/* 得到一相机对象 */
	private Camera getCameraInstance() {
		Camera camera = null;
		try {
			camera = Camera.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return camera;
	}

	/**
	 * 显示无法连接到相机对话框
	 */
	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CameraActivity.this);
		builder.setTitle(R.string.connect_to_camer_error)
				.setMessage(R.string.failed_to_connect_camera)
				.setPositiveButton(R.string.try_again,
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								initCameraAndPreview();
							}
						})
				.setNegativeButton(R.string.exit,
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								CameraActivity.this.finish();
							}
						})
				.create().show();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			float xM = event.values[0];
			float yM = event.values[1];
			float zM = event.values[2];
			magneticNum = Math.sqrt(xM * xM + yM * yM + zM * zM);
			tvEmiRead.setText(Tools.formatText(getString(R.string.emi_read),
					Tools.formatDoubleNoDig(magneticNum)));
		}
	}

	public void takePhoto() {
		try {
			camera.takePicture(null, null, pictureCallback);
			CapturePic = true;
			saveAzumith = nowAzumith;
		} catch (Exception e) {
			Tools.showToast(CameraActivity.this,
					getString(R.string.connect_to_camer_error));
		}
		// camera.autoFocus(new AutoFocusCallback() {
		//
		// @Override
		// public void onAutoFocus(boolean success, Camera camera) {
		// if (success) {
		// }
		// }
		// });
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
				|| keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			takePhoto();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 释放相机
		if (camera != null) {
			camera.release();
			camera = null;
		}
		//
		if (mySurfaceView != null) {
			mySurfaceView = null;
		}
		// 取消注册方向传感器
		if (sensorManagerOri != null) {
			sensorManagerOri.unregisterListener(mySensorEventListenerORI);
			sensorManagerOri = null;
		}
		// 取消注册磁干扰传感器
		if (sensorManagerCiganrao != null) {
			sensorManagerCiganrao.unregisterListener(this);
			sensorManagerCiganrao = null;
		}
		myCameraPhotoPath = null;
		mySensorEventListenerORI = null;
	}
}
