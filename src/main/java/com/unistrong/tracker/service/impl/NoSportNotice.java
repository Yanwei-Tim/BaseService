package com.unistrong.tracker.service.impl;

import java.util.List;

import javax.annotation.Resource;

import module.util.DateUtil;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.Notice;
import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.service.ConditionNotice;
import com.unistrong.tracker.service.PositionService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.util.NoticeResponse;

@Service("notice-101")
public class NoSportNotice implements ConditionNotice {
	
	@Resource
	private UserDeviceService userDeviceService;
	
	@Resource
	private PositionService positionService;
	
	
	public boolean check(Notice notice, Long userId) {
		List<Device> devices = userDeviceService.findOneTypeByUserId(userId, 2);
		long yestoday = DateUtil.getYesterday(-1).getTime();
		StringBuilder sb=new StringBuilder();
		for (Device device : devices) {
			Position position = positionService.get(device.getSn());
			if(position.getSystime()<yestoday){
				sb.append(device.getName());
			}
		}
		
		if(sb.length()<1){
			return false;
		}else{
			String dsn=removeEndLetter(sb.toString());
			notice.setContent(notice.getContent().replace("{device}", dsn));
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
