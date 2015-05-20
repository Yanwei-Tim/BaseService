package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Instruct;

@Component
@Scope("singleton")
public class InstructDao extends BaseHBDao<Instruct, String> {

    @Autowired
    public InstructDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }
    
	/**
	 * 离线指令
	 */
	public List<Instruct> find(String deviceSn) {
		String hql = "from Instruct i where i.deviceSn=? and i.reply=0";
		return list(hql, -1, -1, deviceSn);
	}
	
	
	/**
	 * 删除指令
	 */
	public void delete(String deviceSn) {
		String hql = "delete Instruct i where i.deviceSn=? ";
		executeUpdate(hql, deviceSn);
	}

    public List<Instruct> getByType (String deviceSn, int type) {
        
        String hql = "from Instruct i where i.deviceSn=? and i.type=?";
        return list (hql, -1, -1, deviceSn,type);
        
    }

}
