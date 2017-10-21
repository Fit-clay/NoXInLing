package com.beidian.beidiannonxinling.test;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;

import com.beidian.beidiannonxinling.util.FlightModeChangeUtils;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/216:01
 * @描述: ------------------------------------------
 */

public class TestAttach {
    public int success       = 0;// 成功
    public int fail          = 0;// 失敗
    private final Activity mActivity1;
//    private NonSignaLlingTools mNonSignaLlingTools;

    /**
     * @param activity  上下文,

     */
    public TestAttach(Activity activity) {
        mActivity1 = activity;
//        FlightModeChangeUtils.openFlightMode(mActivity1);
//        FlightModeChangeUtils.closeFlightMode(mActivity1);

    }
    public boolean attach() {
        //开始切换飞行模式
        FlightModeChangeUtils.openFlightMode(mActivity1);
//        mNonSignaLlingTools = new NonSignaLlingTools(mActivity1.getApplicationContext());
//        小区ID=0 获取链接状态:空闲  国家码:460  获取网络码=11  信号质量=  获取网制式=没有数据连接  获取网路制式2g，3g，4g=4G  获取运营商=中国电信  获取频点=0  获取识别码=0  获取基站位置寻呼区=0null
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Log.d(TAG, "mNonSignaLlingTools.getCellId(): "+mNonSignaLlingTools.getCellId()+"  mNonSignaLlingTools.getNetworkDetail(): "+mNonSignaLlingTools.getNetworkDetail()+"  mNonSignaLlingTools.getIdentyCode(): "+mNonSignaLlingTools.getIdentyCode());
//        if (mNonSignaLlingTools.getCellId() == 0 && mNonSignaLlingTools.getNetworkDetail().equals("没有数据连接") && mNonSignaLlingTools.getIdentyCode() == 0) {
//            //切换飞行模式成功
//            Log.d(TAG, "attach: ");
//            FlightModeChangeUtils.closeFlightMode(mActivity1);
//            return true;
//        }
        if (getAirplaneMode(mActivity1)) {
            //切换飞行模式成功
            FlightModeChangeUtils.closeFlightMode(mActivity1);
            return true;
        }
        FlightModeChangeUtils.closeFlightMode(mActivity1);
        return false;
    }
    /**
     * 判断手机是否是飞行模式
     * @param context
     * @return
     */
    public static boolean getAirplaneMode(Context context){
        int isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) ;
        return (isAirplaneMode == 1)?true:false;
    }
}
