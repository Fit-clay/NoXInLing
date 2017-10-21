package com.beidian.beidiannonxinling.util;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * Created by ASUS on 2017/8/31.
 */

public class MapUtil {

    public static String getDistanceFromXtoY(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk = 180 / 3.14169;
        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        Double aDouble = new Double(6366000 * tt);
        aDouble = aDouble / 1000;
        String range = String.format("%.2f", aDouble).toString();


        return range;
    }
    public static MarkerOptions setMarkerOption(LatLng latLng, BitmapDescriptor icon){
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)//mark出现的位置
                .icon(icon)       //mark图标
                .draggable(false)//mark可拖拽
                .animateType(MarkerOptions.MarkerAnimateType.drop)//从天而降的方式
                //.animateType(MarkerOptions.MarkerAnimateType.grow)//从地生长的方式
                ;
        return markerOptions;
    }

    /**
     * 定位方法设置
     */
    public static LocationClientOption setLocationOption(){
        //获取配置参数对象，用于配置定位SDK各配置参数，比如定位模式、定位时间间隔、坐标系类型等
        LocationClientOption option = new LocationClientOption();
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        /*
        * 高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
        * 低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）；
        * 仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。*/
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置定时发起定位请求的间隔需要大于等于1000ms才是有效的
        /*
        * 定位sdk提供2种定位模式，定时定位和app主动请求定位。
        * setScanSpan < 1000 则为 app主动请求定位(手动请求)；
        * setScanSpan >=1000,则为定时定位模式（setScanSpan的值就是定时定位的时间间隔））
        * 定时定位模式中，定位sdk会按照app设定的时间定位进行位置更新，定时回调定位结果。此种定位模式适用于希望获得连续定位结果的情况。
        * 对于单次定位类应用，或者偶尔需要一下位置信息的app，可采用app主动请求定位这种模式。*/
        option.setScanSpan(500);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true);
        //在网络定位时，是否需要设备方向 true:需要 ; false:不需要
        option.setNeedDeviceDirect(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setEnableSimulateGps(false);

        return option;

    }

    public static String getLogMapLocationState(BDLocation bdLocation) {
        //以下打印日志，打印一些详细信息，供参考
        //------------------------------------位置信息日志--------------------------------------------------
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("\nTime : " + bdLocation.getTime());            //服务器返回的当前定位时间
        sBuilder.append("\nError code : " + bdLocation.getLocType());   //定位结果码
        sBuilder.append("\nLatitude : " + bdLocation.getLatitude());    //获取纬度坐标
        sBuilder.append("\nLongtitude : " + bdLocation.getLongitude()); //获取经度坐标
        sBuilder.append("\nRadius : " + bdLocation.getRadius());        //位置圆心

        //根据定位结果码判断是何种定位以及定位请求是否成功
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
            //GPS定位结果
            sBuilder.append("\nSpeed : " + bdLocation.getSpeed());//当前运动的速度
            sBuilder.append("\nSatellite number : " + bdLocation.getSatelliteNumber());//定位卫星数量
            sBuilder.append("\nHeight : " + bdLocation.getAltitude());  //位置高度
            sBuilder.append("\nDirection : " + bdLocation.getDirection());  //定位方向
            sBuilder.append("\nAddrStr : " + bdLocation.getAddrStr());  //位置详细信息
            sBuilder.append("\nStreet : " + bdLocation.getStreetNumber() + " " + bdLocation.getStreet());//街道号、路名
            sBuilder.append("\nDescribtion : GPS 定位成功");
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
            //网络定位结果
            sBuilder.append("\nAddrStr : " + bdLocation.getAddrStr()); //位置详细信息
            sBuilder.append("\nStreet : " + bdLocation.getStreetNumber() + " " + bdLocation.getStreet());//街道号、路名
            sBuilder.append("\nOperators : " + bdLocation.getOperators());//运营商编号
            sBuilder.append("\nDescribtion : 网络定位成功");
        } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {
            //离线定位结果
            sBuilder.append("\nAddrStr : " + bdLocation.getAddrStr()); //位置详细信息
            sBuilder.append("\nStreet : " + bdLocation.getStreetNumber() + " " + bdLocation.getStreet());//街道号、路名
            sBuilder.append("\nDescribtion : 离线定位成功");
        } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
            sBuilder.append("\nDescribtion : 服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
            sBuilder.append("\nDescribtion : 网络故障，请检查网络连接是否正常");
        } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
            sBuilder.append("\nDescribtion : 无法定位结果，一般由于定位SDK内部检测到没有有效的定位依据，" +
                    "比如在飞行模式下就会返回该定位类型， 一般关闭飞行模式或者打开wifi就可以再次定位成功");
        }

        //位置语义化描述
        sBuilder.append("\nLocation decribe : " + bdLocation.getLocationDescribe());
        //poi信息（就是附近的一些建筑信息）,只有设置可获取poi才有值
        List<Poi> poiList = bdLocation.getPoiList();
        if (poiList != null) {
            sBuilder.append("\nPoilist size : " + poiList.size());
            for (Poi p : poiList) {
                sBuilder.append("\nPoi : " + p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }

        return sBuilder.toString();

    }

    public static void isFirst(Boolean isFirstLoc, BDLocation bdLocation, BaiduMap mBaiduMap) {

        //----------------------------------------------定位----------------------------------------
        //构建生成定位数据对象            //定位数据构建器
        MyLocationData myLocationData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())   //设置定位数据的精度信息，单位米
                .direction(100)                     //设定定位数据的方向信息？？啥意思？？
                .latitude(bdLocation.getLatitude()) //设定定位数据的纬度
                .longitude(bdLocation.getLongitude())//设定定位数据的经度
                .build();   //构建生生定位数据对象

        //设置定位数据, 只有先允许定位图层后设置数据才会生效,setMyLocationEnabled(boolean)
        mBaiduMap.setMyLocationData(myLocationData);
        if (isFirstLoc) {
            //Log.d(TAG,"首次定位开始");
            //isFirstLoc = false;
            LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            //为当前定位到的位置设置覆盖物Marker
            // resetOverlay(latLng);
            //描述地图状态将要发生的变化         //生成地图状态将要发生的变化,newLatLngZoom设置地图新中心点
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latLng, 18.0f);
            //MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latLng);
            //以动画方式更新地图状态，动画耗时 300 ms  (聚焦到当前位置)
            mBaiduMap.animateMapStatus(mapStatusUpdate);
        }
    }

    public static String getLocationInformation(BDLocation bdLocation, TextureMapView mMapView) {
        String addr; //定位结果
        //mapView销毁后不在处理新接收的位置
        if (bdLocation == null || mMapView == null) {
            addr = "定位失败定位信息或者地图为空 ";
            return addr;
        } else if (bdLocation.hasAddr()) {
            addr = bdLocation.getAddrStr();
            return addr;
        } else {

            addr = "定位失败...BDLocation has no addr info";
            return addr;
        }
//        else if (!bdLocation.getLocationDescribe().isEmpty()) {
//            addr = bdLocation.getLocationDescribe();
//            return addr;
//        }
    }

    public static void Portion() {

    }


}
