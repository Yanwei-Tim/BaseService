/**
 * 
 */
package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import module.util.DateUtil;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.NoticeDao;
import com.unistrong.tracker.dao.NoticeReadDao;
import com.unistrong.tracker.model.Notice;
import com.unistrong.tracker.model.NoticeRead;

/**
 * @author 
 *
 */
@Service
public class NoticeService {
	
	@Resource
	private NoticeDao noticeDao;
	@Resource
	private NoticeReadDao noticeReadDao;
	
	
	
	public Notice getEnable(String platform,Long uid) {
		List<Notice> li= noticeDao.getEnable(platform);
		for(Notice notice:li){
			Long time=noticeReadDao.getLastReadTime(notice, uid);
			if(time!=null){
				if(notice.getFrequency()!=null&&notice.getFrequency()>0){
					long now = DateUtil.nowDate().getTime();
					long hour=(now-time)/3600000;
					if(hour>notice.getFrequency()){
						return notice;
					}
				}
				
			}else{
				return notice;
			}
			
		}
		
		return null;
	}
	
	
	public void saveRead(Long noticeid,Long uid) {
		NoticeRead nr=new NoticeRead();
		nr.setNoticeid(noticeid);
		nr.setUid(uid);
		nr.setReadtime(DateUtil.nowDate().getTime());
		noticeReadDao.save(nr);
	}
	
	
}
