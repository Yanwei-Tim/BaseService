package com.unistrong.tracker.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import module.util.DateUtil;

import com.unistrong.tracker.model.AlarmType;
import com.unistrong.tracker.model.Notice;
import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.service.AlarmService;
import com.unistrong.tracker.service.ConditionNotice;
import com.unistrong.tracker.service.PositionService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.util.NoticeResponse;

@Service("notice-100")
public class SimCardNotice implements ConditionNotice {

	@Resource
	private UserDeviceService userDeviceService;
	@Resource
	private PositionService positionService;
	@Resource
	private AlarmService alarmService;
	
	public boolean check(Notice notice, Long userId) {
		List<String> deviceSns = userDeviceService.findSnByUserId(userId,-1,-1);
		long now = DateUtil.nowDate().getTime();
		long days3before=DateUtil.getYesterday(-3).getTime();
		StringBuilder sb=new StringBuilder();
		for (String deviceSn : deviceSns) {
			Position position = positionService.get(deviceSn);
			long hour=(now-position.getSystime())/3600000;
			if(hour<36){
				continue;
			}
			//拔出报警数
			int count=alarmService.findTypeByDevice(deviceSn, AlarmType.EPD.getNum(), days3before, now);
			if(count<0){
				sb.append(deviceSn+",");
			}
			
		}
		if(sb.length()<1){
			return false;
		}else{
			String dsn=removeEndLetter(sb.toString());
			notice.setContent(notice.getContent().replace("{devicesn}", dsn));
		}
		return true;
		
	}
	

	 public static String removeEndLetter(String str)
	  {
	    if ((str == null) || (str.length() <= 0)) {
	      return "";
	    }
	    return str.substring(0, str.length() - 1);
	  }
	 

}
