package com.unistrong.tracker.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserCache {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final String USER_TOKEN = "user_token";
	private static final String USER_LANGUAGE = "user_language";
	private static final String PWD_TOKEN = "pwd_token";
	private static final String BACKEND = "backend";
	
	public void setUserToken(String userId, String token){
		redisTemplate.opsForHash().put(USER_TOKEN, userId, token);
	}
	
	public String getUserLanguage(String userId){
		return (String)redisTemplate.opsForHash().get(USER_LANGUAGE, userId);
	}
	
	public void setUserLanguage(String userId, String language){
		redisTemplate.opsForHash().put(USER_LANGUAGE, userId, language);
	}
	
	public String getUserToken(String userId){
		return (String)redisTemplate.opsForHash().get(USER_TOKEN, userId);
	}
	
	
	public void setPwdToken(String userName, String token){
		redisTemplate.opsForHash().put(PWD_TOKEN, userName, token);
	}
	
	public String getPwdToken(String userName){
		return (String)redisTemplate.opsForHash().get(PWD_TOKEN, userName);
	}
	
	public void addBackendPhone(String userId, String duid, String appName){
		redisTemplate.opsForSet().add(BACKEND+":"+userId+":"+appName, duid);
	}
	
	public void removeBackendPhone(String userId, String duid, String appName){
		redisTemplate.opsForSet().remove(BACKEND+":"+userId+":"+appName, duid);
	}
}
