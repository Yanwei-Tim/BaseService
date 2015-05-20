package com.unistrong.tracker.service.cache;

import module.util.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Position;

@Component
public class PositionCache {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final String POSITION = "position";

	private static final String DEVICE_ALARM = "device_alarm";

	private static final String INSTRUCT = "instruct";

	public void setInstruct(String deviceSn) {
		redisTemplate.opsForSet().add(INSTRUCT, deviceSn);
	}

	public void removeInstruct(String deviceSn) {
		redisTemplate.opsForSet().remove(INSTRUCT, deviceSn);
	}

	public int getAlarmCount(String deviceSn) {
		return redisTemplate.opsForSet().size(DEVICE_ALARM + ":" + deviceSn)
				.intValue();
	}

	public void removeAlarmId(String deviceSn, String alarmId) {
		redisTemplate.opsForSet()
				.remove(DEVICE_ALARM + ":" + deviceSn, alarmId);
	}

	public Position getPosition(String deviceSn) {
		String json = (String) redisTemplate.opsForHash().get(POSITION,
				deviceSn);
		if (json != null && !"".equals(json)) {
			return JsonUtils.str2Obj(json, Position.class);
		}
		return null;
	}

	public void setPosition(String deviceSn, Position position) {
		String json = JsonUtils.obj2Str(position);
		redisTemplate.opsForHash().put(POSITION, deviceSn, json);
	}

	public void removePosition(String deviceSn) {
		redisTemplate.opsForHash().delete(POSITION, deviceSn);
	}
}
