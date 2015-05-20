package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
 * 当前位置 最新状态 点
 */
@Entity
@Table(name = "us_position")
public class Position implements Serializable {

	private static final long serialVersionUID = 3526563873288522236L;

	/** 接收时间 */
	@Column(name = "f_receive")
	private Long receive = 0L;

	// 后台处理时间
	@Transient
	private Long systime = 0L;

	@Column(name = "f_lng")
	private Double lng = 116.4985916018D;

	@Column(name = "f_lat")
	private Double lat = 39.9798650053D;

	/** 停留时间 秒 */
	@Column(name = "f_stayed")
	private Integer stayed = 0;

	/** 1:在线 2:离线 3:注销 4:过期 5:服务停止 */
	@Column(name = "f_status")
	private Integer status = 2;

	/** 速度 米/秒 */
	@Column(name = "f_speed")
	private Double speed = 0D;

	/** 方向 */
	@Column(name = "f_direction")
	private Float direction = 0F;

	/** 流量 */
	@Column(name = "f_flow")
	private Float flow = 0F;

	/** 电量 */
	@Column(name = "f_battery")
	private Integer battery = 0;

	/** 定位方式,如:AGPS:V GPS:A */
	@Column(name = "f_mode", length = 1)
	private String mode;

	/** 当天告警数 */
	@Column(name = "f_alarm")
	private Integer alarm = 0;

	@Id
	@Column(name = "f_device_sn", length = 28)
	private String deviceSn;

	@Transient
	private Long stamp;

	/**
	 * 备用字段----
	 */
	@Column(name = "f_info")
	private String info;

	/** 坐标系 */
	private String system;
	/** x点 */
	private Double x;
	/** y点 */
	private Double y;

	/** 室内定位标志 (1:室内定位) */
	private Integer indoor;

	// ************************************************
	public Long getReceive() {
		return receive;
	}

	public void setReceive(Long receive) {
		this.receive = receive;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Float getDirection() {
		return direction;
	}

	public void setDirection(Float direction) {
		this.direction = direction;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public Integer getStayed() {
		return stayed;
	}

	public void setStayed(Integer stayed) {
		this.stayed = stayed;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Position(String deviceSn) {
		super();
		this.deviceSn = deviceSn;
	}

	public Position() {
		super();
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Float getFlow() {
		return flow;
	}

	public void setFlow(Float flow) {
		this.flow = flow;
	}

	public Integer getBattery() {
		return battery;
	}

	public void setBattery(Integer battery) {
		this.battery = battery;
	}

	@Override
	public String toString() {
		return "Position [receive=" + receive + ", lng=" + lng + ", lat=" + lat
				+ ", stayed=" + stayed + ", status=" + status + ", speed="
				+ speed + ", direction=" + direction + ", flow=" + flow
				+ ", battery=" + battery + ", mode=" + mode + ", deviceSn="
				+ deviceSn + ", info=" + info + "]";
	}

	public Integer getAlarm() {
		return alarm;
	}

	public void setAlarm(Integer alarm) {
		this.alarm = alarm;
	}

	public Long getSystime() {
		return systime;
	}

	public void setSystime(Long systime) {
		this.systime = systime;
	}

	public Long getStamp() {
		return stamp;
	}

	public void setStamp(Long stamp) {
		this.stamp = stamp;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Integer getIndoor() {
		return indoor;
	}

	public void setIndoor(Integer indoor) {
		this.indoor = indoor;
	}

}
