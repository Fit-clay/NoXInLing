package com.beidian.beidiannonxinling.bean;


public class FtpTestResult {
	public String netType = "无";// 网络类型
	public String status ="begin";// ftp测试状态
	public double fileLength = 0;// 文件大小，默认1kb
	public long hadFinished = 0;// 下载或者上传已完成量
	public int progress;// 进度
	public Double speed = 0d;// 网速
	public Double avgSpeed = 0d;// 平均速度
	public int totalTime = 0;// 时间
	public int shakeCount = 0;// 抖动次数

	public void reset() {

		netType = "无";
		status = "begin";
		fileLength = 0;
		hadFinished = 0;
		progress = 0;
		speed = 0d;
		avgSpeed = 0d;
		totalTime = 0;
		shakeCount = 0;
	}

	@Override
	public String toString() {
		return "FtpTestResult{" +
				"netType='" + netType + '\'' +
				", status='" + status + '\'' +
				", fileLength=" + fileLength +
				", hadFinished=" + hadFinished +
				", progress=" + progress +
				", speed=" + speed +
				", avgSpeed=" + avgSpeed +
				", totalTime=" + totalTime +
				", shakeCount=" + shakeCount +
				'}';
	}
}
