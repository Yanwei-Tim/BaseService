package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;


@Entity
@Table(name = "us_obd_error_dictionary")
public class ObdErrorDictionary extends IdEntity implements Serializable {
	
	@Column(name = "f_code")
	private String code;
	@Column(name = "f_definition_ch")
	private String definition;
	@Column(name = "f_definition_en")
	private String definitionEn;
	@Column(name = "f_flag")
	private String flag;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getDefinitionEn() {
		return definitionEn;
	}
	public void setDefinitionEn(String definitionEn) {
		this.definitionEn = definitionEn;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
