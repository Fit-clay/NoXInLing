package com.beidian.beidiannonxinling.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/1715:08
 * @描述: ------------------------------------------
 */

public class Permission {

    public void checkCallPhonePermission(Activity mContext,String mobile){

        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions( mContext,new String[]{Manifest.permission.CALL_PHONE},1);
                return;
            }
        }
    }
}
