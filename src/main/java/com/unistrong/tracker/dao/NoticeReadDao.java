package com.unistrong.tracker.dao;

import java.util.List;

import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Notice;
import com.unistrong.tracker.model.NoticeRead;

@Component
@Scope("singleton")
public class NoticeReadDao  extends BaseHBDao<NoticeRead, Long>{

    @Autowired
    public NoticeReadDao  (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }
	
	public Long getLastReadTime(Notice notice,Long uid){
		String hql="from NoticeRead where noticeid=? and uid=? order by readtime desc";
		List<NoticeRead> li=this.list(hql,1,1, notice.getId(),uid);
		NoticeRead nr=null;
		if(li!=null&&li.size()>0){
			nr=li.get(0);
		}
		if(nr!=null){
			return nr.getReadtime();
		}else{
			return null;
		}
	}
	
	
}
