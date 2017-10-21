package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/9/19.
 */

public class TestModelBean implements Serializable {

    private List<TemplateBean> template=new ArrayList<>();

    public List<TemplateBean> getTemplate() {
        return template;
    }

    public void setTemplate(List<TemplateBean> template) {
        this.template = template;
    }

    public static class TemplateBean implements Serializable {
        /**
         * account : 18039916333
         * createdate :
         * id : 1
         * status :
         * taskList : [{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":5,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":null,"templateid":"1","testcount":"10","testinterval":"10","testmode":"time","testtime":"60","testtype":"attach","testurl":"","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"20","callphone":"10086","calltype":"Voice","exectimeout":"","filename":"","ftpmode":"","id":6,"password":"","port":"","retentiontime":"20","serveraddress":"","serverpath":"","targeturl":null,"templateid":"1","testcount":"10","testinterval":"6","testmode":"time","testtime":"20","testtype":"call_csfbz","testurl":"","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"20","callphone":"10086","calltype":"Voice","exectimeout":"","filename":"","ftpmode":"","id":8,"password":"","port":"","retentiontime":"20","serveraddress":"","serverpath":"","targeturl":null,"templateid":"1","testcount":"10","testinterval":"6","testmode":"count","testtime":"20","testtype":"call_voltez","testurl":"","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"10","filename":"","ftpmode":"active","id":10,"password":"Ceping&2015","port":"21","retentiontime":"","serveraddress":"101.231.82.82","serverpath":"/upload","targeturl":null,"templateid":"1","testcount":"3","testinterval":"3","testmode":"timecount","testtime":"60","testtype":"ftp_up","testurl":"","threadcount":"3","timeout":"60","uploadsize":"100M","username":"ceping"},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"10","filename":"2M.rar","ftpmode":"active","id":11,"password":"Ceping&2015","port":"21","retentiontime":"","serveraddress":"101.231.82.82","serverpath":"/","targeturl":null,"templateid":"1","testcount":"3","testinterval":"3","testmode":"timecount","testtime":"60","testtype":"ftp_down","testurl":"","threadcount":"3","timeout":"60","uploadsize":"","username":"ceping"},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":3,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":{"name":"百度","url":"htttp://www.baidu.com"},"templateid":"1","testcount":"10","testinterval":"10","testmode":"count","testtime":"60","testtype":"ping","testurl":"{\"url\": \"htttp://www.baidu.com\", \"name\": \"百度\"}","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":4,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":{"name":"百度","url":"htttp://www.baidu.com"},"templateid":"1","testcount":"10","testinterval":"10","testmode":"count","testtime":"60","testtype":"http","testurl":"{\"url\": \"htttp://www.baidu.com\", \"name\": \"百度\"}","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":1,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":null,"templateid":"1","testcount":"","testinterval":"","testmode":"","testtime":"10","testtype":"idle","testurl":"","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":2,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":null,"templateid":"1","testcount":"","testinterval":"","testmode":"","testtime":"10","testtype":"wait","testurl":"","threadcount":"","timeout":"","uploadsize":"","username":""}]
         * templatename : 宏站单验模版
         * updatedate :
         * workorderno : HCC_test_0001
         */

        private String account;
        private String createdate;
        private int taskid;
        private int id;
        private String status;
        private String templatename;
        private String updatedate;
        private String workorderno;
        private List<TestTask> taskList;

        public int getTaskid() {
            return taskid;
        }

        public void setTaskid(int taskid) {
            this.taskid = taskid;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTemplatename() {
            return templatename;
        }

        public void setTemplatename(String templatename) {
            this.templatename = templatename;
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

        public List<TestTask> getTaskList() {
            return taskList;
        }

        public void setTaskList(List<TestTask> taskList) {
            this.taskList = taskList;
        }
    }
}
