package com.beidian.beidiannonxinling.util;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.SiteSqlBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.net.ResultCallback;
import com.beidian.beidiannonxinling.service.BaiduMapService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/10/1611:29
 * @描述: ------------------------------------------
 */

public class BaiDuMapDraw {
    public Context mContext;
    private List<SiteSqlBean> mSiteSqlBeen = new ArrayList<>();
    private BaiduMap mBaiduMap;
    ArrayList<Overlay> mCellOverlays     = new ArrayList<>();//添加的扇区覆盖物
    ArrayList<Overlay> mCellNameOverlays = new ArrayList<>();//添加的扇区名称覆盖物

    public BaiDuMapDraw(Context context, BaiduMap baiduMap) {
        mContext = context;
        mBaiduMap = baiduMap;
    }

    /**
     * 删除绘制的扇区信息
     *
     * @param info 要绘制的内容"名称" "扇区"
     */
    public void removeAroundSiteInfo(String info) {
        if (info.equals("扇区")) {
            for (Overlay cellOverlay : mCellOverlays) {
                cellOverlay.remove();
            }
            mCellOverlays.clear();
        }
        if (info.equals("名称")) {
            for (Overlay cellOverlay : mCellNameOverlays) {
                cellOverlay.remove();
            }
            mCellNameOverlays.clear();
        }

    }

    /**
     * 绘制周围基站信息
     *
     * @param info 要绘制的内容"名称" "扇区"
     */
    public void drawAroundSiteInfo(final String info) {
        HttpParams params = new HttpParams();
        Map around = getAround(BaiduMapService.mCurrentLon, BaiduMapService.mCurrentLat, 2000);

        StringBuffer stringBuffer = sqlWhere(around);
        params.put("str", stringBuffer.toString());
        //        params.put("workorderNo", "HCC_test_0001");
        OkGo.post(Const.Url.URL_QUERY_SITE).tag(this).params(params).execute(new ResultCallback(mContext, true) {


            @Override
            public void onFailure(Call call, Response response, Exception e) {

            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                mSiteSqlBeen = JSONArray.parseArray(s, SiteSqlBean.class);
                for (SiteSqlBean siteSqlBean : mSiteSqlBeen) {
                    List<SiteSqlBean.LinelistBean> linelist = siteSqlBean.getLinelist();
                    if (info.equals("扇区")) {
                        for (SiteSqlBean.LinelistBean linelistBean : linelist) {
                            showCell(new Integer(linelistBean.getDirectionangle()), 30, siteSqlBean.getLat(), siteSqlBean.getLng());
                        }
                    }
                    if (info.equals("名称")) {
                        showCellName(siteSqlBean.getLat(), siteSqlBean.getLng(), siteSqlBean.getSitename());
                    }
                }
            }
        });
    }

    /**
     * 百度地图两点距离测量
     */
    private MarkerOptions mOoA;
    private MarkerOptions mOoB;
    public List<LatLng> mPoints = new ArrayList<LatLng>();
    private OverlayOptions mText;
    public  Overlay        mOverlayOoA;
    public  Overlay        mOverlayOoB;
    public  Overlay        mOverlayPolyline;
    private Overlay        mOverlayooText;

    /**
     * 测量地图两点的距离
     */
    public void distanceMeasure() {
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {


            /**
             * 单击地图
             */
            public void onMapClick(LatLng point) {
                if (mOoB != null) {//如果mOoB != null则说明是第二次测试,需要把前一次的数据清除
                    mOverlayOoA.remove();
                    mOverlayPolyline.remove();
                    mOverlayOoB.remove();
                    mOverlayooText.remove();
                    mPoints.clear();
                    mOoA = null;
                    mOoB = null;
                }
                //                                mPoints = new ArrayList<LatLng>();
                if (mOoA != null) {//如果mOoA=null则说明是第一个点,否则是第二个点
                    mPoints.add(point);
                    BitmapDescriptor bdB = BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_markb);
                    mOoB = new MarkerOptions().position(point).icon(bdB);
                    mOverlayOoB = mBaiduMap.addOverlay(mOoB);
                    mBaiduMap.setOnMapClickListener(null);
                    OverlayOptions ooPolyline = new PolylineOptions().width(10)
                            .color(0xAAFF0000).points(mPoints);
                    // 添加线
                    mOverlayPolyline = mBaiduMap.addOverlay(ooPolyline);
                    double distance = DistanceUtil.getDistance(mPoints.get(1), mPoints.get(0));
                    long l1 = Math.round(distance * 100);   //四舍五入
                    double ret = l1 / 100000.0;               //注意：使用   100.0   而不是   100

                    // 添加文字

                    mText = new TextOptions().bgColor(0xAAFFFF00)
                            .fontSize(24).fontColor(0xFFFF00FF).text("距离:" + ret + "公里").rotate(-30)
                            .position(point);
                    mOverlayooText = mBaiduMap.addOverlay(mText);
                } else {

                    mPoints.add(point);
                    BitmapDescriptor bdA = BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_marka);

                    mOoA = new MarkerOptions().position(point).icon(bdA);
                    mOverlayOoA = mBaiduMap.addOverlay(mOoA);

                }
            }

            /**
             * 单击地图中的POI点
             */
            public boolean onMapPoiClick(MapPoi poi) {
                //                touchType = "单击POI点";
                //                currentPt = poi.getPosition();
                //                updateMapState();
                return false;
            }
        });
    }

    /**
     * sql 拼接
     *
     * @param around //                        map.put("minLat", minLat+"");
     *               //                        map.put("maxLat", maxLat+"");
     *               //                        map.put("minLng", minLng+"");
     *               //                        map.put("maxLng", maxLng+"");
     */
    private StringBuffer sqlWhere(Map around) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("lat>" + around.get("minLat") + " and " +
                "lat<" + around.get("maxLat") + " and " +
                "lng>" + around.get("minLng") + " and " +
                "lng<" + around.get("maxLng"));
        return stringBuffer;

    }

    /**
     * 显示小区
     *
     * @param p 方位角
     * @param a 扇区角度
     */
    private void showCell(int p, int a, double x0, double y0) {

        /**
         * 圆点坐标：(x0,y0)
         方位角:p,
         半径：r
         角度：a

         则圆上任一点为：（x1,y1）
         x1   =   x0   +   r   *   cos(a   *   3.14   /180   )
         y1   =   y0   +   r   *   sin(a   *   3.14   /180   )
         */
        double r = 0.0015;
        //        double x0 = 39.93923, y0 = 116.357428;
        List<LatLng> pts = new ArrayList<LatLng>();
        pts.add(new LatLng(x0, y0));
        for (int i = p; i < p + a; i++) {
            double x1 = x0 + r * Math.cos(i * Math.PI / 180);
            double y1 = y0 + r * Math.sin(i * Math.PI / 180);

            LatLng pt1 = new LatLng(x1, y1);

            pts.add(pt1);

        }
        OverlayOptions ooPolygon = new PolygonOptions().points(pts)
                .stroke(new Stroke(5, 0xAA0000FF)).fillColor(0xAA0000FF);
        mCellOverlays.add(mBaiduMap.addOverlay(ooPolygon));
    }

    /**
     * 开启锁定 位置功能
     */
    public boolean isBaiDuMapLock = true;
    int a = 0;//控制定位频率,减少性能开销
    public void baiDuMapLocation() {
        a++;
        if (a != 5) {
            return;
        }
        if (isBaiDuMapLock == true) {
            LatLng ll = new LatLng(BaiduMapService.mCurrentLat, BaiduMapService.mCurrentLon);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);

            MyLocationData myLocationData = new MyLocationData.Builder()
                    .accuracy(BaiduMapService.mBdLocation == null ? 0 : BaiduMapService.mBdLocation.getRadius())   //设置定位数据的精度信息，单位米
                    .direction(100)                     //设定定位数据的方向信息？？啥意思？？
                    .latitude(BaiduMapService.mCurrentLat) //设定定位数据的纬度
                    .longitude(BaiduMapService.mCurrentLon)//设定定位数据的经度
                    .build();   //构建生生定位数据对象

            //设置定位数据, 只有先允许定位图层后设置数据才会生效,setMyLocationEnabled(boolean)
            mBaiduMap.setMyLocationData(myLocationData);
        }

    }


    /**
     * 显示小区名
     */
    private void showCellName(double x0, double y0, String name) {
        // 添加文字
        LatLng llText = new LatLng(x0, y0);
        OverlayOptions ooText = new TextOptions().bgColor(0xAAFFFF00)
                .fontSize(24).fontColor(0xFFFF00FF).text(name).rotate(-30)
                .position(llText);

        mCellNameOverlays.add(mBaiduMap.addOverlay(ooText));
    }

    /**
     * 获取当前用户一定距离以内的经纬度值
     * 单位米 return minLat
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     *
     * @param latStr
     * @param lngStr
     * @param raidus
     */

    public Map getAround(double latStr, double lngStr, double raidus) {
        Map map = new HashMap();

        Double latitude = latStr;// 传值给经度
        Double longitude = lngStr;// 传值给纬度


        Double degree = (24901 * 1609) / 360.0; // 获取每度
        double raidusMile = raidus;

        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180)) + "").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //获取最小经度
        Double minLat = longitude - radiusLng;
        // 获取最大经度
        Double maxLat = longitude + radiusLng;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        // 获取最小纬度
        Double minLng = latitude - radiusLat;
        // 获取最大纬度
        Double maxLng = latitude + radiusLat;

        map.put("minLat", minLat + "");
        map.put("maxLat", maxLat + "");
        map.put("minLng", minLng + "");
        map.put("maxLng", maxLng + "");


        //        LatLng latLng = new LatLng(latStr,
        //                lngStr);

        //        double distance1 = DistanceUtil.getDistance( latLng, new LatLng(minLng, minLat));
        //        double distance2 = DistanceUtil.getDistance(latLng, new LatLng(maxLng, maxLat));
        //        map.put("xx", distance1 + "");
        //        map.put("yy", distance2 + "");
        return map;
    }


}
