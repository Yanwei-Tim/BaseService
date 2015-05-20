package com.unistrong.tracker.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import module.orm.BaseHBDao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Login;

@Component
@Scope("singleton")
public class LoginDao extends BaseHBDao<Login, Long> {

    @Autowired
    public LoginDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public int getActiveUsers(long service_id,long begin, long end) {
		String hql = "select count(distinct userId) from Login where serviceId=? and time>=? and time<=? ";
		Long count = aggregate(hql, service_id,begin,end);
		return count.intValue();
	}
	

	@SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public List<Map> getActiveUsers(long begin, long end) {
		Query query = getCurrentSession()
				.createSQLQuery(
						" SELECT ifnull(t.f_service_id,0) as sid, COUNT(distinct t.f_user_id) as count FROM us_login t	WHERE  t.f_time>="
								+ begin
								+ " AND t.f_time<="
								+ end
								+ "  GROUP BY t.f_service_id");

		query.setResultTransformer(new ResultTransformer() {

			/**
             * 
             */
            private static final long serialVersionUID = 8527758088981963352L;

            @Override
			public Object transformTuple(Object[] values, String[] columns) {
				Map<String, Object> map = new LinkedHashMap<String, Object>(1);
				int i = 0;
				for (String column : columns) {
					map.put(column, values[i++]);
				}
				return map;
			}

			@Override
			public List transformList(List list) {
				return list;
			}
		});

		return query.list();
	}

}
