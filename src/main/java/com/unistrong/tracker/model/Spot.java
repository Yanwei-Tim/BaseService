package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 位置 点
 */
@Entity
@Table(name = "us_spot")
@IdClass(value = IdSpot.class)
public class Spot implements Serializable {

	private static final long serialVersionUID = -3261367063305632658L;

	// 上传时间
	@Id
	@Column(name = "f_receive")
	private Long receive = 0L;

	// 后台处理时间
	@Column(name = "f_systime")
	private Long systime = 0L;

	@Column(name = "f_lng")
	private Double lng = 0D;

	@Column(name = "f_lat")
	private Double lat = 0D;

	// 停留时间 秒
	@Column(name = "f_stayed")
	private Integer stayed;

	// 与上个点的里程
	@Column(name = "f_distance")
	private Double distance = 0D;

	// 速度
	@Column(name = "f_speed")
	private Double speed = 0D;

	// 方向
	@Column(name = "f_direction")
	private Float direction = 0F;

	// 定位方式,如:AGPS:V GPS:A
	@Column(name = "f_mode", length = 1)
	private String mode;

	@Id
	@Column(name = "f_device_sn", length = 28)
	private String deviceSn;

	/**
	 * 备用字段----
	 */
	@Column(name = "f_info")
	private String info;

	@Column(name = "f_cell")
	private String cell;

	@Column(name = "f_acc")
	private int accMode;

	@Column(name = "f_rfid")
	private int mode433;

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

	// *******************************************

	public String getInfo() {
		return info;
	}

	public int getAccMode() {
		return accMode;
	}

	public void setAccMode(int accMode) {
		this.accMode = accMode;
	}

	public int getMode433() {
		return mode433;
	}

	public void setMode433(int mode433) {
		this.mode433 = mode433;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Long getReceive() {
		return receive;
	}

	public void setReceive(Long receive) {
		this.receive = receive;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Long getSystime() {
		return systime;
	}

	public void setSystime(Long systime) {
		this.systime = systime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLock() {
		return lock;
	}

	public void setLock(int lock) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deviceSn == null) ? 0 : deviceSn.hashCode());
		result = prime * result + ((lat == null) ? 0 : lat.hashCode());
		result = prime * result + ((lng == null) ? 0 : lng.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Spot other = (Spot) obj;
		if (deviceSn == null) {
			if (other.deviceSn != null)
				return false;
		} else if (!deviceSn.equals(other.deviceSn))
			return false;
		if (lat == null) {
			if (other.lat != null)
				return false;
		} else if (!lat.equals(other.lat))
			return false;
		if (lng == null) {
			if (other.lng != null)
				return false;
		} else if (!lng.equals(other.lng))
			return false;
		return true;
	}

}
