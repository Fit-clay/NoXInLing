package com.beidian.beidiannonxinling.util;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by shanpu on 2017/8/15.
 * Describe:Toast工具类
 */
public class ToastUtils {
    private static Toast mToast = null;

    /**
     * 单利模式显示Toast
     *
     * @param context 上下文
     * @param message 消息
     */
    public static void makeText(Context context, final String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 单利模式显示Toast
     *
     * @param context 上下文
     * @param message 消息
     */
    public static void makeTextLong(Context context, final String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }


}
