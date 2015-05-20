package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Position;

@Component
@Scope("singleton")
public class PositionDao extends BaseHBDao<Position, Long> {

    @Autowired
    public PositionDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	/**
	 * 设备最新位置
	 */
	public Position findByDevice(String sn) {
		String hql = " FROM Position WHERE deviceSn=?";
		return unique(hql, sn);
	}

	/**
	 * 用户下属设备最新位置
	 */
	public List<Position> findByDeviceAndUser(Long userId) {
		String hql = "from Position p where p.deviceSn in(select ud.deviceSn from UserDevice ud where ud.userId = ?)";
		return list(hql, -1, -1, userId);
	}
}
