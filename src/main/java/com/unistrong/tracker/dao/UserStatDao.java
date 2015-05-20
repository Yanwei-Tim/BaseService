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

import com.unistrong.tracker.model.UserStat;

@Component
@Scope("singleton")
public class UserStatDao extends BaseHBDao<UserStat, String> {

    @Autowired
    public UserStatDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	// public List<UserStat> findUserStatByUserAndDate(Long userId, String
	// begin,
	// String end, boolean isAsc) {
	//
	// StringBuilder sb = new StringBuilder();
	//
	// sb.append(" from UserStat t where t.serviceUser=? and t.statDate>=? and t.statDate<=? order by t.statDate ");
	// if (isAsc) {
	// sb.append(" asc ");
	// } else {
	// sb.append(" desc ");
	// }
	//
	// return list(sb.toString(), -1, -1, userId, begin, end);
	// }

	private UserStat findUserStat(Long userId, Date day) {
		String hql = " from UserStat t where t.fServiceId=? and t.fStatDate=? ";
		return aggregate(hql, userId, day);
	}

	public void save(List<UserStat> list) {
		for (UserStat user : list) {
			if (user == null) {
				return;
			}

			UserStat us = findUserStat(user.getfServiceId(), user.getfStatDate());
			if (us == null) {
				save(user);
			} else {
				BeanUtils.copyProperties(user, us,
						EntityUtil.nullField(UserStat.class, user));
				saveOrUpdate(us);
			}
		}
	}

	public List<UserStat> findUserStatByDate(String day) {
		StringBuilder sb = new StringBuilder();
		sb.append(" from UserStat t where t.fStatDate=? ");
		return list(sb.toString(), -1, -1, day);
	}
}