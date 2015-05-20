package com.unistrong.tracker.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * 
 * @author xubin
 */
@Entity
@Table(name = "us_user_jilin")
public class UserJiLin implements Serializable {

	private static final long serialVersionUID = 7116853441940985031L;

	
	@Id
	@Column(name = "f_device_sn")
	private String deviceSn;

	@Column(name = "f_user_name")
	private String userName;

	@Column(name = "f_user_sex")
	private int userSex = 1;//性别

	@Column(name = "f_user_phone")
	private String phone;

	@Column(name = "f_user_number")
	private String number ;// 身份证号

	@Column(name = "f_user_address")
	private String address;

	@Column(name = "f_guarder")
	private String guarder;// 监护人

	@Column(name = "f_renew")
	private String renew;// 续费情况

	@Column(name = "f_remark")
	private String remark;// 备注


	public UserJiLin() {
		super();
	}


	public String getDeviceSn() {
		return deviceSn;
	}


	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getUserSex() {
		return userSex;
	}


	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getGuarder() {
		return guarder;
	}


	public void setGuarder(String guarder) {
		this.guarder = guarder;
	}


	public String getRenew() {
		return renew;
	}


	public void setRenew(String renew) {
		this.renew = renew;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}