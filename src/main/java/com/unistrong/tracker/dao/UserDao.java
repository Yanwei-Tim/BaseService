package com.unistrong.tracker.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.orm.BaseHBDao;
import module.util.Assert;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.manual.Config;
import com.unistrong.tracker.model.User;

@Component
@Scope("singleton")
public class UserDao extends BaseHBDao<User, Long> {
    
	@Resource
	private Config config;

    @Autowired
    public UserDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public User findByName(String name) {
		String hql = "FROM User WHERE name=?";
		return aggregate(hql, name);
	}

	public User findByName(String name, String appName) {
		String hql = "FROM User WHERE name=? AND appName=?";
		return aggregate(hql, name, appName);
	}

	public User findByEmail(String email) {
		String hql = "FROM User WHERE email=?";
		return aggregate(hql, email);
	}

	public User findByPhone(String telephone) {
		String hql = "FROM User WHERE phone=?";
		return aggregate(hql, telephone);
	}

	/**
	 * 属性在库中是否唯一
	 */
	public boolean isUnique(String propertyName, String newValue, Long id) {
		if (newValue == null)
			return false;
		User entity = null;
		if (null != id) {// edit
			entity = get(id);
			Assert.notNull(entity, "entity not exist");
			String oldValue = entity.getName();
			if (newValue.equals(oldValue))
				return true;
		}
		if ("name".equals(propertyName))
			entity = findByName(newValue);
		else if ("phone".equals(propertyName))
			entity = findByPhone(newValue);
		else if ("email".equals(propertyName))
			entity = findByEmail(newValue);
		else
			throw new IllegalArgumentException("not exist propertyName");
		return null == entity;
	}

	public List<User> getUserDown(Long userId, String appName) {
		List<User> userList = new ArrayList<User>();
		int layers = config.getLayers();

		List<User> list = new ArrayList<User>();
		for (int i = 0; i < layers; i++) {
			if (i == 0)
				list.add(new User(userId));
			if (list != null && list.size() > 0) {
				List<User> temp = new ArrayList<User>();
				for (int j = 0; j < list.size(); j++) {
					String hql1 = "FROM User WHERE parentId=? AND appName=?";
					List<User> list1 = list(hql1, -1, -1, list.get(j).getId(),
							appName);
					if (list1 != null && list1.size() > 0) {
						userList.addAll(list1);
						list.addAll(list1);
					}
					temp.add(list.get(j));
				}
				list.removeAll(temp);
			}
		}

		return userList;
	}

	public List<User> getUserUp(Long userId) {
		List<User> userList = new ArrayList<User>();

		Long uid = null;
		for (int i = 0; i < 100; i++) {
			if (i == 0)
				uid = userId;
			String hql1 = "FROM User WHERE id=(SELECT parentId FROM User WHERE id=?)";
			User user = unique(hql1, uid);
			if (user == null) {
				break;
			} else {
				userList.add(user);
				uid = user.getId();
			}
		}

		return userList;
	}

	public String getAppName(Long userId) {
		String hql = "select appName from User ud where id=?";
		List<String> list = list(hql, -1, -1, userId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<User> getUsersByServiceId(Long serviceId) {
		String hql = "FROM User WHERE serviceId=?";
		return list(hql, -1, -1);
	}

	public User findByUserAndServiceId(Long userId, Long serviceId) {
		String hql = "FROM User WHERE id=? and serviceId=?";
		return aggregate(hql, userId, serviceId);
	}

	public int findByServiceUserCount(Long serviceId) {
		String hql = "select count(*) from User where serviceId=? ";
		Long count = aggregate(hql, serviceId);
		return count.intValue();
	}
	
	
	public int findUsersByTime(long serviceId,long begin, long end) {
		String hql = "select count(*) from User where serviceId=? and time>=? and time<=? ";
		Long count = aggregate(hql, serviceId,begin,end);
		return count.intValue();
	}

	@SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public List<Map> findUsersByTime(long begin, long end) {
		Query query = getCurrentSession()
				.createSQLQuery(
						" SELECT ifnull(t.f_service_id,0) as sid, COUNT(*) as count FROM us_user t	WHERE  t.f_time>="
								+ begin
								+ " AND t.f_time<="
								+ end
								+ "  GROUP BY t.f_service_id");

		query.setResultTransformer(new ResultTransformer() {

			/**
             * 
             */
            private static final long serialVersionUID = -1810897435181359080L;

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
