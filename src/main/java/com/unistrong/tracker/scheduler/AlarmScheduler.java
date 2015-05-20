package com.unistrong.tracker.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.dao.AlarmDao;
import com.unistrong.tracker.model.Alarm;
import com.unistrong.tracker.service.AlarmService;
import com.unistrong.tracker.service.cache.PositionCache;

@Component
public class AlarmScheduler  extends QuartzJobBean{
	
	private static Logger logger = LoggerFactory.getLogger(AlarmScheduler.class);

	
	private PositionCache positionCache;
	
	private AlarmService alarmService;
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		List<Alarm> li=alarmService.listUnreadBefore3Day();
		
		Map<String,List> map=new HashMap();
		for(Alarm alarm:li){
			List as=map.get(alarm.getDeviceSn());
			if(as==null){
				as=new ArrayList();
				map.put(alarm.getDeviceSn(), as);
			}
			as.add(alarm);
		}
		logger.info("delete before 3 days cache alarm begin...");
		for(String sn:map.keySet()){
			List<Alarm> list=map.get(sn);
			logger.info("delete "+sn+" alarm :"+list.size());
			for(Alarm am:list){
				positionCache.removeAlarmId(sn, am.getId()+"");
			}
			
		}
		
	}

	
	public void setPositionCache(PositionCache positionCache) {
		this.positionCache = positionCache;
	}


	public void setAlarmService(AlarmService alarmService) {
		this.alarmService = alarmService;
	}


	
	
	
	

}
