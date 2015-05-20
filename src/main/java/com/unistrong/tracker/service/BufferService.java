package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.BufferDao;
import com.unistrong.tracker.model.Buffer;

@Service
public class BufferService {

	@Resource
	private BufferDao bufferDao;

	/**
	 * 新增或修改缓冲区
	 */
	public void saveOrUpdate(Buffer buffer) {
		bufferDao.saveOrUpdate(buffer);
	}

	/**
	 * 删除缓冲区
	 */
	public void delete(Buffer entity) {
		bufferDao.deleteObject(entity);
	}

	/**
	 * 根据ID查询缓冲区
	 * @param id
	 * @return
	 */
	public Buffer findById(Integer id) {
		return bufferDao.get(id);
	}

	/**
	 * 查询用户缓冲区
	 * @param userId
	 * @return
	 */
	public List<Buffer> findByUser(Long userId) {
		return bufferDao.findByUser(userId);
	}

}
