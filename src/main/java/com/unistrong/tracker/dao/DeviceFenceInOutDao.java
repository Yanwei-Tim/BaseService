package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.DeviceFenceInOut;

@Component
@Scope("singleton")
public class DeviceFenceInOutDao extends BaseHBDao<DeviceFenceInOut, Long> {

    @Autowired
    public DeviceFenceInOutDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<DeviceFenceInOut> findAllBySn(String sn, int pageNumber,
			int pageSize) {
		String hql = "FROM DeviceFenceInOut WHERE sn = ?";
		return list(hql, pageNumber, pageSize, sn);
	}

	public DeviceFenceInOut findBySnAndSystem(String sn, String system) {
		String hql = " FROM DeviceFenceInOut WHERE sn=? AND system=?";
		return unique(hql, sn, system);
	}

	// public List<DeviceFenceInOut> findBySnAndTime(String sn, Long begin,
	// Long end, Integer type) {
	// String hql =
	// " FROM DeviceFenceInOut WHERE sn=? AND begin>=? and end=<? and type=? ";
	// return list(hql, -1, -1, sn, begin, end, type);
	// }

	// public List<DeviceFenceInOut> findBySnAndTime(String sn, Long begin,
	// Long end, Integer type) {
	// String hql =
	// " FROM DeviceFenceInOut WHERE sn=? AND begin>=? and end=<? and type=? ";
	// return list(hql, -1, -1, sn, begin, end, type);
	// }

//	public List<CountModel> findBySnAndTime(String sn, Long begin, Long end,
//			Integer type) {
//
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT s.f_sn as deviceSn,s.f_fence_id as fenceId,count(*) as num FROM us_device_fence_inout s WHERE s.f_sn=? and s.f_time >=? and s.f_time <=? and s.f_type =? "
//				+ " group by  s.f_sn,s.f_fence_id order by s.f_sn,s.f_fence_id ");
//
//		List<Object> ls = new ArrayList<Object>();
//		ls.add(sn);
//		ls.add(begin);
//		ls.add(end);
//		ls.add(type);
//
//		return query(sb.toString(), ls);
//	}

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
		String hql = " delete DeviceFenceInOut WHERE fenceId=? ";
		executeUpdate(hql, fenceId);
	}

}
