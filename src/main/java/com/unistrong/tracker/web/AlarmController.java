package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.AlarmHandle;

@Controller
public class AlarmController {
	@Resource
	private AlarmHandle alarmHandle;


	/**
	 * 2.9 获取单个设备的所有警告
	 */
	@RequestMapping("/alarm.list.do")
	public Map<String, Object> listByDevice(String user_id, String device_sn, String begin,
			String end) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(alarmHandle.listByDevice(user_id, device_sn, begin, end, "-1", "-1"));
		return rs;
	}

	/**
	 * 所有警告
	 */
	@RequestMapping("/alarm.all.do")
	public Map<String, Object> list(String user_id, String begin, String end, String target_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(alarmHandle.list(user_id, begin, end, target_id, app_name));
		return rs;
	}

	/**
	 * 2.20修改是否已读状态 与 新增告警(模拟)
	 */
	@RequestMapping("/alarm.edit.do")
	public Map<String, Object> editAlarm(String mode, String[] alarm_id, String deviceSn,
			String user_id, String type, String time, String read, String lng, String lat,
			String info, String speed) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		if (!"1".equals(mode)) {// 批量修改告警为已读
			rs.putAll(alarmHandle.editAlarm(alarm_id, null, user_id, null, null, "1", null, null,
					null, null));
		}
		return rs;
	}
}
