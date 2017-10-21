package com.beidian.beidiannonxinling.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 跟网络相关的工具类
 *
 * @author henry
 */
public class NonSignaLlingTools implements SignaLlingImp {


    private ConnectivityManager                mConnectivity;
    private TelephonyManager                   mTm;
    private SignStrenthChangeListener signChangeListener;


    public NonSignaLlingTools(Context context) {
        mConnectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        mTm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

    }

    /**
     * 设置一个监听
     * @param listener
     */
    public void setOnSignStrenthChange(SignStrenthChangeListener listener) {
        this.signChangeListener = listener;
    }

    /**
     * 监听信令回调
     */
    public interface SignStrenthChangeListener {
        public void onSignStenthChange(int db, int qa);
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        if (null != mConnectivity) {
            NetworkInfo info = mConnectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     *
     * @return
     */
    @Override
    public boolean isWifi() {
        if (mConnectivity == null)
            return false;
        NetworkInfo networkInfo = mConnectivity.getActiveNetworkInfo();
        if (networkInfo == null)
            return false;
        return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取小区id
     * @return
     */
    @SuppressLint("NewApi")
    @Override
    public int getCellId() {
        if (mTm != null) {
            List<CellInfo> allCellInfo = mTm.getAllCellInfo();
            if (allCellInfo != null) {
                for (CellInfo cellInfo : allCellInfo) {
                    if (cellInfo instanceof CellInfoCdma) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
                        return cellInfoCdma.getCellIdentity().getSystemId();
                    }
                    if (cellInfo instanceof CellInfoGsm) {
                        CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                        return cellInfoGsm.getCellIdentity().getCid();
                    }
                    if (cellInfo instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                        return cellInfoLte.getCellIdentity().getCi();
                    }
                }
            } else {
                CellLocation cellLocation = mTm.getCellLocation();
                if (cellLocation != null) {
                    if (cellLocation instanceof CdmaCellLocation) {
                        CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                        return cdmaCellLocation.getSystemId();
                    }
                    if (cellLocation instanceof GsmCellLocation) {
                        GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                        return gsmCellLocation.getCid();
                    }

                }
            }

        }
        return -1;
    }

    @Override
    public String getNetworkType() {
        String strNetworkType = "";

        NetworkInfo networkInfo = mConnectivity
                .getActiveNetworkInfo();
        String _strSubTypeName = "";

        int type = mTm.getNetworkType();
        if (networkInfo != null) {
            _strSubTypeName = networkInfo.getSubtypeName();
        }

        switch (type) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by
                // 11
                strNetworkType = "2G";
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by
                // 14
            case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by
                // 12
            case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by
                // 15
                // case TelephonyManager.Ne
                strNetworkType = "3G";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by
                // 13
                strNetworkType = "4G";
                break;
            case 17:// 移动3G
                strNetworkType = "3G";
                break;
            default:
                // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                if (_strSubTypeName.equalsIgnoreCase("TD_SCDMA")
                        || _strSubTypeName.equalsIgnoreCase("WCDMA")
                        || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                    strNetworkType = "3G";
                } else {
                    strNetworkType = _strSubTypeName;
                }

                break;
        }
        return strNetworkType;
    }

    /**
     * *获取网制式
     *
     * @return
     */
    @Override
    public String getNetworkDetail() {
        String strNetworkType = "";

        NetworkInfo networkInfo = mConnectivity
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        strNetworkType = "GSM";
                        break;
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        strNetworkType = "CDMA";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by
                        strNetworkType = "LTE";
                        break;
                    default:
                        break;
                }
                if (_strSubTypeName.equalsIgnoreCase("CDMA")) {
                    strNetworkType = "CDMA";
                }
                if (_strSubTypeName.equalsIgnoreCase("GSM")) {
                    strNetworkType = "GSM";
                }
                if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA")) {
                    strNetworkType = "TD-SCDMA";
                }
                if (_strSubTypeName.equalsIgnoreCase("WCDMA")) {
                    strNetworkType = "WCDMA";
                }
                if (_strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                    strNetworkType = "CDMA2000";
                }

            }
        } else {
            return "没有数据连接";
        }
        return strNetworkType;
    }

    /**
     * * 获取运营商
     *
     * @return
     */
    @Override
    public String getOperator() {

        String imsi = mTm.getSubscriberId();
        // 为了区分移动、联通还是电信，推荐使用imsi来判断(万不得己的情况下用getNetworkType()判断，比如imsi为空时)
        if (imsi != null && !"".equals(imsi)) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")
                    || imsi.startsWith("46007")) {// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
                return "中国移动";
            } else if (imsi.startsWith("46001") || imsi.startsWith("46006")) {
                return "中国联通";
            } else if (imsi.startsWith("46003") || imsi.startsWith("46005")
                    || imsi.startsWith("46011")) {
                return "中国电信";
            } else if (imsi.startsWith("46020")) {
                return "中国铁通";
            }
        }
        return "无法获取imsi";
    }

    /**
     * * 获取国家码
     *
     * @return
     */
    @Override
    public String getCountryCode() {
        String imsi = mTm.getSubscriberId();
        if (imsi != null && imsi.length() > 0) {
            return imsi.substring(0, 3);
        }
        return "无法获取";
    }

    /**
     * 获取网络码
     *
     * @return
     */
    @Override
    public String getNetCode() {
        String imsi = mTm.getSubscriberId();
        if (imsi != null && imsi.length() > 0) {
            return imsi.substring(3, 5);
        }
        return "无法获取";
    }

    /**
     * 获取基站位置寻呼区
     *
     * @return
     */
    @SuppressLint("NewApi")
    @Override
    public int getLac() {
        if (mTm != null) {
            List<CellInfo> allCellInfo = mTm.getAllCellInfo();
            if (allCellInfo != null) {
                for (CellInfo cellInfo : allCellInfo) {
                    if (cellInfo instanceof CellInfoCdma) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
//                         int lac = cellInfoCdma.getCellIdentity();
                        return -1;
                    }
                    if (cellInfo instanceof CellInfoGsm) {
                        CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                        int lac = cellInfoGsm.getCellIdentity().getLac();
                        return lac;
                    }
                    if (cellInfo instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                        int tac = cellInfoLte.getCellIdentity().getTac();
                        return tac;
                    }
                    // if (cellInfo instanceof CellInfoWcdma) {
                    // CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
                    // int lac = cellInfoWcdma.getCellIdentity().getLac();
                    // return lac;
                    // }
                }
            } else {
                CellLocation cellLocation = mTm.getCellLocation();
                if (cellLocation != null) {
                    if (cellLocation instanceof CdmaCellLocation) {
                        CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                        return cdmaCellLocation.getSystemId();
                    }
                    if (cellLocation instanceof GsmCellLocation) {
                        GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                        return gsmCellLocation.getLac();
                    }
                }
            }
        }

        return -1;
    }

    /**
     * * 获取识别码
     *
     * @return
     */
    @SuppressLint("NewApi")
    @Override
    public int getIdentyCode() {

        if (mTm != null) {
            List<NeighboringCellInfo> neighboringCellInfos = mTm
                    .getNeighboringCellInfo();
            List<CellInfo> allCellInfo = mTm.getAllCellInfo();
            if (allCellInfo != null) {
                for (CellInfo cellInfo : allCellInfo) {
                    if (cellInfo instanceof CellInfoCdma) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
                        int netWorkId = cellInfoCdma.getCellIdentity()
                                .getNetworkId();
                        return netWorkId;
                    }
                    if (cellInfo instanceof CellInfoGsm) {
                        CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                        int psc = cellInfoGsm.getCellIdentity().getPsc();
                        return psc;
                    }
                    if (cellInfo instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                        int pci = cellInfoLte.getCellIdentity().getPci();
                        return pci;
                    }
                    // if (cellInfo instanceof CellInfoWcdma) {
                    // CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
                    // int psc = cellInfoWcdma.getCellIdentity().getPsc();
                    // return psc;
                    // }
                }
            } else {
                CellLocation cellLocation = mTm.getCellLocation();
                if (cellLocation != null) {
                    if (cellLocation instanceof CdmaCellLocation) {
                        CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                        return cdmaCellLocation.getNetworkId();
                    }
                    if (cellLocation instanceof GsmCellLocation) {
                        GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                        return gsmCellLocation.getPsc();
                    }
                }
            }

        }

        return 0;
    }

    /**
     * 获取频点
     *
     * @return
     */
    @SuppressLint("NewApi")
    @Override
    public int getFrequency() {

        if (mTm != null) {
            List<CellInfo> allCellInfo = mTm.getAllCellInfo();
            if (allCellInfo != null) {
                for (CellInfo cellInfo : allCellInfo) {

                    if (cellInfo.getClass() == CellInfoCdma.class) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
                        int baseStationId = cellInfoCdma.getCellIdentity()
                                .getBasestationId();
                        return baseStationId;
                    }
                    if (cellInfo.getClass() == CellInfoGsm.class) {
                        CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                        int cid = cellInfoGsm.getCellIdentity().getCid();
                        return cid;
                    }
                    if (cellInfo.getClass() == CellInfoLte.class) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                        int ci = cellInfoLte.getCellIdentity().getCi();
                        return ci;
                    }
                }
            } else {
                CellLocation cellLocation = mTm.getCellLocation();
                if (cellLocation != null) {
                    if (cellLocation instanceof CdmaCellLocation) {
                        CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                        return cdmaCellLocation.getSystemId();
                    }
                    if (cellLocation instanceof GsmCellLocation) {
                        GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                        return gsmCellLocation.getCid();
                    }
                }
            }

        }
        return -1;
    }

    /**
     * 获取信号强度
     */
    @Override
    public void startSignStrenthChangeListener() {

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onSignalStrengthsChanged(
                    SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);

                if (signChangeListener != null) {
                    String signalInfo = signalStrength.toString();
//                    Log.d("tag--------------", signalInfo);
                    String[] parts = signalInfo.split(" ");
                    // 4G，LTE
                    int ltedbm = 0;
                    int lteQa = 0;
                    // 3G电信，CDMA2000
                    int cdma2000Dbm = 0;
                    int cdma2000Qa = 0;
                    // 3G移动，TDSCDMA
                    // 3G联通，WCDMA
                    // 2G电信,CDMA
                    int cdmaDbm = 0;
                    try {
                        ltedbm = Integer.parseInt(parts[9]);
                        lteQa = Integer.parseInt(parts[11]) / 10;
                        cdma2000Dbm = Integer.parseInt(parts[3]);
                        cdma2000Qa = Integer.parseInt(parts[5]);
                        cdmaDbm = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {

                        e.printStackTrace();
                    }
                    // 2G联通移动，GSM
                    int asu = signalStrength.getGsmSignalStrength();
                    int gsmDbm = -113 + 2 * asu;

                    switch (mTm.getNetworkType()) {
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            // 4G
                            // 判断手机品牌如果是华为则反射获取4g信号强度与质量
                            if ("HUAWEI".equals(Build.MANUFACTURER)) {
                                try {
                                    Class<? extends SignalStrength> signalStreng = signalStrength
                                            .getClass();
                                    Method signalStr = signalStreng
                                            .getDeclaredMethod("getLteRsrp", (Class<?>) null);
                                    Method signalQa = signalStreng
                                            .getDeclaredMethod("getLteRssnr", (Class<?>) null);
                                    signalStr.setAccessible(true);
                                    signalQa.setAccessible(true);
                                    int signal = (Integer) signalStr.invoke(
                                            signalStrength, (Object) null);
                                    int qa = (Integer) signalQa.invoke(
                                            signalStrength, (Object) null);
                                    // Log.d("4g信号强度", "" + signal);
                                    // Log.d("4g信号质量", "" + qa);
                                    signChangeListener.onSignStenthChange(signal,
                                            qa - 3);
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                signChangeListener
                                        .onSignStenthChange(ltedbm, lteQa);
                            }
                            break;
                        default:
                            // 2G，3G根据运营商不同返回不同字段
                            String operator = getOperator();
                            String netType = getNetworkType();
                            if (operator.equals("中国电信")) {
                                if (netType.equals("2G")) {
                                    signChangeListener.onSignStenthChange(cdmaDbm,
                                            -1);
                                    break;
                                }
                                if (netType.equals("3G")) {
                                    signChangeListener.onSignStenthChange(
                                            cdma2000Dbm, cdma2000Qa);
                                    break;
                                }
                            }
                            if (operator.equals("中国移动")) {
                                if (netType.equals("2G")) {
                                    signChangeListener.onSignStenthChange(gsmDbm,
                                            -1);
                                    break;
                                }
                                if (netType.equals("3G")) {
                                    signChangeListener.onSignStenthChange(-1, -1);
                                    break;
                                }
                            }
                            if (operator.equals("中国联通")) {
                                if (netType.equals("2G")) {
                                    signChangeListener.onSignStenthChange(gsmDbm,
                                            -1);
                                    break;
                                }
                                if (netType.equals("3G")) {
                                    signChangeListener.onSignStenthChange(-1, -1);
                                    break;
                                }
                            }
                            break;
                    }
                    mTm.listen(this, PhoneStateListener.LISTEN_NONE);
                }
            }

            ;
        };
        mTm.listen(phoneStateListener,
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    /**
     * 获取邻区信息
     *
     * @return
     */
    @SuppressLint("NewApi")
    @Override
    public List<NeighboringCellInfo> getAllNeighboringCellInfo() {
        if (mTm != null) {
            List<NeighboringCellInfo> allCellInfo = mTm.getNeighboringCellInfo();
            if (allCellInfo != null && allCellInfo.size() > 0) {
                allCellInfo.remove(0);
                return allCellInfo;
            }
        }
        return null;
    }

    /**
     * 获取连接状态1.空闲2.数据连接，3.语音
     *
     * @return
     */
    @Override
    public String getConnectState() {
        // 去进行判断网络是否连接
        if (mConnectivity.getActiveNetworkInfo() != null) {
            boolean flag = mConnectivity.getActiveNetworkInfo().isAvailable();
            if (flag) {
                return "数据连接";
            } else {
                return "空闲";
            }
        } else {
            return "空闲";
        }
    }

    /**
     * 获取 信号质量
     *
     * @param strenth
     * @param qa
     * @return
     */
    @Override
    public String getNetState(int strenth, int qa) {
        if (strenth < -105 || qa < 3) {
            return "差";
        }
        if ((strenth <= -95 && strenth >= -105) || (qa <= 9 && qa >= 3)) {
            return "一般";
        }
        if (strenth > -95 || qa > 9) {
            return "优秀";
        }

        return "差";
    }


}

