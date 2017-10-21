package com.beidian.beidiannonxinling.util;

import android.annotation.SuppressLint;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;

import java.util.List;

/**
 * 跟网络相关的工具类
 *
 * @author henry
 */
public interface SignaLlingImp {
    /**
     * 判断网络是否连接
     *
     * @return
     */
    boolean isConnected();

    /**
     * 判断是否是wifi连接
     */
    boolean isWifi();

    /**
     * 获取小区ID
     *
     * @return
     */
    @SuppressLint("NewApi")
    int getCellId();

    /**
     * 获取网路制式2g，3g，4g
     *
     * @return
     */
    String getNetworkType();


    /**
     * 获取网制式
     *
     * @return
     */
    String getNetworkDetail();

    /**
     * 获取运营商
     *
     * @return
     */
    String getOperator();

    /**
     * 获取国家码
     *
     * @return
     */
    String getCountryCode();

    /**
     * 获取网络码
     *
     * @return
     */
    String getNetCode();

    /**
     * 获取基站位置寻呼区
     *
     * @return
     */
    @SuppressLint("NewApi")
    int getLac();

    /**
     * 获取识别码
     *
     * @return
     */
    @SuppressLint("NewApi")
    int getIdentyCode();

    /**
     * 获取频点
     *
     * @return
     */
    @SuppressLint("NewApi")
    int getFrequency();

    /**
     * 获取信号强度
     *
     */
    void startSignStrenthChangeListener();

    /**
     * 获取邻区信息
     *
     */
    @SuppressLint("NewApi")
    List<NeighboringCellInfo> getAllNeighboringCellInfo();

    /**
     * 获取连接状态1.空闲2.数据连接，3.语音
     *
     * @return
     */
    String getConnectState();

    /**
     * 获取网络质量
     * @param strenth
     * @param qa
     * @return
     */

    String getNetState( int strenth, int qa);
}
