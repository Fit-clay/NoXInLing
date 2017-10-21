package com.beidian.beidiannonxinling.bean;

/**
 * Created by Eric on 2017/10/16.
 * 存储页面的信息数据
 */

public class TestBaseBean {
    private String itemName;
    private String itemState="pass";

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


}
