package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import module.orm.IdEntity;

@Entity
@Table(name = "us_api_doc")
public class ApiDoc extends IdEntity implements Serializable {

	private static final long serialVersionUID = 8917163414864536685L;

	@Column(name = "f_api")
	private String api;

	@Column(name = "f_api_name")
	private String apiName;

	@Column(name = "f_desc")
	private String desc;

	@Column(name = "f_model_id")
	private Long modelId;

	@Transient
	private String modelName;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
}