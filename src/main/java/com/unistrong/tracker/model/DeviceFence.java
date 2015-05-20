package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "us_device_fence")
public class DeviceFence implements Serializable {

	private static final long serialVersionUID = -2639680569543322880L;

	@Id
	@Column(name = "f_id")
	private Long id;

	@Column(name = "f_sn")
	private String sn;

	@Column(name = "f_fence_id")
	private Long fenceId;

	// 围栏报警开关：1开; 2关
	@Column(name = "f_fence_switch")
	private Integer fenceSwitch = 1;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public DeviceFence() {
		super();
	}

	public Integer getFenceSwitch() {
		return fenceSwitch;
	}

	public void setFenceSwitch(Integer fenceSwitch) {
		this.fenceSwitch = fenceSwitch;
	}

	@Override
	public String toString() {
		return "DeviceFence [sn=" + sn + " ,fenceId=" + fenceId
				+ ",  fenceSwitch=" + fenceSwitch + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFenceId() {
		return fenceId;
	}

	public void setFenceId(Long fenceId) {
		this.fenceId = fenceId;
	}

}