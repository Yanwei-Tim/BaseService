package com.unistrong.tracker.entry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.SpotHandle;
import com.unistrong.tracker.model.Protocol;

@Component
public class MapingSpot {

	@Resource
	private SpotHandle spotHandle;

	/**
	 * 轨迹回放信息
	 */
	public Map<String, Object> getTrack(Map<String, Object> cmd,
			Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String sn = Opt.getByMap(cmd, "device_sn");
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		String part = Opt.getByMap(cmd, "part");
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(spotHandle.getTrack(sn, begin, end, part, appName));
		return rs;
	}

	/**
	 * 轨迹回放信息-分段
	 */
	public Map<String, Object> getTrackPart(Map<String, Object> cmd,
			Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String sn = Opt.getByMap(cmd, "device_sn");
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		String part = "Y";
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(spotHandle.getTrack(sn, begin, end, part, appName));
		return rs;
	}

	/**
	 * 只取分段起终点
	 */
	public Map<String, Object> getTrackBeginEnd(Map<String, Object> cmd,
			Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String sn = Opt.getByMap(cmd, "device_sn");
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(spotHandle.getPark(protocol.getUserId(), sn, begin, end,
				null, appName));
		return rs;
	}

	/**
	 * 获取统计信息
	 */
	public Map<String, Object> statistic(Map<String, Object> cmd,
			Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String deviceSn = Opt.getByMap(cmd, "sn");
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(spotHandle.statistic(protocol.getUserId(), deviceSn, begin,
				end, null, appName));
		return rs;
	}

	/**
	 * 轨迹回放信息
	 */
	public Map<String, Object> getIndoorTrack(Map<String, Object> cmd,
			Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String sn = Opt.getByMap(cmd, "device_sn");
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		String part = Opt.getByMap(cmd, "part");
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(spotHandle.getIndoorTrack(sn, begin, end, part, appName));
		return rs;
	}

	/**
	 * 轨迹回放信息-分段
	 */
	public Map<String, Object> getIndoorTrackPart(Map<String, Object> cmd,
			Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String sn = Opt.getByMap(cmd, "device_sn");
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		String part = "Y";
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(spotHandle.getIndoorTrack(sn, begin, end, part, appName));
		return rs;
	}

	/**
	 * 只取分段起终点
	 */
	public Map<String, Object> getIndoorTrackBeginEnd(Map<String, Object> cmd,
			Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String sn = Opt.getByMap(cmd, "device_sn");
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(spotHandle.getIndoorPark(protocol.getUserId(), sn, begin,
				end, null, appName));
		return rs;
	}

}
