package com.unistrong.tracker.service.cache;

import module.util.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.OBD;
import com.unistrong.tracker.model.OBDError;

@Component
public class OBDCache {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final String OBD = "obd";
	
	private static final String OBD_ERR = "obd_err";
	
	
	public OBD getOBD(String sn) {
		String json = (String) redisTemplate.opsForHash().get(OBD,sn);
		if (json != null && !"".equals(json)) {
			return JsonUtils.str2Obj(json, OBD.class);
		}
		return null;
	}
	
	public OBDError getOBDErr(String sn) {
		String json = (String) redisTemplate.opsForHash().get(OBD_ERR, sn);
		if (json != null && !"".equals(json)) {
			return JsonUtils.str2Obj(json, OBDError.class);
		}
		return null;
	}
	
	public void removeOBDErr(String sn) {
		redisTemplate.opsForHash().delete(OBD_ERR, sn);
	}
	
}
