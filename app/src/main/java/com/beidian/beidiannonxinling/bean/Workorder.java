package com.beidian.beidiannonxinling.bean;

import android.support.annotation.NonNull;

import com.beidian.beidiannonxinling.util.DateUtil;

import java.io.Serializable;

/**
 * <p>
 * 模版表
 * </p>
 *
 * @author ruiyi
 * @since 2017-08-22
 */
public class Workorder implements Comparable<Workorder>, Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 起始經緯度
	 */
	private String startY;

	private String startX;
	/**
	 * 距離
	 */
	private String Range;
	/**
	 * 用户
	 */
	private String account;
	/**
	 * 派单时间
	 */
	private String createtime;
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 纬度
	 */
	private Double latitude;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 基站名称
	 */
	private String sitename;
	/**
	 * 基站类型:室分、宏站，可扩展
	 */
	private String sitetype;
	/**
	 * 工单号
	 */
	private String workorderno;
	/**
	 * 工单状态(0:待处理，1已完成，2已提交)
	 */
	private String workorderstatus;
	/**
	 * 工单类型(勘察、单验，可扩展)
	 */
	private String workordertype;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Workorder{" +
				"startY='" + startY + '\'' +
				", startX='" + startX + '\'' +
				", Range='" + Range + '\'' +
				", account='" + account + '\'' +
				", createtime='" + createtime + '\'' +
				", id=" + id +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", sitename='" + sitename + '\'' +
				", sitetype='" + sitetype + '\'' +
				", workorderno='" + workorderno + '\'' +
				", workorderstatus='" + workorderstatus + '\'' +
				", workordertype='" + workordertype + '\'' +
				'}';
	}

	public String getRange() {
		return Range;
	}

	public void setRange(String range) {
		Range = range;
	}

	public String getStartY() {
		return startY;
	}

	public void setStartY(String startY) {
		this.startY = startY;
	}

	public String getStartX() {
		return startX;
	}

	public void setStartX(String startX) {
		this.startX = startX;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getSitetype() {
		return sitetype;
	}

	public void setSitetype(String sitetype) {
		this.sitetype = sitetype;
	}

	public String getWorkorderno() {
		return workorderno;
	}

	public void setWorkorderno(String workorderno) {
		this.workorderno = workorderno;
	}

	public String getWorkorderstatus() {
		return workorderstatus;
	}

	public void setWorkorderstatus(String workorderstatus) {
		this.workorderstatus = workorderstatus;
	}

	public String getWorkordertype() {
		return workordertype;
	}

	public void setWorkordertype(String workordertype) {
		this.workordertype = workordertype;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public int compareTo(@NonNull Workorder o) {
		return (int) (DateUtil.getTime1(this.getCreatetime()) - DateUtil.getTime1(o
				.getCreatetime()));
	}

}
