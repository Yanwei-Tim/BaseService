package com.unistrong.tracker.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unistrong.tracker.dao.LoginDao;
import com.unistrong.tracker.dao.UserDao;
import com.unistrong.tracker.dao.UserDeviceDao;
import com.unistrong.tracker.model.User;

@Service
public class UserService {

	@Resource
	private UserDao userDao;

	@Resource
	private UserDeviceDao userDeviceDao;
	
	@Resource
	private LoginDao loginDao;

	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
	}

	/**
	 * 删除用户
	 */
	public void delete(User user) {
		userDao.deleteObject(user);
	}

	public void delete(Long id) {
		userDao.delete(id);
		userDeviceDao.deleteByUser(id);
	}

	public String getAppName(Long userId) {
		return userDao.getAppName(userId);
	}

	/**
	 * 查询用户
	 */
	@Transactional
	public User get(Long id) {
		return userDao.get(id);
	}

	/**
	 * 查询用户
	 */
	public User findByName(String name) {
		return userDao.findByName(name);
	}

	/**
	 * 查询用户
	 */
	public User findByName(String name, String appName) {
		return userDao.findByName(name, appName);
	}

	/**
	 * 查询用户
	 */
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	/**
	 * 查询用户
	 */
	public User findByPhone(String telephone) {
		return userDao.findByPhone(telephone);
	}

	public boolean isUnique(String property, String newValue, Long id) {
		return userDao.isUnique(property, newValue, id);
	}

	public List<User> getUserDown(Long userId, String appName) {
		List<User> list = userDao.getUserDown(userId, appName);
		User user = userDao.get(userId);
		list.add(0, user);
		return list;
	}

	public List<User> getUserUp(Long userId) {
		List<User> list = userDao.getUserUp(userId);
		User user = userDao.get(userId);
		list.add(user);
		return list;
	}

	public boolean isAuthorized(Long userId, Long targetId, String appName) {
		List<User> list = getUserDown(userId, appName);
		boolean b = false;
		for (User user : list) {
			if (user.getId().equals(targetId)) {
				b = true;
				break;
			}
		}
		return b;
	}

	/**
	 * 查询用户
	 */
	public List<User> getUsersByServiceId(Long serviceId) {
		return userDao.getUsersByServiceId(serviceId);
	}

	
	/**
	 * 查询用户
	 */
	public User findByUserAndServiceId(Long userId,Long serviceId) {
		return userDao.findByUserAndServiceId(userId,serviceId);
	}

	
	/**
	 * 查询用户数
	 */
	public int findByServiceUserCount(Long serviceId) {
		return userDao.findByServiceUserCount(serviceId);
	}

	/**
	 * 查询用户数
	 */
	public List<Map> findUsersByTime(long begin, long end) {
		return userDao.findUsersByTime(begin, end);
	}
	
	/**
	 * 查询用户数
	 */
	public int findUsersByTime(long serviceId,long begin, long end) {
		return userDao.findUsersByTime(serviceId,begin, end);
	}
	
}
