package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.unistrong.tracker.handle.UserJiLinHandle;

/**
 * 
 * 
 */
@Controller
public class UserJiLinController {

	@Resource
	private UserJiLinHandle userJiLinHandle;

	@RequestMapping("/user.jilin.edit.do")
	public Map<String, Object> editDelDevice(String device_sn,
			String user_name, String user_sex, String user_phone,
			String user_number, String user_address, String guarder,
			String renew, String remark) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(userJiLinHandle.editUser(device_sn, user_name, user_sex,
				user_phone, user_number, user_address, guarder, renew, remark));
		return rs;
	}

	@RequestMapping("/user.jilin.get.do")
	public Map<String, Object> get(String device_sn) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.put("user", userJiLinHandle.get(device_sn));
		return rs;
	}

}
