package com.unistrong.tracker.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.UserDeviceDao;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.UserDevice;

@Service
public class UserDeviceService {

	@Resource
	private UserDeviceDao userDeviceDao;

	public void saveOrUpdate(UserDevice entity) {
		userDeviceDao.saveOrUpdate(entity);
	}

	public int deleteByDevice(String deviceSn) {
		return userDeviceDao.deleteByDevice(deviceSn);
	}

	public int deleteByDeviceAndUser(String deviceSn, Long userId) {
		return userDeviceDao.deleteByDeviceAndUser(deviceSn, userId);
	}

	public boolean isBind(String deviceSn) {
		return userDeviceDao.isBind(deviceSn);
	}

	public UserDevice getBySnAndUser(String deviceSn, Long userId) {
		return userDeviceDao.getBySnAndUser(deviceSn, userId);
	}

	public List<Long> findUserIdBySn(String deviceSn) {
		return userDeviceDao.findUserIdBySn(deviceSn);
	}
	
	public List<UserDevice> findBySn(String deviceSn) {
		return userDeviceDao.findBySn(deviceSn);
	}

	public List<String> findSnByUserId(Long userId,int page_number, int page_size) {
		return userDeviceDao.findSnByUserId(userId,page_number,page_size);
	}

	public List<Device> findOneTypeByUserId(Long userId, int type) {
		return userDeviceDao.findOneTypeByUserId(userId, type);
	}

	public int findBatchDevices(Long userId, long begin, long end) {
		return userDeviceDao.findBatchDevices(userId, begin, end);
	}

	public List<Map> findBatchDevices(long begin, long end) {
		return userDeviceDao.findBatchDevices(begin, end);
	}

}
