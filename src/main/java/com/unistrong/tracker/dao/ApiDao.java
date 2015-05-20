package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Api;

@Component
@Scope("singleton")
public class ApiDao extends BaseHBDao<Api, String> {

    @Autowired
    public ApiDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<Api> getApis(int pageNumber, int pageSize) {
		String hql = " from Api ";
		return list(hql, pageNumber, pageSize);
	}
}