package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.PetReport;

@Component
@Scope("singleton")
public class PetReportDao extends BaseHBDao<PetReport, String> {

    @Autowired
    public PetReportDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<PetReport> find(String deviceSn, Long begin, Long end) {
		String hql = "from PetReport r where r.deviceSn=? and r.time>=? and r.time<=? order by r.time desc";
		return list(hql, -1, -1, deviceSn, begin, end);
	}

}
