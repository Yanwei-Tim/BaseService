/**
 * 
 */
package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fss
 * 
 */
@Entity
@Table(name = "us_share")
public class Share implements Serializable {

	private static final long serialVersionUID = -3261367063305632658L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "f_id")
	private Long id;

	@Column(name = "f_device_sn")
	private String deviceSn;

	@Column(name = "f_location_time")
	private String locationTime;

	@Column(name = "f_begin")
	private Long begin;

	@Column(name = "f_end")
	private Long end;

	@Column(name = "f_user_id")
	private Long userId;

	@Column(name = "f_privacy_type")
	private Integer privacyType;

	@Column(name = "f_publish")
	private Long publish;

	@Column(name = "f_act")
	private Long act;

	@Column(name = "f_expire")
	private Long expire;

	@Column(name = "f_content_type")
	private Integer contentType;
	
	@Column(name="f_map_type")
	private String mapType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public Long getBegin() {
		return begin;
	}

	public void setBegin(Long begin) {
		this.begin = begin;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPublish() {
		return publish;
	}

	public void setPublish(Long publish) {
		this.publish = publish;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

	public Integer getPrivacyType() {
		return privacyType;
	}

	public void setPrivacyType(Integer privacyType) {
		this.privacyType = privacyType;
	}

	public Long getAct() {
		return act;
	}

	public void setAct(Long act) {
		this.act = act;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getLocationTime() {
		return locationTime;
	}

	public void setLocationTime(String locationTime) {
		this.locationTime = locationTime;
	}

	public String getMapType() {
		return mapType;
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

}
