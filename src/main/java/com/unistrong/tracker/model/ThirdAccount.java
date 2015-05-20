package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "us_third_account")
public class ThirdAccount extends IdEntity implements Serializable {

	private static final long serialVersionUID = -9090124884504569770L;

	@Column(name = "f_type")
	private String type;

	@Column(name = "f_account")
	private String account;

	@Column(name = "f_user_id")
	private Integer userId;

	// *******************************************
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
