package com.unistrong.tracker.handle;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import module.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.unistrong.tracker.model.UserJiLin;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.UserJiLinService;

@Component
public class UserJiLinHandle {

	@Resource
	private DeviceService deviceService;

	@Resource
	private UserJiLinService userJiLinService;

	private static Logger logger = LoggerFactory
			.getLogger(UserJiLinHandle.class);

	public Map<String, Object> editUser(String device_sn,
			String user_name, String user_sex, String user_phone,
			String user_number, String user_address, String guarder,
			String renew, String remark) {
		HttpUtil.validateNull(new String[] { "device_sn" },
				new String[] { device_sn });
		Integer sex = HttpUtil.getInt(user_sex, 1);
		UserJiLin user = new UserJiLin();
		user.setDeviceSn(device_sn);
		user.setAddress(user_address);
		user.setGuarder(guarder);
		user.setNumber(user_number);
		user.setPhone(user_phone);
		user.setRemark(remark);
		user.setUserName(user_name);
		user.setUserSex(sex);
		userJiLinService.saveOrUpdate(user);
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("desc", "更新成功");
		return rs;
	}

	/**
	 * 查询用户信息
	 */
	public UserJiLin get(String sn) {
		HttpUtil.validateNull(new String[] { "sn" }, new String[] { sn });
		UserJiLin user = userJiLinService.getUser(sn);
		return user;
	}

}
