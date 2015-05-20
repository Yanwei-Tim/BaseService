package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.DeviceFence;

@Component
@Scope("singleton")
public class DeviceFenceDao extends BaseHBDao<DeviceFence, Long> {

    @Autowired
    public DeviceFenceDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<DeviceFence> findAllBySn(String sn, int pageNumber, int pageSize) {
		String hql = "FROM DeviceFence WHERE sn = ?";
		return list(hql, pageNumber, pageSize, sn);
	}

	public DeviceFence findBySnAndSystem(String sn, String system) {
		String hql = " FROM DeviceFence WHERE sn=? AND system=?";
		return unique(hql, sn, system);
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

	public void deleteByFenceId(Long fenceId) {
		String hql = " delete DeviceFence WHERE fenceId=? ";
		executeUpdate(hql, fenceId);
	}

}
