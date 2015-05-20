package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.UserDeviceForbidDao;
import com.unistrong.tracker.model.UserDeviceForbid;

@Service
public class UserDeviceForbidService {

	@Resource
	private UserDeviceForbidDao userDeviceForbidDao;

	public void saveOrUpdate(UserDeviceForbid entity) {
		userDeviceForbidDao.saveOrUpdate(entity);
	}

	public int deleteByDevice(String deviceSn) {
		return userDeviceForbidDao.deleteByDevice(deviceSn);
	}
	
	public List<UserDeviceForbid> findBySn(String deviceSn) {
		return userDeviceForbidDao.findBySn(deviceSn);
	}

}
