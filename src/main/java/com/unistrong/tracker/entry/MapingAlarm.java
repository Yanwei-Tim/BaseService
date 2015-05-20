package com.unistrong.tracker.entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.AlarmHandle;
import com.unistrong.tracker.handle.SpotHandle;
import com.unistrong.tracker.model.Protocol;

@Component
public class MapingAlarm {

	@Resource
	private AlarmHandle alarmHandle;

	@Resource
	private SpotHandle spotHandle;

	/**
	 * 获取单个设备的所有警告
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
	 * 下属所有告警
	 */
	public Map<String, Object> list(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(alarmHandle.list(protocol.getUserId(), begin, end, null, appName));
		return rs;
	}


	/**
	 * 修改告警为已读
	 */
	public Map<String, Object> editAlarm(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		List<String> alarmIdList = (List<String>) cmd.get("alarm_id");
		String[] alarm_ids = new String[alarmIdList.size()];
		alarmIdList.toArray(alarm_ids);
		rs.putAll(alarmHandle.editAlarm(alarm_ids, null, protocol.getUserId(), null, null, "1",
				null, null, null, null));
		return rs;
	}

}
