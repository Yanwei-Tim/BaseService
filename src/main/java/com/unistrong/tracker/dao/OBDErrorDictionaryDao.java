package com.unistrong.tracker.dao;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.ObdErrorDictionary;

@Component
@Scope("singleton")
public class OBDErrorDictionaryDao extends BaseHBDao<ObdErrorDictionary, Long> {
	
    @Autowired
    public OBDErrorDictionaryDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }
	
	public ObdErrorDictionary getErrorMapping(String code) {
		String hql = "FROM ObdErrorDictionary s WHERE s.code=? ";
		return unique(hql, code);
	}
	
}
