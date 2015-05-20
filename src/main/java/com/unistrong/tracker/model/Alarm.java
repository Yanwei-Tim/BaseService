package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "us_alarms")
public class Alarm extends IdEntity implements Serializable {

	private static final long serialVersionUID = 8917163414864536685L;

	@Column(name = "f_time")
	private Long time;

	@Column(name = "f_systime")
	private Long systime;

	@Column(name = "f_read")
	private Integer read;// 1已读 2未读

	@Column(name = "f_type")
	private Integer type;// 告警类型

	@Column(name = "f_info")
	private String info;

	@Column(name = "f_lng")
	private Double lng;

	@Column(name = "f_lat")
	private Double lat;

	@Column(name = "f_device_sn", length = 28)
	private String deviceSn;

	@Column(name = "f_addr")
	private String addr = "";
	@Column(name = "f_addr_en")
	private String addrEn = "";

	@Column(name = "f_extra")
	private String extra;

	@Column(name = "f_speed")
	private Double speed;

	@Column(name = "f_battery")
	private Integer battery;

	@Column(name = "f_cell")
	private String cell;

	@Column(name = "f_acc")
	private Integer accMode;

	@Column(name = "f_rfid")
	private Integer mode433;

	@Column(name = "f_name")
	private String name;// 用户名或者物流锁开锁次数

	@Column(name = "f_lock")
	private Integer lock;// 物流锁开关状态（1开，0关）

	@Column(name = "f_system")
	private String system;// 坐标系统

	@Column(name = "f_px")
	private Double px;// 坐标x点

	@Column(name = "f_py")
	private Double py;// 坐标y点

	@Column(name = "f_indoor")
	private Integer indoor;// 室内定位标志(1：室内定位)

	// ************************************************

	public void setRead(Integer read) {
		this.read = read;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public Integer getAccMode() {
		return accMode;
	}

	public void setAccMode(Integer accMode) {
		this.accMode = accMode;
	}

	public Integer getMode433() {
		return mode433;
	}

	public void setMode433(Integer mode433) {
		this.mode433 = mode433;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Integer getRead() {
		return read;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Long getSystime() {
		return systime;
	}

	public void setSystime(Long systime) {
		this.systime = systime;
	}

	public String getAddrEn() {
		return addrEn;
	}

	public void setAddrEn(String addrEn) {
		this.addrEn = addrEn;
	}

	public Integer getBattery() {
		return battery;
	}

	public void setBattery(Integer battery) {
		this.battery = battery;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLock() {
		return lock;
	}

	public void setLock(Integer lock) {
		this.lock = lock;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Double getPx() {
		return px;
	}

	public void setPx(Double px) {
		this.px = px;
	}

	public Double getPy() {
		return py;
	}

	public void setPy(Double py) {
		this.py = py;
	}

	public Integer getIndoor() {
		return indoor;
	}

	public void setIndoor(Integer indoor) {
		this.indoor = indoor;
	}

}
