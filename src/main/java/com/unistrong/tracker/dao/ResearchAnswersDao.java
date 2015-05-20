/**
 * 
 */
package com.unistrong.tracker.dao;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.ResearchAnswers;

/**
 * @author fss
 * 
 */
@Component
@Scope("singleton")
public class ResearchAnswersDao extends BaseHBDao<ResearchAnswers, Long> {

    @Autowired
    public ResearchAnswersDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public ResearchAnswers getByUserAndTopic(Long userId, Long topicId) {
		String hql = "FROM ResearchAnswers WHERE userId=? AND topicId=?";
		return unique(hql, userId, topicId);
	}
}
