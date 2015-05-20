package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import module.util.JsonUtils;

@Entity
@Table(name = "us_fence")
public class Fences implements Serializable {

	private static final long serialVersionUID = -2639680569543322880L;

	@Id
	@Column(name = "f_id")
	private Long id;

	/**
	 * 坐标系
	 */
	@Column(name = "f_system")
	private String system;

	/**
	 * 1室内，2室外
	 */
	@Column(name = "f_type")
	private Integer type;

	@Column(name = "f_fence_json")
	private String fenceJson;

	public IndoorFence getFence() {
		IndoorFence f = null;
		if (this.fenceJson != null && !"".equals(fenceJson)) {
			f = JsonUtils.str2Obj(fenceJson, IndoorFence.class);
		}
		return f;
	}

	public void setFence(IndoorFence f) {
		this.fenceJson = JsonUtils.getJson(f);
	}

	public Fences() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFenceJson() {
		return fenceJson;
	}

	public void setFenceJson(String fenceJson) {
		this.fenceJson = fenceJson;
	}

	@Override
	public String toString() {
		return "Fences [type=" + type + ",system=" + system + ", fence="
				+ fenceJson + "]";
	}

}