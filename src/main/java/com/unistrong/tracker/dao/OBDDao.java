package com.unistrong.tracker.dao;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.OBD;

@Component
@Scope("singleton")
public class OBDDao extends BaseHBDao<OBD, Long> {

    @Autowired
    public OBDDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public OBD getOBD(String deviceSn, Long receive) {
		String hql = "FROM OBD s WHERE s.deviceSn=? and s.receive=?";
		return unique(hql, deviceSn, receive);
	}
	
	
}
