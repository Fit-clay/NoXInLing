package com.beidian.beidiannonxinling.bean;

/**
 * Created by Administrator on 2017/9/20.
 */

public class NetFramework extends TestBaseBean{
    private String far;
    private String overlap;
    private String height;
    private String near;


    public String getFar() {
        return far;
    }

    public void setFar(String far) {
        this.far = far;
    }

    public String getOverlap() {
        return overlap;
    }

    public void setOverlap(String overlap) {
        this.overlap = overlap;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getNear() {
        return near;
    }

    public void setNear(String near) {
        this.near = near;
    }

    @Override
    public String toString() {
        return "NetFramework{" +
                "far='" + far + '\'' +
                ", overlap='" + overlap + '\'' +
                ", height='" + height + '\'' +
                ", near='" + near + '\'' +
                '}';
    }
}
