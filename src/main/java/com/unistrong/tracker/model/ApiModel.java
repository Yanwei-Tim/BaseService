package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "us_api_model")
public class ApiModel extends IdEntity implements Serializable {

	private static final long serialVersionUID = 8917163414864536685L;

	@Column(name = "f_model_name")
	private String modelName;

	@Column(name = "f_model_desc")
	private String modelDesc;

	// ************************************************

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

}