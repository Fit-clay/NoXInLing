package com.beidian.beidiannonxinling.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.MapBean;
import com.beidian.beidiannonxinling.bean.UserInfoBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.net.ResultCallback;
import com.beidian.beidiannonxinling.ui.activity.BaseTestActivity;
import com.beidian.beidiannonxinling.util.JsonUtils;
import com.beidian.beidiannonxinling.util.LogUtils;
import com.beidian.beidiannonxinling.util.MapUtil;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by shanpu on 2017/8/14.
 * <p>
 */

public class MapFragment extends BaseFragment implements BaiduMap.OnMapLoadedCallback {
    boolean isFirstLoc = true;
    String account;
    String telphone;
    //是否是首次定位,实际上没有用到，因为我设置了不定时请求位置信息
    private MapStatus ms;
    private Button mLocation;
    private LatLng mylocation;
    /**
     * 地图基础管理
     */
    private TextureMapView mMapView = null;
    private BaiduMap mBaiduMap;
    /**
     * 地图覆盖物
     */
    private Marker marker = null;          //覆盖物
    //初始化bitmap信息，不用的时候请及时回收recycle   //覆盖物图标
    //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
    private BitmapDescriptor rd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
    private BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_blue);
    private BitmapDescriptor gd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_green);
    /**
     * 百度定位监听
     */
    public BDLocationListener myListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.d(TAG, "BDLocationListener -> onReceiveLocation");
            MapUtil.getLocationInformation(bdLocation, mMapView);
            String log = MapUtil.getLogMapLocationState(bdLocation);
            //打印以上信息
            Log.d(TAG, "定位结果详细信息 : " + log);

            mylocation = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());

            MapUtil.isFirst(isFirstLoc, bdLocation, mBaiduMap);
            refrenceMarker();
            locationClient.stop();
            // locationText.setText(addr);
        }
    };
    /**
     * 地图定位
     */
    private LocationClient locationClient = null;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null);
        mMapView = (TextureMapView) view.findViewById(R.id.map_fragment);
        mLocation = (Button) view.findViewById(R.id.request);
        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setOnMapLoadedCallback(this);
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
        // 定义点聚合管理类ClusterManager
        //允许定位图层,如果不设置这个参数，那么baiduMap.setMyLocationData(myLocationData);定位不起作用
        mBaiduMap.setMyLocationEnabled(true);
        //设置mark覆盖物点击监听器
        mBaiduMap.setOnMarkerClickListener(new MyMarkerClickListener());
        //生成定位服务的客户端对象，此处需要注意：LocationClient类必须在主线程中声明
        locationClient = new LocationClient(getContext());
        //设定定位SDK的定位方式
//        locationClient.setLocOption(MapUtil.setLocationOption());
        //开始、请求定位
        //locationClient.start();
        requestLocation();

        return view;
    }

    @Override
    protected void loadData() {

    }

    /**
     * 获取Mark信息
     */
    public void refrenceMarker() {
        UserInfoBean bean = BaseApplication.getUserInfo();
        account = bean.getUsername();
        telphone = bean.getTelphone();
        HttpParams params = new HttpParams();
        params.put("account", account);
        OkGo.post(Const.Url.URL_GET_WORK_ORDER).tag(MapFragment.this).params(params).execute(new ResultCallback(getActivity(), true) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                clearOverlay();
                List<MapBean> maps = new ArrayList<MapBean>();
                maps = JsonUtils.getMapList(s);
                if (maps != null && maps.size() != 0) {
                    for (MapBean m : maps) {
                        resetOverlay(m.getmPosition(), m.getTaskid(), m.getmState());

                    }
                }
            }

            @Override
            public void onFailure(Call call, Response response, Exception e) {
                ToastUtils.makeText(getContext(), "服务器请求失败" + response);
            }
        });
    }

    @Override
    protected void initListener() {
        //注册定位监听函数，当开始定位.start()或者请求定位.requestLocation()时会触发
        locationClient.registerLocationListener(myListener);
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocation();


            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getClass().getSimpleName() + " onCreate...");
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.setVisibility(View.VISIBLE);
        mMapView.onResume();
        Log.i(TAG, getClass().getSimpleName() + " onResume...");
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.setVisibility(View.INVISIBLE);
        mMapView.onPause();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            LogUtils.i(TAG, "onHiddenChanged  " + TAG + "隐藏");
            mMapView.setVisibility(View.INVISIBLE);
            mMapView.onPause();
        } else {
            LogUtils.i(TAG, "onHiddenChanged  " + TAG + "可见");
            mMapView.setVisibility(View.VISIBLE);
            mMapView.onResume();
        }
    }

    @Override
    public void onMapLoaded() {
        // TODO Auto-generated method stub
        //定位模式以及缩放大小
        ms = new MapStatus.Builder().target(new LatLng(31.24396, 121.446278)).zoom(9).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));

    }

    /**
     * 覆盖物管理
     */
    //清除覆盖物
    private void clearOverlay() {
        mBaiduMap.clear();
        marker = null;
    }

    //重置覆盖物
    private void resetOverlay(LatLng latLng, String taskid, String state) {
        minitOverlay(latLng, taskid, state);
    }

    //初始化添加覆盖物mark
    private void minitOverlay(LatLng latLng, String taskid, String state) {
        if (mylocation != null) {
            String range = MapUtil.getDistanceFromXtoY(latLng.latitude, latLng.longitude, mylocation.latitude, mylocation.longitude);
            Double aDouble = new Double(range);
            if (aDouble < 5) {
                Log.d(TAG, "Start initOverlay");
                if (state.equals("0")) {
                    //设置覆盖物添加的方式与效果
                    marker = (Marker) (mBaiduMap.addOverlay(MapUtil.setMarkerOption(latLng, rd)));//地图上添加mark
                    marker.setTitle(taskid);
                } else if (state.equals("1")) {

                    marker = (Marker) (mBaiduMap.addOverlay(MapUtil.setMarkerOption(latLng, gd)));//地图上添加mark
                    marker.setTitle(taskid);
                } else if (state.equals("2")) {
                    //添加mark

                    marker = (Marker) (mBaiduMap.addOverlay(MapUtil.setMarkerOption(latLng, bd)));//地图上添加mark
                    marker.setTitle(taskid);
                }
            }

            Log.d(TAG, "End initOverlay");
        }
    }

    /**
     * 定位管理
     */
    //开始定位或者请求定位
    private void requestLocation() {

        //如果请求定位客户端已经开启，就直接请求定位，否则开始定位
        //很重要的一点，是要在AndroidManifest文件中注册定位服务，否则locationClient.isStarted一直会是false,
        // 而且可能出现一种情况是首次能定位，之后再定位无效
        if (locationClient != null && locationClient.isStarted()) {
            Log.d(TAG, "Req" + "启动定位正在定位...");

            //请求定位，异步返回，结果在locationListener中获取.
            locationClient.requestLocation();
        } else if (locationClient != null && !locationClient.isStarted()) {
            Log.d(TAG, "Req : " + locationClient.isStarted() + "正在定位");
            //定位没有开启 则开启定位，结果在locationListener中获取.
            locationClient.start();

        } else {
            Log.e(TAG, "定位请求失败");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationClient.stop();
        mMapView.onDestroy();
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        mMapView.onDestroy();
    }

    //覆盖物点击监听器
    public class MyMarkerClickListener implements BaiduMap.OnMarkerClickListener {

        @Override
        public boolean onMarkerClick(Marker marker) {
            //调用方法,弹出View(气泡，意即在地图中显示一个信息窗口)，显示当前mark位置信息
            ToastUtils.makeText(getContext(), String.valueOf(marker.getPosition()));
            Intent intent = new Intent(getContext(), BaseTestActivity.class);
            intent.putExtra("workorder", marker.getTitle());
            startActivity(intent);
            return false;
        }
    }
}
