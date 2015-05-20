/**
 * 
 */
package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.ResearchTopicDao;
import com.unistrong.tracker.model.ResearchTopic;

/**
 * @author fss
 *
 */
@Service
public class ResearchTopicService {
	
	@Resource
	private ResearchTopicDao researchTopicDao;
	
	public List<ResearchTopic> listAll(){
		return researchTopicDao.listAll();
	}
}
