package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 2017/9/5.
 */

public class CQTTestModelBean implements Serializable{

    /**
     * template : [{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":1,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":"","testcount":"","testinterval":"","testtime":"10","testtype":"idle","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":2,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":"","testcount":"","testinterval":"","testtime":"10","testtype":"wait","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":3,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":"{\"url\": \"htttp://www.baidu.com\", \"name\": \"百度\"}","testcount":"10","testinterval":"10","testtime":"60","testtype":"ping","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":4,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":"{\"url\": \"htttp://www.baidu.com\", \"name\": \"百度\"}","testcount":"10","testinterval":"10","testtime":"60","testtype":"http","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"","filename":"","ftpmode":"","id":5,"password":"","port":"","retentiontime":"","serveraddress":"","serverpath":"","targeturl":"","testcount":"10","testinterval":"10","testtime":"60","testtype":"attach","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"20","callphone":"10086","calltype":"Voice","exectimeout":"","filename":"","ftpmode":"","id":6,"password":"","port":"","retentiontime":"20","serveraddress":"","serverpath":"","targeturl":"","testcount":"10","testinterval":"6","testtime":"20","testtype":"call_csfbz","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"20","callphone":"10086","calltype":"Voice","exectimeout":"","filename":"","ftpmode":"","id":7,"password":"","port":"","retentiontime":"20","serveraddress":"","serverpath":"","targeturl":"","testcount":"10","testinterval":"6","testtime":"20","testtype":"call_csfbb","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"20","callphone":"10086","calltype":"Voice","exectimeout":"","filename":"","ftpmode":"","id":8,"password":"","port":"","retentiontime":"20","serveraddress":"","serverpath":"","targeturl":"","testcount":"10","testinterval":"6","testtime":"20","testtype":"call_voltez","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"20","callphone":"10086","calltype":"Voice","exectimeout":"","filename":"","ftpmode":"","id":9,"password":"","port":"","retentiontime":"20","serveraddress":"","serverpath":"","targeturl":"","testcount":"10","testinterval":"6","testtime":"20","testtype":"call_volteb","threadcount":"","timeout":"","uploadsize":"","username":""},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"10","filename":"","ftpmode":"active","id":10,"password":"Ceping&2015","port":"21","retentiontime":"","serveraddress":"101.231.82.82","serverpath":"/upload","targeturl":"","testcount":"3","testinterval":"3","testtime":"60","testtype":"ftp_up","threadcount":"3","timeout":"60","uploadsize":"100M","username":"ceping"},{"blockingtime":"","callphone":"","calltype":"","exectimeout":"10","filename":"2M.rar","ftpmode":"active","id":11,"password":"Ceping&2015","port":"21","retentiontime":"","serveraddress":"101.231.82.82","serverpath":"/","targeturl":"","testcount":"3","testinterval":"3","testtime":"60","testtype":"ftp_down","threadcount":"3","timeout":"60","uploadsize":"","username":"ceping"}]
     * templateName : 宏站单验模版
     */

    private String templateName;
    private List<TestTask> template;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<TestTask> getTemplate() {
        return template;
    }

    public void setTemplate(List<TestTask> template) {
        this.template = template;
    }
}
