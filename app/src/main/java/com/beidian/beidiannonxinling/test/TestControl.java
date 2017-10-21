package com.beidian.beidiannonxinling.test;

import android.app.Activity;

import com.beidian.beidiannonxinling.bean.OneKeyTestBean;
import com.beidian.beidiannonxinling.bean.TestResult;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.ThreadPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/211:26
 * @描述: ------------------------------------------
 */

public class TestControl {
    protected ObservableEmitter en;
    public  Observable<TestResult>               observable         = Observable.create(new ObservableOnSubscribe<TestResult>() {
        @Override
        public void subscribe(ObservableEmitter<TestResult> e) throws Exception {
            en = e;
        }
    });
    //定时器,用于矫正时间
    private long                                 timer              = 0;
    private boolean                              isStop             = false;
    private ArrayList<TestControl.TimerListener> timerListenerArray = new ArrayList<>();
    private ThreadPool                           threadPool         = new ThreadPool(1);//创建一个线程池
    public  ArrayList<TestModel>                 mTestModels        = new ArrayList<>();//创建一个任务集合,用于记录任务

    /**
     * @param list 测试任务
     * @return 1.ok 任务加入队列完成
     * 2.busy 线程池中有任务忙
     */
    public String oneByOne(List<TestTask> list, final Activity activity,OneKeyTestBean oneKeyTestBean) {
        if (threadPool.getActiveCount() != 0) {
            return Const.TestManage.ONE_BY_ONE_BUSY;
        }
        //遍历任务集合,把每个任务放入线程池队列执行
        final TestControl testControl = this;
        for (final TestTask testTask : list) {
            final TestModel testModel = new TestModel(testTask, activity, testControl,oneKeyTestBean);
            threadPool.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        testModel.test();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            mTestModels.add(testModel);
        }
        //创建一个定时器
        creatTimer();
        return Const.TestManage.ONE_BY_ONE_OK;
    }

    /**
     * 定时器用于同步整个测试流程的时间
     */
    private void creatTimer() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (!isStop) {
                            //监控线程池,如果线程池已经空了,就初始化数据
                            if (threadPool.getActiveCount() == 0) {
                                stop();
                                return;
                            }
                            //返回监听的时间
                            for (TimerListener timerListener : timerListenerArray) {
                                timerListener.onTimer(timer);
                            }
                            timer++;
                            try {
                                Thread.sleep(Const.TestManage.TEST_LOG_SAVE_TIME);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //测试完成,通知主界面完成测试
                        en.onComplete();
                        timerListenerArray.clear();
                    }
                }
        ).start();
    }


    /**
     * 设置一个时间监听器,用于同步整个测试时间
     *
     * @param timerListener
     */
    public void setTimerListener(TimerListener timerListener) {
        timerListenerArray.add(timerListener);
    }

    /**
     * 删除监听器
     * @param timerList
     */
    public void delTimerListener(TestControl.TimerListener timerList) {
        timerListenerArray.remove(timerList);
    }


    /**
     * 设置一个时间监听器,用于同步整个测试时间
     */
    public abstract class TimerListener {
        public abstract void onTimer(long time);
    }


    /**
     * 結束任务,立即销毁线程池,但如果里面有sleep,wait,网络请求等耗时的操作,还是需要等到线程任务执行完成才能销毁,
     */
    public void stop() {
        en.onComplete();
        timerListenerArray.clear();
        //通知所有的任务停止
        for (TestModel testModel : mTestModels) {
            testModel.isStop = true;
        }
        mTestModels.clear();
        isStop = true;
        threadPool.shutdownNow();
    }
}
