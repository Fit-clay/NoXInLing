package com.beidian.beidiannonxinling.ui.model;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.bean.OneKeyTestBean;
import com.beidian.beidiannonxinling.bean.TestResult;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.listener.CellPhoneStateListener;
import com.beidian.beidiannonxinling.test.TestControl;
import com.beidian.beidiannonxinling.ui.activity.CqtTestActivity;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.MapUtil;
import com.beidian.beidiannonxinling.util.ObserverNextComplete;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;

import static com.beidian.beidiannonxinling.ui.widget.Tools.showToast;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/3116:14
 * @描述: ------------------------------------------
 */

public class CqtTestActivityModel {
    //标记两个数据是否第一次保存.正式发布的时候可以取消,
    private boolean flage_save = false;
    private LocationClient  mLocClient;//定位对象
    private CqtTestActivity mCqtTestActivity;//绑定当前model的activity
    private TestControl     mTestControl;//测试控制层
    private double mLongitude = 0; //经度
    private double mLatitude  = 0;//维度
    private CellPhoneStateListener mCellPhoneStateListener;//信令扇区的监听器
    private TelephonyManager       mTelephonyManager;
    //RXjava切换到主线程接受消息
    ObserverNextComplete<TestResult> observer = new ObserverNextComplete<TestResult>() {
        @Override
        public void onNext(@NonNull TestResult testResult) {
            //            Log.d(TAG, "onNextssssssssss: "+testResult.toString());
            //把数据传给activity更新,把经纬度附加到此
            testResult.setLat(mLatitude + "");
            testResult.setLng(mLongitude + "");
            testResult.setCi(mCellPhoneStateListener.mLteCellBean.getCi());
            testResult.setPci(mCellPhoneStateListener.mLteCellBean.getPci() + "");
            testResult.setRsrp(mCellPhoneStateListener.mLteCellBean.getRsrp() + "");
            testResult.setSinr(mCellPhoneStateListener.mLteCellBean.getSinr() + "");
            for (int i = 0; i < mAllCell.size(); i++) {
                CellinfoListBean cellinfoListBean = mAllCell.get(i);
                if (mCellPhoneStateListener.mLteCellBean.getCi().equals(cellinfoListBean)) {
                    testResult.setEnb(mCqtTestActivity.mOneKeyTestBean.getBaseInfoTestBean().getSiteInfo().getEnodebid());
                }
            }
            mCqtTestActivity.refreshData(testResult);

        }

        @Override
        public void onComplete() {
            //            //打包日志文件并上传
            //            OneKeyTestBean oneKeyTestBean = mCqtTestActivity.mOneKeyTestBean;
            //            String workorfilePathName = Environment.getExternalStorageDirectory().toString() + File.separator + Const.SaveFile.ROOT_FILE_DIR + File.separator + oneKeyTestBean.getWorkorderno() + File.separator;
            //            String zipWorkorfilePathName = Environment.getExternalStorageDirectory().toString()+ File.separator + Const.SaveFile.ROOT_FILE_DIR  + File.separator+ oneKeyTestBean.getWorkorderno() +".zip";
            //            File[] files = new File[]{new File(workorfilePathName)};
            //            File zip = new File(zipWorkorfilePathName);
            //            try {
            //                ZipUtils.ZipFiles(zip, "", files);
            //            } catch (IOException e) {
            //                e.printStackTrace();
            //            }
            //            FileUpDown.upload(zipWorkorfilePathName,Const.Url.LOG_UP);
            //停止百度地图服务
            if (mLocClient.isStarted()) {
                mLocClient.stop();
            }

            mCqtTestActivity.closeActivity();

        }
    };
    private final List<CellinfoListBean> mAllCell;
    private       OneKeyTestBean         mOneKeyTestBean;


    public CqtTestActivityModel(CqtTestActivity cqtTestActivity) {
        mAllCell = cqtTestActivity.mOneKeyTestBean.getBaseInfoTestBean().getSiteInfo().getCellinfoList();
        this.mCqtTestActivity = cqtTestActivity;
        //初始化百度地图,设置监听回调
        mLocClient = new LocationClient(cqtTestActivity);
        //创建一个监听任务，用于获取信令
        mTelephonyManager = (TelephonyManager) (BaseApplication.getAppContext().getSystemService(BaseApplication.getAppContext().TELEPHONY_SERVICE));
        mCellPhoneStateListener = new CellPhoneStateListener(mTelephonyManager);
        mTelephonyManager.listen(mCellPhoneStateListener,
                PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR |
                        PhoneStateListener.LISTEN_CALL_STATE |
                        PhoneStateListener.LISTEN_CELL_LOCATION | PhoneStateListener.LISTEN_CELL_INFO | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        //获取经纬度
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation == null) {
                    return;
                }
                double mCurrentLon = bdLocation.getLongitude();
                mLongitude = bdLocation.getLongitude();
                mLatitude = bdLocation.getLatitude();

            }
        });
        mLocClient.setLocOption(MapUtil.setLocationOption());
        mLocClient.start();

    }


    public void testStart() {
        //                String assetsString = getAssetsString("json.txt");
        //org.json.JSONArray.parseArray(assetsString, TestTask.class)
        //                List<TestTask> list = new ArrayList<>(JSONArray.parseArray(assetsString, TestTask.class));
        List<TestTask> list = mCqtTestActivity.mOneKeyTestBean.getChangeModel();
        //判空处理
        if (list == null) {
            list = new ArrayList<>();
        }
        mTestControl = new TestControl();

        mTestControl.observable.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        Intent intent = mCqtTestActivity.getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            mOneKeyTestBean = new OneKeyTestBean();

        } else {
            mOneKeyTestBean  = (OneKeyTestBean) extras.get(Const.IntentTransfer.ONE_KEY_TEST);
        }
        String s = mTestControl.oneByOne(list, mCqtTestActivity, mOneKeyTestBean);
        switch (s) {
            case Const.TestManage.ONE_BY_ONE_OK:
                showToast(mCqtTestActivity, "任务准备就绪...");
                //                        Toast.makeText(this, "任务准备就绪...", Toast.LENGTH_LONG).show();

                break;
            case Const.TestManage.ONE_BY_ONE_BUSY:
                showToast(mCqtTestActivity, "线程池正在忙...");
                //                        Toast.makeText(this, "线程池正在忙...", Toast.LENGTH_LONG).show();
                break;
        }
    }


    public void stop() {
        if (mTestControl == null) {
            return;
        }
        //停止百度地图服务
        if (mLocClient.isStarted()) {
            mLocClient.stop();
        }
        mTestControl.stop();
    }


    /**
     * 获取资源文件
     *
     * @param fileName
     * @return
     */
    private String getAssetsString(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    mCqtTestActivity.getAssets().open(fileName), "UTF-8"));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 保存信令数据到文件
     *
     * @param testResult     测试结果数据
     * @param oneKeyTestBean 测试初始的数据,订单号,小区号,
     */
    public void saveSignalLingData(TestResult testResult, OneKeyTestBean oneKeyTestBean) {

        String str = JSONObject.toJSONString(testResult);
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.append('\r');

        String fileAbsolutePathName =  oneKeyTestBean.getResultPath()+File.separator+"log"+File.separator+ "signalling.log";
        //        Log.d("1111", fileAbsolutePathName);
        if (flage_save) {
            FileUtils.saveFile(mCqtTestActivity, stringBuffer.toString(), fileAbsolutePathName, true);
        } else {
            flage_save = true;
            FileUtils.deleteDir(new File(oneKeyTestBean.getResultPath()+File.separator+"log"+File.separator));
            FileUtils.saveFile(mCqtTestActivity, stringBuffer.toString(), fileAbsolutePathName, false);
        }

    }

    /**
     * 保存测试数据到文件
     *
     * @param testResult     测试结果数据
     * @param oneKeyTestBean 测试初始的数据,订单号,小区号,
     */
    public void saveTestData(TestResult testResult, OneKeyTestBean oneKeyTestBean) {
        String str = JSONObject.toJSONString(testResult);
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.append('\r');
        //        Log.d(TAG, "saveTestData: " + oneKeyTestBean.getWorkorderno());
        String fileAbsolutePathName = oneKeyTestBean.getResultPath()+File.separator+"log"+File.separator+ testResult.getTestType() + ".log";
        //        Log.d(TAG, "saveTestData: " + fileAbsolutePathName);
        if (flage_save) {
            FileUtils.saveFile(mCqtTestActivity, stringBuffer.toString(), fileAbsolutePathName, true);
        } else {
            flage_save = true;
            FileUtils.deleteDir(new File(oneKeyTestBean.getResultPath()+File.separator+"log"+File.separator));
            FileUtils.saveFile(mCqtTestActivity, stringBuffer.toString(), fileAbsolutePathName, false);
        }
    }

    public void unRegisterPhoneState() {
        if (mCellPhoneStateListener != null && mTelephonyManager != null) {
            mTelephonyManager.listen(mCellPhoneStateListener, PhoneStateListener.LISTEN_NONE);
            mCellPhoneStateListener = null;
            mTelephonyManager = null;
        }
    }
}
