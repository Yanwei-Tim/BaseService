package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.SpotHandle;

/**
 * @author fyq
 */
@Controller
public class SpotController {

	@Resource
	private SpotHandle spotHandle;

	/**
	 * 2.14 轨迹回放信息(一系列点)
	 */
	@RequestMapping("/get.track.do")
	public Map<String, Object> getTrack(String user_id, String device_sn,
			String begin, String end, String part, String page_number,
			String page_size, String target_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(spotHandle.getTrack(device_sn, begin, end, part, app_name));
		return rs;
	}

	/**
	 * 2.14 轨迹回放信息(起终点)
	 */
	@RequestMapping("/get.part.do")
	public Map<String, Object> getPark(String user_id, String device_sn,
			String begin, String end, String target_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(spotHandle.getPark(user_id, device_sn, begin, end, target_id,
				app_name));
		return rs;
	}

	/**
	 * 2.27统计
	 */
	@RequestMapping("/statistic.do")
	public Map<String, Object> statistic(String user_id, String device_sn,
			String begin, String end, String target_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(spotHandle.statistic(user_id, device_sn, begin, end,
				target_id, app_name));
		return rs;
	}

	//*********以下是室内定位**********//
	/**
	 * 2.14 轨迹回放信息(一系列点)
	 */
	@RequestMapping("/get.indoor.track.do")
	public Map<String, Object> getIndoorTrack(String user_id, String device_sn,
			String begin, String end, String part, String page_number,
			String page_size, String target_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(spotHandle.getIndoorTrack(device_sn, begin, end, part,
				app_name));
		return rs;
	}

	/**
	 * 2.14 轨迹回放信息(起终点)
	 */
	@RequestMapping("/get.indoor.part.do")
	public Map<String, Object> getIndoorPark(String user_id, String device_sn,
			String begin, String end, String target_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(spotHandle.getIndoorPark(user_id, device_sn, begin, end,
				target_id, app_name));
		return rs;
	}
}
