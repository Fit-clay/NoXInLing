package com.beidian.beidiannonxinling.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.Workorder;
import com.beidian.beidiannonxinling.manage.WalkingRouteOverlay;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.MapUtil;
import com.beidian.beidiannonxinling.util.ToastUtils;

import java.util.List;

public class LocatActivity extends BaseActivity implements BaiduMap.OnMapLoadedCallback, OnGetRoutePlanResultListener {

    // 浏览路线节点相关
    WalkingRouteOverlay walkingRouteOverlay = null;

    private TextureMapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private TextView tv_title;
    private LatLng mStart = null; // 起始点位
    private LatLng mEnd = null;//结束点位
    private RoutePlanSearch mPlan = null;
    private LinearLayout ll_back;
    private Marker marker;
    private MapStatus ms;
    private Dialog dialog;
    private LocationClient locationClient = null;
    //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
    private BitmapDescriptor rd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
    private BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_blue);
    private BitmapDescriptor gd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding_green);

    @Override
    protected void initView() {
        setContentView(R.layout.activity_locat);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        mMapView = (TextureMapView) findViewById(R.id.map_activity_navigation);
        //获取地图控制器
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
        // 定义点聚合管理类ClusterManager


        Log.i(TAG, mMapView.getLogoPosition() + " onCreateView...");


        //允许定位图层,如果不设置这个参数，那么baiduMap.setMyLocationData(myLocationData);定位不起作用
        mBaiduMap.setMyLocationEnabled(true);
        //生成定位服务的客户端对象，此处需要注意：LocationClient类必须在主线程中声明

        //生成定位服务的客户端对象，此处需要注意：LocationClient类必须在主线程中声明
        locationClient = new LocationClient(mContext);
        //设定定位SDK的定位方式
        locationClient.setLocOption(MapUtil.setLocationOption());
        //开始、请求定位
        //locationClient.start();
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("导航");
        walkingRouteOverlay = new WalkingRouteOverlay(mBaiduMap);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initListener() {

        //开启规划模式
        Log.i(TAG, "initListener: ");
        mPlan = RoutePlanSearch.newInstance();
        mPlan.setOnGetRoutePlanResultListener(this);
        Intent intent = getIntent();
        Workorder task = (Workorder) intent.getSerializableExtra("task");
        Log.i(TAG, "initListener: " + intent);
        Log.i(TAG, "initListener: " + task.getStartY());
        Log.i(TAG, "initListener: " + task.getStartX());
        if (intent != null) {

            if (task.getStartY() != null && task.getStartX() != null) {
                mEnd = new LatLng(Double.parseDouble(String.valueOf(task.getLatitude())), Double.parseDouble(String.valueOf(task.getLongitude())));
                mStart = new LatLng(Double.parseDouble(task.getStartY()), Double.parseDouble(task.getStartX()));
                PlanNode fromPlanNode = PlanNode.withLocation(mStart);
                PlanNode toPlanNode = PlanNode.withLocation(mEnd);
                ToastUtils.makeText(mContext, "初始化中请耐心等待");
                dialog = DialogUtil.showLodingDialog(mContext, "初始化中");
                mPlan.walkingSearch(new WalkingRoutePlanOption().from(fromPlanNode).to(toPlanNode));

            } else {
                ToastUtils.makeText(mContext, "你不能在距离未初始化前进行导航");
            }

        }


    }



    @Override
    public void onMapLoaded() {
        //将地图定位默认位置在上海市
        ms = new MapStatus.Builder().target(new LatLng(31.24396, 121.446278)).zoom(9).build();
        //使用动画移动到当前位置
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        locationClient.stop();
        //mMapView.onDestroy();
    }


    @Override
    public void onGetWalkingRouteResult(final WalkingRouteResult walkingRouteResult) {
        Log.i(TAG, "onGetWalkingRouteResult: ");
        if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtils.makeText(mContext, "抱歉，未找到结果请检查网络设置");
            dialog.dismiss();
            finish();

            return;
        }
        List<WalkingRouteLine> routeLines = walkingRouteResult.getRouteLines();
        mBaiduMap.clear();
        walkingRouteOverlay.removeFromMap();
        walkingRouteOverlay.setData(routeLines.get(0));
        new Thread(new Runnable() {
            @Override
            public void run() {

                walkingRouteOverlay.addToMap();
                walkingRouteOverlay.zoomToSpan();
                dialog.dismiss();
            }
        }).start();

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
}


