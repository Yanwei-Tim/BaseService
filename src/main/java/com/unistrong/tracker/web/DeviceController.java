package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.DeviceHandle;

/**
 * 设备相关接口
 * 
 * @date 2013-3-22,上午11:11:43
 * @author fyq
 */
@Controller
public class DeviceController {

	@Resource
	private DeviceHandle deviceHandle;

	/**
	 * 2.7 绑定/解绑/更新设备
	 */
	@RequestMapping("/device.edit.do")
	public Map<String, Object> editDelDevice(String user_id, String fence_id,
			String device_sn, String name, String icon, String car,
			String phone, String flow, String battery, String speedThreshold,
			String region, String height, String weight, String gender,
			String age, String tick, String sos, String speedingSwitch,
			String fenceSwitch, String moveSwitch, String operate,
			String target_id, String app_name, String birth, String dog_figure,
			String dog_breed, String contact, String sms_alarm,String ptype,String service_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(deviceHandle.editDelDevice(user_id, device_sn, name, icon,
				car, phone, flow, battery, speedThreshold, height, weight,
				gender, age, tick, sos, speedingSwitch, fenceSwitch,
				moveSwitch, operate, target_id, app_name, birth, dog_figure,
				dog_breed, contact, sms_alarm,ptype,service_id));
		return rs;
	}
	
	/**
	 * 前台设备操作
	 */
	@RequestMapping("/device.operate.do")
	public Map<String, Object> operateDevice(String service_id, String user_id,
			String device_sn, String operate, String target_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(deviceHandle.operateDevice(service_id, user_id, device_sn,
				operate, target_id, app_name));
		return rs;
	}
	
	/**
	 * 更新设备
	 */
	@RequestMapping("/device.update.do")
	public Map<String, Object> updateDevice(String user_id, String fence_id,
			String device_sn, String name, String icon, String car,
			String phone, String flow, String battery, String speedThreshold,
			String region, String height, String weight, String gender,
			String age, String tick, String sos, String speedingSwitch,
			String fenceSwitch, String moveSwitch, String target_id,
			String app_name, String birth, String dog_figure, String dog_breed,
			String contact, String sms_alarm) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(deviceHandle.updateDevice(user_id, device_sn, name, icon,
				car, phone, flow, battery, speedThreshold, height, weight,
				gender, age, tick, sos, speedingSwitch, fenceSwitch,
				moveSwitch, target_id, app_name, birth, dog_figure, dog_breed,
				contact, sms_alarm));
		return rs;
	}
	

	/**
	 * 2.11 获取所有设备列表
	 */
	@RequestMapping("/device.list.do")
	public Map<String, Object> list(String user_id, String target_id,
			String type, String app_name, String page_number, String page_size) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(deviceHandle.list(user_id, target_id, app_name, page_number,
				page_size));
		return rs;
	}

	/**
	 * 2.8 获取单个设备信息
	 */
	@RequestMapping("/device.get.do")
	public Map<String, Object> get(String user_id, String device_sn) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.put("device", deviceHandle.get(user_id, device_sn));
		return rs;
	}

	/**
	 * 2.8 余额查询
	 */
	@RequestMapping("/device.feecheck.do")
	public Map<String, Object> feeCheck(String device_sn) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.put("feeCheck", deviceHandle.getFeeCheck(device_sn));
		return rs;
	}



	/**
	 * 服务后台:获取某个后台用户的设备列表
	 */
	@RequestMapping("/backend.device.list.do")
	public Map<String, Object> list(String user_id, String device_sn,
			String oper_type, String page_number, String page_size) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(deviceHandle.findByServiceUser(user_id, device_sn, oper_type,
				page_number, page_size));
		return rs;
	}

	//
	// /**
	// * 服务后台:查看设备信息
	// */
	// @RequestMapping("/backend.device.lookup.do")
	// public Map<String, Object> lookup(String user_id, String device_sn) {
	// Map<String, Object> rs = new HashMap<String, Object>();
	// rs.put("ret", 1);
	// rs.putAll(deviceHandle.lookup(user_id, device_sn));
	// return rs;
	// }

	/**
	 * 服务后台:设备操作
	 */
	@RequestMapping("/backend.device.operate.do")
	public Map<String, Object> operateBackendDevice(String user_id,
			String device_sn, String operate) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(deviceHandle
				.operateBackendDevice(user_id, device_sn, operate));
		return rs;
	}



	/**
	 * 服务后台:添加设备
	 */
	@RequestMapping("/backend.device.add.do")
	public Map<String, Object> addDevice(String user_id, String device_sn,
			String phone, String is_virtual, String type, String protocol,
			String hardware,String conductorSn) {

		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(deviceHandle.addDevice(user_id, device_sn, phone, is_virtual,
				type, protocol, hardware,conductorSn));
		return rs;
	}
}
