package com.beidian.beidiannonxinling.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;

import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.bean.FtpConfig;
import com.beidian.beidiannonxinling.bean.HttpResult;
import com.beidian.beidiannonxinling.bean.OneKeyTestBean;
import com.beidian.beidiannonxinling.bean.PingResul;
import com.beidian.beidiannonxinling.bean.TestResult;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.ObserverOnNext;
import com.beidian.beidiannonxinling.util.ThreadPool;

import java.io.File;
import java.io.IOException;

import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

import static android.content.ContentValues.TAG;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/211:33
 * @描述: 测试控制model
 */

public class TestModel {
    private int mQa = 0;
    private int mDb = 0;
    private final ObservableEmitter en;
    private       TestTask          mTestTask;//任务信息实例
    private       Activity          mActivity;//上下文
    private       TestControl       mTestControl;//测试控制类
    private       int               mTestinterva;//获取每个任务执行完成后间隔时间
    public  boolean isStop         = false;
    public  long    globalTestTime = 0;//全局时间,从第一个任务开始的时间
    private long    currentTime    = 0;//当前任务执行的总时间
    private long    currentCount   = 0;//当前任务执行的总次数

    private int                    mTestTaskTime; //模板的测试时间
    private int                    mTestTaskCount;//模板的测试次数
    private int                    mTestTaskRetention;//模板的保持时间
    private TestTask.TargetUrlBean mTargetUrl;//获取测试url


    private int  successSum  = 0;// 成功次数、
    private int  failSum     = 0;// 失败次数、
    private long successRate = 0;// 成功率、

    private float speedSum    = 0;// 速率总和
    private float costTimeSum = 0;// 页面下载时间总和
    //    private       NonSignaLlingTools                                 mNonSignaLlingTools;
    CellinfoListBean mCurrentCell;


    public TestModel(TestTask testTask, Activity activity, final TestControl testControl, OneKeyTestBean oneKeyTestBean) {
        this.mTestTask = testTask;
        this.mActivity = activity;
        //获取上一级界面传递过来的数据//当前被选择的扇区
        //        Intent intent = mActivity.getIntent();
        //        Bundle extras = intent.getExtras();
        //        if (extras == null) {
        //            mCurrentCell = new CellinfoListBean();
        //
        //        } else {
        //        Object o = extras.get(Const.IntentTransfer.ONE_KEY_TEST);
        //            mCurrentCell = ((OneKeyTestBean) o).getCurrentCell();
        //        }
        if (oneKeyTestBean != null) {
            mCurrentCell = oneKeyTestBean.getCurrentCell();
        } else {
            mCurrentCell = new CellinfoListBean();
        }
        this.mTestControl = testControl;
        this.en = mTestControl.en;
        //获取时间间隔
        mTestinterva = Integer.parseInt(testTask.getTestinterval() == null || testTask.getTestinterval().equals("") ? "0" : testTask.getTestinterval());
        //获取测试url
        mTargetUrl = testTask.getTargeturl();
        //获取模板的测试时间
        mTestTaskTime = Integer.parseInt(testTask.getTesttime() == null || testTask.getTesttime().equals("") ? "0" : testTask.getTesttime());
        //获取模板的测试次数
        mTestTaskCount = Integer.parseInt(testTask.getTestcount() == null || testTask.getTestcount().equals("") ? "0" : testTask.getTestcount());
        //获取模板的保持时间
        mTestTaskRetention = Integer.parseInt(testTask.getRetentiontime() == null || testTask.getRetentiontime().equals("") ? "0" : testTask.getRetentiontime());

        //        //初始化信令工具类,并设置一个监听器
        //        mNonSignaLlingTools = new NonSignaLlingTools(activity.getApplicationContext());


    }


    public void test() throws IOException {
        if (mTestTask.getTesttype().equals(Const.TestManage.TEST_TYPE_CALL_VOLTEZ)) {
            isOpenVolte();
        }


        Log.d(TAG, "AlertDialog: 请你你需要手动打开Votle功能");

        //给当前任务添加一个时间监听器,用于同步时间
        TestControl.TimerListener timerListener = mTestControl.new TimerListener() {
            @Override
            public void onTimer(long time) {
                globalTestTime = time;
                currentTime++;
                //判断是否达到测试结束的条件
                isStop();
                //定时返回测试的结果数据
                TestResult testResult = new TestResult();
                initTestSingLingResult(testResult);
                en.onNext(testResult);
            }
        };
        mTestControl.setTimerListener(timerListener);
        currentTime = 0;
        //while控制测试次数和时间
        while (true) {
            //判断是否终止任务,主要控制
            if (isStop()) {
                break;
            }
            currentCount++;
            run();
            try {
                Thread.sleep(mTestinterva * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        mTestControl.delTimerListener(timerListener);
    }

    private void isOpenVolte() {
        final boolean[] flage = {false};
        final DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ThreadPool.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        flage[0] = false;
                    }
                });
            }
        };
        flage[0] = true;
        ThreadPool.runOnMainThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog dialog = new AlertDialog.Builder(mActivity)
                        .setMessage("此项测试需要手动检查是否打开Votle功能!确认打开后点击继续")
                        .setPositiveButton("继续", dialogListener)
                        .create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
        while (flage[0]) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void run() throws IOException {
        TestResult testResult;

        switch (mTestTask.getTesttype()) {
            //如果是WAIT IDLE:全都规划到计时的方式测试,间隔为1秒
            case Const.TestManage.TEST_TYPE_WAIT:
                mTestinterva = 1;
                testResult = new TestResult();
                //初始化结果数据
                initTestResult(testResult);
                en.onNext(testResult);
                break;
            case Const.TestManage.TEST_TYPE_IDLE:
                mTestinterva = 1;
                testResult = new TestResult();
                //初始化结果数据
                initTestResult(testResult);
                en.onNext(testResult);
                break;
            case Const.TestManage.TEST_TYPE_PING:
                testResult = new TestResult();
                PingTest pingTest = new PingTest();
                PingResul ping = pingTest.ping(mTargetUrl.getUrl().replace("htttp://", ""), "1", mActivity);
                //初始化结果数据,不要问为什么从HttpResult里面取数据,时间不够,没时间整理这些老代码
                //                Log.d(TAG, "run: "+ping.toString());

                if (pingTest.isSuccess.equals("成功")) {
                    testResult.setDelay(Integer.parseInt(ping.getDelay()));
                    successSum++;
                } else {
                    testResult.setDelay(0);
                    failSum++;
                }
                Log.d(TAG, "run: currentCount" + currentCount + " successSum:" + successSum);
                long la = currentCount == 0 ? 0 : successSum * 100 / currentCount;
                testResult.setSuccessRate(la);
                //初始化结果数据
                initTestResult(testResult);
                en.onNext(testResult);
                break;
            case Const.TestManage.TEST_TYPE_HTTP:
                testResult = new TestResult();
                HttpTest httpTest = new HttpTest();
                HttpResult httptestresult = new HttpResult();
                HttpResult httpTestResult = httpTest.doHttpTest(mActivity, httptestresult, mTargetUrl.getUrl().replace("htttp://", ""));
                //初始化结果数据,不要问为什么从HttpResult里面取数据,时间不够,没时间整理这些老代码
                speedSum += httptestresult.getSpeed();
                costTimeSum += httptestresult.getCostTime();
                testResult.setAvgSpeed((long) (speedSum / costTimeSum));
                testResult.setSpeed((long) httptestresult.getSpeed() / 1000);
                testResult.setCostTime(httptestresult.getCostTime());
                initTestResult(testResult);
                en.onNext(testResult);
                break;
            case Const.TestManage.TEST_TYPE_CALL_VOLTEZ:
            case Const.TestManage.TEST_TYPE_CALL_CSFBZ:
                TestCallCsfbZ testCallCsfbZ = new TestCallCsfbZ(mActivity, mTestTask, this);
                //关联rxjava,用于接收时时的保留时间
                Observer<? super TestResult> observerOnNext1 = new ObserverOnNext<TestResult>() {
                    @Override
                    public void onNext(@NonNull TestResult testResult) {
                        //初始化结果数据
                        initTestResult(testResult);
                        //设置结果数据
                        testResult.setSuccess(successSum);
                        testResult.setFail(failSum);
                        //为什么成功次数要+1.注意这个回调的和下面的次序

                        //                        Log.d(TAG, "currentCount:---------- " + currentCount + "   successSum:" + successSum + "  l:" + l + "  failSum:" + failSum);
                        testResult.setSuccessRate(successRate);
                        en.onNext(testResult);
                    }
                };
                testCallCsfbZ.objectObservable.subscribe(observerOnNext1);
                boolean call = testCallCsfbZ.call();
                if (call) {
                    successSum++;
                } else {
                    failSum++;
                }
                successRate = currentCount == 0 ? 0 : successSum * 100 / currentCount;
                break;
            case Const.TestManage.TEST_TYPE_FTP_DOWNLOAD:
            case Const.TestManage.TEST_TYPE_FTP_UP:
                //// TODO: 2017/9/2 正式发布版本记得修改路径位置,文件的
                //本地文件保存路径
                String strLocal = Environment.getExternalStorageDirectory().toString() + File.separator + Const.SaveFile.ROOT_FILE_DIR + File.separator + mTestTask.getFilename();
                FtpConfig ftpconfig = new FtpConfig(mTestTask.getServeraddress(), Integer.parseInt(mTestTask.getPort()), mTestTask.getUsername(), mTestTask.getPassword(), mTestTask.getFilename(), strLocal);
                //获取模板的测试x线程数
                int testThreadcount = Integer.parseInt(mTestTask.getThreadcount());
                //创建一个多线程下载的对象
                Ftp ftp = new Ftp();
                //关联rxjava,用于接收时时的进度信息
                Observer<? super TestResult> observerOnNext = new ObserverOnNext<TestResult>() {
                    @Override
                    public void onNext(@NonNull TestResult testResult) {
                        //                        Log.d(TAG, "onNext-----------: " + testResult.toString());
                        initTestResult(testResult);
                        Log.d(TAG, "CurrentTime: " + testResult.getCurrentTime() + "---" + testResult.getCurrentCount());
                        en.onNext(testResult);
                    }
                };
                ftp.objectObservable.subscribe(observerOnNext);
                ftp.test(ftpconfig, this, testThreadcount, Const.TestManage.TEST_TYPE_FTP_UP);
                break;
            case Const.TestManage.TEST_TYPE_ATTACH:
                TestAttach testAttach = new TestAttach(mActivity);
                boolean attach = testAttach.attach();
                //设置结果数据testAttach.isSuccess
                if (attach) {
                    successSum++;
                } else {
                    failSum++;
                }
                testResult = new TestResult();
                initTestResult(testResult);
                testResult.setSuccess(successSum);
                testResult.setFail(failSum);

                long l = currentCount == 0 ? 0 : successSum * 100 / currentCount;
                //                Log.d(TAG, "currentCount:---------- " + currentCount + "   successSum:" + successSum + "  l:" + l + "  failSum:" + failSum);
                testResult.setSuccessRate(((int) l));
                testResult.setFailOrSuccess(attach);
                en.onNext(testResult);
                break;

        }
    }

    /**
     * 初始化返回的结果数据
     *
     * @param testResult
     */
    private void initTestResult(TestResult testResult) {
        testResult.setGlobalTestTime((int) globalTestTime);
        testResult.setCurrentTime((int) currentTime);
        testResult.setCurrentCount(currentCount);
        testResult.setTestType(mTestTask.getTesttype());
        testResult.setResultType(Const.TestManage.RESULT_TYPE_TEST);
        testResult.setTestTask(mTestTask);
    }

    private void initTestSingLingResult(TestResult testResult) {
        testResult.setGlobalTestTime((int) globalTestTime);
        testResult.setCurrentTime((int) currentTime);
        testResult.setCurrentCount(currentCount);
        testResult.setTestType(mTestTask.getTesttype());
        testResult.setResultType(Const.TestManage.RESULT_TYPE_SIGNALLING);
        testResult.setTestTask(mTestTask);
        testResult.setCellName(mCurrentCell.getCellname() + "");


    }

    /**
     * 判斷是否到达任务时间和次数
     *
     * @return true就是达到次数或者时间
     */
    public boolean isStop() {
        //        Log.d(TAG, "currentTime: "+currentTime+"  mTestTask.getTestmode=="+mTestTask.getTestmode()+"  mTestTask.getTesttype=="+mTestTask.getTesttype());
        //当标记为停止的时候停止
        if (isStop) {
            return true;
        }

        switch (mTestTask.getTestmode() == null ? "" : mTestTask.getTestmode()) {

            //测试类型为限时计次的时候
            case Const.TestManage.TEST_MODE_TIME_COUNT:
                if (currentTime >= mTestTaskTime || currentCount >= mTestTaskCount) {
                    isStop = true;
                    Log.d(TAG, "isStop: 任务停止====" + mTestTask.getTesttype());
                    return true;
                }
                break;
            //测试类型为时间的时候
            case Const.TestManage.TEST_MODE_TIME:
                if (currentTime >= mTestTaskTime) {
                    isStop = true;
                    return true;
                }
                break;
            //测试类型为次数的时候
            case Const.TestManage.TEST_MODE_COUNT:
                if (currentCount >= mTestTaskCount) {
                    isStop = true;
                    return true;
                }
                break;
        }
        //idle和wait是例外情况,他们是每秒一次的方式,所以只能用时间的方式判断
        switch (mTestTask.getTesttype()) {
            case Const.TestManage.TEST_TYPE_WAIT:
                if (currentTime >= mTestTaskTime) {
                    isStop = true;
                    return true;
                }
                break;
            case Const.TestManage.TEST_TYPE_IDLE:
                if (currentTime >= mTestTaskTime) {
                    isStop = true;
                    return true;
                }
                break;
        }
        return false;
    }

    //    /**
    //     * 获取信令的方法
    //     */
    //    public void getNonSignaLlingInfo() {
    //
    //        mNonSignaLlingTools.startSignStrenthChangeListener();
    //    }
}
