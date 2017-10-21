package com.beidian.beidiannonxinling.test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VoiceTest {
    /**
     * 拨打电话
     *
     * @param telephoneNumber
     */
    public void callPhone(Context context, String telephoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + telephoneNumber);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    /**
     * 接听电话
     */
    public static void answerCall() {
        try {
            Method method = Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null,
                    new Object[]{"phone"});
            ITelephony iTelephony = ITelephony.Stub.asInterface(binder);
            iTelephony.answerRingingCall();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 挂断电话
     */
    public  void stopCall(Activity activity) {
        try {
            ITelephony iTelephony = getITelephony(activity);
            iTelephony.endCall();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ITelephony getITelephony(Activity activity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TelephonyManager manager = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);
        Method getITelephonyMethod;
        getITelephonyMethod = manager.getClass().getDeclaredMethod(
                "getITelephony", (Class[]) null);
        getITelephonyMethod.setAccessible(true);// 私有化函数也能使用
        Object invoke = getITelephonyMethod
                .invoke(manager, (Object[]) null);
        ITelephony invoke1 = (ITelephony) invoke;
        return invoke1;
    }
}
