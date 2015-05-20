package com.unistrong.tracker.entry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.PositionHandle;
import com.unistrong.tracker.model.Protocol;

@Component
public class MapingPosition {

	@Resource
	private PositionHandle positionHandle;

	/**
	 * 获取所有设备状态 原最新位置
	 */
	public Map<String, Object> getLasts(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(positionHandle.getLasts(protocol.getUserId(), null, appName,"-1","-1"));
		return rs;
	}

	/**
	 * 设备最新位置
	 */
	public Map<String, Object> getLast(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String deviceSn = Opt.getByMap(cmd, "device_sn");
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(positionHandle.getLast(protocol.getUserId(), deviceSn, null, appName));
		return rs;
	}

	/**
	 * 是否推送位置
	 */
	public Map<String, Object> pushSpot(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String duid = Opt.getByMap(cmd, "duid");
		String push = Opt.getByMap(cmd, "push");
		String userId = protocol.getUserId();
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(positionHandle.pushSpot(duid, push, userId, appName));
		return rs;
	}

}
