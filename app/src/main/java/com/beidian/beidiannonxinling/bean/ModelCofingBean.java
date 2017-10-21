package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;

/**
 * Created by ASUS on 2017/9/25.
 */

public class ModelCofingBean implements Serializable {

    private int id;// 测试项id

    private String name;// 测试项名称
    /**
     * Const.TEST_PING; Const.Const.TEST_FTP_UPLOAD; Const.TEST_FTP_DOWNLOAD;
     * Const.TEST_HTTP; Const.TEST_VIDEO; Const.TEST_TACH; Const.TEST_VOICE;
     * Const.TEST_WAIT; Const.TEST_IDLE;
     */
    private Byte testType;// 测试项类型

    private Integer testTimes;// 循环次数

    private Integer circleTime;// 循环间隔时长

    private Integer testTime;// 测试时长，在限时测试模式下的测试时长

    private Integer durationTime;//保持时间

    private Long modelId;// 模板Id;

    private Byte testModel;// 测试模式

    private Byte threadNum;// 测试线程数量

    private Integer noDataTime;// 停转超时

    private Integer outTime;// 超时时间

    private String connectPath;// ftp服务器路径

    private Integer rateMax;//限速

    private String connectIp;//ftp服务器ip

    private String userName;//ftp登录用户名

    private String password;//ftp登录密码

    private Integer port;//ftp连接端口

    private Byte passiveModel;//ftp连接模式，是否是主动

    private String url;//http、ping测试url

    private String targetName;//url对应的名称

    private Integer waitTime;//测试模板等待时间

    private Byte coordinateMode;//协调方式

    private Byte callType;//呼叫方式

    private String callNumber;//主叫时是呼叫号码，被叫时是打来的号码（接收号码）

    private Integer blockTime;//阻塞时间

    private boolean isDefault;//是否是默认的测试项

    private Long uploadsize;//上传大小(M)

    private boolean isCopy;

    private String targetUrlName;// 对应的TargetURL的名字

    private long targetUrlId;// 对应的TargetUrlId;

    private String allNotDefaultTargetUrlIds;

    private String fileName;//FTPDown 对应的文件名

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getTestType() {
        return testType;
    }

    public void setTestType(Byte testType) {
        this.testType = testType;
    }

    public Integer getTestTimes() {
        return testTimes;
    }

    public void setTestTimes(Integer testTimes) {
        this.testTimes = testTimes;
    }

    public Integer getCircleTime() {
            return circleTime;
    }

    public void setCircleTime(Integer circleTime) {
        this.circleTime = circleTime;
    }

    public Integer getTestTime() {
        return testTime;
    }

    public void setTestTime(Integer testTime) {
        this.testTime = testTime;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Byte getTestModel() {
        return testModel;
    }

    public void setTestModel(Byte testModel) {
        this.testModel = testModel;
    }

    public Byte getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Byte threadNum) {
        this.threadNum = threadNum;
    }

    public Integer getNoDataTime() {
        return noDataTime;
    }

    public void setNoDataTime(Integer noDataTime) {
        this.noDataTime = noDataTime;
    }

    public Integer getOutTime() {
        return outTime;
    }

    public void setOutTime(Integer outTime) {
        this.outTime = outTime;
    }

    public String getConnectPath() {
        return connectPath;
    }

    public void setConnectPath(String connectPath) {
        this.connectPath = connectPath;
    }

    public Integer getRateMax() {
        return rateMax;
    }

    public void setRateMax(Integer rateMax) {
        this.rateMax = rateMax;
    }

    public String getConnectIp() {
        return connectIp;
    }

    public void setConnectIp(String connectIp) {
        this.connectIp = connectIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Byte getPassiveModel() {
        return passiveModel;
    }

    public void setPassiveModel(Byte passiveModel) {
        this.passiveModel = passiveModel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public Byte getCoordinateMode() {
        return coordinateMode;
    }

    public void setCoordinateMode(Byte coordinateMode) {
        this.coordinateMode = coordinateMode;
    }

    public Byte getCallType() {
        return callType;
    }

    public void setCallType(Byte callType) {
        this.callType = callType;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public Integer getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Integer blockTime) {
        this.blockTime = blockTime;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Long getUploadsize() {
        return uploadsize;
    }

    public void setUploadsize(Long uploadsize) {
        this.uploadsize = uploadsize;
    }

    public boolean isCopy() {
        return isCopy;
    }

    public void setCopy(boolean copy) {
        isCopy = copy;
    }

    public String getTargetUrlName() {
        return targetUrlName;
    }

    public void setTargetUrlName(String targetUrlName) {
        this.targetUrlName = targetUrlName;
    }

    public long getTargetUrlId() {
        return targetUrlId;
    }

    public void setTargetUrlId(long targetUrlId) {
        this.targetUrlId = targetUrlId;
    }

    public String getAllNotDefaultTargetUrlIds() {
        return allNotDefaultTargetUrlIds;
    }

    public void setAllNotDefaultTargetUrlIds(String allNotDefaultTargetUrlIds) {
        this.allNotDefaultTargetUrlIds = allNotDefaultTargetUrlIds;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
