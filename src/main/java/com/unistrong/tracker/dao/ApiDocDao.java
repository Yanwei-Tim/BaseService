package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.ApiDoc;

@Component
@Scope("singleton")
public class ApiDocDao extends BaseHBDao<ApiDoc, String> {

    @Autowired
    public ApiDocDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }
    
	public List<ApiDoc> getDocs(int pageNumber, int pageSize) {
		String hql = " from ApiDoc ";
		return list(hql, pageNumber, pageSize);
	}
}