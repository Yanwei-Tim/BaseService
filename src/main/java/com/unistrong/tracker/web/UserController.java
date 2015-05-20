package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.UserHandle;

/**
 * 用户相关接口
 * 
 * @author fyq
 * @date 2013-3-26,下午02:31:58
 */
@Controller
public class UserController {

	@Resource
	private UserHandle userHandle;

	/**
	 * 2.1 注册
	 */
	@RequestMapping("/register.do")
	public Map<String, Object> register(String type, String name, String pwd,
			String phone, String ios_device_token) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.register(type, name, pwd, phone, ios_device_token));
		return rs;
	}

	/**
	 * 2.1 注册(加上"昵称"字段)-(人员种类：服务后台用户，服务前台企业用户，服务前台个人用户)
	 */
	@RequestMapping("/user.register.do")
	public Map<String, Object> register(String type, String name, String pwd,
			String phone, String ios_device_token, String nick_name,
			String service_id, String plat_form, String business) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.register(type, name, pwd, phone, ios_device_token,
				nick_name, service_id, plat_form, business));
		return rs;
	}

	/**
	 * 2.1 注册
	 */
	@RequestMapping("/register.app.do")
	public Map<String, Object> register_app(String type, String name,
			String pwd, String phone, String ios_device_token, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.register_app(type, name, pwd, phone,
				ios_device_token, app_name));
		return rs;
	}

	/**
	 * 2.2 检查name是否可用
	 */
	@RequestMapping("/check.name.do")
	public Map<String, Object> checkName(String username) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.putAll(userHandle.checkName(username));
		rs.put("ret", 1);
		return rs;
	}

	/**
	 * 2.3 登陆
	 */
	@RequestMapping("/logon.do")
	public Map<String, Object> logon(HttpServletRequest request,
			String username, String pwd, String ts, String lastnum,
			String ios_device_token, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);

		// 获取ip地址
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		rs.putAll(userHandle.saveLogon(username, pwd, ts, lastnum, null,
				ios_device_token, app_name, ip));
		return rs;
	}

	/**
	 * 2.3 后台登陆
	 */
	@RequestMapping("/backend.logon.do")
	public Map<String, Object> logon(String username, String pwd, String ts) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.saveBackLogon(username, pwd, ts));
		return rs;
	}

	/**
	 * 2.4 修改密码
	 */
	@RequestMapping("/update.pwd.do")
	public Map<String, Object> updatePwd(String user_id, String password,
			String new_password, String ts) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.updatePwd(user_id, password, new_password, ts));
		return rs;
	}

	/**
	 * 2.4 修改昵称
	 */
	@RequestMapping("/update.nick.do")
	public Map<String, Object> updateNick(String user_id, String nick) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.updateNick(user_id, nick));
		return rs;
	}

	/**
	 * 忘记密码/重设密码
	 */
	@RequestMapping("/forget.pwd.do")
	public Map<String, Object> forgetPwd(String username, String pwd_token,
			String pwd) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.forgetPwd(username, pwd_token, pwd));
		return rs;
	}

	/**
	 * 获取企业用户目录树
	 */
	@RequestMapping("/get.usertree.do")
	public Map<String, Object> getUserTree(String user_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.getUserTree(user_id, app_name));
		return rs;
	}

	/**
	 * 企业用户增加、修改、删除用户
	 * 
	 * @return
	 */
	@RequestMapping("/edit.user.do")
	public Map<String, Object> editUserTree(String user_id, String username,
			String email, String pwd, String phone, String parent_id,
			String role, String nick, String app_name, String real,
			String address, String operate, String target_id, String type,
			String ts) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.editUser(user_id, username, email, pwd, phone,
				parent_id, role, nick, app_name, real, address, operate,
				target_id, type, ts));

		return rs;
	}

	/**
	 * 设置密码（企业用户给下属设置密码）
	 */
	@RequestMapping("/set.pwd.do")
	public Map<String, Object> resetPwd(String user_id, String pwd, String ts,
			String target_id, String target_pwd, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.setPwd(user_id, pwd, ts, target_id, target_pwd,
				app_name));

		return rs;
	}

	/**
	 * 用户模式接口： 开发者模式or后台模式
	 */
	@RequestMapping("/backend.user.mode.do")
	public Map<String, Object> setUserMode(String user_id, String user_mode) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.setUserMode(user_id, user_mode));
		return rs;
	}

	/**
	 * 用户种类接口：企业or个人
	 */
	@RequestMapping("/backend.user.type.do")
	public Map<String, Object> setUserType(String user_id, String user_type) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.setUserType(user_id, user_type));
		return rs;
	}

	/**
	 * 开发者模式中，设置用户apikey
	 */
	@RequestMapping("/backend.user.apikey.do")
	public Map<String, Object> setUserApiKey(String user_id, String api_key) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.setUserApiKey(user_id, api_key));
		return rs;
	}

	/**
	 * 根据用户名获得用户id
	 */
	@RequestMapping("/user.getid.do")
	public Map<String, Object> getUserIdByName(String name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userHandle.getUserIdByName(name));
		return rs;
	}

}
