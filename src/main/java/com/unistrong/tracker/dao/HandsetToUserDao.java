package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.HandsetToUser;

@Component
@Scope("singleton")
public class HandsetToUserDao extends BaseHBDao <HandsetToUser, Long> {

    private final String SELECT_BY_USERID = "from HandsetToUser h where h.userId = ?";
    @Autowired
    public HandsetToUserDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

    public List <HandsetToUser> findByUserId (long userId) {

       return list (SELECT_BY_USERID, -1, -1, userId);
    }
}
