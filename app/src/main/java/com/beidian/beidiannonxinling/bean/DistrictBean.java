package com.beidian.beidiannonxinling.bean;

/**
 * Created by Eric on 2017/9/14.
 */

public class DistrictBean {
    String name;
    boolean isUserAdd;

    public DistrictBean( ) { }

    public DistrictBean(String name, boolean isUserAdd) {
        this.name = name;
        this.isUserAdd = isUserAdd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUserAdd() {
        return isUserAdd;
    }

    public void setUserAdd(boolean userAdd) {
        isUserAdd = userAdd;
    }
}
