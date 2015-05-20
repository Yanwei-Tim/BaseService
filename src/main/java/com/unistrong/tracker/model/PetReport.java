package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * pet统计报告
 * 
 * @author fss
 */
@Entity
@Table(name = "us_pet_report")
public class PetReport implements Serializable {

	private static final long serialVersionUID = 7116853441940985031L;

	@Id
	@Column(name = "f_id")
	@JsonIgnore
	private String id;

	@Column(name = "f_device_sn")
	private String deviceSn;

	@Column(name = "f_time")
	private Long time;

	@Column(name = "f_energy")
	private int energy;
	
	@Column(name = "f_rest")
	@JsonIgnore
	private Double rest = 0D;
	
	@Column(name = "f_slow")
	@JsonIgnore
	private Double slow = 0D;
	
	@Column(name = "f_fast")
	@JsonIgnore
	private Double fast = 0D;;

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public Double getRest() {
		return rest;
	}

	public void setRest(Double rest) {
		this.rest = rest;
	}

	public Double getSlow() {
		return slow;
	}

	public void setSlow(Double slow) {
		this.slow = slow;
	}

	public Double getFast() {
		return fast;
	}

	public void setFast(Double fast) {
		this.fast = fast;
	}

	public PetReport(String deviceSn, Long time) {
		super();
		this.id = deviceSn + "-" + time;
		this.deviceSn = deviceSn;
		this.time = time;
	}

	public PetReport() {
		super();
	}
}