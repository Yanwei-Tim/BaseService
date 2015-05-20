package com.unistrong.tracker.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 指挥机
 * 
 * @author wyr
 *
 */
@Entity
@Table(name = "rd_conductor")
public class Conductor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6395187318565034141L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "sn")
	private String sn = "";

	@Column(name = "name")
	private String name;

	//ec_user id (企业)
	@Column(name = "user_Id")
	private long userId;

	@Column(name="create_date")
	private Date createDate = new Date();
	
	@Column(name = "accmode")
	private String accMode;//类型
	
	@Column(name = "address")
	private String conductor_addr;//地址
	
	@Column(name = "frequentness")
	private String frequentness;//频度
	
	@Column(name = "type")
	private String conductor_type;//类型
	
	@Column(name = "level")
	private String conductor_level;//通信等级
	
	@Column(name = "encryption")
	private String encryption;//是否加密
	
	@Column(name = "bean")
	private String conductor_bean;//入站波束
	
	@Column(name = "userno")
	private String user_no;//所辖用户数
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Conductor() {
		super();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAccMode() {
		return accMode;
	}

	public void setAccMode(String accMode) {
		this.accMode = accMode;
	}

	public String getConductor_addr() {
		return conductor_addr;
	}

	public void setConductor_addr(String conductor_addr) {
		this.conductor_addr = conductor_addr;
	}

	public String getFrequentness() {
		return frequentness;
	}

	public void setFrequentness(String frequentness) {
		this.frequentness = frequentness;
	}

	public String getConductor_type() {
		return conductor_type;
	}

	public void setConductor_type(String conductor_type) {
		this.conductor_type = conductor_type;
	}

	public String getConductor_level() {
		return conductor_level;
	}

	public void setConductor_level(String conductor_level) {
		this.conductor_level = conductor_level;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public String getConductor_bean() {
		return conductor_bean;
	}

	public void setConductor_bean(String conductor_bean) {
		this.conductor_bean = conductor_bean;
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	
}
