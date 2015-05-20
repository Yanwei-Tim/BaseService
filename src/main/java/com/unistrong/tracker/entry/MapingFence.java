package com.unistrong.tracker.entry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import module.util.JsonUtils;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.FenceHandle;
import com.unistrong.tracker.model.Protocol;
import com.unistrong.tracker.model.serialize.SpotVo;

@Component
public class MapingFence {

	@Resource
	private FenceHandle fenceHandle;

	/**
	 * 设置/更新围栏信息
	 */
	public Map<String, Object> fenceEdit(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String operate = Opt.getByMap(cmd, "operate");
		String name = Opt.getByMap(cmd, "name");

		Object objRegion = cmd.get("region");
		String str = JsonUtils.obj2Str(objRegion);
		SpotVo[] region = JsonUtils.str2Obj(str, SpotVo[].class);
		Object objCenter = cmd.get("center");
		str = JsonUtils.obj2Str(objCenter);
		Double[] center = JsonUtils.str2Obj(str, Double[].class);

		String deviceSn = Opt.getByMap(cmd, "device_sn");
		String type = Opt.getByMap(cmd, "type");
		String radius = Opt.getByMap(cmd, "radius");
		String out = Opt.getByMap(cmd, "out");
		rs.putAll(fenceHandle.fenceEdit(operate, protocol.getUserId(), name, region, deviceSn,
				type, radius, center, out));
		return rs;
	}

}
