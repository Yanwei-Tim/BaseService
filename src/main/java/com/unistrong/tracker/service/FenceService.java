package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.FenceDao;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.Fence;

@Service
public class FenceService {

	@Resource
	private FenceDao fenceDao;

	/**
	 * 新增或修改围栏
	 */
	public void saveOrUpdate(Fence fence) {
		fenceDao.saveOrUpdate(fence);
	}

	/**
	 * 删除围栏
	 */
	public void delete(Fence entity) {
		fenceDao.deleteObject(entity);
	}

	public Fence get(Long id) {
		return fenceDao.get(id);
	}

	/**
	 * 查询某用户下具体围栏,可能为空
	 */
	public Fence findByIdAndUser(Long id, Long userId) {
		return fenceDao.findByIdAndUser(id, userId);
	}

	/**
	 * 查询某用户下属围栏列表
	 */
	public List<Fence> findAllByUser(Long userId, int pageNumber, int pageSize) {
		return fenceDao.findAllByUser(userId, pageNumber, pageSize);
	}

	public List<Device> findByFence(Long fenceId, Long userId) {
		return fenceDao.findByFence(fenceId, userId);
	}

	public List<Device> findByFence(Long fenceId) {
		return fenceDao.findByFence(fenceId);
	}
}
