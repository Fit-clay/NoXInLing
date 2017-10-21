package com.beidian.beidiannonxinling.test;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.beidian.beidiannonxinling.bean.TestResult;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.util.ObserverOnNext;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

import static android.content.ContentValues.TAG;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/216:01
 * @描述: ------------------------------------------
 */

public class TestCallCsfbZ {
    public boolean isSuccess;// 成功// 失敗
    public int retentionTime = 0;// 保持时间、
    private final VoiceTest mVoiceTest;
    private final Context   mActivity1;
    private final String    mCallphone1;
    private int     mCallStatus = 0;
    private boolean isOFFHOOK   = false;
    private final TestTask mTestTask1;
    private final int      mRetentiontime;
    private int     time   = 0;
    public  boolean isStop = false;
    private final TestModel                     mTestModel1;
    private       ObservableEmitter<TestResult> en;
    public Observable<TestResult> objectObservable = Observable.create(new ObservableOnSubscribe<TestResult>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<TestResult> e) throws Exception {
            en = e;
        }
    });

    /**
     * @param activity  上下文,
     * @param testTask  任務
     * @param testModel
     */
    public TestCallCsfbZ(Activity activity, TestTask testTask, TestModel testModel) {
        mVoiceTest = new VoiceTest();
        mTestModel1 = testModel;
        mActivity1 = activity;
        mCallphone1 = testTask.getCallphone();
        mRetentiontime = Integer.parseInt(testTask.getRetentiontime());
        mTestTask1 = testTask;
        PhoneCallReceiver.observable.subscribe(new ObserverOnNext<Integer>() {
            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext:电话状态被改变了" + integer);
                if (integer == TelephonyManager.CALL_STATE_RINGING) {
                    VoiceTest.answerCall();
                }
                mCallStatus = integer;
            }
        });
    }

    /**
     *
     * @return 返回是否呼叫成功
     */
    public boolean call() {
        //开始呼叫
        mVoiceTest.callPhone(mActivity1, mCallphone1);
        //当电话处于接通电话的时候开始初始化时间,控制isoffhook,
        hold();
        return isOFFHOOK;

    }

    //当达到保持时间的时候挂断电话
    //当挂断时候判断是否接通过,如果没有就失败+1
    //    if (isOFFHOOK) {
    //        testResult.setSuccess(testResult.getSuccess()+1);
    //    }
    //    if (!isOFFHOOK) {
    //        testResult.setFail(testResult.getFail()+1);
    //    }
    public void hold() {
        while (true) {
            //把测试数据返回 给TESTmodel
            TestResult testResult = new TestResult();
            testResult.setRetentionTime(time);


            en.onNext(testResult);
            if (isStop || mTestModel1.isStop) {
                retentionTime = time;
                break;
            }
            //当时间超过保留时间
            if (time >= mRetentiontime) {
                retentionTime = time;
                //停止呼叫
                mVoiceTest.stopCall((Activity) mActivity1);
                break;
            }
            //当电话处于接通电话的时候开始初始化时间,控制isoffhook,
            if (mCallStatus == TelephonyManager.CALL_STATE_OFFHOOK) {
                isOFFHOOK = true;
                if (!isOFFHOOK) {
                    time = 0;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time++;
        }
        if (isOFFHOOK) {
            isSuccess = true;
        }
        if (!isOFFHOOK) {
            isSuccess = false;
        }


    }
}
