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

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.UserDevice;

@Component
@Scope("singleton")
public class UserDeviceDao extends BaseHBDao<UserDevice, Long> {

    @Autowired
    public UserDeviceDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public int deleteByDevice(String deviceSn) {
		String hql = "delete UserDevice where deviceSn=?";
		return executeUpdate(hql, deviceSn);
	}

	public int deleteByDeviceAndUser(String deviceSn, Long userId) {
		String hql = "delete UserDevice where deviceSn=? and userId=?";
		return executeUpdate(hql, deviceSn, userId);
	}

	public int deleteByUser(Long userId) {
		String hql = "delete UserDevice where userId=?";
		return executeUpdate(hql, userId);
	}

	public boolean isBind(String deviceSn) {
		String hql = "from UserDevice ud where ud.deviceSn=?";
		boolean tag = list(hql, -1, -1, deviceSn).size() > 0;
		return tag;
	}

	public UserDevice getBySnAndUser(String deviceSn, Long userId) {
		String hql = "from UserDevice ud where ud.deviceSn=? and ud.userId =?";
		return unique(hql, deviceSn, userId);
	}

	public List<Long> findUserIdBySn(String deviceSn) {
		String hql = "select ud.userId from UserDevice ud where ud.deviceSn=?";
		return list(hql, -1, -1, deviceSn);
	}
	
	public List<UserDevice> findBySn(String deviceSn) {
		String hql = " from UserDevice ud where ud.deviceSn=?";
		return list(hql, -1, -1, deviceSn);
	}

	public List<String> findSnByUserId(Long userId,int page_number, int page_size) {
		String hql = "select ud.deviceSn from UserDevice ud where ud.userId=?";
		return list(hql, page_number, page_size, userId);
	}

	@SuppressWarnings("unchecked")
    public List<Device> findOneTypeByUserId(Long userId, int type) {
		Query query = getCurrentSession()
				.createSQLQuery(
						"SELECT d.* from us_device d join us_user_device  ud on  ud.f_device_sn=d.f_sn WHERE   ud.f_user_id="
								+ userId + " and d.f_type=" + type).addEntity(
						Device.class);
		return query.list();
	}

	/**
	 * 指定时间内绑定设备数(设备属于某个服务用户)
	 * 
	 * @param userId
	 *            服务用户
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	public int findBatchDevices(Long userId, long begin, long end) {
		String hql = "select count(distinct t.deviceSn) from UserDevice t where t.time>=? and t.time<=? and  t.deviceSn in(select d.sn from Device d where d.serviceId=?) ";
		Long count = aggregate(hql, begin, end, userId);
		return count.intValue();
	}

	@SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public List<Map> findBatchDevices(long begin, long end) {
		Query query = getCurrentSession()
				.createSQLQuery(
						" SELECT ifnull(t.f_service_id,0) as sid, COUNT(DISTINCT d.f_device_sn) as count FROM us_device t,  us_user_device d	WHERE t.f_sn = d.f_device_sn	AND d.f_time>="
								+ begin
								+ " AND d.f_time<="
								+ end
								+ "  GROUP BY t.f_service_id");

		query.setResultTransformer(new ResultTransformer() {

			/**
             * 
             */
            private static final long serialVersionUID = 8911974634430504385L;

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
