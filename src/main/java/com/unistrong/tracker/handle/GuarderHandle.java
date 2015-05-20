package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.UserGuarder;
import com.unistrong.tracker.model.UserJiLin;
import com.unistrong.tracker.service.GuarderService;
import com.unistrong.tracker.service.UserJiLinService;

@Component
public class GuarderHandle {

	@Resource
	private GuarderService guarderService;

	@Resource
	private UserJiLinService userJiLinService;

	private static Logger logger = LoggerFactory.getLogger(GuarderHandle.class);

	public Map<String, Object> editGuarder(String device_sn, String user_name,
			String user_sex, String user_phone, String user_number,
			String user_address, String guarder, String renew, String remark,
			String guarder_id1, String guarder_id2, String guarder_id3,
			String guarder_name1, String guarder_phone1,
			String guarder_address1, String guarder_company1,
			String guarder_name2, String guarder_phone2,
			String guarder_address2, String guarder_company2,
			String guarder_name3, String guarder_phone3,
			String guarder_address3, String guarder_company3, String operate1,
			String operate2, String operate3) {
		Long id1 = HttpUtil.getLong(guarder_id1, 0l);
		Long id2 = HttpUtil.getLong(guarder_id2, 0l);
		Long id3 = HttpUtil.getLong(guarder_id3, 0l);
		Long guarderId1 = 0L;
		Long guarderId2 = 0L;
		Long guarderId3 = 0L;
		UserGuarder userGuarder1 = new UserGuarder();
		UserGuarder userGuarder2 = new UserGuarder();
		UserGuarder userGuarder3 = new UserGuarder();
		if (id1 != 0) {
			userGuarder1 = guarderService.getGuarder(id1);
		}
		if (id2 != 0) {
			userGuarder2 = guarderService.getGuarder(id2);
		}
		if (id3 != 0) {
			userGuarder3 = guarderService.getGuarder(id3);
		}
		if ("1".equals(operate1)) {//添加监护人1
			userGuarder1.setAddress(guarder_address1);
			userGuarder1.setCompany(guarder_company1);
			userGuarder1.setGuarderName(guarder_name1);
			userGuarder1.setPhone(guarder_phone1);
			guarderId1 = guarderService.save(userGuarder1);

		} else if ("2".equals(operate1)) {//修改监护人1
			guarderService.saveOrUpdate(userGuarder1);
			guarderId1 = userGuarder1.getId();
		}
		if ("1".equals(operate2)) {//添加监护人2
			userGuarder2.setAddress(guarder_address2);
			userGuarder2.setCompany(guarder_company2);
			userGuarder2.setGuarderName(guarder_name2);
			userGuarder2.setPhone(guarder_phone2);
			guarderId2 = guarderService.save(userGuarder2);

		} else if ("2".equals(operate2)) {//修改监护人2
			guarderService.saveOrUpdate(userGuarder2);
		}
		if ("1".equals(operate3)) {//添加监护人3
			userGuarder3.setAddress(guarder_address3);
			userGuarder3.setCompany(guarder_company3);
			userGuarder3.setGuarderName(guarder_name3);
			userGuarder3.setPhone(guarder_phone3);
			guarderId3 = guarderService.save(userGuarder3);

		} else if ("2".equals(operate3)) {//修改监护人3
			guarderService.saveOrUpdate(userGuarder3);
		}
		StringBuffer str = new StringBuffer();
		str.append(guarderId1);
		str.append("," + guarderId2);
		str.append("," + guarderId3);
		String guarderId = str.toString();
		Integer sex = HttpUtil.getInt(user_sex, 1);
		UserJiLin user = new UserJiLin();
		user.setDeviceSn(device_sn);
		user.setAddress(user_address);
		user.setGuarder(guarderId);
		user.setNumber(user_number);
		user.setPhone(user_phone);
		user.setRemark(remark);
		user.setUserName(user_name);
		user.setUserSex(sex);
		user.setRenew(renew);
		userJiLinService.saveOrUpdate(user);
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("desc", "更新成功");
		return rs;
	}

	/**
	 * 查询监护人信息
	 */
	public List<UserGuarder> get(String device_sn) {
		HttpUtil.validateNull(new String[] { "device_sn" },
				new String[] { device_sn });
		UserJiLin user = userJiLinService.getUser(device_sn);
		List<UserGuarder> guarderList = new ArrayList<UserGuarder>();
		if (user != null) {
			String guarderIds = user.getGuarder();
			if (guarderIds != null) {
				String str[] = guarderIds.split(",");
				for (int i = 0; i < str.length; i++) {
					UserGuarder guarder = guarderService.getGuarder(Long
							.parseLong(str[i]));
					guarderList.add(guarder);
				}
			}
		}
		return guarderList;
	}

}
