package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Buffer;

@Component
@Scope("singleton")
public class BufferDao extends BaseHBDao<Buffer, Integer> {

    @Autowired
    public BufferDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	/** 用户缓冲区 */
	public List<Buffer> findByUser(Long userId) {
		String hql = "select b FROM Buffer b WHERE b.userId=?";
		return list(hql, -1, -1,userId);
	}

}
