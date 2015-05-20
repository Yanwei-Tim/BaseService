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
@Table(name = "us_user_stat")
public class UserStat implements Serializable {

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
	 * 新增用户
	 */
	@Column(name = "f_user_new")
	private Integer fUserNew;

	/**
	 * 累计用户
	 */
	@Column(name = "f_user_total")
	private Integer fUserTotal;

	/**
	 * 活跃用户
	 */
	@Column(name = "f_user_active")
	private Integer fUserActive;

	/**
	 * 同比
	 */
	@Column(name = "f_month_rate")
	private Double fMonthRate;

	/**
	 * 环比
	 */
	@Column(name = "f_day_rate")
	private Double fDayRate;

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

	public Integer getfUserNew() {
		return fUserNew;
	}

	public void setfUserNew(Integer fUserNew) {
		this.fUserNew = fUserNew;
	}

	public Integer getfUserTotal() {
		return fUserTotal;
	}

	public void setfUserTotal(Integer fUserTotal) {
		this.fUserTotal = fUserTotal;
	}

	public Integer getfUserActive() {
		return fUserActive;
	}

	public void setfUserActive(Integer fUserActive) {
		this.fUserActive = fUserActive;
	}

	public Double getfMonthRate() {
		return fMonthRate;
	}

	public void setfMonthRate(Double fMonthRate) {
		this.fMonthRate = fMonthRate;
	}

	public Double getfDayRate() {
		return fDayRate;
	}

	public void setfDayRate(Double fDayRate) {
		this.fDayRate = fDayRate;
	}

	public Long getfId() {
		return fId;
	}

	public void setfId(Long fId) {
		this.fId = fId;
	}

}