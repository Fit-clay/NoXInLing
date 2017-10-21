package com.beidian.beidiannonxinling.bean;

import java.util.List;

/**
 * Created by denge on 2017/9/5.
 */

public class CQTHongZhanTestBean {
    private String sectorSelect;
    private String lat,lng,address,remarks,rsrp,sinr,ping,http,ftpDown,ftpUp,volte,csfb,result;
    private String testModel;
    private String itemname,itemstate;
    private String testName;

    private List<TestTask> testTaskList;

    public String getTestModel() {
        return testModel;
    }

    public void setTestModel(String testModel) {
        this.testModel = testModel;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<TestTask> getTestTaskList() {
        return testTaskList;
    }

    public void setTestTaskList(List<TestTask> testTaskList) {
        this.testTaskList = testTaskList;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemstate() {
        return itemstate;
    }

    public void setItemstate(String itemstate) {
        this.itemstate = itemstate;
    }

    public String getRsrp() {
        return rsrp;
    }

    public void setRsrp(String rsrp) {
        this.rsrp = rsrp;
    }

    public String getSinr() {
        return sinr;
    }

    public void setSinr(String sinr) {
        this.sinr = sinr;
    }

    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public String getFtpDown() {
        return ftpDown;
    }

    public void setFtpDown(String ftpDown) {
        this.ftpDown = ftpDown;
    }

    public String getFtpUp() {
        return ftpUp;
    }

    public void setFtpUp(String ftpUp) {
        this.ftpUp = ftpUp;
    }

    public String getVolte() {
        return volte;
    }

    public void setVolte(String volte) {
        this.volte = volte;
    }

    public String getCsfb() {
        return csfb;
    }

    public void setCsfb(String csfb) {
        this.csfb = csfb;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSectorSelect() {
        return sectorSelect;
    }

    public void setSectorSelect(String sectorSelect) {
        this.sectorSelect = sectorSelect;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
