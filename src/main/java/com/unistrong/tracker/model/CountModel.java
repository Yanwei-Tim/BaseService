package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "us_count_model")
public class CountModel implements Serializable {

	private static final long serialVersionUID = 3267646008244221814L;

	@Id
	@Column(name = "f_device_sn")
	private String deviceSn;

	@Id
	@Column(name = "f_fence_id")
	private Long fenceId;

	@Column(name = "f_num")
	private Integer num;

	// ***********************************

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public Long getFenceId() {
		return fenceId;
	}

	public void setFenceId(Long fenceId) {
		this.fenceId = fenceId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public CountModel() {
		super();
	}

	public String toString() {
		return "CountModel [deviceSn=" + deviceSn + ", fenceId=" + fenceId
				+ ", num=" + num + "]";
	}

}
