package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.ApiModel;

@Component
@Scope("singleton")
public class ApiModelDao extends BaseHBDao<ApiModel, String> {

    @Autowired
    public ApiModelDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<ApiModel> getModels(int pageNumber, int pageSize) {
		String hql = " from ApiModel ";
		return list(hql, pageNumber, pageSize);
	}
}