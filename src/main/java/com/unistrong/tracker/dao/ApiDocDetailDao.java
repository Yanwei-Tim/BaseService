package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.ApiDocDetail;

@Component
@Scope("singleton")
public class ApiDocDetailDao extends BaseHBDao<ApiDocDetail, String> {

    @Autowired
    public ApiDocDetailDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<ApiDocDetail> getDocsDetail(int doc_id, int pageNumber,
			int pageSize) {
		String hql = " from ApiDocDetail where  docId=? ";
		return list(hql, pageNumber, pageSize, doc_id);
	}

	public List<ApiDocDetail> getDocsDetail(int doc_id, int rtype,
			int pageNumber, int pageSize) {
		String hql = " from ApiDocDetail where  docId=? and returnType=? ";
		return list(hql, pageNumber, pageSize, doc_id, rtype);
	}
}