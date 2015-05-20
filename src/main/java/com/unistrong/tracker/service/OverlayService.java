package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.OverlayDao;
import com.unistrong.tracker.model.Overlay;

@Service
public class OverlayService {

	@Resource
	private OverlayDao overlayDao;

	/**
	 * 新增或修改重叠区
	 */
	public void saveOrUpdate(Overlay overlay) {
		overlayDao.saveOrUpdate(overlay);
	}

	/**
	 * 删除重叠区
	 */
	public void delete(Overlay entity) {
		overlayDao.deleteObject(entity);
	}

	/**
	 * 根据ID查询重叠区
	 * @param id
	 * @return
	 */
	public Overlay findById(Integer id) {
		return overlayDao.get(id);
	}

	/**
	 * 查询用户缓冲区
	 * @param userId
	 * @return
	 */
	public List<Overlay> findByUser(Long userId) {
		return overlayDao.findByUser(userId);
	}

}
