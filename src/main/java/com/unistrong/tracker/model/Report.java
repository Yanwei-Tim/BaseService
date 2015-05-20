package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 每日统计报告
 * 
 * @author fyq
 */
@Entity
@Table(name = "us_report")
public class Report implements Serializable {

	private static final long serialVersionUID = 7116853441940985031L;

	@Id
	@Column(name = "f_id")
	private String id;

	@Column(name = "f_device_sn")
	private String deviceSn;

	@Column(name = "f_day")
	private Long day;

	@Column(name = "f_distance")
	private Double distance = 0D;

	@Column(name = "f_stop")
	private Float stop = 0F;// 停留小时

	@Column(name = "f_speeding")
	private Integer speeding = 0;// 超速次数
	
	@Column(name = "f_out_fence")
	private Integer outFence = 0;// 出围栏次数

	@Column(name = "f_distance30")
	@JsonIgnore
	private Double distance30 = 0D;// 时速小于30km

	@Column(name = "f_distance36")
	@JsonIgnore
	private Double distance36 = 0D;// 时速30-60km

	@Column(name = "f_distance61")
	@JsonIgnore
	private Double distance61 = 0D;// 时速60-100k

	@Column(name = "f_distance10")
	@JsonIgnore
	private Double distance10 = 0D;// 时速大于100km

	public Long getDay() {
		return day;
	}

	public void setDay(Long day) {
		this.day = day;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Float getStop() {
		return stop;
	}

	public void setStop(Float stop) {
		this.stop = stop;
	}

	@Override
	public String toString() {
		return "Report [day=" + day + ", distance=" + distance + ", stop=" + stop + "]";
	}

	public Integer getSpeeding() {
		return speeding;
	}

	public void setSpeeding(Integer speeding) {
		this.speeding = speeding;
	}

	public Double getDistance30() {
		return distance30;
	}

	public void setDistance30(Double distance30) {
		this.distance30 = distance30;
	}

	public Double getDistance36() {
		return distance36;
	}

	public void setDistance36(Double distance36) {
		this.distance36 = distance36;
	}

	public Double getDistance61() {
		return distance61;
	}

	public void setDistance61(Double distance61) {
		this.distance61 = distance61;
	}

	public Double getDistance10() {
		return distance10;
	}

	public void setDistance10(Double distance10) {
		this.distance10 = distance10;
	}
	

	public Integer getOutFence() {
		return outFence;
	}

	public void setOutFence(Integer outFence) {
		this.outFence = outFence;
	}

	public Report(String deviceSn, Long day) {
		super();
		this.id = deviceSn + "-" + day;
		this.deviceSn = deviceSn;
		this.day = day;
	}

	public Report() {
		super();
	}
}