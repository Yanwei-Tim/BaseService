package com.unistrong.tracker.dao;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.HandSetToConductor;

@Component
@Scope("singleton")
public class HandsetToConductorDao extends BaseHBDao <HandSetToConductor, Long> {

    private final String DELETE_BY_HANDSETID = "delete HandSetToConductor h where h.deviceSn = :deviceSn";
    
    @Autowired
    public HandsetToConductorDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

    public void deleteByHandsetId (String sn) {

        getCurrentSession ().createQuery (DELETE_BY_HANDSETID).setString ("deviceSn", sn).executeUpdate ();
    }

}
