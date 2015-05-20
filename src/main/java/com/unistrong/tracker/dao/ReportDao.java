package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Report;

@Component
@Scope("singleton")
public class ReportDao extends BaseHBDao<Report, Long> {

    @Autowired
    public ReportDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<Report> find(String deviceSn, Long begin, Long end) {
		String hql = "from Report r where r.deviceSn=? and r.day>=? and r.day<=? order by r.day desc";
		return list(hql, -1, -1, deviceSn, begin, end);
	}

}
