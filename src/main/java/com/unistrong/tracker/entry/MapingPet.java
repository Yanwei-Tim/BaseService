package com.unistrong.tracker.entry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.PetHandle;
import com.unistrong.tracker.model.Protocol;

@Component
public class MapingPet {

	@Resource
	private PetHandle petHandle;

	/**
	 * 获取统计信息
	 */
	public Map<String, Object> statistic(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String deviceSn = Opt.getByMap(cmd, "sn");
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		//String appName = Opt.getByMap(cmd, "app_name");
		rs.putAll(petHandle.getSportStatic(protocol.getUserId(), deviceSn, begin, end));
		return rs;
	}

}
