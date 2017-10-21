package com.beidian.beidiannonxinling.bean;

import java.io.Serializable;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/2323:08
 * @描述: 测试结果类
 */
public class TestResult implements Serializable {

    private String testType       = "未知";//测试类型
    private String resultType     = "未知";//结果类型
    private int    globalTestTime = 0;//当前测试时间,整个测试流程的时间钟
    private long   currentCount   = 0;//当前任务执行的总次数
    private int    currentTime    = 0;//当前时间,当前测试项目的时间钟
    //文件上传下载
    private long   fileSize       = 0;    //文件总大小
    private long   progress       = 0;// 进度
    private long   overfileSize   = 0;    //进度
    private long   speed          = 0;// 速率      ftp上传下载
    private long   avgSpeed       = 0;// 平均速率   Http延时
    private long   maxSpeed       = 0;// 最大速率
    //call测试


    private int    retentionTime = 0;// 保持时间、
    private int    tryCall       = 0;// 尝试测试、
    private int    success       = 0;// 成功次数、
    private int    fail          = 0;// 失败次数、
    private double successRate   = 0;// 成功率     接通率
    private boolean failOrSuccess;// 是否成功
    //http
    public float costTime = 0;// 时间
    private float    delay;// 延时      ping延时
    //小区名
    private String cellName="";
    //   ENB
    private String enb="";
    //  CI
    private String ci="";
    // PCI
    private String pci="";
    ///  EARFCN
    private String earfcn="";
    //  RSRP
    private String rsrp="";
    // SINR
    private String sinr="";
    //  速率DL
    private String speedDL="";
    //   速率UL
    private String speedUL="";
    // 经度
    private String lng="0.0";
    //   维度
    private String lat="0.0";
    //测试模板
    private TestTask testTask;

    private boolean isMobile;

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    private float x;
    private float y;
    private int color=-1;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public TestTask getTestTask() {
        return testTask;
    }

    public void setTestTask(TestTask testTask) {
        this.testTask = testTask;
    }

    public long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(long currentCount) {
        this.currentCount = currentCount;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public int getGlobalTestTime() {
        return globalTestTime;
    }

    public void setGlobalTestTime(int globalTestTime) {
        this.globalTestTime = globalTestTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getOverfileSize() {
        return overfileSize;
    }

    public void setOverfileSize(long overfileSize) {
        this.overfileSize = overfileSize;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(long avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public long getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(long maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(int retentionTime) {
        this.retentionTime = retentionTime;
    }

    public int getTryCall() {
        return tryCall;
    }

    public void setTryCall(int tryCall) {
        this.tryCall = tryCall;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public boolean isFailOrSuccess() {
        return failOrSuccess;
    }

    public void setFailOrSuccess(boolean failOrSuccess) {
        this.failOrSuccess = failOrSuccess;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getEnb() {
        return enb;
    }

    public void setEnb(String enb) {
        this.enb = enb;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(String earfcn) {
        this.earfcn = earfcn;
    }

    public String getRsrp() {
        return rsrp;
    }

    public void setRsrp(String rsrp) {
        this.rsrp = rsrp;
    }

    public String getSinr() {
        return sinr;
    }

    public void setSinr(String sinr) {
        this.sinr = sinr;
    }

    public String getSpeedDL() {
        return speedDL;
    }

    public void setSpeedDL(String speedDL) {
        this.speedDL = speedDL;
    }

    public String getSpeedUL() {
        return speedUL;
    }

    public void setSpeedUL(String speedUL) {
        this.speedUL = speedUL;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public float getCostTime() {
        return costTime;
    }

    public void setCostTime(float costTime) {
        this.costTime = costTime;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "testType='" + testType + '\'' +
                ", resultType='" + resultType + '\'' +
                ", globalTestTime=" + globalTestTime +
                ", currentCount=" + currentCount +
                ", currentTime=" + currentTime +
                ", fileSize=" + fileSize +
                ", progress=" + progress +
                ", overfileSize=" + overfileSize +
                ", speed=" + speed +
                ", avgSpeed=" + avgSpeed +
                ", maxSpeed=" + maxSpeed +
                ", retentionTime=" + retentionTime +
                ", tryCall=" + tryCall +
                ", success=" + success +
                ", fail=" + fail +
                ", successRate=" + successRate +
                ", failOrSuccess=" + failOrSuccess +
                ", cellName='" + cellName + '\'' +
                ", enb='" + enb + '\'' +
                ", ci='" + ci + '\'' +
                ", pci='" + pci + '\'' +
                ", earfcn='" + earfcn + '\'' +
                ", rsrp='" + rsrp + '\'' +
                ", sinr='" + sinr + '\'' +
                ", speedDL='" + speedDL + '\'' +
                ", speedUL='" + speedUL + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
