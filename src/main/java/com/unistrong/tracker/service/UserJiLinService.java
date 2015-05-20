package com.unistrong.tracker.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.unistrong.tracker.dao.UserJiLinDao;
import com.unistrong.tracker.model.UserJiLin;

/**
 * 
 */
@Service
public class UserJiLinService {

	@Resource
	private UserJiLinDao userJiLinDao;

	public UserJiLin getUser(String sn) {
		return userJiLinDao.get(sn);
	}

	public void saveOrUpdate(UserJiLin entity) {
		userJiLinDao.saveOrUpdate(entity);
	}

}