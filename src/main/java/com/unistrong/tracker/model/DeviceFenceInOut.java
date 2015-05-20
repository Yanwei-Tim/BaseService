package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "us_device_fence_inout")
public class DeviceFenceInOut implements Serializable {

	private static final long serialVersionUID = -2639680569543322880L;

	@Id
	@Column(name = "f_id")
	private Long id;

	@Column(name = "f_sn")
	private String sn;

	@Column(name = "f_fence_id")
	private Long fenceId;

	@Column(name = "f_time")
	private Long time;

	@Column(name = "f_type")
	private Integer type;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public DeviceFenceInOut() {
		super();
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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "DeviceFenceCount [sn=" + sn + " ,fenceId=" + fenceId
				+ ",  time=" + time + ",type=" + type + "]";
	}

}