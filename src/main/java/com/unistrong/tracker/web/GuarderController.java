package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.GuarderHandle;

/**
 * 
 * 
 */
@Controller
public class GuarderController {

	@Resource
	private GuarderHandle guarderHandle;

	@RequestMapping("/guarder.edit.do")
	public Map<String, Object> editDelDevice(String device_sn,
			String user_name, String user_sex, String user_phone,
			String user_number, String user_address, String guarder,
			String renew, String remark, String guarder_id1,
			String guarder_id2, String guarder_id3, String guarder_name1,
			String guarder_phone1, String guarder_address1,
			String guarder_company1, String guarder_name2,
			String guarder_phone2, String guarder_address2,
			String guarder_company2, String guarder_name3,
			String guarder_phone3, String guarder_address3,
			String guarder_company3, String operate1, String operate2,
			String operate3) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(guarderHandle.editGuarder(device_sn, user_name, user_sex,
				user_phone, user_number, user_address, guarder, renew, remark,
				guarder_id1, guarder_id2, guarder_id3, guarder_name1,
				guarder_phone1, guarder_address1, guarder_company1,
				guarder_name2, guarder_phone2, guarder_address2,
				guarder_company2, guarder_name3, guarder_phone3,
				guarder_address3, guarder_company3, operate1, operate2,
				operate3));
		return rs;
	}

	@RequestMapping("/guarder.get2.do")
	public Map<String, Object> get2(String device_sn) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.put("guarder", guarderHandle.get(device_sn));
		return rs;
	}
	
	@RequestMapping("/guarder.get.do")
	public Map<String, Object> get(String guarder_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.put("guarder", guarderHandle.get(guarder_id));
		return rs;
	}

}
