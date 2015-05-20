package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.StatHandle;

/**
 * 统计模块
 * 
 * @author zhangxianpeng
 * 
 */
@Controller
public class StatController {

	@Resource
	private StatHandle statHandle;

	@RequestMapping("/backend.sum.stat.do")
	public Map<String, Object> getUsersAndDevices(String service_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(statHandle.getUsersAndDevices(service_id));
		return rs;
	}
	
	@RequestMapping("/get.device.stat.do")
	public Map<String, Object> getDeviceStat(String service_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(statHandle.getDeviceStat(service_id));
		return rs;
	}
	
	@RequestMapping("/get.user.stat.do")
	public Map<String, Object> getUserStat(String service_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(statHandle.getUserStat(service_id));
		return rs;
	}
	
}
