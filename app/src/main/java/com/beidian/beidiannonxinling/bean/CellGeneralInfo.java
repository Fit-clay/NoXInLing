package com.beidian.beidiannonxinling.bean;

import android.telephony.TelephonyManager;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/1316:45
 * @描述: ------------------------------------------
 */

public class CellGeneralInfo implements Cloneable {
    public int type;
    public int CId;
    public int lac;
    public int tac;
    public int psc;
    public int pci;
    public int RatType = TelephonyManager.NETWORK_TYPE_UNKNOWN;
    public int    rsrp;
    public int    rsrq;
    public int    sinr;
    public int    rssi;
    public int    cqi;
    public int    asulevel;
    public int    getInfoType;
    public String time;

    @Override
    public Object clone() {
        CellGeneralInfo cellinfo = null;
        try {
            cellinfo = (CellGeneralInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cellinfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCId() {
        return CId;
    }

    public void setCId(int CId) {
        this.CId = CId;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }

    public int getTac() {
        return tac;
    }

    public void setTac(int tac) {
        this.tac = tac;
    }

    public int getPsc() {
        return psc;
    }

    public void setPsc(int psc) {
        this.psc = psc;
    }

    public int getPci() {
        return pci;
    }

    public void setPci(int pci) {
        this.pci = pci;
    }

    public int getRatType() {
        return RatType;
    }

    public void setRatType(int ratType) {
        RatType = ratType;
    }

    public int getRsrp() {
        return rsrp;
    }

    public void setRsrp(int rsrp) {
        this.rsrp = rsrp;
    }

    public int getRsrq() {
        return rsrq;
    }

    public void setRsrq(int rsrq) {
        this.rsrq = rsrq;
    }

    public int getSinr() {
        return sinr;
    }

    public void setSinr(int sinr) {
        this.sinr = sinr;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getCqi() {
        return cqi;
    }

    public void setCqi(int cqi) {
        this.cqi = cqi;
    }

    public int getAsulevel() {
        return asulevel;
    }

    public void setAsulevel(int asulevel) {
        this.asulevel = asulevel;
    }

    public int getGetInfoType() {
        return getInfoType;
    }

    public void setGetInfoType(int getInfoType) {
        this.getInfoType = getInfoType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CellGeneralInfo{" +
                "type=" + type +
                ", CId=" + CId +
                ", lac=" + lac +
                ", tac=" + tac +
                ", psc=" + psc +
                ", pci=" + pci +
                ", RatType=" + RatType +
                ", rsrp=" + rsrp +
                ", rsrq=" + rsrq +
                ", sinr=" + sinr +
                ", rssi=" + rssi +
                ", cqi=" + cqi +
                ", asulevel=" + asulevel +
                ", getInfoType=" + getInfoType +
                ", time='" + time + '\'' +
                '}';
    }
}