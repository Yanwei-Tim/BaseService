package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.UserDeviceForbid;

@Component
@Scope("singleton")
public class UserDeviceForbidDao extends BaseHBDao <UserDeviceForbid, Long> {

    @Autowired
    public UserDeviceForbidDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

    public int deleteByDevice (String deviceSn) {

        String hql = "delete UserDeviceForbid where deviceSn=?";
        return executeUpdate (hql, deviceSn);
    }

    public List <UserDeviceForbid> findBySn (String deviceSn) {

        String hql = " from UserDeviceForbid ud where ud.deviceSn=?";
        return list (hql, -1, -1, deviceSn);
    }
}
