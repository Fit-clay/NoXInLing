package com.beidian.beidiannonxinling.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/6/1223:29
 * @Package
 * @描述: 线程池
 * ------------------------------------------
 */

public class ThreadPoolUtil {
    private static ThreadPoolExecutor sThreadPoolExecutor = new ThreadPoolExecutor(1
            , 2 * Runtime.getRuntime().availableProcessors() + 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private ThreadPoolUtil() {

    }

    public static void runOnSubThread(Runnable runnable) {
        if (sThreadPoolExecutor == null) {
            sThreadPoolExecutor = new ThreadPoolExecutor(1
                    , 2 * Runtime.getRuntime().availableProcessors() + 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        }
        sThreadPoolExecutor.execute(runnable);

    }

    /**
     * 监控线程池的任务
     *
     * @return 返回监控的信息
     */
    public static String getPoolSize() {
        return "* getLargestPoolSize()*" + sThreadPoolExecutor.getLargestPoolSize() + "* getPoolSize()*" + sThreadPoolExecutor.getPoolSize() + "* getCorePoolSize()*" + sThreadPoolExecutor.getCorePoolSize() + "* getMaximumPoolSize()*" + sThreadPoolExecutor.getMaximumPoolSize() + "* getCompletedTaskCount()*" + sThreadPoolExecutor.getCompletedTaskCount() + "* getActiveCount()*" + sThreadPoolExecutor.getActiveCount();
    }

    /**
     * 获取线程池的任务数量
     *
     * @return 返回
     */
    public static int getActiveCount() {

        return sThreadPoolExecutor == null ? 0 : sThreadPoolExecutor.getActiveCount();
    }

    /**
     * 立即终止线程池
     */
    public static void shutdownNow() {
        if (sThreadPoolExecutor == null) {
            return;
        }
        sThreadPoolExecutor.shutdownNow();
        sThreadPoolExecutor = null;

    }
}
