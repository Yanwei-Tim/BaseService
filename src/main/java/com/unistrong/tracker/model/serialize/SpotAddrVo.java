package com.unistrong.tracker.model.serialize;

public class SpotAddrVo {
	private Double lng;
	private Double lat;
	private Long receive;
	private String addr;

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

	@Override
	public String toString() {
		return "SpotAddrVo [lng=" + lng + ", lat=" + lat + ", receive=" + receive + ", addr="
				+ addr + "]";
	}

}
