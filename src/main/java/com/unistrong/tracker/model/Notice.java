package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "bs_bulletin")
public class Notice extends IdEntity implements Serializable {
	@Column(name = "f_type")
	private Integer type;
	@Column(name = "f_title")
	private String title;
	@Column(name = "f_content")
	private String content;
	@Column(name = "f_online_time")
	private Long onlinetime;
	@Column(name = "f_offline_time")
	private Long offlinetime;
	@Column(name = "f_platform")
	private String platform;
	@Column(name = "f_frequency")
	private Integer frequency;
	@Column(name = "f_url")
	private String url;
	@Column(name = "f_condition")
	private String condition;
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getOnlinetime() {
		return onlinetime;
	}
	public void setOnlinetime(Long onlinetime) {
		this.onlinetime = onlinetime;
	}
	public Long getOfflinetime() {
		return offlinetime;
	}
	public void setOfflinetime(Long offlinetime) {
		this.offlinetime = offlinetime;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
}
