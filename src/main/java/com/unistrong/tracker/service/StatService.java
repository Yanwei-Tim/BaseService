package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.DeviceDao;
import com.unistrong.tracker.dao.DeviceStatDao;
import com.unistrong.tracker.dao.UserDao;
import com.unistrong.tracker.dao.UserStatDao;
import com.unistrong.tracker.model.DeviceStat;
import com.unistrong.tracker.model.UserStat;

/**
 * 
 * @author zhangxianpeng
 * 
 */
@Service
public class StatService {

	@Resource
	private UserStatDao userStatDao;

	@Resource
	private DeviceStatDao deviceStatDao;

	@Resource
	private UserDao userDao;

	@Resource
	private DeviceDao deviceDao;

	// public List<DeviceStatistics> findDeviceStatByUser(Long userId, int top,
	// boolean isAsc) {
	// return deviceStatDao.findDeviceStatByUser(userId, top, isAsc);
	// }
	//
	// public List<DeviceStatistics> findDeviceStatByUserAndDate(Long userId,
	// String begin, String end, boolean isAsc) {
	// return deviceStatDao.findDeviceStatByUserAndDate(userId, begin, end,
	// isAsc);
	// }
	//
	// public List<DeviceStatistics> findUserStatByUserAndDate(Long userId,
	// String begin, String end, boolean isAsc) {
	// return userStatDao.findUserStatByUserAndDate(userId, begin, end, isAsc);
	// }

	public void saveDeviceStat(List<DeviceStat> list) {
		deviceStatDao.save(list);
	}

	public void saveUserStat(List<UserStat> list) {
		userStatDao.save(list);
	}

	public List<UserStat> findUserStatByDate(String day) {
		return userStatDao.findUserStatByDate(day);
	}

	public int getUserCount(Long service_id) {
		return userDao.findByServiceUserCount(service_id);
	}
	
	public int getDeviceCount(Long service_id) {
		return deviceDao.findByServiceUserCount(service_id);
	}

}
