/**
 * 
 */
package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;
import module.util.DateUtil;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Notice;

/**
 * @author haisheng
 * 
 */
@Component
@Scope("singleton")
public class NoticeDao extends BaseHBDao<Notice, Long> {

    @Autowired
    public NoticeDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

    //type小的级别高
	public List<Notice> getEnable(String platform) {
		long now = DateUtil.nowDate().getTime();
		String hql = "from Notice where  onlinetime<? and offlinetime>=? and platform like ?  order by type asc";
		List<Notice> li= list(hql, -1,-1, now,now,"%"+platform+"%");
		return li;
		
	}
	
	
	
}
