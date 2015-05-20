package com.unistrong.tracker.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author zhangxianpeng
 * 
 */
@Entity
@Table(name = "us_device_stat")
public class DeviceStat implements Serializable {

	private static final long serialVersionUID = -2639680569543322880L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "f_id")
	private Long fId;

	/**
	 * 服务后台用户ID
	 */
	@Column(name = "f_service_id")
	private Long fServiceId;

	/**
	 * 统计时间
	 */
	@Column(name = "f_stat_date")
	private Date fStatDate;

	/**
	 * 总设备数
	 */
	@Column(name = "f_total_device")
	private Integer fTotalDevice;

	/**
	 * 新绑定数
	 */
	@Column(name = "f_install_device")
	private Integer fInstallDevice;

	/**
	 * 已绑定数
	 */
	@Column(name = "f_install_total_device")
	private Integer fInstallTotalDevice;

	/**
	 * 已绑定占总设备比率
	 */
	@Column(name = "f_install_rate")
	private Double fInstallRate;

	/**
	 * 新绑定占已绑定设备比率
	 */
	@Column(name = "f_new_install_rate")
	private Double fNewInstallRate;

	// ******************************************


	public Long getfServiceId() {
		return fServiceId;
	}

	public void setfServiceId(Long fServiceId) {
		this.fServiceId = fServiceId;
	}

	public Date getfStatDate() {
		return fStatDate;
	}

	public void setfStatDate(Date fStatDate) {
		this.fStatDate = fStatDate;
	}

	public Integer getfTotalDevice() {
		return fTotalDevice;
	}

	public void setfTotalDevice(Integer fTotalDevice) {
		this.fTotalDevice = fTotalDevice;
	}

	public Integer getfInstallDevice() {
		return fInstallDevice;
	}

	public void setfInstallDevice(Integer fInstallDevice) {
		this.fInstallDevice = fInstallDevice;
	}

	public Integer getfInstallTotalDevice() {
		return fInstallTotalDevice;
	}

	public void setfInstallTotalDevice(Integer fInstallTotalDevice) {
		this.fInstallTotalDevice = fInstallTotalDevice;
	}

	public Double getfInstallRate() {
		return fInstallRate;
	}

	public void setfInstallRate(Double fInstallRate) {
		this.fInstallRate = fInstallRate;
	}

	public Long getfId() {
		return fId;
	}

	public void setfId(Long fId) {
		this.fId = fId;
	}

	public Double getfNewInstallRate() {
		return fNewInstallRate;
	}

	public void setfNewInstallRate(Double fNewInstallRate) {
		this.fNewInstallRate = fNewInstallRate;
	}

}