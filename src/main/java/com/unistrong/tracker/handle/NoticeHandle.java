/**
 * 
 */
package com.unistrong.tracker.handle;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import module.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Notice;
import com.unistrong.tracker.service.ConditionNotice;
import com.unistrong.tracker.service.NoticeService;
import com.unistrong.tracker.util.NoticeResponse;

/**
 * @author haisheng
 * 
 */
@Component
public class NoticeHandle implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(NoticeHandle.class);
	

	@Resource
	private NoticeService noticeService;
	
	private ApplicationContext ctx;
	
	public Map<String, Object> read(String userIdStr,  String platform) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpUtil.validateNull(new String[] { "platform" }, new String[] { platform });
		HttpUtil.validateLong(new String[] { "user_id" }, new String[] { userIdStr });
		Long userId = HttpUtil.getLong(userIdStr);
		
		Notice notice = noticeService.getEnable(platform,userId);
		NoticeResponse responseData = new NoticeResponse();
		
		if (notice == null) {
			responseData.setType(1);
		}else{
			responseData.setTitle(notice.getTitle());
			responseData.setUrl(notice.getUrl());
			responseData.setContent(notice.getContent());
			if(notice.getType().equals(2)){//版本更新
				if("force".equals(notice.getCondition())){
					//强制更新
					responseData.setType(4);
				}else{
					responseData.setType(3);
				}
				
			}else{
				ConditionNotice cn=null;
				try{
					cn=ctx.getBean("notice-"+notice.getType(), ConditionNotice.class);
				}catch(Exception e){
				}
				if(cn!=null){
					boolean isShow=cn.check(notice, userId);
					if(isShow){
						responseData.setType(2);
					}else{
						responseData.setType(1);
					}
					
				}else{
					logger.warn("notice-"+notice.getType()+" service lost,please check!!!!!!");
					responseData.setType(2);
				}
				
				
			}
			
		}
		
	    if(responseData.getType()>1){
	    	noticeService.saveRead(notice.getId(), userId);
	    }
		rs.put("version", responseData);
		
		return rs;
	}
	
	
	 
		public void setApplicationContext(ApplicationContext ctx) throws BeansException {
			this.ctx = ctx;
		}
	  
}
