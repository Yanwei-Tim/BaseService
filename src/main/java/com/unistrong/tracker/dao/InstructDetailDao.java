package com.unistrong.tracker.dao;


import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.InstructDetail;

@Component
@Scope("singleton")
public class InstructDetailDao extends BaseHBDao<InstructDetail, String> {

    @Autowired
    public InstructDetailDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }
}
