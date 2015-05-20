package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Fences;

@Component
@Scope("singleton")
public class IndoorFenceDao extends BaseHBDao<Fences, Long> {

    @Autowired
    public IndoorFenceDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<Fences> findAllBySystem(String system) {
		String hql = "FROM Fences WHERE system=?";
		return list(hql, -1, -1, system);
	}

}
