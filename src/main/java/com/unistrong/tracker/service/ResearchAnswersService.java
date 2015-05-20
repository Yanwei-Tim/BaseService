/**
 * 
 */
package com.unistrong.tracker.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.ResearchAnswersDao;
import com.unistrong.tracker.model.ResearchAnswers;

/**
 * @author fss
 *
 */
@Service
public class ResearchAnswersService {
	
	@Resource
	private ResearchAnswersDao researchAnswersDao;
	
	public void saveOrUpdate(ResearchAnswers entity) {
		researchAnswersDao.saveOrUpdate(entity);
	}
	
	public ResearchAnswers getByUserAndTopic(Long userId, Long topicId) {
		return researchAnswersDao.getByUserAndTopic(userId, topicId);
	}
}
