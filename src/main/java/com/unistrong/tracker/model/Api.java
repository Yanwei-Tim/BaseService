package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "us_api")
public class Api extends IdEntity implements Serializable {

	private static final long serialVersionUID = 8917163414864536685L;

	@Column(name = "f_api")
	private String api;

	@Column(name = "f_api_name")
	private String apiName;

	@Column(name = "f_status")
	private Integer status = 1;

	// ************************************************

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}