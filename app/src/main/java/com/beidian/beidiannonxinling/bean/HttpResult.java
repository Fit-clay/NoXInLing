package com.beidian.beidiannonxinling.bean;

/*
 * http测试结果类
 */
public class HttpResult {
	public String status = "未知";// 状态
	public String netType = "未知";// 网络类型
	public String target = "未知";// 目标
	public String targetName = "未知";// 目标名字
	public float progress = 0;// 进度
	public float size = 0;// 总量
	public float costTime = 0;// 时间
	public float feelTime = 0;// 感知



	public void setProgress(float progress) {
		this.progress = progress;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getCostTime() {
		return costTime;
	}

	public void setCostTime(float costTime) {
		this.costTime = costTime;
	}

	public float getFeelTime() {
		return feelTime;
	}

	public float getProgress() {
		return progress;
	}

	public float getSpeed() {
		return speed;
	}

	public void setFeelTime(float feelTime) {
		this.feelTime = feelTime;
	}



	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float speed = 0;// 速率

	@Override
	public String toString() {
		return "HttpResult{" +
				"状态='" + status + '\'' +
				", 网络类型='" + netType + '\'' +
				", 目标='" + target + '\'' +
				", 目标名字='" + targetName + '\'' +
				", 进度=" + progress +
				", 总量=" + size +
				", 时间=" + costTime +
				", 感知=" + feelTime +
				", 速率=" + speed +
				'}';
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

}
