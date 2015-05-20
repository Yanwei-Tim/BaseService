package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import module.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.FenceHandle;
import com.unistrong.tracker.model.IndoorSpotVo;
import com.unistrong.tracker.model.serialize.SpotVo;
import com.unistrong.tracker.util.Logic;

@Controller
public class FenceController {

	@Resource
	private FenceHandle fenceHandle;

	/**
	 * 2.12 设置/更新围栏信息
	 * 
	 * 左下第一个点,顺时针
	 */
	@RequestMapping("/fence.edit.do")
	public Map<String, Object> fenceEdit(String operate, String type,
			String user_id, String name, String region, String device_sn,
			String radius, String center, String out) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);

		SpotVo[] regionArray = null;
		Double[] centerArray = null;
		if ("3".equals(operate)) {
			rs.putAll(fenceHandle.fenceEdit(operate, user_id, name,
					regionArray, device_sn, type, radius, centerArray, out));
			return rs;
		}
		if ("1".equals(type)) {
			try {
				centerArray = JsonUtils.str2Obj(center, Double[].class);
			} catch (Exception ex) {
				rs.put(Logic.RET, 2);
				rs.put(Logic.DESC, "圆型围栏格式有误(参考[1.0,2.2])");
				return rs;
			}
		} else {
			try {
				regionArray = JsonUtils.str2Obj(region, SpotVo[].class);
			} catch (Exception ex) {
				rs.put(Logic.RET, 2);
				rs.put(Logic.DESC,
						"矩形围栏格式有误(参考[{'lat':1, 'lng':1},...],左下第一个点,顺时针)");
				return rs;
			}
		}
		rs.putAll(fenceHandle.fenceEdit(operate, user_id, name, regionArray,
				device_sn, type, radius, centerArray, out));
		return rs;
	}

	@RequestMapping("/indoor.fence.delete.do")
	public Map<String, Object> deleteFence(String fence_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(fenceHandle.deleteFence(fence_id));
		return rs;
	}

	@RequestMapping("/indoor.fence.add.do")
	public Map<String, Object> addFence(String operate, String type,
			String user_id, String name, String region, String device_sn,
			String radius, String center, String out, String system,
			String indoorType) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		IndoorSpotVo[] regionArray = null;
		Double[] centerArray = null;
		if ("1".equals(type)) {
			try {
				centerArray = JsonUtils.str2Obj(center, Double[].class);
			} catch (Exception ex) {
				rs.put(Logic.RET, 2);
				rs.put(Logic.DESC, "圆型围栏格式有误(参考[1.0,2.2])");
				return rs;
			}
		} else {
			try {
				regionArray = JsonUtils.str2Obj(region, IndoorSpotVo[].class);
			} catch (Exception ex) {
				rs.put(Logic.RET, 2);
				rs.put(Logic.DESC,
						"矩形围栏格式有误(参考[{'lat':1, 'lng':1},...],左下第一个点,顺时针)");
				return rs;
			}
		}
		rs.putAll(fenceHandle.addFence(user_id, regionArray, device_sn, type,
				radius, centerArray, out, system, indoorType));
		return rs;
	}

	@RequestMapping("/indoor.fence.bind.do")
	public Map<String, Object> bindFence(String fence_id, String device_sn) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(fenceHandle.bindFence(fence_id, device_sn));
		return rs;
	}

	@RequestMapping("/indoor.fence.count.do")
	public Map<String, Object> getInOutCount(String device_sn, String begin,
			String end,String type) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(fenceHandle.getInOutCount(device_sn, begin, end,type));
		return rs;
	}

}
