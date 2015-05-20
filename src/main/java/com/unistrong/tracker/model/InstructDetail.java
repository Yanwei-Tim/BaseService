package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "us_instruct_detail")
public class InstructDetail implements Serializable {

	private static final long serialVersionUID = -8164031615167124852L;

	@Id
	@Column(name = "f_id")
	private String id;

	@Column(name = "f_device_sn")
	private String deviceSn;

	@Column(name = "f_type")
	private Integer type;

	@Column(name = "f_content")
	private String content;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Instruct [id=" + id + ", deviceSn=" + deviceSn + ", type="
				+ type + ", content=" + content + "]";
	}

}