package com.unistrong.tracker.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import module.orm.BaseHBDao;

import com.unistrong.tracker.model.FeeCheck;

@Component
@Scope("singleton")
public class FeeCheckDao extends BaseHBDao<FeeCheck, String> {

    @Autowired
    public FeeCheckDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

    
	public List<FeeCheck> findBySn(String deviceSn) {

		String hql = " from  FeeCheck where date_format(receiveTime,'%Y%m%d') = (select max(date_format(receiveTime,'%Y%m%d')) from FeeCheck where deviceSn=?) "
				+ "and deviceSn = ?";
		return list(hql, -1, -1, deviceSn, deviceSn);
	}

}
