package com.unistrong.tracker.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.unistrong.tracker.dao.GuarderDao;
import com.unistrong.tracker.model.UserGuarder;

/**
 * 
 */
@Service
public class GuarderService {

	@Resource
	private GuarderDao guarderDao;

	public UserGuarder getGuarder(Long guarderId) {
		return guarderDao.get(guarderId);
	}

	public void saveOrUpdate(UserGuarder entity) {
		guarderDao.saveOrUpdate(entity);
	}
	
	public Long save(UserGuarder entity) {
		return guarderDao.save(entity);
	}

}