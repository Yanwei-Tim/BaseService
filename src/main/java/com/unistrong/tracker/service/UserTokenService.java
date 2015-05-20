package com.unistrong.tracker.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.UserTokenDao;
import com.unistrong.tracker.model.UserToken;

@Service
public class UserTokenService {

	@Resource
	private UserTokenDao userTokenDao;

	public String saveOrUpdate(UserToken userToken) {
		userTokenDao.saveOrUpdate(userToken);
		return userToken.getToken();
	}

	public void delete(Long userId) {
		userTokenDao.delete(userId);
	}

	public UserToken get(Long userId) {
		return userTokenDao.get(userId);
	}

}
