package com.unistrong.tracker.entry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.AlarmHandle;
import com.unistrong.tracker.handle.DeviceHandle;
import com.unistrong.tracker.handle.util.InstructDb;
import com.unistrong.tracker.model.Protocol;

@Component
public class MapingDevice {

	@Resource
	private DeviceHandle deviceHandle;

	@Resource
	private AlarmHandle alarmHandle;

	/**
	 * 获取所有设备列表
	 */
	public Map<String, Object> list(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// String state = Opt.getByMap(cmd, "state");//去掉,一直未使用，现在默认设备加上当前位置
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(deviceHandle.list(protocol.getUserId(), null, appName, "-1", "-1"));

		return rs;
	}

	/**
	 * 绑定/解绑/更新设备
	 */
	public Map<String, Object> editDelDevice(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String operate = Opt.getByMap(cmd, "operate");
		String sn = Opt.getByMap(cmd, "device_sn");
		String name = Opt.getByMap(cmd, "name");
		String phone = Opt.getByMap(cmd, "phone");
		String flow = Opt.getByMap(cmd, "flow");
		String battery = Opt.getByMap(cmd, "battery");
		String car = Opt.getByMap(cmd, "car");
		String height = Opt.getByMap(cmd, "height");
		String weight = Opt.getByMap(cmd, "weight");
		String gender = Opt.getByMap(cmd, "gender");
		String age = Opt.getByMap(cmd, "age");
		String tick = Opt.getByMap(cmd, "tick");
		String sos = Opt.getByMap(cmd, "sosNum");
		String speedThreshold = Opt.getByMap(cmd, "speedThreshold");
		String speedingSwitch = Opt.getByMap(cmd, "speedingSwitch");
		String fenceSwitch = Opt.getByMap(cmd, "fenceSwitch");
		String moveSwitch = Opt.getByMap(cmd, "moveSwitch");
		String appName = Opt.getByMap(cmd, "app_name");
		String birth = Opt.getByMap(cmd, "birth");
		String dogFigure = Opt.getByMap(cmd, "dog_figure");
		String dogBreed = Opt.getByMap(cmd, "dog_breed");
		String smsAlarm = Opt.getByMap(cmd, "smsAlarm");
		String contact = Opt.getByMap(cmd, "contact");
		if("".equals (contact)||contact == null){
		    contact = InstructDb.arrayDb(sos);
		}
		rs.putAll(deviceHandle.editDelDevice(protocol.getUserId(), sn, name, null, car, phone,
				flow, battery, speedThreshold, height, weight, gender, age, tick, sos,
				speedingSwitch, fenceSwitch, moveSwitch, operate, null, appName, birth, dogFigure,
				dogBreed,contact,smsAlarm,"",""));
		return rs;
	}

	/**
	 * 获取单个设备信息
	 */
	public Map<String, Object> getDevice(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String sn = Opt.getByMap(cmd, "device_sn");
		rs.put("device", deviceHandle.get(protocol.getUserId(), sn));
		return rs;
	}

	/**
	 * 获取单个设备信息
	 */
	public Map<String, Object> listByDevice(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String sn = Opt.getByMap(cmd, "device_sn");
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		rs.putAll(alarmHandle.listByDevice(protocol.getUserId(), sn, begin, end, "-1", "-1"));
		return rs;
	}
	
	/**
	 *余额查询 
	 */
	public Map<String, Object> feeCheck(Map<String, Object> cmd, Protocol protocol) {
		String sn = Opt.getByMap(cmd, "device_sn");
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.put("feeCheck", deviceHandle.getFeeCheck(sn));
		return rs;
	}
}
