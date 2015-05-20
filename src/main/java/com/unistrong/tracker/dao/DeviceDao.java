package com.unistrong.tracker.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import module.orm.BaseHBDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.Fence;

/**
 * @author fyq
 */
@Component
@Scope("singleton")
public class DeviceDao extends BaseHBDao<Device, String> {

    @Autowired
    public DeviceDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	public List<Device> findAllByUser(Long userId, int pageNumber, int pageSize) {
		String hql = "from Device d where d.sn in (select deviceSn from UserDevice ud where ud.userId=?)";
		// if ("Y".equals(state))
		// hql =
		// "from Device d left join fetch d.position where d.sn in (select deviceSn from UserDevice ud where ud.userId=?)";
		return list(hql, pageNumber, pageSize, userId);
	}

	public List<String> findAllSnByUser(Long userId,int pageNumber, int pageSize) {
		String hql = "select ud.deviceSn from UserDevice ud where ud.userId=?";
		List<String> list= list(hql, pageNumber, pageSize,userId);
		return list;
	}
	
	public List<Device> findAllByHardWare(String hardWare) {
		String hql = "from Device d where d.hardware = ?";
		return list(hql, -1, -1, hardWare);
	}
	
	public List<Device> findAllDeviceBySns(String sns,int pageNumber, int pageSize) {
		String hql = "from Device d where d.sn in ("+sns+")";
		List<Device> list= list(hql, pageNumber, pageSize);
		return list;
	}
	
	public Device findBySnAndUser(String sn, Long userId) {
		String hql = "from Device d where d.sn=? and d.sn in (select deviceSn from UserDevice ud where ud.userId=?)";
		return unique(hql, sn, userId);
	}

	/** 关系 */
	public Fence getFenceByDevice(String sn) {
		String hql = "SELECT d.fence FROM Device d WHERE d.sn=?";
		return unique(hql, sn);
	}

	/** 所有sn */
	public List<String> findAllSn() {
		String hql = "SELECT sn FROM Device";
		return list(hql, -1, -1);
	}

	// ***********************************************************

	/** 用户下设备总数 未使用 */
	public int getCountByUser(Long userId) {
		String hql = "SELECT COUNT(*) FROM Device WHERE userId=?";
		Long count = aggregate(hql, userId);
		return count.intValue();
	}

	/** 未使用 */
	public Device findBySnAndUserInit(String sn, Long userId) {
		String hql = "FROM Device d WHERE d.sn=? AND d.userId=?";
		Device device = unique(hql, sn, userId);
		Hibernate.initialize(device.getFence());
		return device;
	}
	
	/** 根据车牌号取设备号 */
    public String getSnByLicenseNum(String licenseNum) {
        String hql = "SELECT d.sn FROM Device d WHERE d.car=?";
        return unique(hql,licenseNum);
    }

	/**
	 * 后台用户查询属于自己的设备
	 * 
	 * @param userId
	 * @param sn
	 * @param operType
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<Device> findByServiceUser(Long serviceId, String sn, int operType,
			int pageNumber, int pageSize) {
		String hql = " from Device d where d.serviceId=? ";

		if (!StringUtils.isBlank(sn)) {
			if (operType == 1) {// 模糊
				hql += " and d.sn like '%" + sn + "%' ";
			} else if (operType == 2) {// 开头
				hql += " and d.sn like '" + sn + "%' ";
			} else if (operType == 3) {// 结尾
				hql += " and d.sn like '%" + sn + "' ";
			} else if (operType == 4) {// 精确
				hql += " and d.sn = " + sn;
			}
		}

		return list(hql, pageNumber, pageSize, serviceId);
	}
	
	public Device findBySnAndServiceUser(String sn, Long serviceId) {
		String hql = "from Device d where d.sn=? and d.serviceId=?)";
		return unique(hql, sn, serviceId);
	}
	
	
	/**
	 * 查询用户拥有的设备数目
	 * 
	 * @param userId
	 * @return
	 */
	public int findByServiceUserCount(Long serviceId) {
		String hql = "select count(*) from Device where serviceId=? ";
		Long count = aggregate(hql, serviceId);
		return count.intValue();
	}

	/**
	 * 删除用户拥有的设备
	 * 
	 * @param serviceId
	 */
	public void deteteByServiceUser(Long serviceId) {
		String hql = "delete Device where serviceId=?";
		executeUpdate(hql, serviceId);
	}
	
	
	@SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public List<Map> findDevicesCount() {
		Query query = getCurrentSession()
				.createSQLQuery(
						"SELECT ifnull(t.f_service_id,0) as sid, COUNT(DISTINCT t.f_sn) as count FROM us_device t group by t.f_service_id ");

		query.setResultTransformer(new ResultTransformer() {

			/**
             * 
             */
            private static final long serialVersionUID = -1522516056075610048L;

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
