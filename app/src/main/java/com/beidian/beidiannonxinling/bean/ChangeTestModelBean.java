package com.beidian.beidiannonxinling.bean;

import java.util.List;

/**
 * Created by Eric on 2017/9/29.
 */

public class ChangeTestModelBean {
    private String distractChange;
    private String lat,lng,address;
    private String unit,floorInfo,referenceHeight;
    private String TestWay;
    private String testModel;
    private List<TestTask> testChange;
    private ShiFenBBU_RRUBean rru;
    private String remark;
    private TestModelBean testModelBean;

    public TestModelBean getTestModelBean() {
        return testModelBean;
    }

    public void setTestModelBean(TestModelBean testModelBean) {
        this.testModelBean = testModelBean;
    }

    public String getDistractChange() {
        return distractChange;
    }

    public void setDistractChange(String distractChange) {
        this.distractChange = distractChange;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFloorInfo() {
        return floorInfo;
    }

    public void setFloorInfo(String floorInfo) {
        this.floorInfo = floorInfo;
    }

    public String getReferenceHeight() {
        return referenceHeight;
    }

    public void setReferenceHeight(String referenceHeight) {
        this.referenceHeight = referenceHeight;
    }

    public String getTestWay() {
        return TestWay;
    }

    public void setTestWay(String testWay) {
        TestWay = testWay;
    }

    public String getTestModel() {
        return testModel;
    }

    public void setTestModel(String testModel) {
        this.testModel = testModel;
    }

    public List<TestTask> getTestChange() {
        return testChange;
    }

    public void setTestChange(List<TestTask> testChange) {
        this.testChange = testChange;
    }

    public ShiFenBBU_RRUBean getRru() {
        return rru;
    }

    public void setRru(ShiFenBBU_RRUBean rru) {
        this.rru = rru;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
