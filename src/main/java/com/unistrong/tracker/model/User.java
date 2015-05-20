package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "us_user")
public class User extends IdEntity implements Serializable {
	private static final long serialVersionUID = -4143634935828884660L;

	@Column(name = "f_name", nullable = false)
	private String name;

	@Column(name = "f_password")
	private String password;

	@Column(name = "f_phone")
	private String phone;

	@Column(name = "f_email")
	private String email;

	@Column(name = "f_qq_id")
	private String qqId;

	@Column(name = "f_sina_id")
	private String sinaId;

	@Column(name = "f_rr_id")
	private String rrId;

	@Column(name = "f_parent_id")
	private Long parentId;

	@Column(name = "f_role")
	private Integer role;

	@Column(name = "f_nick")
	private String nick;

	@Column(name = "f_app_name")
	private String appName;

	@Column(name = "f_real")
	private String real;

	@Column(name = "f_address")
	private String address;

	@Column(name = "f_time")
	private Long time;

	@Column(name = "f_type")
	private Integer type;

	/**
	 * 用户模式： 1开发者模式or2后台模式
	 */
	@Column(name = "f_user_mode")
	private Integer userMode;

	/**
	 * 用户种类：1 企业or2个人
	 */
	@Column(name = "f_user_type")
	private Integer userType;
	
	/**
	 * 开发者的apiKey
	 */
	@Column(name = "f_api_key")
	private String apiKey;
	
	
	/**
	 * 所属服务平台用户id
	 */
	@Column(name = "f_service_id")
	private Long serviceId;
	
	
	/**
	 * 用户所属平台(1后台用户or2前台用户)
	 */
	@Column(name = "f_platform")
	private Integer platform;
	
	

	// *******************************************

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Integer getUserMode() {
		return userMode;
	}

	public void setUserMode(Integer userMode) {
		this.userMode = userMode;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User(Long id) {
		super();
		this.id = id;
	}

	public User() {
		super();
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			if (((User) obj).getId() == this.getId())
				return true;
		}
		return false;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getSinaId() {
		return sinaId;
	}

	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}

	public String getRrId() {
		return rrId;
	}

	public void setRrId(String rrId) {
		this.rrId = rrId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getReal() {
		return real;
	}

	public void setReal(String real) {
		this.real = real;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

}
