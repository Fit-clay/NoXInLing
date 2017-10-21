package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by denge on 2017/9/5.
 */

public class OneKeyTestBean implements Serializable {
    String           sector;//扇区名称
    String           workorderno; //工单号
    List<TestTask>   changeModel;//选择的模板
    TestModelBean    testModelBean; //测试模板的父实例
    String           testOrder;//测试任务号
    String           resultPath;//测试结果路径，用于存放log文件等
    BaseInfoTestBean baseInfoTestBean; //基本的测试信息
    int              changeDistrictIndex;
    String remarks;

    public OneKeyTestBean() {
    }

    public TestModelBean getTestModelBean() {
        return testModelBean;
    }

    public void setTestModelBean(TestModelBean testModelBean) {
        this.testModelBean = testModelBean;
    }

    public OneKeyTestBean(String sector, String workorderno, List<TestTask> changeModel, String testOrder, String resultPath, BaseInfoTestBean baseInfoTestBean, int changeDistrictIndex,String remarks) {
        this.sector = sector;
        this.workorderno = workorderno;
        this.changeModel = changeModel;
        this.testOrder = testOrder;
        this.baseInfoTestBean = baseInfoTestBean;
        this.changeDistrictIndex = changeDistrictIndex;
        this.resultPath = resultPath;
        this.remarks=remarks;
    }
    public  CellinfoListBean getCurrentCell() {
        List<CellinfoListBean> cellinfoList = baseInfoTestBean.getSiteInfo().getCellinfoList();
          CellinfoListBean currentCell = cellinfoList.get(changeDistrictIndex);

        return currentCell;
    }

    public int getChangeDistrictIndex() {
        return changeDistrictIndex;
    }

    public void setChangeDistrictIndex(int changeDistrictIndex) {
        this.changeDistrictIndex = changeDistrictIndex;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public BaseInfoTestBean getBaseInfoTestBean() {
        return baseInfoTestBean;
    }

    public void setBaseInfoTestBean(BaseInfoTestBean baseInfoTestBean) {
        this.baseInfoTestBean = baseInfoTestBean;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getWorkorderno() {
        return workorderno;
    }

    public void setWorkorderno(String workorderno) {
        this.workorderno = workorderno;
    }

    public List<TestTask> getChangeModel() {
        return changeModel;
    }

    public void setChangeModel(List<TestTask> changeModel) {
        this.changeModel = changeModel;
    }

    public String getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(String testOrder) {
        this.testOrder = testOrder;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
