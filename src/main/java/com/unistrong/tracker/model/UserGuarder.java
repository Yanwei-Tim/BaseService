package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import module.orm.IdEntity;

/**
 * 
 * 
 * @author xubin
 */
@Entity
@Table(name = "us_user_guarder")
public class UserGuarder extends IdEntity implements Serializable {

	private static final long serialVersionUID = 7116853441940985031L;

	@Column(name = "f_guarder_name")
	private String guarderName;

	@Column(name = "f_guarder_phone")
	private String phone;

	@Column(name = "f_guarder_address")
	private String address;

	@Column(name = "f_guarder_company")
	private String company;// 单位
	
	@Transient
	private String sn;


	public UserGuarder() {
		super();
	}


	public String getGuarderName() {
		return guarderName;
	}


	public void setGuarderName(String guarderName) {
		this.guarderName = guarderName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getSn() {
		return sn;
	}


	public void setSn(String sn) {
		this.sn = sn;
	}

}