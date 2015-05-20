package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.DeviceFenceDao;
import com.unistrong.tracker.model.DeviceFence;

@Service
public class DeviceFenceService {

	@Resource
	private DeviceFenceDao deviceFenceDao;

	public void saveOrUpdate(DeviceFence entity) {
		deviceFenceDao.saveOrUpdate(entity);
	}

	public void update(DeviceFence entity) {
		deviceFenceDao.update(entity);
	}

	public void delete(DeviceFence entity) {
		deviceFenceDao.deleteObject(entity);
	}
	
	public void deleteByFenceId(Long fence_id) {
		deviceFenceDao.deleteByFenceId(fence_id);
	}
	

	public List<DeviceFence> findAllBySn(String sn, int pageNumber, int pageSize) {
		return deviceFenceDao.findAllBySn(sn, pageNumber, pageSize);
	}

	public List<DeviceFence> findAll() {
		return deviceFenceDao.listAll();
	}

	public DeviceFence findBySnAndSystem(String sn, String system) {
		return deviceFenceDao.findBySnAndSystem(sn, system);
	}

}
