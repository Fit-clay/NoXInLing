package com.beidian.beidiannonxinling.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/6/1223:29
 * @Package com.example.li.weichat.net.bither.util
 * @描述: 线程池
 * ------------------------------------------
 */

public class ThreadPool {


    private ThreadPoolExecutor mSThreadPoolExecutor;

    public ThreadPool(int corePoolSize) {
        mSThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize
                , 2 * Runtime.getRuntime().availableProcessors() + 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 子线程执行任务
     *
     * @param runnable 任务
     */
    public void runOnSubThread(Runnable runnable) {
        if (mSThreadPoolExecutor == null) {
            mSThreadPoolExecutor = new ThreadPoolExecutor(1
                    , 2 * Runtime.getRuntime().availableProcessors() + 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        }
        mSThreadPoolExecutor.execute(runnable);

    }

    /**
     * 主线程执行任务
     *
     * @param runnable 任务
     */
    static Handler sHandler = new Handler(Looper.getMainLooper());
    public static void runOnMainThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    /**
     * 监控线程池的任务,注意null
     *
     * @return 返回监控的信息
     */
    public String getPoolSize() {
        return "* getLargestPoolSize()*" + mSThreadPoolExecutor.getLargestPoolSize() + "* getPoolSize()*" + mSThreadPoolExecutor.getPoolSize() + "* getCorePoolSize()*" + mSThreadPoolExecutor.getCorePoolSize() + "* getMaximumPoolSize()*" + mSThreadPoolExecutor.getMaximumPoolSize() + "* getCompletedTaskCount()*" + mSThreadPoolExecutor.getCompletedTaskCount() + "* getActiveCount()*" + mSThreadPoolExecutor.getActiveCount();
    }

    /**
     * @return 返回
     */
    public int getActiveCount() {

        return mSThreadPoolExecutor == null ? 0 : mSThreadPoolExecutor.getActiveCount();
    }

    /**
     * 立即终止线程池
     */
    public void shutdownNow() {
        if (mSThreadPoolExecutor == null) {
            return;
        }
        mSThreadPoolExecutor.shutdownNow();
        mSThreadPoolExecutor = null;

    }
}
