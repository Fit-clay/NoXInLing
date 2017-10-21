package com.beidian.beidiannonxinling.bean;


public class PingResul {

	private String plateform = "未知";// ping目标
	private String netType = "未知";// 网络类型
	private String destination = "未知";// 目标地址
	private Integer timesTransfor = 0;// 发送次数
	private Integer timesReceiver = 0;// 接收次数
	private String rateOfSuccess = "0";// 成功率
	private String delay = "0";// 时延
	private String result = "未知";// 结果

	public PingResul() {
		super();
	}

	public PingResul(String netType, String destination, Integer timesTransfor, Integer timesReceiver,
					 String rateOfSuccess, String delay, String result) {
		super();
		this.netType = netType;
		this.destination = destination;
		this.timesTransfor = timesTransfor;
		this.timesReceiver = timesReceiver;
		this.rateOfSuccess = rateOfSuccess;
		this.delay = delay;
		this.result = result;
	}

	public String getPlateform() {
		return plateform;
	}

	public void setPlateform(String plateform) {
		this.plateform = plateform;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getTimesTransfor() {
		return timesTransfor;
	}

	public void setTimesTransfor(Integer timesTransfor) {
		this.timesTransfor = timesTransfor;
	}

	public Integer getTimesReceiver() {
		return timesReceiver;
	}

	public void setTimesReceiver(Integer timesReceiver) {
		this.timesReceiver = timesReceiver;
	}

	public String getRateOfSuccess() {
		return rateOfSuccess;
	}

	public void setRateOfSuccess(String rateOfSuccess) {
		this.rateOfSuccess = rateOfSuccess;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "[netType=" + netType + ", destination="
		// + destination
				+ ", timesTransfor=" + timesTransfor + ", timesReceiver=" + timesReceiver + ", rateOfSuccess="
				+ rateOfSuccess + ", delay=" + delay + "]";
	}
}
