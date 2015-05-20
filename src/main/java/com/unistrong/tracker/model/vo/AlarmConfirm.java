package com.unistrong.tracker.model.vo;

import java.io.Serializable;

import com.unistrong.tracker.model.Alarm;

/**
 * @author fyq
 */
public class AlarmConfirm implements Serializable {

	private static final long serialVersionUID = 8238624998643317934L;

	// 是否推送成功
	private Boolean pushTag = false;

	// 是否已经被获取
	private Boolean getTag = false;

	private Alarm alarm;

	public Boolean getPushTag() {
		return pushTag;
	}

	public void setPushTag(Boolean pushTag) {
		this.pushTag = pushTag;
	}

	public Boolean getGetTag() {
		return getTag;
	}

	public void setGetTag(Boolean getTag) {
		this.getTag = getTag;
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	public AlarmConfirm(Alarm alarm) {
		super();
		this.alarm = alarm;
	}

	public AlarmConfirm() {
		super();
	}

}
