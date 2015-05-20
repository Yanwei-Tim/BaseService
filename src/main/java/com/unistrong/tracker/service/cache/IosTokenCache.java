package com.unistrong.tracker.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class IosTokenCache {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/** userId对应的IOS token */
	private static final String IOS_TOKEN = "ios_token";

	private static final String IOS_TOKEN_USER = "ios_token_user";

	public void addIosToken(Long userId, String token) {
		redisTemplate.opsForSet().add(IOS_TOKEN + ":" + userId, token);
	}

	public void removeIosToken(Long userId, String token) {
		redisTemplate.opsForSet().remove(IOS_TOKEN + ":" + userId, token);
	}

	public void setTokenUser(Long userId, String token) {
		redisTemplate.opsForHash().put(IOS_TOKEN_USER, token,
				String.valueOf(userId));
	}

	public Long getTokenUser(String token) {
		Object obj = redisTemplate.opsForHash().get(IOS_TOKEN_USER, token);
		if (obj == null) {
			return null;
		}
		return Long.valueOf(String.valueOf(obj));
	}
}
