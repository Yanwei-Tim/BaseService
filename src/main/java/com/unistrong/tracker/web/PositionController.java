package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.PositionHandle;

@Controller
public class PositionController {

	@Resource
	private PositionHandle positionHandle;

	/**
	 * 2.10 获取所有设备状态(原最新位置) 有报警状态,追踪
	 */
	@RequestMapping("/get.last.do")
	public Map<String, Object> getLasts(String user_id, String target_id,
			String app_name, String page_number, String page_size) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(positionHandle.getLasts(user_id, target_id, app_name,
				page_number, page_size));
		return rs;
	}

	/**
	 * 单个设备状态(原最新位置)
	 */
	@RequestMapping("/get.status.do")
	public Map<String, Object> get(String user_id, String device_sn,
			String taget_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(positionHandle
				.getLast(user_id, device_sn, taget_id, app_name));
		return rs;
	}
	
	/**
	 * 单个设备位置
	 */
	@RequestMapping("/get.position.do")
	public Map<String, Object> get(String user_id, String device_sn) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(positionHandle.getLast(user_id, device_sn));
		return rs;
	}
	
	
	/**
	 * 2.10 获取所有设备状态(原最新位置) 有报警状态,追踪
	 */
	@RequestMapping("/get.indoor.last.do")
	public Map<String, Object> getIndoorLasts(String user_id, String target_id,
			String app_name, String page_number, String page_size) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(positionHandle.getLasts(user_id, target_id, app_name,
				page_number, page_size));
		return rs;
	}
}
