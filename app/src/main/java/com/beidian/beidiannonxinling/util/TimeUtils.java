package com.beidian.beidiannonxinling.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shanpu on 2017/8/31.
 * <p>
 */

public class TimeUtils {

    /**
     * 获取当前系统时间
     *
     * @return yyyy-MM-dd HH:mm:ss格式的时间字符串
     */
    public static String getyyyyMMddHHmmss() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.getDefault());
//        Date date = new Date(System.currentTimeMillis());
//        return sdf.format(date);
        return getyyyyMMddHHmmss("yyyy-MM-dd  HH:mm:ss");
    }


    /**
     * 获取当前系统时间
     *
     * @return yyyy-MM-dd HH:mm:ss格式的时间字符串
     */
    public static String getyyyyMMddHHmmss(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }
}
