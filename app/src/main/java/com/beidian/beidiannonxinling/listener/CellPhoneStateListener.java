package com.beidian.beidiannonxinling.listener;

import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.beidian.beidiannonxinling.bean.CellGeneralInfo;
import com.beidian.beidiannonxinling.bean.LTECellBean;
import com.beidian.beidiannonxinling.bean.PhoneGeneralInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/1316:23
 * @描述: ------------------------------------------
 */

public class CellPhoneStateListener extends PhoneStateListener {
    private int mCId;
    private int mtac;
    private int mpci;
    // for current
    public PhoneGeneralInfo phoneGeneralInfo = new PhoneGeneralInfo();
    public  CellGeneralInfo       serverCellInfo;
    private List<CellGeneralInfo> HistoryServerCellList;
    private TelephonyManager      phoneManager;
    public final LTECellBean mLteCellBean;

    public CellPhoneStateListener(TelephonyManager phoneManager) {
        this.phoneManager = phoneManager;
        this.serverCellInfo = new CellGeneralInfo();
        this. mLteCellBean = new LTECellBean();
        this.HistoryServerCellList = new ArrayList<>();
    }

    @Override
    public void onCellInfoChanged(List<CellInfo> cellInfo) {
        super.onCellInfoChanged(cellInfo);
        if (cellInfo == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                throw new Exception("版本过低CellInfoLte最低要求在API17以上！！！");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //4G网络的时候的数据
        for (CellInfo info : cellInfo) {
            Log.d("ssssssssss", "onCellInfoChanged: " + mLteCellBean.toString());
            if (info instanceof CellInfoLte) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    return;
                }

                CellInfoLte info1 = (CellInfoLte) info;
                mLteCellBean.ci = info1.getCellIdentity().getCi();
                mLteCellBean.tac = info1.getCellIdentity().getTac();
                mLteCellBean.pci = info1.getCellIdentity().getPci();
            }

        }
    }


    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);

        getPhoneGeneralInfo();
        getServerCellInfo();
        //4G网络的时候的数据lte
        if (phoneGeneralInfo.ratType == TelephonyManager.NETWORK_TYPE_LTE) {
            Log.d("cellPhone：", "当前是:NETWORK_TYPE_LTE ----"+mLteCellBean.toString());
            try {
//                serverCellInfo.rssi = (Integer) signalStrength.getClass().getMethod("getLteSignalStrength").invoke(signalStrength);
                mLteCellBean.rsrp = (Integer) signalStrength.getClass().getMethod("getLteRsrp").invoke(signalStrength);
                mLteCellBean.rsrq = (Integer) signalStrength.getClass().getMethod("getLteRsrq").invoke(signalStrength);
                mLteCellBean.sinr = (Integer) signalStrength.getClass().getMethod("getLteRssnr").invoke(signalStrength);
//                serverCellInfo.cqi = (Integer) signalStrength.getClass().getMethod("getLteCqi").invoke(signalStrength);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else if (phoneGeneralInfo.ratType == TelephonyManager.NETWORK_TYPE_GSM) {
//            Log.d("cellPhone：", "当前是:NETWORK_TYPE_GSM ----"+serverCellInfo.toString());
            try {
                serverCellInfo.rssi = signalStrength.getGsmSignalStrength();
                serverCellInfo.rsrp = (Integer) signalStrength.getClass().getMethod("getGsmDbm").invoke(signalStrength);
                serverCellInfo.asulevel = (Integer) signalStrength.getClass().getMethod("getAsuLevel").invoke(signalStrength);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else if (phoneGeneralInfo.ratType == TelephonyManager.NETWORK_TYPE_TD_SCDMA) {
//            Log.d("cellPhone：", "当前是:NETWORK_TYPE_TD_SCDMA ----"+serverCellInfo.toString());
            try {
                serverCellInfo.rssi = (Integer) signalStrength.getClass().getMethod("getTdScdmaLevel").invoke(signalStrength);
                serverCellInfo.rsrp = (Integer) signalStrength.getClass().getMethod("getTdScdmaDbm").invoke(signalStrength);
                serverCellInfo.asulevel = (Integer) signalStrength.getClass().getMethod("getAsuLevel").invoke(signalStrength);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        serverCellInfo.time = formatter.format(now);
        updateHistoryCellList(serverCellInfo);

    }

    public void getPhoneGeneralInfo() {
        try {
            phoneGeneralInfo.operaterName = phoneManager.getNetworkOperatorName();
            phoneGeneralInfo.operaterId = phoneManager.getNetworkOperator();
            phoneGeneralInfo.mnc = Integer.parseInt(phoneGeneralInfo.operaterId.substring(0, 3));
            phoneGeneralInfo.mcc = Integer.parseInt(phoneGeneralInfo.operaterId.substring(3));
            phoneGeneralInfo.phoneDatastate = phoneManager.getDataState();
            phoneGeneralInfo.deviceId = phoneManager.getDeviceId();
            phoneGeneralInfo.Imei = phoneManager.getSimSerialNumber();
            phoneGeneralInfo.Imsi = phoneManager.getSubscriberId();
            phoneGeneralInfo.serialNumber = phoneManager.getSimSerialNumber();
            phoneGeneralInfo.deviceSoftwareVersion = android.os.Build.VERSION.RELEASE;
            phoneGeneralInfo.phoneModel = android.os.Build.MODEL;
            phoneGeneralInfo.ratType = phoneManager.getNetworkType();
            phoneGeneralInfo.sdk = android.os.Build.VERSION.SDK_INT;
        } catch (Exception environment) {
        }


    }

    public void getServerCellInfo() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                throw new Exception("版本过低CellInfoLte最低要求在API17以上！！！");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        try {
            List<CellInfo> allCellinfo;
            allCellinfo = phoneManager.getAllCellInfo();

            if (allCellinfo != null) {

                CellInfo cellInfo = allCellinfo.get(0);
                serverCellInfo.getInfoType = 1;
                if (cellInfo instanceof CellInfoGsm) {
                    Log.d("cellPhone：", "当前是:CellInfoGsm ----"+serverCellInfo.toString());
                    CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                    serverCellInfo.CId = cellInfoGsm.getCellIdentity().getCid();
                    serverCellInfo.rsrp = cellInfoGsm.getCellSignalStrength().getDbm();
                    serverCellInfo.asulevel = cellInfoGsm.getCellSignalStrength().getAsuLevel();
                    serverCellInfo.lac = cellInfoGsm.getCellIdentity().getLac();
                    serverCellInfo.RatType = TelephonyManager.NETWORK_TYPE_GSM;
                } else if (cellInfo instanceof CellInfoWcdma) {
                    Log.d("cellPhone：", "当前是:CellInfoWcdma ----"+serverCellInfo.toString());
                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
                    serverCellInfo.CId = cellInfoWcdma.getCellIdentity().getCid();
                    serverCellInfo.psc = cellInfoWcdma.getCellIdentity().getPsc();
                    serverCellInfo.lac = cellInfoWcdma.getCellIdentity().getLac();
                    serverCellInfo.rsrp = cellInfoWcdma.getCellSignalStrength().getDbm();
                    serverCellInfo.asulevel = cellInfoWcdma.getCellSignalStrength().getAsuLevel();
                    serverCellInfo.RatType = TelephonyManager.NETWORK_TYPE_UMTS;
                } else if (cellInfo instanceof CellInfoLte) {
                    Log.d("cellPhone：", "当前是:CellInfoLte ----"+serverCellInfo.toString());
                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                    mLteCellBean.ci = cellInfoLte.getCellIdentity().getCi();
                    mLteCellBean.pci = cellInfoLte.getCellIdentity().getPci();
                    mLteCellBean.tac = cellInfoLte.getCellIdentity().getTac();
                    mLteCellBean.rsrp = cellInfoLte.getCellSignalStrength().getDbm();
                    mLteCellBean.asu = cellInfoLte.getCellSignalStrength().getAsuLevel();
                    mLteCellBean.networkType = TelephonyManager.NETWORK_TYPE_LTE;
                }
            } else
            //for older devices
            {
                getServerCellInfoOnOlderDevices();
            }
        } catch (Exception e) {
            getServerCellInfoOnOlderDevices();
        }

    }

    void getServerCellInfoOnOlderDevices() {
        CellLocation cellLocation = phoneManager.getCellLocation();
        if (cellLocation instanceof CdmaCellLocation) {
//            Log.d("cellPhone：", "当前是:CdmaCellLocation ----"+serverCellInfo.toString());
            CdmaCellLocation location = (CdmaCellLocation) phoneManager.getCellLocation();
            int baseStationLatitude = location.getBaseStationLatitude();
        }
        if (cellLocation instanceof GsmCellLocation) {
//            Log.d("cellPhone：", "当前是:GsmCellLocation ----"+serverCellInfo.toString());
            GsmCellLocation location = (GsmCellLocation) phoneManager.getCellLocation();
            mLteCellBean.ci = location.getCid();
            mLteCellBean.tac = location.getLac();
            serverCellInfo.psc = location.getPsc();
        }

        serverCellInfo.getInfoType = 0;
        //            location.getSystemId()
        serverCellInfo.type = phoneGeneralInfo.ratType;
    }

    void updateHistoryCellList(CellGeneralInfo serverinfo) {
        CellGeneralInfo newcellInfo = (CellGeneralInfo) serverinfo.clone();
        HistoryServerCellList.add(newcellInfo);
    }

}
