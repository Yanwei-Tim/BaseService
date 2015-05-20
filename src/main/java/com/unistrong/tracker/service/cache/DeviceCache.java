package com.unistrong.tracker.service.cache;

import module.util.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Device;

@Component
public class DeviceCache {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final String DEVICE = "device";

	public void setDevice(String deviceSn, Device device) {
		String json = JsonUtils.obj2Str(device);
		redisTemplate.opsForHash().put(DEVICE, deviceSn, json);
	}

	public Device getDevice(String deviceSn) {
		String json = (String) redisTemplate.opsForHash().get(DEVICE, deviceSn);
		if (json != null && !"".equals(json)) {
			return JsonUtils.str2Obj(json, Device.class);
		}
		return null;
	}

	public void removeDevice(String deviceSn) {
		redisTemplate.opsForHash().delete(DEVICE, deviceSn);
	}
}
