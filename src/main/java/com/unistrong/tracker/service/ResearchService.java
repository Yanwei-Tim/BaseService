/**
 * 
 */
package com.unistrong.tracker.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.ResearchDao;
import com.unistrong.tracker.model.Research;

/**
 * @author fss
 *
 */
@Service
public class ResearchService {

	@Resource
	private ResearchDao researchDao;

	public void saveOrUpdate(Research research) {
		researchDao.saveOrUpdate(research);
	}
	
	public void update(Research research) {
		researchDao.update(research);
	}

	public void delete(Research research) {
		researchDao.deleteObject(research);
	}
	
	public Research get(Long userId) {
		return researchDao.get(userId);
	}
}
