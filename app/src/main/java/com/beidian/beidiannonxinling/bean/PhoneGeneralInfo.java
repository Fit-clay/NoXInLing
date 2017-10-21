package com.beidian.beidiannonxinling.bean;

import android.telephony.TelephonyManager;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/1316:44
 * @描述: ------------------------------------------
 */
public class PhoneGeneralInfo {
    public String serialNumber;
    public String operaterName;
    public String operaterId;
    public String deviceId;
    public String deviceSoftwareVersion;
    public String Imsi;
    public String Imei;
    public int    mnc;
    public int    mcc;
    public int ratType = TelephonyManager.NETWORK_TYPE_UNKNOWN;
    public int    phoneDatastate;
    public String phoneModel;
    public int    sdk;
}