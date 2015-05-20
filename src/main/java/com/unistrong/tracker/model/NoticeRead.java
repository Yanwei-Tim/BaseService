package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import module.orm.IdEntity;

@Entity
@Table(name = "us_notice_read")
public class NoticeRead extends IdEntity implements Serializable {
	
	@Column(name = "f_user_id")
	private Long uid;
	@Column(name = "f_notice_id")
	private Long noticeid;
	@Column(name = "f_read_time")
	private Long readtime;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(Long noticeid) {
		this.noticeid = noticeid;
	}
	public Long getReadtime() {
		return readtime;
	}
	public void setReadtime(Long readtime) {
		this.readtime = readtime;
	}
	
	
}
