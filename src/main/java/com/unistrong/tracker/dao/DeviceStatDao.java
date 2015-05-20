package com.unistrong.tracker.dao;

import java.util.Date;
import java.util.List;

import module.orm.BaseHBDao;
import module.util.EntityUtil;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.DeviceStat;

@Component
@Scope("singleton")
public class DeviceStatDao extends BaseHBDao<DeviceStat, String> {

    @Autowired
    public DeviceStatDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

    
	/**
	 * top n
	 * 
	 * @param userId
	 * @param top
	 * @param isAsc
	 * @return
	 */
	public List<DeviceStat> findDeviceStatByUser(Long userId, int top,
			boolean isAsc) {

		StringBuilder sb = new StringBuilder();

		sb.append(" from DeviceStat t where t.fServiceId=? order by t.fStatDate ");
		if (isAsc) {
			sb.append(" asc ");
		} else {
			sb.append(" desc ");
		}
		sb.append(" limit " + top);

		return list(sb.toString(), -1, -1, userId);
	}

	/**
	 * 设备绑定
	 * 
	 * @param userId
	 * @param begin
	 * @param end
	 * @param isAsc
	 * @return
	 */
	public List<DeviceStat> findDeviceStatByUserAndDate(Long userId,
			String begin, String end, boolean isAsc) {

		StringBuilder sb = new StringBuilder();

		sb.append(" from DeviceStat t where t.fServiceId=? and t.fStatDate>=? and t.fStatDate<=? order by t.fStatDate ");
		if (isAsc) {
			sb.append(" asc ");
		} else {
			sb.append(" desc ");
		}

		return list(sb.toString(), -1, -1, userId, begin, end);
	}

	private DeviceStat findDeviceStat(Long userId, Date day) {
		String hql = " from DeviceStat t where t.fServiceId=? and t.fStatDate=? ";
		return aggregate(hql, userId, day);
	}

	public void save(List<DeviceStat> list) {
		for (DeviceStat dv : list) {
			if (dv == null) {
				return;
			}

			DeviceStat ds = findDeviceStat(dv.getfServiceId(),
					dv.getfStatDate());
			if (ds == null) {
				save(dv);
			} else {
				BeanUtils.copyProperties(dv, ds,
						EntityUtil.nullField(DeviceStat.class, dv));
				saveOrUpdate(ds);
			}
		}
	}
}