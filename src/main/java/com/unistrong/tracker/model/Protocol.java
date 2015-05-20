package com.unistrong.tracker.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.jms.core.MessageCreator;

/**
 * @author fyq
 */
public class Protocol implements MessageCreator, Serializable {

	private static final long serialVersionUID = 3803153283622046688L;

	private String duid;

	@JsonProperty(value = "protocol")
	private List<Map<String, Object>> cmdList;

	@JsonProperty(value = "user_id")
	private String userId;

	private String token;

	/** 1推给手机,2给设备下发指令,3来的地方 */
	private Integer target = 3;

	/** 0不是/1是 */
	private Integer login = 0;

	private List<Long> userIds;
	
	private String ip;

	public Protocol(String duid, List<Map<String, Object>> cmdList, String userId, String token) {
		this.duid = duid;
		this.cmdList = cmdList;
		this.userId = userId;
		this.token = token;
	}

	public Protocol(String duid, List<Map<String, Object>> cmdList, String userId, String token,
			Integer target, Integer login, List<Long> userIds) {
		this.duid = duid;
		this.cmdList = cmdList;
		this.userId = userId;
		this.token = token;
		this.target = target;
		this.login = login;
		this.userIds = userIds;
	}

	@Override
	public Message createMessage(Session session) throws JMSException {
		Protocol protocol = new Protocol(duid, cmdList, userId, token, target, login, userIds);
		ObjectMessage objectMessage = session.createObjectMessage(protocol);
		return objectMessage;
	}

	public String getDuid() {
		return duid;
	}

	public void setDuid(String duid) {
		this.duid = duid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Map<String, Object>> getCmdList() {
		return cmdList;
	}

	public void setCmdList(List<Map<String, Object>> cmdList) {
		this.cmdList = cmdList;
	}

	public Protocol() {
		super();
	}

	/** 1推给手机,2给设备下发指令,3来的地方 */
	public Integer getTarget() {
		return target;
	}

	/** 1推给手机,2给设备下发指令,3来的地方 */
	public void setTarget(Integer target) {
		this.target = target;
	}

	/** 是否有登陆cmd */
	public Integer getLogin() {
		return login;
	}

	/** 是否有登陆cmd */
	public void setLogin(Integer login) {
		this.login = login;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	@Override
	public String toString() {
		return "Protocol [duid=" + duid + ", cmdList=" + cmdList + ", userId=" + userId
				+ ", token=" + token + ", target=" + target + ", login=" + login + ", userIds="
				+ userIds + "]";
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
