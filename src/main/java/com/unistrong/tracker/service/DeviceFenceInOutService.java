package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.DeviceFenceInOutDao;
import com.unistrong.tracker.model.CountModel;
import com.unistrong.tracker.model.DeviceFenceInOut;

@Service
public class DeviceFenceInOutService {

	@Resource
	private DeviceFenceInOutDao deviceFenceInOutDao;

	public void saveOrUpdate(DeviceFenceInOut entity) {
		deviceFenceInOutDao.saveOrUpdate(entity);
	}

	public void update(DeviceFenceInOut entity) {
		deviceFenceInOutDao.update(entity);
	}

	public void delete(DeviceFenceInOut entity) {
		deviceFenceInOutDao.deleteObject(entity);
	}

	public void deleteByFenceId(Long fence_id) {
		deviceFenceInOutDao.deleteByFenceId(fence_id);
	}

	public List<DeviceFenceInOut> findAllBySn(String sn, int pageNumber,
			int pageSize) {
		return deviceFenceInOutDao.findAllBySn(sn, pageNumber, pageSize);
	}

	public List<DeviceFenceInOut> findAll() {
		return deviceFenceInOutDao.listAll();
	}

	public DeviceFenceInOut findBySnAndSystem(String sn, String system) {
		return deviceFenceInOutDao.findBySnAndSystem(sn, system);
	}

//	public List<CountModel> findBySnAndTime(String sn, Long begin,
//			Long end, Integer type) {
//		return deviceFenceInOutDao.findBySnAndTime(sn, begin, end, type);
//	}

}
