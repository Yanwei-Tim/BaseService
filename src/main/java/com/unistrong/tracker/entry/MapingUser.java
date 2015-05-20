package com.unistrong.tracker.entry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.UserHandle;
import com.unistrong.tracker.model.Protocol;

@Component
public class MapingUser {

	@Resource
	private UserHandle userHandle;

	/**
	 * 注册
	 */
	public Map<String, Object> register(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String name = Opt.getByMap(cmd, "name");
		String pwd = Opt.getByMap(cmd, "pwd");
		String type = Opt.getByMap(cmd, "type");
		String phone = Opt.getByMap(cmd, "phone");
		String iosToken = Opt.getByMap(cmd, "ios_device_token");
		rs.putAll(userHandle.register(type, name, pwd, phone, iosToken));
		return rs;
	}
	
	
	/**
	 * 注册-appname
	 */
	public Map<String, Object> register_app(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String name = Opt.getByMap(cmd, "name");
		String pwd = Opt.getByMap(cmd, "pwd");
		String type = Opt.getByMap(cmd, "type");
		String phone = Opt.getByMap(cmd, "phone");
		String iosToken = Opt.getByMap(cmd, "ios_device_token");
		String app_name = Opt.getByMap(cmd, "app_name");
		rs.putAll(userHandle.register_app(type, name, pwd, phone, iosToken,app_name));
		return rs;
	}

	/**
	 * 检查name是否可用
	 */
	public Map<String, Object> checkName(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String name = Opt.getByMap(cmd, "username");
		rs.putAll(userHandle.checkName(name));
		return rs;
	}

	/**
	 * ios注销
	 */
	public Map<String, Object> logout(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// String userIdStr = Opt.getByMap(cmd, "user_id");
		String userIdStr = protocol.getUserId();
		String iosToken = Opt.getByMap(cmd, "ios_device_token");
		rs.putAll(userHandle.logout(userIdStr, iosToken));
		return rs;
	}

	/**
	 * 登陆
	 */
	public Map<String, Object> saveLogon(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		protocol.setLogin(1);
		String name = Opt.getByMap(cmd, "username");
		String pwd = Opt.getByMap(cmd, "pwd");
		String ts = Opt.getByMap(cmd, "ts");
		String lastnum = Opt.getByMap(cmd, "lastnum");
		String iosToken = Opt.getByMap(cmd, "ios_device_token");
		String appName = Opt.getByMap(cmd, "app_name");
		
		rs.putAll(userHandle.saveLogon(name, pwd, ts, lastnum, protocol.getDuid(), iosToken, appName,protocol.getIp()));
		return rs;
	}

	/**
	 * 修改密码
	 */
	public Map<String, Object> updatePwd(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String password = Opt.getByMap(cmd, "password");
		String new_password = Opt.getByMap(cmd, "new_password");
		String ts = Opt.getByMap(cmd, "ts");
		rs.putAll(userHandle.updatePwd(protocol.getUserId(), password, new_password, ts));
		return rs;
	}

	/**
	 * 忘记密码
	 */
	public Map<String, Object> forgetPwd(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String username = Opt.getByMap(cmd, "username");
		String pwd_token = Opt.getByMap(cmd, "pwd_token");
		String pwd = Opt.getByMap(cmd, "pwd");
		rs.putAll(userHandle.forgetPwd(username, pwd_token, pwd));
		return rs;
	}

	/**
	 * 重新连接
	 */
	public Map<String, Object> reConn(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// rs.put("user_id", protocol.getUserId());
		// rs.put("token", protocol.getToken());
		return rs;
	}

}
