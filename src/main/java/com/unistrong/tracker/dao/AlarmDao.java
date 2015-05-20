package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;
import module.util.DateUtil;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Alarm;

@Component
@Scope("singleton")
public class AlarmDao extends BaseHBDao<Alarm, String> {

    @Autowired
    public AlarmDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public void updateToRead(Long alarmId, String deviceSn) {
		String hql = "update Alarm set read=1 where id=? and deviceSn=?";
		executeUpdate(hql, alarmId, deviceSn);
	}

	public List<Alarm> listUnreadBefore3Day() {
		String hql = null;
		long max = DateUtil.getYesterday(-3).getTime();
		// long min = DateUtil.getYesterday(-90).getTime();
		hql = "from Alarm where  time<? and read=2";
		return list(hql, -1, -1, max);

	}

	public long getCountByTime(String deviceSn, Long start, Long end, int type) {
		if (end <= 0) {
			String hql = "select count(*) from Alarm where deviceSn=? and time>? and type=?";
			return unique(hql, deviceSn, start, type);
		} else {
			String hql = "select count(*) from Alarm where deviceSn=? and time>? and time<? and type=?";
			return unique(hql, deviceSn, start, end, type);
		}
	}

	public List<Alarm> listByTime(String deviceSn, Long start, Long end) {
		String hql = null;
		long day_limit = DateUtil.getYesterday(-3).getTime();
		if (day_limit > start) {
			start = day_limit;
		}
		if (end <= 0) {
			hql = "from Alarm where deviceSn=? and time>? ";
			return list(hql, -1, -1, deviceSn, start);
		} else {
			hql = "from Alarm where deviceSn=? and time>? and time<?";
			return list(hql, -1, -1, deviceSn, start, end);
		}

	}

	public int findTypeCount(String deviceSn, int type, Long start, Long end) {
		String hql = null;
		long day_limit = DateUtil.getYesterday(-3).getTime();
		if (day_limit > start) {
			start = day_limit;
		}
		hql = "SELECT COUNT(*) from Alarm where deviceSn=? and time>? and time<? and type=?";
		Long count = aggregate(hql, deviceSn, start, end, type);

		return count.intValue();

	}

	public void delete(String deviceSn) {
		String hql = " delete Alarm i where  i.deviceSn=? ";
		executeUpdate(hql, deviceSn);
	}

}
