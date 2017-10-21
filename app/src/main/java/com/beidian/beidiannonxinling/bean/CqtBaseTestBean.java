package com.beidian.beidiannonxinling.bean;

import java.util.List;

/**
 * Created by denge on 2017/9/5.
 */

public class CqtBaseTestBean {
    private String sectorSelect;
    private String lat,lng,address,remarks,rsrp,sinr,ping,http,ftpDown,ftpUp,volte,csfb,result;
    private String testModel;
    private String itemName,itemState;
    private String testName;
    private String buildingInfo;
    private String floorInfo;
    private String edtHeight;
    private String floorAddress;
    private String rru;
    private ShiFenBBU_RRUBean bbuRruBean;

    public ShiFenBBU_RRUBean getBbuRruBean() {
        return bbuRruBean;
    }

    public void setBbuRruBean(ShiFenBBU_RRUBean bbuRruBean) {
        this.bbuRruBean = bbuRruBean;
    }

    public String getBuildingInfo() {
        return buildingInfo;
    }

    public void setBuildingInfo(String buildingInfo) {
        this.buildingInfo = buildingInfo;
    }

    public String getFloorInfo() {
        return floorInfo;
    }

    public void setFloorInfo(String floorInfo) {
        this.floorInfo = floorInfo;
    }

    public String getEdtHeight() {
        return edtHeight;
    }

    public void setEdtHeight(String edtHeight) {
        this.edtHeight = edtHeight;
    }

    public String getFloorAddress() {
        return floorAddress;
    }

    public void setFloorAddress(String floorAddress) {
        this.floorAddress = floorAddress;
    }

    public String getRru() {
        return rru;
    }

    public void setRru(String rru) {
        this.rru = rru;
    }

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


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemState() {
        return itemState;
    }

    public void setItemState(String itemState) {
        this.itemState = itemState;
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
