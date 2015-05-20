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

import com.unistrong.tracker.model.ResearchTopic;

/**
 * @author fss
 * 
 */
@Component
@Scope("singleton")
public class ResearchTopicDao extends BaseHBDao<ResearchTopic, Long> {

    @Autowired
    public ResearchTopicDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

}
