package com.beidian.beidiannonxinling.ui.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.widget.CircularSeekBar;
import com.beidian.beidiannonxinling.ui.widget.CommonDialog;
import com.beidian.beidiannonxinling.ui.widget.DowntiltSurfaceView;
import com.beidian.beidiannonxinling.ui.widget.Tools;

/**
 * 机械下倾角测量页面
 * 
 * @author fxx
 *
 */
public class MechanicalActivity extends Activity implements OnClickListener {
	private static final int PERMISSION_REQUEST_CODE = 0;
	private Button        mBtCapture;
	private TextView      mTvShow;// 实时显示角度
	private Camera        camera;// 相机实例
	private DowntiltSurfaceView mySurfaceView;// 相机预览用
	private FrameLayout   preview;// 存放surfaceview
	private double mAngle = 0;// 机械下倾角
	private String mWhoCallMe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mechanical);
		//设置屏幕为竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mTvShow = (TextView) findViewById(R.id.tv_show);
		mBtCapture = (Button) findViewById(R.id.bt_record_mechanical);
		findViewById(R.id.bt_cancel).setOnClickListener(this);
		mBtCapture.setOnClickListener(this);
		preview = (FrameLayout) findViewById(R.id.container_preview);
		mWhoCallMe = getIntent().getStringExtra(Const.IntentTransfer.MAIN_ACTIVITY_TOOLS);
		if (mWhoCallMe!=null&&mWhoCallMe.equals(Const.IntentTransfer.MAIN_ACTIVITY_TOOLS)) {
			mBtCapture.setVisibility(View.GONE);
		}
		initSeek();
		checkCameraPermission();
	}

	@Override
	protected void onDestroy() {
		if (camera != null) {
			camera.release();
		}
		super.onDestroy();
	}

	/**
	 * 初始化seekbar
	 */
	private void initSeek() {
		CircularSeekBar cbs = (CircularSeekBar) findViewById(R.id.cbs);
		cbs.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
			@Override
			public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
				double result = 0;
				if (progress <= 500) {
					result = 360 * ((double) progress / 1000);
				} else {
					result = 360 * ((double) (1000 - progress) / 1000);
				}
				mAngle = Tools.formatDouble2(result);
				mTvShow.setText(mAngle + "°");
			}

			@Override
			public void onStopTrackingTouch(CircularSeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(CircularSeekBar seekBar) {

			}
		});
	}

	@SuppressLint("NewApi")
	private void checkCameraPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
				initCamera();
			} else {
				requestCameraPermission();
			}
		} else {
			initCamera();
		}
	}

	/**
	 * 申请相机权限
	 */
	@SuppressLint("NewApi")
	private void requestCameraPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			requestPermissions(new String[] { Manifest.permission.CAMERA }, PERMISSION_REQUEST_CODE);
		}
	}

	/**
	 * 权限申请结果回调
	 */
	@SuppressLint("NewApi")
	@Override
	public void onRequestPermissionsResult(int arg0, String[] arg1, int[] arg2) {
		if (arg2.length > 0 && arg2[0] == PackageManager.PERMISSION_GRANTED) {
			initCamera();
		} else {
			if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
				showCameraPermission();
			} else {
				checkCameraPermission();
			}
		}
	}

	/**
	 * 向用户解释申请相机权限的原因
	 */
	private void showCameraPermission() {
		CommonDialog commonDialog = new CommonDialog(MechanicalActivity.this,
				getString(R.string.permission_camera_reason), 5);
		commonDialog.setClicklistener(new CommonDialog.OnClickListener() {

			@Override
			public void doConfirm() {
				goApplicationDetail();
			}

			@Override
			public void doCancel() {
			}
		});
		commonDialog.show();
	}

	/**
	 * 跳转到本app的详情页
	 */
	private void goApplicationDetail() {
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.fromParts("package", getPackageName(), null));
		startActivity(intent);
	}

	/**
	 * 初始化相机
	 */
	private void initCamera() {
		camera = getCameraInstance();
		if (null != camera) {
			camera.setDisplayOrientation(90);
			mySurfaceView = new DowntiltSurfaceView(getApplicationContext(), camera);
			preview.addView(mySurfaceView);
			camera.getParameters().setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		} else {
			// 提示无法链接相机
			Toast.makeText(MechanicalActivity.this, "无法连接相机", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_record_mechanical:// 记录数据
			Intent data = new Intent();
			data.putExtra(Const.IntentTransfer.resultCode_MechanicalActivity, mAngle);
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

}
