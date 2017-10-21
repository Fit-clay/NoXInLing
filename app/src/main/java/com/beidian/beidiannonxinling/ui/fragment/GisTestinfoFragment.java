package com.beidian.beidiannonxinling.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.LocationClient;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.OneKeyTestBean;
import com.beidian.beidiannonxinling.bean.TestResult;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.listener.CellPhoneStateListener;
import com.beidian.beidiannonxinling.service.BaiduMapService;
import com.beidian.beidiannonxinling.test.TestControl;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.ObserverNextComplete;
import com.beidian.beidiannonxinling.util.ThreadPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.beidian.beidiannonxinling.ui.widget.Tools.showToast;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/2510:57
 * @描述: ------------------------------------------
 */

public class GisTestinfoFragment extends TestInfoFragment implements View.OnClickListener {
    private View                   mFragment_cqt_base_info;
    private View                   mFragment_cqt_work_info;
    private TextView               mTv_cellName;
    private TextView               mTv_enb;
    private TextView               mTv_ci;
    private TextView               mTv_pci;
    private TextView               mTv_earfcn;
    private TextView               mTv_rsrp;
    private TextView               mTv_sinr;
    private TextView               mTv_speedDL;
    private TextView               mTv_speedUL;
    private TextView               mTv_lat;
    private TextView               mTv_lng;
    private TextView               mTv_1;
    private TextView               mTv_0;
    private TextView               mTv_2;
    private TextView               mTv_3;
    private TextView               mTv_4;
    private TextView               mTv_5;
    private TextView               mTv_6;
    private TestInfoFragment       mTestInfoFragment;
    private TestInfoFragment       mWorkInfoFragment;
    private View                   mFragment_test_info;
    private TextView               mTv_testInfo;
    private TextView               mTv_baseInfo;
    private Activity               mGisActivity1;
    private TestControl            mTestControl;
    private CellPhoneStateListener mCellPhoneStateListener;
    private TelephonyManager       mTelephonyManager;
    private LocationClient         mLocClient;
    boolean isStartRefresh = true;//控制采集信令刷新
    private Double lastX             = 0.0;
    public  int    mCurrentDirection = 0;
    //    public  double mCurrentLat       = 0.0;
    //    public  double mCurrentLon       = 0.0;
    private float mCurrentAccracy;
    //标记两个数据是否第一次保存.正式发布的时候可以取消,
    private boolean isFirstSave = true;
    boolean isFirstLoc = true; // 是否首次定位
    private TestResult mTestResult_Signalling = null;
    private TestResult mTestResult_Test       = null;
    //RXjava切换到主线程接受消息
    ObserverNextComplete<TestResult> observer = new ObserverNextComplete<TestResult>() {
        @Override
        public void onNext(@NonNull final TestResult testResult) {
            //把数据传给activity更新,把经纬度附加到此
            testResult.setLat(BaiduMapService.mCurrentLat + "");
            testResult.setLng(BaiduMapService.mCurrentLon + "");
            testResult.setCi(mCellPhoneStateListener.mLteCellBean.getCi());
            testResult.setPci(mCellPhoneStateListener.mLteCellBean.getPci() + "");
            testResult.setRsrp(mCellPhoneStateListener.mLteCellBean.getRsrp() + "");
            testResult.setSinr(mCellPhoneStateListener.mLteCellBean.getSinr() + "");

            switch (testResult.getResultType()) {
                case Const.TestManage.RESULT_TYPE_SIGNALLING:
                    mTestResult_Signalling = testResult;
                    //刷新信令数据
                    refreshDataSignal(mTestResult_Signalling);
                    break;
                case Const.TestManage.RESULT_TYPE_TEST:
                    mTestResult_Test = testResult;
                    //刷新测试数据
                    refreshDataTest(mTestResult_Test);
                    //保存数据
                    saveTestData(testResult, mOneKeyTestBean);
                    //发送数据到回调
                    mResultLsitener.onTestResult(mTestResult_Test);
                    break;
            }
        }

        @Override
        public void onComplete() {
            //停止百度地图服务
            closeActivity();

        }
    };


    private void startSignaLling() {


        //此线程用于更新和保存信令数据,
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isStartRefresh) {
                    ThreadPool.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mTestResult_Signalling != null) {
                                //发送数据到回调
                                mResultLsitener.onSignalLingResult(mTestResult_Signalling);
                                //保存数据
                                saveSignalLingData(mTestResult_Signalling, mOneKeyTestBean);
                            }
                        }
                    });
                    try {
                        Thread.sleep(Const.TestManage.COLLECTION_SPEED);
                        //                        Thread.sleep(Const.TestManage.COLLECTION_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private OneKeyTestBean    mOneKeyTestBean;
    private ServiceConnection conn;
    private ResultLsitener    mResultLsitener;


    public void closeActivity() {
        //停止刷新界面
        isStartRefresh = false;
        //销毁当前activity,并且传消息给上一级页面f
        stop();
        unRegisterPhoneState();
//        showWaitingDialog();//等待一会退出,等候线程销毁
        Intent intent = new Intent();
        OneKeyTestBean oneKeyTestBean = new OneKeyTestBean();
        intent.putExtra(Const.IntentTransfer.ONE_KEY_TEST, oneKeyTestBean);
        mGisActivity1.setResult(Const.IntentTransfer.resultCode_CqtTestActivityActivity, intent);
        mGisActivity1.finish();
    }

    /**
     * 取消注册的监听电话
     */
    private void unRegisterPhoneState() {
        if (mCellPhoneStateListener != null && mTelephonyManager != null) {
            mTelephonyManager.listen(mCellPhoneStateListener, PhoneStateListener.LISTEN_NONE);
            mCellPhoneStateListener = null;
            mTelephonyManager = null;
        }
    }

    private void stop() {
        if (mTestControl == null) {
            return;
        }


        mTestControl.stop();
    }

    @Override
    public void onDestroy() {
        Log.d("ssx", "onDestroy:fragment ");
        if (conn != null) {
            getActivity().unbindService(conn);
        }

        super.onDestroy();
    }

//    /**
//     * 刷新测试结果数据
//     *
//     * @param testResult
//     */
//    public void refreshData(TestResult testResult) {
//        switch (testResult.getResultType()) {
//            case Const.TestManage.RESULT_TYPE_SIGNALLING:
//                refreshDataSignal(testResult);
//                //保存数据
//                saveSignalLingData(testResult, mOneKeyTestBean);
//                //发送数据到回调
//                mResultLsitener.onSignalLingResult(testResult);
//                break;
//            case Const.TestManage.RESULT_TYPE_TEST:
//                refreshDataTest(testResult);
//                //保存数据
//                saveTestData(testResult, mOneKeyTestBean);
//                //发送数据到回调
//                mResultLsitener.onTestResult(testResult);
//                break;
//        }
//    }

    /**
     * 刷新测试界面
     *
     * @param testResult
     */
    private void refreshDataTest(TestResult testResult) {
        StringBuffer stringBuffer;

        switch (testResult.getTestType()) {
            case Const.TestManage.TEST_TYPE_IDLE:
                mTv_0.setText("IDLE");
                stringBuffer = new StringBuffer();
                stringBuffer.append("空闲时间 :  ");
                stringBuffer.append(testResult.getCurrentTime());
                stringBuffer.append("s");
                mTv_1.setText("空闲时间 :  " + testResult.getCurrentTime() + "s");
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.INVISIBLE);
                mTv_3.setVisibility(View.INVISIBLE);
                mTv_4.setVisibility(View.INVISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_WAIT:
                mTv_0.setText("WAIT");
                stringBuffer = new StringBuffer();
                stringBuffer.append("等待时间 :  ");
                stringBuffer.append(testResult.getCurrentTime());
                stringBuffer.append("s");
                mTv_1.setText("等待时间 :  " + testResult.getCurrentTime() + "s");
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.INVISIBLE);
                mTv_3.setVisibility(View.INVISIBLE);
                mTv_4.setVisibility(View.INVISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_PING:
                mTv_0.setText("PING");
                stringBuffer = new StringBuffer();
                stringBuffer.append("时延 :  ");
                stringBuffer.append(testResult.getDelay());
                stringBuffer.append("ms");
                mTv_1.setText("时延 :  " + testResult.getDelay() + "ms");
                stringBuffer = new StringBuffer();
                stringBuffer.append("成功率 :  ");
                stringBuffer.append(testResult.getSuccessRate());
                stringBuffer.append("%");
                mTv_2.setText("成功率 :  " + testResult.getSuccessRate() + "%");
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.VISIBLE);
                mTv_3.setVisibility(View.INVISIBLE);
                mTv_4.setVisibility(View.INVISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_HTTP:
                mTv_0.setText("HTTP");
                stringBuffer = new StringBuffer();
                stringBuffer.append("页面下载时间 :  ");
                stringBuffer.append(testResult.getCostTime());
                stringBuffer.append("ms");
                mTv_1.setText("页面下载时间 :  " + testResult.getCostTime() + "ms");
                stringBuffer = new StringBuffer();
                stringBuffer.append("平均速率 :  ");
                stringBuffer.append(testResult.getAvgSpeed());
                stringBuffer.append("kb/s");
                mTv_2.setText("平均速率 :  " + testResult.getAvgSpeed() + "kb/s");
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.VISIBLE);
                mTv_3.setVisibility(View.INVISIBLE);
                mTv_4.setVisibility(View.INVISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_CALL_CSFBZ:
            case Const.TestManage.TEST_TYPE_CALL_VOLTEZ:
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_CALL_CSFBZ)) {
                    mTv_0.setText("CSFBZ");
                    refreshDataShowTitle(testResult, "CSFBZ :  ");
                }
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_CALL_VOLTEZ)) {
                    mTv_0.setText("VOLTEZ");
                    refreshDataShowTitle(testResult, "VOLTEZ :  ");
                }
                stringBuffer = new StringBuffer();
                stringBuffer.append("保持时间 :  ");
                stringBuffer.append(testResult.getRetentionTime());
                stringBuffer.append("s");
                mTv_1.setText("保持时间 :  " + testResult.getRetentionTime() + "s");
                stringBuffer = new StringBuffer();
                stringBuffer.append("成功率 :  ");
                stringBuffer.append(testResult.getSuccessRate());
                stringBuffer.append("%");
                mTv_2.setText("成功率 :  " + testResult.getSuccessRate() + "%");
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.VISIBLE);
                mTv_3.setVisibility(View.INVISIBLE);
                mTv_4.setVisibility(View.INVISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_ATTACH:
                mTv_0.setText("ATTACH");
                stringBuffer = new StringBuffer();
                stringBuffer.append("尝试测试 :  ");
                stringBuffer.append(testResult.getCurrentCount());
                stringBuffer.append("次");
                mTv_1.setText(stringBuffer);
                stringBuffer = new StringBuffer();
                stringBuffer.append("成功次数 :  ");
                stringBuffer.append(testResult.getSuccess());
                stringBuffer.append("次");
                mTv_2.setText(stringBuffer);
                stringBuffer = new StringBuffer();
                stringBuffer.append("失败次数 :  ");
                stringBuffer.append(testResult.getFail());
                stringBuffer.append("次");
                mTv_3.setText(stringBuffer);
                stringBuffer = new StringBuffer();
                stringBuffer.append("成功率 :  ");
                stringBuffer.append(testResult.getSuccessRate());
                stringBuffer.append("次");
                mTv_4.setText(stringBuffer);
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.VISIBLE);
                mTv_3.setVisibility(View.VISIBLE);
                mTv_4.setVisibility(View.VISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_FTP_DOWNLOAD:
            case Const.TestManage.TEST_TYPE_FTP_UP:
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_FTP_DOWNLOAD)) {
                    mTv_0.setText("DOWNLOAD");
                    stringBuffer = new StringBuffer();
                    stringBuffer.append("速率DL :  ");
                    stringBuffer.append(testResult.getAvgSpeed());
                    stringBuffer.append("kb/s");
                    mTv_speedDL.setText(stringBuffer);
                }
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_FTP_UP)) {
                    mTv_0.setText("UP");
                    stringBuffer = new StringBuffer();
                    stringBuffer.append("速率UL :  ");
                    stringBuffer.append(testResult.getAvgSpeed());
                    stringBuffer.append("kb/s");
                    mTv_speedUL.setText(stringBuffer);
                }
                stringBuffer = new StringBuffer();
                stringBuffer.append("文件大小 :  ");
                stringBuffer.append(testResult.getFileSize());
                stringBuffer.append("字节");
                mTv_1.setText("文件大小 :  " + testResult.getFileSize() + "字节");


                stringBuffer = new StringBuffer();
                stringBuffer.append("已传时间 :  ");
                stringBuffer.append(testResult.getCurrentTime());
                stringBuffer.append("s");
                mTv_2.setText("已传时间 :  " + testResult.getCurrentTime() + "s");
                stringBuffer = new StringBuffer();
                stringBuffer.append("传输进程 :  ");
                stringBuffer.append(testResult.getProgress());
                stringBuffer.append("%");
                mTv_3.setText("传输进程 :  " + testResult.getProgress() + "%");
                stringBuffer = new StringBuffer();
                stringBuffer.append("已传数据 :  ");
                stringBuffer.append(testResult.getOverfileSize());
                stringBuffer.append("字节");
                mTv_4.setText("已传数据 :  " + testResult.getOverfileSize() + "字节");
                stringBuffer = new StringBuffer();
                stringBuffer.append("峰值速率 :  ");
                stringBuffer.append(testResult.getMaxSpeed());
                stringBuffer.append("kb/s");
                mTv_5.setText("峰值速率 :  " + testResult.getMaxSpeed() + "kb/s");
                stringBuffer = new StringBuffer();
                stringBuffer.append("平均速率 :  ");
                stringBuffer.append(testResult.getAvgSpeed());
                stringBuffer.append("kb/s");
                mTv_6.setText("平均速率 :  " + testResult.getAvgSpeed() + "kb/s");
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.VISIBLE);
                mTv_3.setVisibility(View.VISIBLE);
                mTv_4.setVisibility(View.VISIBLE);
                mTv_5.setVisibility(View.VISIBLE);
                mTv_6.setVisibility(View.VISIBLE);
                break;

        }
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

        String fileAbsolutePathName = oneKeyTestBean.getResultPath() + File.separator + "log" + File.separator + "signalling.log";
        //        Log.d("1111", fileAbsolutePathName);
        if (!isFirstSave) {
            FileUtils.saveFile(mGisActivity1, stringBuffer.toString(), fileAbsolutePathName, true);
        } else {
            isFirstSave = false;
            FileUtils.deleteDir(new File(oneKeyTestBean.getResultPath() + File.separator + "log" + File.separator));
            FileUtils.saveFile(mGisActivity1, stringBuffer.toString(), fileAbsolutePathName, false);
        }

    }

    private void showWaitingDialog() {
    /* 等待Dialog具有屏蔽其他控件的交互能力
     * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
     * 下载等事件完成后，主动调用函数关闭该Dialog
     */
        final ProgressDialog waitingDialog =
                new ProgressDialog(getActivity());
        waitingDialog.setTitle("正在退出...");
        waitingDialog.setMessage("等待中...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitingDialog.dismiss();
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
        String fileAbsolutePathName = oneKeyTestBean.getResultPath() + File.separator + "log" + File.separator + testResult.getTestType() + ".log";
        //        Log.d(TAG, "saveTestData: " + fileAbsolutePathName);
        if (isFirstSave) {
            FileUtils.saveFile(mGisActivity1, stringBuffer.toString(), fileAbsolutePathName, true);
        } else {
            isFirstSave = true;
            FileUtils.deleteDir(new File(oneKeyTestBean.getResultPath() + File.separator + "log" + File.separator));
            FileUtils.saveFile(mGisActivity1, stringBuffer.toString(), fileAbsolutePathName, false);
        }
    }

    /**
     * 处理显示的标题
     *
     * @param testResult   结果数据
     * @param testTypeIdle 测试类型
     */
    private void refreshDataShowTitle(TestResult testResult, String testTypeIdle) {
        StringBuffer stringBuffer;
        switch (testResult.getTestTask().getTestmode()) {
            case Const.TestManage.TEST_MODE_COUNT:
                stringBuffer = new StringBuffer();
                stringBuffer.append(testTypeIdle);
                stringBuffer.append(testResult.getCurrentCount());
                stringBuffer.append("/");
                stringBuffer.append(testResult.getTestTask().getTestcount());
                mTv_0.setText(stringBuffer);
                break;
            case Const.TestManage.TEST_MODE_TIME_COUNT:
                stringBuffer = new StringBuffer();
                stringBuffer.append(testTypeIdle);
                stringBuffer.append(testResult.getCurrentCount());
                stringBuffer.append("/");
                stringBuffer.append(testResult.getTestTask().getTestcount());
                stringBuffer.append("-");
                stringBuffer.append(testResult.getCurrentTime());
                stringBuffer.append("s");
                mTv_0.setText(testTypeIdle + testResult.getCurrentCount() + "/" + testResult.getTestTask().getTestcount() + "-" + testResult.getCurrentTime() + "s");
                break;
            case Const.TestManage.TEST_MODE_TIME:
                stringBuffer = new StringBuffer();
                stringBuffer.append(testTypeIdle);
                stringBuffer.append(testResult.getCurrentTime());
                stringBuffer.append("s");
                mTv_0.setText(testTypeIdle + testResult.getCurrentTime() + "s");
                break;
        }
    }

    /**
     * 刷新信令界面
     *
     * @param testResult 数据结果
     */
    private void refreshDataSignal(TestResult testResult) {
        mTv_cellName.setText("小区名 :  " + testResult.getCellName());
        //        mTv_cellName.setText("小区名 :  " );
        mTv_enb.setText("ENB :  " + testResult.getEnb());
        //        mTv_enb.setText("ENB :  " );
        mTv_ci.setText("ECI :  " + testResult.getCi());
        mTv_pci.setText("PCI :  " + testResult.getPci());
        //        mTv_earfcn.setText("EARFCN :  " + testResult.getEarfcn());
        mTv_rsrp.setText("RSRP :  " + testResult.getRsrp());
        mTv_sinr.setText("SINR :  " + testResult.getSinr());
        String speedDL = testResult.getSpeedDL();
        String speedUL = testResult.getSpeedUL();
        if (!speedDL.equals("")) {
            mTv_speedDL.setText("速率DL :  " + speedDL);
        }
        if (!speedUL.equals("")) {
            mTv_speedUL.setText("速率UL :  " + speedUL);
        }
        mTv_lng.setText("经度 :  " + testResult.getLng());
        mTv_lat.setText("维度 :  " + testResult.getLat());
    }

    public void initeFragment(Activity gisActivity) {
        mGisActivity1 = gisActivity;


        //初始化布局
        mFragment_test_info = LayoutInflater.from(gisActivity).inflate(R.layout.fragment_test_info, null, true);
        init(mFragment_test_info);
        //基本测试,业务测试
        mTv_testInfo = (TextView) mFragment_test_info.findViewById(R.id.tv_testInfo);
        mTv_baseInfo = (TextView) mFragment_test_info.findViewById(R.id.tv_workInfo);
        mTv_testInfo.setOnClickListener(this);
        mTv_baseInfo.setOnClickListener(this);

        //初始化布局
        mFragment_cqt_base_info = LayoutInflater.from(gisActivity).inflate(R.layout.fragment_cqt_base_info, null, true);
        //小区名
        mTv_cellName = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_cellName);
        //ENB
        mTv_enb = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_enb);
        //CI
        mTv_ci = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_ci);
        //PCI
        mTv_pci = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_pci);
        //EARFCN
        //        mTv_earfcn = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_earfcn);
        //RSRP
        mTv_rsrp = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_rsrp);
        //SINR
        mTv_sinr = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_sinr);
        //速率DL
        mTv_speedDL = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_speedDL);
        //速率UL
        mTv_speedUL = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_speedUL);
        //经度
        mTv_lng = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_lng);
        //维度
        mTv_lat = (TextView) mFragment_cqt_base_info.findViewById(R.id.tv_lat);
        mFragment_cqt_work_info = LayoutInflater.from(gisActivity).inflate(R.layout.fragment_cqt_work_info, null, false);
        mTv_0 = (TextView) mFragment_cqt_work_info.findViewById(R.id.tv_0);
        mTv_1 = (TextView) mFragment_cqt_work_info.findViewById(R.id.tv_1);
        mTv_2 = (TextView) mFragment_cqt_work_info.findViewById(R.id.tv_2);
        mTv_3 = (TextView) mFragment_cqt_work_info.findViewById(R.id.tv_3);
        mTv_4 = (TextView) mFragment_cqt_work_info.findViewById(R.id.tv_4);
        mTv_5 = (TextView) mFragment_cqt_work_info.findViewById(R.id.tv_5);
        mTv_6 = (TextView) mFragment_cqt_work_info.findViewById(R.id.tv_6);
        mTestInfoFragment = new TestInfoFragment();
        mWorkInfoFragment = new TestInfoFragment();
        mTestInfoFragment.init(mFragment_cqt_base_info);
        mWorkInfoFragment.init(mFragment_cqt_work_info);


    }

    private void initLsitener() {
        //创建一个监听任务，用于获取信令
        mTelephonyManager = (TelephonyManager) (BaseApplication.getAppContext().getSystemService(BaseApplication.getAppContext().TELEPHONY_SERVICE));
        mCellPhoneStateListener = new CellPhoneStateListener(mTelephonyManager);

        mTelephonyManager.listen(mCellPhoneStateListener,
                PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR |
                        PhoneStateListener.LISTEN_CALL_STATE |
                        PhoneStateListener.LISTEN_CELL_LOCATION | PhoneStateListener.LISTEN_CELL_INFO | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        initBaiDuService();
    }


    private void initBaiDuService() {
        Intent intent = new Intent(getActivity(), BaiduMapService.class);

        conn = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

        };
        getActivity().bindService(intent, conn, BIND_AUTO_CREATE);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //初始化fragment布局
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_testBody, mTestInfoFragment, "mTestInfoFragment");
        fragmentTransaction.add(R.id.fl_testBody, mWorkInfoFragment, "mWorkInfoFragment");
        fragmentTransaction.hide(mWorkInfoFragment).commit();
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            //业务测试
            case R.id.tv_testInfo:
                //切换背景色和文字颜色
                mTv_testInfo.setBackgroundColor(getResources().getColor(R.color.cqt_button_background_blue));
                mTv_baseInfo.setBackgroundColor(getResources().getColor(R.color.cqt_button_background_white));
                mTv_testInfo.setTextColor(getResources().getColor(R.color.text_color_white));
                mTv_baseInfo.setTextColor(getResources().getColor(R.color.text_color_gray));
                //切换fragment
                fragmentTransaction.show(mTestInfoFragment);
                fragmentTransaction.hide(mWorkInfoFragment).commit();
                break;
            //基本测试
            case R.id.tv_workInfo:
                //切换背景色和文字颜色
                mTv_testInfo.setBackgroundColor(getResources().getColor(R.color.cqt_button_background_white));
                mTv_baseInfo.setBackgroundColor(getResources().getColor(R.color.cqt_button_background_blue));
                mTv_baseInfo.setTextColor(getResources().getColor(R.color.text_color_white));
                mTv_testInfo.setTextColor(getResources().getColor(R.color.text_color_gray));
                //切换fragment
                fragmentTransaction.hide(mTestInfoFragment);
                fragmentTransaction.show(mWorkInfoFragment).commit();
                break;


        }
    }

    public void testStart(OneKeyTestBean oneKeyTestBean) {
        initLsitener();
        mOneKeyTestBean = oneKeyTestBean;

        String assetsString = getAssetsString("json.txt");
        List<TestTask> list = new ArrayList<>(JSONArray.parseArray(assetsString, TestTask.class));
        //        List<TestTask> list = mOneKeyTestBean.getChangeModel();
        //判空处理
        if (list == null) {
            list = new ArrayList<>();
        }
        mTestControl = new TestControl();

        mTestControl.observable
                                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        String s = mTestControl.oneByOne(list, mGisActivity1, oneKeyTestBean);
        switch (s) {
            case Const.TestManage.ONE_BY_ONE_OK:
                showToast(mGisActivity1, "任务准备就绪...");
                //                        Toast.makeText(this, "任务准备就绪...", Toast.LENGTH_LONG).show();
                startSignaLling();//创建一个子线程根据设置的采集频率进行数据的采集信令数据

                break;
            case Const.TestManage.ONE_BY_ONE_BUSY:
                showToast(mGisActivity1, "线程池正在忙...");
                //                        Toast.makeText(this, "线程池正在忙...", Toast.LENGTH_LONG).show();
                break;
        }
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
                    mGisActivity1.getAssets().open(fileName), "UTF-8"));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void setResultLsitener(ResultLsitener resultLsitener) {
        mResultLsitener = resultLsitener;
    }

    public interface ResultLsitener {
        void onTestResult(TestResult testResult);

        void onSignalLingResult(TestResult testResult);
    }

    @Override
    protected void finalize() throws Throwable {
        Log.d("ssx", "finalize: 我已经被GC回收" + getClass().getName());
        super.finalize();
    }
}
