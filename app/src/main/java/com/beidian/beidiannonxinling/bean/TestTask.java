package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/2116:09
 * @描述: ------------------------------------------
 */

public class TestTask implements Serializable {
    /**
     * taskname 子模板名称
     */

    private String taskname;


    /**
     * blockingtime : 阻塞时间
     * callphone : 被叫号码
     * calltype : 呼叫方式
     * coordinatemode : 协调方式
     * exectimeout : 执行超时
     * filename : 文件名
     * ftpmode : ftp模式
     * id : 1
     * password : 密码
     * port : 端口
     * retentiontime : 保持时间
     * serveraddress : 服务器地址
     * serverpath : 服务器路径
     * targeturl : {"name":"百度","url":"htttp://www.baidu.com "}
     * testcount : 测试次数
     * testinterval : 测试间隔
     * testmode : 测试模式:计时,计次,限时计次
     * testtime : 测试时长
     * testnumber : 限时计次
     * testtype : 测试类型
     * threadcount : 线程数量
     * timeout : 停传超时
     * uploadsize : 上传大小
     * username : 用户名
     *
     */

    /**
     *wait 相关
     * waittime : 等待时间
     */
    private Boolean       isComeFromService;
    private String        blockingtime;
    private String        callphone;
    private String        coordinatemode;
    private String        calltype;
    private String        exectimeout;
    private String        filename;
    private String        ftpmode;
    private int           id;
    private int           taskid;
    private String        password;
    private String        port;
    private String        retentiontime;
    private String        serveraddress;
    private String        serverpath;
    private String        waittime;
    /**
     * name : 百度
     * url : htttp://www.baidu.com
     */

    private TargetUrlBean targeturl;
    private String        testinterval;
    private String        testmode;
    private String        testnumber;
    private String        testcount;
    private String        testtime;
    private String        testtype;
    private String        threadcount;
    private String        timeout;
    private String        uploadsize;
    private String        username;

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getWaittime() {
        return waittime;
    }

    public void setWaittime(String waittime) {
        this.waittime = waittime;
    }

    public String getCoordinatemode() {
        return coordinatemode;
    }

    public void setCoordinatemode(String coordinatemode) {
        this.coordinatemode = coordinatemode;
    }

    public Boolean getComeFromService() {
        return isComeFromService;
    }

    public void setComeFromService(Boolean comeFromService) {
        isComeFromService = comeFromService;
    }

    public String getTestnumber() {
        return testnumber;
    }

    public void setTestnumber(String testnumber) {
        this.testnumber = testnumber;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getBlockingtime() {
        return blockingtime;
    }

    public void setBlockingtime(String blockingtime) {
        this.blockingtime = blockingtime;
    }

    public String getCallphone() {
        return callphone;
    }

    public void setCallphone(String callphone) {
        this.callphone = callphone;
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }

    public String getExectimeout() {
        return exectimeout;
    }

    public void setExectimeout(String exectimeout) {
        this.exectimeout = exectimeout;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFtpmode() {
        return ftpmode;
    }

    public void setFtpmode(String ftpmode) {
        this.ftpmode = ftpmode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRetentiontime() {
        return retentiontime;
    }

    public void setRetentiontime(String retentiontime) {
        this.retentiontime = retentiontime;
    }

    public String getServeraddress() {
        return serveraddress;
    }

    public void setServeraddress(String serveraddress) {
        this.serveraddress = serveraddress;
    }

    public String getServerpath() {
        return serverpath;
    }

    public void setServerpath(String serverpath) {
        this.serverpath = serverpath;
    }

    public TargetUrlBean getTargeturl() {
        return targeturl;
    }

    public void setTargeturl(TargetUrlBean targeturl) {
        this.targeturl = targeturl;
    }

    public String getTestcount() {
        return testcount;
    }

    public void setTestcount(String testcount) {
        this.testcount = testcount;
    }

    public String getTestinterval() {
        return testinterval;
    }

    public void setTestinterval(String testinterval) {
        this.testinterval = testinterval;
    }

    public String getTestmode() {
        return testmode;
    }

    public void setTestmode(String testmode) {
        this.testmode = testmode;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public String getThreadcount() {
        return threadcount;
    }

    public void setThreadcount(String threadcount) {
        this.threadcount = threadcount;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getUploadsize() {
        return uploadsize;
    }

    public void setUploadsize(String uploadsize) {
        this.uploadsize = uploadsize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public static class TargetUrlBean implements Serializable{
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
