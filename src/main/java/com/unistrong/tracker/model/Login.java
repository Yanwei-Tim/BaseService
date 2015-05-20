package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "us_login")
public class Login extends IdEntity implements Serializable {
	private static final long serialVersionUID = -4143634935828884660L;

	@Column(name = "f_user_id")
	private Long userId;

	@Column(name = "f_service_id")
	private Long serviceId;
	
	@Column(name = "f_time")
	private Long time;

	// *******************************************

	public Login(Long id) {
		super();
		this.id = id;
	}

	public Login() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

}