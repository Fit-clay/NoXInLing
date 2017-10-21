package com.beidian.beidiannonxinling.bean;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by ASUS on 2017/9/1.
 */

public class MapBean {
    private final LatLng mPosition;
    private String Taskid;
    private String mState;

    public MapBean(String taskid, LatLng position, String state) {
        this.Taskid = taskid;
        this.mPosition = position;
        this.mState = state;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getTaskid() {
        return Taskid;
    }

    public void setTaskid(String taskid) {
        Taskid = taskid;
    }

    public LatLng getmPosition() {
        return mPosition;
    }


}