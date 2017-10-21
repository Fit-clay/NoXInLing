package com.beidian.beidiannonxinling.test;

import android.util.Log;

import com.beidian.beidiannonxinling.bean.FtpConfig;
import com.beidian.beidiannonxinling.bean.FtpInfo;
import com.beidian.beidiannonxinling.bean.TestResult;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.ThreadPool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

import static android.content.ContentValues.TAG;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/309:45
 * @描述: ftp下载的多线程控制
 */

public class Ftp {
    //用于记录ftp的每个线程的数组
    public final ArrayList<FtpInfo>            ftps             = new ArrayList<FtpInfo>();
    //远程文件大小
    private      long                          mFileSize        = 0;
    public       long                          mProgress        = 0;
    //是否终止任务
    private      boolean                       isStop           = false;
    //最大速率
    private      long                          maxSpend         = 0;
    //rx观察者用于回传到testmanage类测试结果
    public       ObservableEmitter<TestResult> en               = null;
    public       Observable<TestResult>        objectObservable = Observable.create(new ObservableOnSubscribe<TestResult>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<TestResult> e) throws Exception {
            en = e;
        }
    });

    public void test(final FtpConfig ftpconfig, final TestModel testManage, int threadNum, final String testType) {
        //最开始的时间
        long startIsStartTime = System.currentTimeMillis();
        //当时下载的时候就从服务器获取总文件大小
        if (testType == Const.TestManage.TEST_TYPE_FTP_DOWNLOAD) {
            DownLoad ftpClient = new DownLoad(ftpconfig);
            try {
                mFileSize = ftpClient.getFileSize(ftpconfig.getRemotePath() + ftpconfig.getRemoteFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //当时上传的时候就从本地获取文件大小
        if (testType == Const.TestManage.TEST_TYPE_FTP_UP) {
            File file = new File(Const.Ftp.FTP_TEPY_UP_FILE_PATH);
            if (file.length() == 0) {
                try {
                    createFixLengthFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            file = new File(Const.Ftp.FTP_TEPY_UP_FILE_PATH);
            mFileSize = file.length();
            Log.d(TAG, "FtpTestThreads: FTP文件大小"+mFileSize);
        }
        //远程文件大小不为0
        if (mFileSize == 0) {
            Log.d(TAG, "FtpTestThreads: FTP文件不存在");
            return;
        }
        //创建一个线程池
        ThreadPool threadPool = new ThreadPool(threadNum);
        //创建一个数组用于记录ftptest任务

        final int ThreadNum = threadNum;
        //计算每个线程下载量
        final long l = mFileSize / ThreadNum;
        for (int i = 0; i < ThreadNum; i++) {
            // 下载
            final int finalI = i;

            threadPool.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    //下载文件
                    if (testType == Const.TestManage.TEST_TYPE_FTP_DOWNLOAD) {
                        DownLoad ftp = new DownLoad(ftpconfig);
                        ftps.add(ftp);
                        try {
                            if (ftp.connect()) {
                                //                            Log.d(TAG, "run: 线程开始--"+finalI);
                                ftp.download(l * finalI, l * finalI + l);
                                //                            Log.d(TAG, "run: 线程结束--"+finalI);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //上传文件
                    if (testType == Const.TestManage.TEST_TYPE_FTP_UP) {

                        Up ftp = new Up(ftpconfig);
                        ftps.add(ftp);
                        try {
                            ftp.uploadFile(Const.Ftp
                                    .FTP_TEPY_UP_FILE_PATH, l * finalI, l * finalI + l);
                        } catch (IOException e) {
                            e.printStackTrace();
                            //FTP链接异常测试终止
                        }
                    }

                }
            });
        }

        //上一次的已经上传的数据大小
        long lastFileSize = 0;
        //上一次的时间
        long lastTime = System.currentTimeMillis();
        //上一次的速率
        long lastSpeed = 0;
        //此死循环是为了阻塞线程,其次是为了统计下载量下载进度
        while (true) {
            long i = 0;
            for (FtpInfo ftp : ftps) {
                if (ftp!=null) {
                    ftp.isStop = isStop;
                    i += ftp.fileSize;
                }

            }
            long endTime = System.currentTimeMillis();
            //计算下载的进度
            mProgress = i * 100 / mFileSize;
            //把测试数据返回
            TestResult testResult = new TestResult();
            testResult.setResultType(Const.TestManage.RESULT_TYPE_TEST);
            testResult.setProgress(mProgress>100?100:mProgress);
            testResult.setOverfileSize(i>mFileSize?mFileSize:i);
            testResult.setFileSize(mFileSize);
            testResult.setGlobalTestTime((int) testManage.globalTestTime);

            long la = (endTime - lastTime) != 0 ? endTime - lastTime : 1000;
            testResult.setSpeed((i - lastFileSize) / la * 1000 / 1000);
            if (lastSpeed > maxSpend) {
                maxSpend = lastSpeed;
            }
            testResult.setMaxSpeed(maxSpend);
            long lv = (endTime - startIsStartTime)==0?1:endTime - startIsStartTime;
            testResult.setAvgSpeed(i / lv * 1000 / 1024);
//            Log.d(TAG, "onNext: " + testResult.toString() + "`========" + testManage.isStop);
            en.onNext(testResult);

            lastFileSize = i;
            lastSpeed = testResult.getSpeed();
            lastTime = System.currentTimeMillis();
            //下面我知道很烦为了安全,多加点判断
            if (isStop(testManage, threadPool, i))
                return;
            try {
                //此时间和testmanager同步
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //当线程池的任务执行完就终止
            if (isStop(testManage, threadPool, i))
                return;

        }
    }

    private void createFixLengthFile() throws IOException {
        File file = new File(Const.Ftp.FTP_TEPY_UP_FILE_PATH);
        switch (file.getName()) {
            case "1G":
                FileUtils.createFixLengthFile(Const.Ftp.FTP_TEPY_UP_FILE_PATH, 1024*1024*1024);
                break;
            case "10M":
                FileUtils.createFixLengthFile(Const.Ftp.FTP_TEPY_UP_FILE_PATH, 1024*1024*10);
                break;
            case "1M":
                FileUtils.createFixLengthFile(Const.Ftp.FTP_TEPY_UP_FILE_PATH, 1024*1024*1);
                break;
            case "2M":
                FileUtils.createFixLengthFile(Const.Ftp.FTP_TEPY_UP_FILE_PATH, 1024*1024*2);
                break;
            case "100M":
                FileUtils.createFixLengthFile(Const.Ftp.FTP_TEPY_UP_FILE_PATH, 1024*1024*100);
                break;

        }
        try {
//            Log.d(TAG, "test: " + Const.Ftp.FTP_TEPY_UP_FILE_PATH);
            FileUtils.createFixLengthFile(Const.Ftp.FTP_TEPY_UP_FILE_PATH, 107374182);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isStop(TestModel testManage, ThreadPool threadPool, long i) {
        //当线程池的任务执行完就终止
        if (threadPool.getActiveCount() == 0) {
            for (FtpInfo ftp : ftps) {
                ftp.stop();
            }
            return true;
        }
        //当下载量大于文件实际大小就终止
        if (mFileSize - i <= 0) {
            for (FtpInfo ftp : ftps) {
                ftp.stop();
            }
            return true;
        }
        //当整个任务结束的时候终止
        if (testManage.isStop) {
            for (FtpInfo ftp : ftps) {
                if (ftp!=null) {
                ftp.stop();
                }
            }
            return true;
        }

        return false;
    }

    public void stop() {
        for (FtpInfo ftp : ftps) {
            ftp.stop();
        }
        this.isStop = true;
    }

}
