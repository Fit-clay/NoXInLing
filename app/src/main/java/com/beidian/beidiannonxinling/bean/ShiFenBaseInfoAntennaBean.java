package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;

/**
 * Created by Eric on 2017/9/22.
 */

public class ShiFenBaseInfoAntennaBean  implements Serializable {
    private String buildPath;
    private String antenna1;
    private String antenna2;
    private boolean isUserAdd;

    public ShiFenBaseInfoAntennaBean(String buildPath, String antenna1, String antenna2) {
        this.buildPath = buildPath;
        this.antenna1 = antenna1;
        this.antenna2 = antenna2;
    }

    public boolean isUserAdd() {
        return isUserAdd;
    }

    public void setUserAdd(boolean userAdd) {
        isUserAdd = userAdd;
    }

    public String getBuildPath() {
        return buildPath;
    }

    public void setBuildPath(String buildPath) {
        this.buildPath = buildPath;
    }

    public String getAntenna1() {
        return antenna1;
    }

    public void setAntenna1(String antenna1) {
        this.antenna1 = antenna1;
    }

    public String getAntenna2() {
        return antenna2;
    }

    public void setAntenna2(String antenna2) {
        this.antenna2 = antenna2;
    }

}
