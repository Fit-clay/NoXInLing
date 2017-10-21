package com.beidian.beidiannonxinling.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.OneKeyTestBean;
import com.beidian.beidiannonxinling.bean.TestResult;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.fragment.TestInfoFragment;
import com.beidian.beidiannonxinling.ui.model.CqtTestActivityModel;
import com.beidian.beidiannonxinling.ui.widget.BounceBallView;

public class CqtTestActivity extends BaseActivity implements View.OnClickListener {

    private TextView             mTv_testInfo;
    private TextView             mTv_baseInfo;
    private TestInfoFragment     mTestInfoFragment;
    private TestInfoFragment     mWorkInfoFragment;
    private View                 mFragment_cqt_base_info;
    private View                 mFragment_cqt_work_info;
    private TextView             mTv_cellName;
    private TextView             mTv_enb;
    private TextView             mTv_ci;
    private TextView             mTv_pci;
    private TextView         mTv_earfcn;
    private TextView         mTv_rsrp;
    private TextView             mTv_sinr;
    private TextView             mTv_speedDL;
    private TextView             mTv_speedUL;
    private TextView             mTv_lat;
    private TextView             mTv_lng;
    private TextView             mTv_1;
    private TextView             mTv_0;
    private TextView             mTv_2;
    private TextView             mTv_3;
    private TextView             mTv_4;
    private TextView             mTv_5;
    private TextView             mTv_6;
    private Button               mBt_stop;
    private CqtTestActivityModel mCqtTestActivityModel;
    private TextView             mTv_title;
    public  OneKeyTestBean       mOneKeyTestBean;//上一个页面传进来的数据,有订单号,任务号
    //小动画
    private BounceBallView       bbv1;


    protected void initData() {
        mBt_stop.setOnClickListener(this);
        mTv_testInfo.setOnClickListener(this);
        mTv_baseInfo.setOnClickListener(this);
        //初始化model
        mCqtTestActivityModel = new CqtTestActivityModel(this);
//        bbv1.config()
//                .ballCount(15)
//                .bounceCount(3)
//                .ballDelay(220)
//                .duration(3300)
//                .radius(15)
//                .isPhysicMode(true)
//                .isRamdomPath(true)
//                .isRandomColor(true)
//                .isRandomRadius(true)
//                .apply();
        bbv1.start();
        //开始测试
        mCqtTestActivityModel.testStart();
    }

    @Override
    protected void initListener() {

    }

    protected void initView() {

        setContentView(R.layout.activity_cqt_test);
        //获取上一级界面传递过来的数据
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mOneKeyTestBean = (OneKeyTestBean) extras.get(Const.IntentTransfer.ONE_KEY_TEST);

        //标题
        View inc_title = (View) findViewById(R.id.inc_title);
        //标题
        mTv_title = (TextView) (inc_title.findViewById(R.id.tv_title));
        mTv_title.setText("CQT定点测试");
        //返回箭头
        View ll_back = inc_title.findViewById(R.id.ll_back);
        ll_back.setVisibility(View.INVISIBLE);
        //stop按鈕
        mBt_stop = (Button) findViewById(R.id.bt_stop);
        //基本测试,业务测试
        mTv_testInfo = (TextView) findViewById(R.id.tv_testInfo);
        mTv_baseInfo = (TextView) findViewById(R.id.tv_workInfo);
        //初始化布局
        mFragment_cqt_base_info = LayoutInflater.from(this).inflate(R.layout.fragment_cqt_base_info, null, true);
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
        //小动画控件
        bbv1 = (BounceBallView) findViewById(R.id.bbv1);
        mFragment_cqt_work_info = LayoutInflater.from(this).inflate(R.layout.fragment_cqt_work_info, null, false);
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
        //初始化fragment布局
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_testBody, mTestInfoFragment, "mTestInfoFragment");
        fragmentTransaction.add(R.id.fl_testBody, mWorkInfoFragment, "mWorkInfoFragment");
        fragmentTransaction.hide(mWorkInfoFragment).commit();
    }

    @Override
    public void onClick(View v) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
            case R.id.bt_stop:
                closeActivity();
                break;


        }
    }


    //阻止back退出界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 刷新测试结果数据
     *
     * @param testResult
     */
    public void refreshData(TestResult testResult) {

        switch (testResult.getResultType()) {
            case Const.TestManage.RESULT_TYPE_SIGNALLING:
                refreshDataSignal(testResult);
                //保存数据
                mCqtTestActivityModel.saveSignalLingData(testResult, mOneKeyTestBean);
                break;
            case Const.TestManage.RESULT_TYPE_TEST:
                refreshDataTest(testResult);
                //保存数据
                mCqtTestActivityModel.saveTestData(testResult, mOneKeyTestBean);
                break;
        }
        switch (testResult.getTestType()) {
            case Const.TestManage.TEST_TYPE_IDLE:
//                testResult.getTestTask().setTestmode(Const.TestManage.TEST_MODE_TIME);//因为idle和wait没有测试模式,所以设置为时间类型
//                refreshDataShowTitle(testResult,"IDLE :  ");
                mTv_0.setText("IDLE");
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.INVISIBLE);
                mTv_3.setVisibility(View.INVISIBLE);
                mTv_4.setVisibility(View.INVISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_WAIT:
//                testResult.getTestTask().setTestmode(Const.TestManage.TEST_MODE_TIME);//因为idle和wait没有测试模式,所以设置为时间类型
//                refreshDataShowTitle(testResult,"WAIT :  ");
                mTv_0.setText("WAIT");
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.INVISIBLE);
                mTv_3.setVisibility(View.INVISIBLE);
                mTv_4.setVisibility(View.INVISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_PING:
                refreshDataShowTitle(testResult,"PING :  ");
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.VISIBLE);
                mTv_3.setVisibility(View.INVISIBLE);
                mTv_4.setVisibility(View.INVISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_HTTP:
                refreshDataShowTitle(testResult,"HTTP :  ");
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
                    refreshDataShowTitle(testResult,"CSFBZ :  ");
                }
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_CALL_VOLTEZ)) {
                    refreshDataShowTitle(testResult,"VOLTEZ :  ");
                }
                mTv_1.setVisibility(View.VISIBLE);
                mTv_2.setVisibility(View.VISIBLE);
                mTv_3.setVisibility(View.INVISIBLE);
                mTv_4.setVisibility(View.INVISIBLE);
                mTv_5.setVisibility(View.INVISIBLE);
                mTv_6.setVisibility(View.INVISIBLE);
                break;
            case Const.TestManage.TEST_TYPE_ATTACH:
                refreshDataShowTitle(testResult,"ATTACH :  ");
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
                    refreshDataShowTitle(testResult,"DOWNLOAD :  ");
                }
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_FTP_UP)) {
                    refreshDataShowTitle(testResult,"UP :  ");
                }
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
     * 处理显示的标题
     * @param testResult 结果数据
     * @param testTypeIdle 测试类型
     */
    private void refreshDataShowTitle(TestResult testResult, String testTypeIdle) {
        switch (testResult.getTestTask().getTestmode()) {
            case Const.TestManage.TEST_MODE_COUNT:
                mTv_0.setText(testTypeIdle+testResult.getCurrentCount()+"/"+testResult.getTestTask().getTestcount());
                break;
            case Const.TestManage.TEST_MODE_TIME_COUNT:
                mTv_0.setText(testTypeIdle+testResult.getCurrentCount()+"/"+testResult.getTestTask().getTestcount()+"-"+testResult.getCurrentTime()+"s");
                break;
            case Const.TestManage.TEST_MODE_TIME:
                mTv_0.setText(testTypeIdle+testResult.getCurrentTime()+"s");
                break;
        }
    }

    /**
     * 刷新测试界面
     *
     * @param testResult
     */
    private void refreshDataTest(TestResult testResult) {
        switch (testResult.getTestType()) {
            case Const.TestManage.TEST_TYPE_IDLE:
                mTv_0.setText("IDLE");
                mTv_1.setText("空闲时间 :  " + testResult.getCurrentTime() + "s");
                break;
            case Const.TestManage.TEST_TYPE_WAIT:
                mTv_0.setText("WAIT");
                mTv_1.setText("等待时间 :  " + testResult.getCurrentTime() + "s");
                break;
            case Const.TestManage.TEST_TYPE_PING:
                mTv_0.setText("PING");
                mTv_1.setText("时延 :  " + testResult.getDelay() + "ms");
                mTv_2.setText("成功率 :  " + testResult.getSuccessRate() + "%");
                break;
            case Const.TestManage.TEST_TYPE_HTTP:
                mTv_0.setText("HTTP");
                mTv_1.setText("页面下载时间 :  " + testResult.getCostTime() + "ms");
                mTv_2.setText("平均速率 :  " + testResult.getAvgSpeed() + "kb/s");
                break;
            case Const.TestManage.TEST_TYPE_CALL_CSFBZ:
            case Const.TestManage.TEST_TYPE_CALL_VOLTEZ:
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_CALL_CSFBZ)) {
                    mTv_0.setText("CSFBZ");
                }
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_CALL_VOLTEZ)) {
                    mTv_0.setText("VOLTEZ");
                }
                mTv_1.setText("保持时间 :  " + testResult.getRetentionTime() + "s");
                mTv_2.setText("成功率 :  " + testResult.getSuccessRate() + "%");
                break;
            case Const.TestManage.TEST_TYPE_ATTACH:
                mTv_0.setText("ATTACH");
                mTv_1.setText("尝试测试 :  " + testResult.getCurrentCount() + "次");
                mTv_2.setText("成功次数 :  " + testResult.getSuccess() + "次");
                mTv_3.setText("失败次数 :  " + testResult.getFail() + "次");
                mTv_4.setText("成功率 :  " + testResult.getSuccessRate() + "%");
                break;
            case Const.TestManage.TEST_TYPE_FTP_DOWNLOAD:
            case Const.TestManage.TEST_TYPE_FTP_UP:
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_FTP_DOWNLOAD)) {
                    mTv_0.setText("DOWNLOAD");
                    mTv_speedDL.setText("速率DL :  " +testResult.getAvgSpeed() + "kb/s");
                }
                if (testResult.getTestType().equals(Const.TestManage.TEST_TYPE_FTP_UP)) {
                    mTv_0.setText("UP");
                    mTv_speedUL.setText("速率UL :  " +testResult.getAvgSpeed() + "kb/s");
                }
                mTv_1.setText("文件大小 :  " + testResult.getFileSize() + "字节");
                mTv_2.setText("已传时间 :  " + testResult.getCurrentTime() + "s");
                mTv_3.setText("传输进程 :  " + testResult.getProgress() + "%");
                mTv_4.setText("已传数据 :  " + testResult.getOverfileSize() + "字节");
                mTv_5.setText("峰值速率 :  " + testResult.getMaxSpeed() + "kb/s");
                mTv_6.setText("平均速率 :  " + testResult.getAvgSpeed() + "kb/s");
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
        mTv_speedDL.setText("速率DL :  " + testResult.getSpeedDL());
        mTv_speedUL.setText("速率UL :  " + testResult.getSpeedUL());
        mTv_lng.setText("经度 :  " + testResult.getLng());
        mTv_lat.setText("维度 :  " + testResult.getLat());
    }


    public void closeActivity() {
        //销毁当前activity,并且传消息给上一级页面f
        mCqtTestActivityModel.stop();
        mCqtTestActivityModel.unRegisterPhoneState();
        //// TODO: 2017/9/14 有问题，不知道为什么
        //        mCqtTestActivityModel = null;
        Intent intent = new Intent();
        OneKeyTestBean oneKeyTestBean = new OneKeyTestBean();
        intent.putExtra(Const.IntentTransfer.ONE_KEY_TEST, oneKeyTestBean);
        setResult(Const.IntentTransfer.resultCode_CqtTestActivityActivity, intent);
        finish();
    }
}
