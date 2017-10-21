package com.beidian.beidiannonxinling.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/3013:08
 * @描述: ------------------------------------------
 */

public class BaiduMapService extends Service {

    private       LocationClient                client;   //客户端定位
    public static ObservableEmitter<BDLocation> en;
    public static double                        mCurrentLat;
    public static double                        mCurrentLon;
    public static BDLocation                    mBdLocation;
    public static Observable<BDLocation> mObjectObservable = Observable.create(new ObservableOnSubscribe<BDLocation>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<BDLocation> e) throws Exception {
            en = e;
        }
    });
    private MyLocationLitener mBdLocationListener;

    private   void disSubscribe() {
        mObjectObservable.subscribe(new Observer<BDLocation>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BDLocation bdLocation) {
                Log.d("sss", "onReceiveLocation: "+bdLocation.getLatitude());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    @Override
    public void onCreate() {
        disSubscribe();
        Log.d(TAG, "onCreate: ");
        //调用方法
        getLocation();
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    //得到用户的具体位置
    private void getLocation() {
        //
        client = new LocationClient(getApplicationContext());
        //给客户端注册监听
        mBdLocationListener = new MyLocationLitener();
        client.registerLocationListener(mBdLocationListener);
        //新建一个客户端位置选项
        LocationClientOption option = new LocationClientOption();
        //设置打开GPS
        option.setOpenGps(true);
        //设置地区类型
        option.setAddrType("all");
        option.setCoorType("bd0911");
        //设置范围
        option.setScanSpan(1000);
        //客户端得到这个选项
        client.setLocOption(option);
        //打开客户端
        client.start();
    }

    String TAG = "ss";
    //此方法是为了监听用户操作
    private class MyLocationLitener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                Toast.makeText(getApplicationContext(), "网络无法连接，定位失败！", Toast.LENGTH_SHORT).show();
                return;
            }
            mCurrentLat = bdLocation.getLatitude();
            mCurrentLon = bdLocation.getLongitude();
            mBdLocation = bdLocation;
            en.onNext(bdLocation);

        }

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (client.isStarted()) {
            client.stop();
        }
        client.unRegisterLocationListener(mBdLocationListener);
        mBdLocationListener = null;
        client = null;
        Log.d("ssx", "onDestroy: ");
        disSubscribe();
        super.onDestroy();
    }
    @Override
    protected void finalize() throws Throwable {
        Log.d("ssx", "finalize: 我已经被GC回收"+getClass().getName());
        super.finalize();
    }

}