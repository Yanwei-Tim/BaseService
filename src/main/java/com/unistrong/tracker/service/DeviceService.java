package com.unistrong.tracker.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.DeviceDao;
import com.unistrong.tracker.dao.FeeCheckDao;
import com.unistrong.tracker.dao.UserDao;
import com.unistrong.tracker.dao.UserDeviceDao;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.FeeCheck;
import com.unistrong.tracker.model.Fence;
import com.unistrong.tracker.model.User;
import com.unistrong.tracker.model.UserDevice;
import com.unistrong.tracker.service.cache.DeviceCache;

/**
 * @author fyq
 */
@Service
public class DeviceService {

	@Resource
	private DeviceDao deviceDao;

	@Resource
	private UserDeviceDao userDeviceDao;

	@Resource
	private UserDao userDao;

	@Resource
	private DeviceCache deviceCache;

	@Resource
	private FeeCheckDao feeCheckDao;

	/**
	 * change
	 */
	public void saveOrUpdate(Device entity, UserDevice userDevice) {
		deviceDao.saveOrUpdate(entity);
		userDeviceDao.saveOrUpdate(userDevice);
	}

	public void saveOrUpdate(Device entity) {
		deviceDao.saveOrUpdate(entity);
		deviceCache.setDevice(entity.getSn(), entity);
	}

	public void update(Device entity) {
		deviceDao.update(entity);
		deviceCache.setDevice(entity.getSn(), entity);
	}

	/**
	 * change
	 */
	public void delete(Device entity) {
		userDeviceDao.deleteByDevice(entity.getSn());
		deviceDao.deleteObject(entity);
	}

	public Device get(String sn) {
		return deviceDao.get(sn);
	}

	public List<Device> findAllByUser(Long userId, int pageNumber, int pageSize) {
		return deviceDao.findAllByUser(userId, pageNumber, pageSize);
	}
	
	public List<Device> findAllByHardWare(String hardWare) {
		return deviceDao.findAllByHardWare(hardWare);
	}

	public List<Device> findByServiceUser(Long serviceId, String sn, int operType,
			int pageNumber, int pageSize) {
		return deviceDao.findByServiceUser(serviceId, sn, operType, pageNumber,
				pageSize);
	}

	public Device findBySnAndServiceUser(String sn, Long serviceId) {
		return deviceDao.findBySnAndServiceUser(sn, serviceId);
	}

	public List<String> findAllSnByUser(Long userId, int pageNumber,
			int pageSize) {
		return deviceDao.findAllSnByUser(userId, pageNumber, pageSize);
	}

	public List<Device> findAllDeviceBySns(String sns, int pageNumber,
			int pageSize) {
		return deviceDao.findAllDeviceBySns(sns, pageNumber, pageSize);
	}
	
	public List<Device> findAll() {
		return deviceDao.listAll();
	}

	public List<String> findAllSn() {
		return deviceDao.findAllSn();
	}

	public int countAll() {
		return deviceDao.countAll();
	}

	public Device findBySnAndUser(String sn, Long userId) {
		return deviceDao.findBySnAndUser(sn, userId);
	}

	public Fence getFenceByDevice(String sn) {
		return deviceDao.getFenceByDevice(sn);
	}

	public List<String> getSnByBusinessUser(Long userId, String appName,int page_number, int page_size) {
		List<String> sns = new ArrayList<String>();
		List<User> userList = userDao.getUserDown(userId, appName);

		Set<String> snSet = new HashSet<String>();
		for (User user : userList) {
			List<Device> temp = findAllByUser(user.getId(), page_number, page_size);
			if (temp != null && temp.size() > 0) {
				for (Device device : temp) {
					snSet.add(device.getSn());
				}
			}
		}
		sns.addAll(snSet);
		return sns;
	}

	
	public List<FeeCheck> getFeeCheck(String deviceSn){
		return feeCheckDao.findBySn(deviceSn);
	}
	
	public String getSnByLicenseNum(String licenseNum){
	    return deviceDao.getSnByLicenseNum(licenseNum);
	}

	/**
	 * 查询用户拥有的设备数目
	 * 
	 * @param userId
	 * @return
	 */
	public int findByServiceUserCount(Long serviceId) {
		return deviceDao.findByServiceUserCount(serviceId);
	}

	/**
	 * 删除用户拥有的设备
	 * 
	 * @param userId
	 */
	public void deteteByServiceUser(Long serviceId) {
		deviceDao.deteteByServiceUser(serviceId);
	}
	
	public List<Map> findDevicesCount() {
		return deviceDao.findDevicesCount();
	}


}
