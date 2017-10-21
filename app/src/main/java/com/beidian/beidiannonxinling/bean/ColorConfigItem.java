package com.beidian.beidiannonxinling.bean;

/**
 * Created by ASUS on 2017/9/21.
 */

public class ColorConfigItem {

    private long id; // id

    private String unit;// //单位名称 例如m/s dbm

    private int type;// 类型

    private int leftType; // 数据小值边界类型;0:"(";1:"[";

    private int minValue; // 显示的最小值

    private int maxValue;// 显示的最大值

    private int rightType; // 数据大值边界类型;0:")";1:"]";

    private int color;// 颜色值

    private String cid;// 颜色值唯一键

    private String officeId;// 运营商id

    private int virtualMinValue;//有效的最小值

    private int virtualMaxValue;//有效的最大值

    @Override
    public String toString() {
        return "ColorConfigItem{" +
                "id=" + id +
                ", unit='" + unit + '\'' +
                ", type=" + type +
                ", leftType=" + leftType +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", rightType=" + rightType +
                ", color=" + color +
                ", cid='" + cid + '\'' +
                ", officeId='" + officeId + '\'' +
                ", virtualMinValue=" + virtualMinValue +
                ", virtualMaxValue=" + virtualMaxValue +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public int getVirtualMinValue() {
        return virtualMinValue;
    }

    public void setVirtualMinValue(int virtualMinValue) {
        this.virtualMinValue = virtualMinValue;
    }

    public int getVirtualMaxValue() {
        return virtualMaxValue;
    }

    public void setVirtualMaxValue(int virtualMaxValue) {
        this.virtualMaxValue = virtualMaxValue;
    }
}
