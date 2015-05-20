/**
 * 
 */
package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fss
 * 
 */
@Entity
@Table(name = "us_research")
public class Research implements Serializable {

	private static final long serialVersionUID = -3261367063305632658L;

	@Id
	@Column(name = "f_user_id")
	private Long userId;

	@Column(name = "f_status")
	private Integer status;

	@Column(name = "f_time")
	private Long time;

	@Column(name = "f_topic_ids")
	private String topics;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}


	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}
}
