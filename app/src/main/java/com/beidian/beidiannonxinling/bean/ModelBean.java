package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ASUS on 2017/9/26.
 */

public class ModelBean implements Serializable {

    private int id;

    private long modelId;//子模板对应id

    private String name;//模板名称

    private int circleTimes;//循环测试次数

    private String account;//账号account : 18039916333

    private String createdate;

    private String status;

    private String updatedate;//

    private String workorderno;// HCC_test_0001

    private List<ModelCofingBean> taskList;//子模板

    public ModelBean() {
    }

    public ModelBean(String name, int circleTimes) {
        this.name = name;
        this.circleTimes = circleTimes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getWorkorderno() {
        return workorderno;
    }

    public void setWorkorderno(String workorderno) {
        this.workorderno = workorderno;
    }

    public List<ModelCofingBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ModelCofingBean> taskList) {
        this.taskList = taskList;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCircleTimes() {
        return circleTimes;
    }

    public void setCircleTimes(int circleTimes) {
        this.circleTimes = circleTimes;
    }



}
