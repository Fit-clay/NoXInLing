package com.beidian.beidiannonxinling.util;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/2713:31
 * @描述: 用于崩溃日志收集，和app的重启（这个很重要)
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.ui.activity.LoginActivity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler crashHandler = new CrashHandler();

    private Context mContext;
    /**
     * 错误日志文件
     */
    private File logFile = new File(Environment.getExternalStorageDirectory(), "crashLog.trace");
    private Class<LoginActivity> mLoginActivityClass1;

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            synchronized (CrashHandler.class) {
                if (crashHandler == null) {
                    crashHandler = new CrashHandler();
                }
            }
        }
        return crashHandler;
    }

    /**
     * 崩溃处理的初始化
     * @param context  application应用本身
     * @param loginActivityClass 崩溃后需要重启的界面
     */
    public void init(Context context, Class<LoginActivity> loginActivityClass) {
        mContext = context;
        mLoginActivityClass1 = loginActivityClass;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置为线程默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当app发生崩溃的时候会回调此方法
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        // 打印异常信息
        Log.d("bug", "######################################异常信息start#####################################################" );

        ex.printStackTrace();
        Log.d("bug", "######################################异常信息end#####################################################" );
        // 我们没有处理异常 并且默认异常处理不为空 则交给系统处理
        if (!handlelException(ex) && mDefaultHandler != null) {
            // 系统处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                // 上传错误日志到服务器
                upLoadErrorFileToServer(logFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(mContext, mLoginActivityClass1);
            // 新开任务栈
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            Process.killProcess(Process.myPid());
//            // 杀死我们的进程
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//
//                @Override
//                public void run() {
//                    Process.killProcess(Process.myPid());
//                }
//            }, 2 * 1000);

        }
    }

    private boolean handlelException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                //Toast的初始化
                Toast toastStart = new Toast(mContext);
                //获取屏幕高度
                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                int height = wm.getDefaultDisplay().getHeight();
                //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
                toastStart.setGravity(Gravity.TOP, 0, height / 3);
                toastStart.setDuration(Toast.LENGTH_LONG);
                TextView textView = new TextView(mContext);
                textView.setBackgroundResource(R.drawable.shape_button);
                textView.setTextColor(Color.rgb(255, 255, 255));
                textView.setText("应用数据发生异常，3s后重启应用。。。。。");
                toastStart.setView(textView);
                toastStart.show();

                Looper.loop();
            }
        }.start();

        PrintWriter pw = null;
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            pw = new PrintWriter(logFile);
            // 收集手机及错误信息
            logFile = collectInfoToSDCard(pw, ex);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 上传错误日志到服务器
     *
     * @throws IOException
     */
    private void upLoadErrorFileToServer(File errorFile) {

    }

    /**
     * 收集手机信息
     *
     * @throws NameNotFoundException
     */
    private File collectInfoToSDCard(PrintWriter pw, Throwable ex)
            throws NameNotFoundException {

        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        // 错误发生时间
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        pw.print("time : ");
        pw.println(time);
        // 版本信息
        pw.print("versionCode : ");
        pw.println(pi.versionCode);
        // 应用版本号
        pw.print("versionName : ");
        pw.println(pi.versionName);
        try {
            /** 暴力反射获取数据 */
            Field[] Fields = Build.class.getDeclaredFields();
            for (Field field : Fields) {
                field.setAccessible(true);
                pw.print(field.getName() + " : ");
                pw.println(field.get(null).toString());
            }
        } catch (Exception e) {
            Log.i(TAG, "an error occured when collect crash info" + e);
        }

        // 打印堆栈信息
        ex.printStackTrace(pw);
        return logFile;
    }
}