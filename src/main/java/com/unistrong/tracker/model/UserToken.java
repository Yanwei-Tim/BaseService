package com.unistrong.tracker.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.unistrong.tracker.util.UsConstants;

import module.util.SpecialStrUtil;

@Entity
@Table(name = "us_user_token")
public class UserToken implements Serializable {
	private static final long serialVersionUID = -4143634935828884660L;

	public static final long EXPIRE = 604800000;
	
	@Id
	@Column(name = "f_user_id")
	private Long userId;

	@Column(name = "f_token")
	private String token;

	@Column(name = "f_expire")
	private Long expire;

	// **************************************
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserToken(Long userId) {
		super();
		this.userId = userId;
		this.token = SpecialStrUtil.getUuid();
		long expire = new Date().getTime() + EXPIRE;
		this.expire = expire;
	}

	public UserToken() {
		super();
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

}
