package com.beidian.beidiannonxinling.test;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 电话状态广播接收
 */
public class PhoneCallReceiver extends BroadcastReceiver {
	private static ObservableEmitter<Integer> abs;
	public static Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
		@Override
		public void subscribe(ObservableEmitter<Integer> e) throws Exception {
			abs = e;
		}
	});

	@Override
	public void onReceive(Context context, Intent intent) {
		if (abs==null) {
			observable.subscribe(new Observer<Integer>() {
				@Override
				public void onSubscribe(@NonNull Disposable d) {

				}

				@Override
				public void onNext(@NonNull Integer integer) {

				}

				@Override
				public void onError(@NonNull Throwable e) {

				}

				@Override
				public void onComplete() {

				}
			});

		}
		// /*
		// * 电话类型，呼入，还是呼出
		// */
		// String callType = intent.getAction();
//		Log.d(TAG, "PhoneCallReceiver--------电话");
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Service.TELEPHONY_SERVICE);
			tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);


		// // 如果是去电
		// if (callType.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
		// } else if
		// (callType.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {//
		// 如果是来电
		//
		// }
	}

	public  int phoneState;
	/**
	 * 电话监听内部类
	 */
	PhoneStateListener listener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// 注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
			super.onCallStateChanged(state, incomingNumber);
//			Log.d(TAG, "PhoneCallReceiver--------电话状态:"+state);
//			Log.d(TAG, "onReceive: ************"+state);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
//				Log.d(TAG, "PhoneCallReceiver--------电话响铃状态");
				phoneState = TelephonyManager.CALL_STATE_RINGING;
				abs.onNext(phoneState);
				break;
			case TelephonyManager.CALL_STATE_IDLE: // 闲置状态：打电话监听后会首先进入此状态，从挂机状态再次进入此状态才表示电话挂断
//				Log.d(TAG, "PhoneCallReceiver--------闲置状态");
				phoneState = TelephonyManager.CALL_STATE_IDLE;
				abs.onNext(phoneState);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// 打电话时进入闲置状态后会立即进入挂机状态(无法判断对方是否接听)
//				Log.d(TAG, "PhoneCallReceiver--------打电话时进入闲置状态后会立即进入挂机状态");
				phoneState = TelephonyManager.CALL_STATE_OFFHOOK;
				abs.onNext(phoneState);
				break;
			}

		}
	};

}
