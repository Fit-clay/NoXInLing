package com.beidian.beidiannonxinling.bean;

import java.util.List;

/**
 * Created by Eric on 2017/10/16.
 */

public class LETBean extends TestBaseBean {
    private List<TestResult> testResults;
    private ChangeTestModelBean changeTestModelBean;

    public List<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestResult> testResults) {
        this.testResults = testResults;
    }

    public ChangeTestModelBean getChangeTestModelBean() {
        return changeTestModelBean;
    }

    public void setChangeTestModelBean(ChangeTestModelBean changeTestModelBean) {
        this.changeTestModelBean = changeTestModelBean;
    }
}
