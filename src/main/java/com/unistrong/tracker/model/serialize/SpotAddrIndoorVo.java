package com.unistrong.tracker.model.serialize;

public class SpotAddrIndoorVo {
	private Double lng;
	private Double lat;
	private Long receive;
	private String addr;
	private String system;// 坐标系统
	private Double px;// 坐标x点
	private Double py;// 坐标y点
	private Integer indoor;// 室内定位标志(1：室内定位)

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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Long getReceive() {
		return receive;
	}

	public void setReceive(Long receive) {
		this.receive = receive;
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
	public String toString() {
		return "SpotAddrIndoorVo [lng=" + lng + ", lat=" + lat + ", receive="
				+ receive + ", addr=" + addr + ",system=" + system + ",px="
				+ px + ",py=" + py + ",indoor=" + indoor + "]";
	}

}
