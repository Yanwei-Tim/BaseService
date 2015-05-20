package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.IndoorFenceDao;
import com.unistrong.tracker.model.Fences;

@Service
public class IndoorFenceService {

	@Resource
	private IndoorFenceDao indoorFenceDao;

	public Long save(Fences fence) {
		return indoorFenceDao.save(fence);
	}

	public void saveOrUpdate(Fences fence) {
		indoorFenceDao.saveOrUpdate(fence);
	}

	public void delete(Fences entity) {
		indoorFenceDao.deleteObject(entity);
	}

	public void deleteById(Long id) {
		indoorFenceDao.delete(id);
	}

	public Fences get(Long id) {
		return indoorFenceDao.get(id);
	}

	public List<Fences> findAllBySystem(String system) {
		return indoorFenceDao.findAllBySystem(system);
	}

}
