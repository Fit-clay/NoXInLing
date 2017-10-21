package com.beidian.beidiannonxinling.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * 系统进程相关的帮助类
 * Created by Hank on 2016/11/14.
 * Email : laohuangshu@foxmail.com
 * @author hank
 */
public class OsHelper {

    /**
     * @return 如果指定的进程不存在,则返回null
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 版本名
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 获取当前应用包名
     * @param context
     * @return
     */
    public static String getPackage(Context context){
        return getPackageInfo(context).packageName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    public static int getSDKVersion(){
        return Build.VERSION.SDK_INT;
    }

    public static String getMetaValue(Context context,String key) throws PackageManager.NameNotFoundException {
        Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA).metaData;
        if(null != bundle){
            return bundle.getString(key);
        }
        return null;
    }

    public static int getMetaIntValue(Context context,String key) throws PackageManager.NameNotFoundException {
        Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA).metaData;
        if(null != bundle){
            return bundle.getInt(key);
        }
        return -1;
    }

    /**
     *
     * @param context
     * @param key
     * @param defaultValue 不可为null,否则永远返回 null
     * @return
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMetaT(Context context,String key,T defaultValue){
        if(null == defaultValue){ return defaultValue; }

        Bundle bundle = null;
        try {
            bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA).metaData;
        } catch (PackageManager.NameNotFoundException e) {
            bundle = null;
        }
        if(null != bundle){
            Object o = bundle.get(key);
            if(o == null){ return defaultValue; }
            return (T)o;
        }
        return defaultValue;
    }


    public static final String PHONE_BRAND_HUAWEI = "huawei";
    public static final String PHONE_BRAND_XIAOMI = "xiaomi";
    public static final String PHONE_BRAND_MEIZU = "meizu";


    public static String getPhoneBrand(){
        String brand = Build.BRAND;
        if(null != brand){ brand = brand.toLowerCase(); }
        return brand;
    }

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     * @param
     */
    public static void verifyStoragePermissions(Activity activity,String []PERMISSIONS_STORAGE ) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    1);
        }

    }
    //根据包名启动应用
    public static void doStartApplicationWithPackageName(String packaPath,Context  mContext) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = mContext.getPackageManager().getPackageInfo(packaPath, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList =mContext. getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            mContext.startActivity(intent);
        }
    }

}
