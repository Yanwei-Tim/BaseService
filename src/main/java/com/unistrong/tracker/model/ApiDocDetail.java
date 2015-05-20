package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "us_api_doc_detail")
public class ApiDocDetail extends IdEntity implements Serializable {

	private static final long serialVersionUID = 8917163414864536685L;

	@Column(name = "f_name")
	private String name;

	@Column(name = "f_type")
	private String type;

	@Column(name = "f_desc")
	private String desc;

	@Column(name = "f_must")
	private String must;

	@Column(name = "f_return_type")
	private Integer returnType;

	@Column(name = "f_doc_id")
	private Integer docId;

	@Column(name = "f_json")
	private String json;

	// ************************************************

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMust() {
		return must;
	}

	public void setMust(String must) {
		this.must = must;
	}

	public Integer getReturnType() {
		return returnType;
	}

	public void setReturnType(Integer returnType) {
		this.returnType = returnType;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}