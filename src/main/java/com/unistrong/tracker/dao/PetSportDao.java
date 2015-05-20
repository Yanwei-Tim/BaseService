/**
 * 
 */
package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.PetSport;

/**
 * @author fss
 * 
 */
@Component
@Scope("singleton")
public class PetSportDao extends BaseHBDao<PetSport, Long> {

    @Autowired
    public PetSportDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<PetSport> getSportList(String deviceSn, Long begin, Long end){
		String hql0 = "FROM PetSport p WHERE p.deviceSn=? and p.receive<?  ORDER BY p.steps DESC ,p.receive DESC";
		List<PetSport> list = list(hql0, 1, 1, deviceSn, begin);
		
		String hql = "FROM PetSport p WHERE p.deviceSn=? and p.receive>=? and p.receive<=?  ORDER BY p.steps ASC,p.receive ASC ";
		List<PetSport> list0 = list(hql, -1, -1, deviceSn, begin, end);
		list.addAll(list0);
		return list;
	}
	
	public List<PetSport> getMax(String deviceSn, Long begin, Long end){
		String hql = "from PetSport p WHERE p.deviceSn=? and p.receive>? and p.receive<? and p.mode='A'";
		return list(hql, 1, 1, deviceSn, begin, end);
	}
}
