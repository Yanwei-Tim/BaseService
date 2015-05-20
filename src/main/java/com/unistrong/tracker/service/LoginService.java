package com.unistrong.tracker.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.LoginDao;
import com.unistrong.tracker.model.Login;

@Service
public class LoginService {

	@Resource
	private LoginDao loginDao;

	public void saveOrUpdate(Login entity) {
		loginDao.saveOrUpdate(entity);
	}

	public void delete(Login entity) {
		loginDao.deleteObject(entity);
	}

	public void delete(Long id) {
		loginDao.delete(id);
	}

	/**
	 * 查询用户数
	 */
	public List<Map> getActiveUsers(long begin, long end) {
		return loginDao.getActiveUsers(begin, end);
	}

	/**
	 * 查询用户数
	 */
	public int getActiveUsers(long service_id, long begin, long end) {
		return loginDao.getActiveUsers(service_id, begin, end);
	}
}
