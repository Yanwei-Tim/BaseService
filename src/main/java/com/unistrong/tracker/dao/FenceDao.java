package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.Fence;

@Component
@Scope("singleton")
public class FenceDao extends BaseHBDao<Fence, Long> {

    @Autowired
    public FenceDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }
    
	/**
	 * 用户下围栏列表
	 */
	public List<Fence> findAllByUser(Long userId, int pageNumber, int pageSize) {
		String hql = "FROM Fence WHERE userId = ?";
		return list(hql, pageNumber, pageSize, userId);
	}

	/**
	 * 用户下具体围栏,无则空
	 */
	public Fence findByIdAndUser(Long id, Long userId) {
		String hql = " FROM Fence WHERE id=? AND userId=?";
		return unique(hql, id, userId);
	}

	/** 围栏下属设备 关系 */
	public List<Device> findByFence(Long fenceId, Long userId) {
		String hql = "select f.devices FROM Fence f WHERE f.id=? AND f.userId=?";
		return list(hql, -1, -1, fenceId, userId);
	}

	public List<Device> findByFence(Long fenceId) {
		String hql = "select f.devices FROM Fence f WHERE f.id=?";
		return list(hql, -1, -1, fenceId);
	}
}
