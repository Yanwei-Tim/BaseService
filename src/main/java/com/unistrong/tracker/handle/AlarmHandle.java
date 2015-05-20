package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.Assert;
import module.util.HttpUtil;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Alarm;
import com.unistrong.tracker.model.User;
import com.unistrong.tracker.service.AlarmService;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.service.UserService;
import com.unistrong.tracker.service.cache.PositionCache;
import com.unistrong.tracker.util.UsConstants;

/**
 * @author fyq
 */
@Component
public class AlarmHandle {

	@Resource
	private AlarmService alarmService;

	@Resource
	private UserDeviceService userDeviceService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private PositionCache positionCache;

	// *****************************************

	/**
	 * 修改告警
	 */
	public Map<String, Object> editAlarm(String[] alarmIds, String deviceSn, String userIdStr,
			String type, String time, String read, String lng, String lat, String info, String speed) {
		Map<String, Object> rs = new HashMap<String, Object>();
		
		if (alarmIds != null) {// 批量修改告警为已读
			HttpUtil.validateLong(new String[] { "user_id" }, new String[] { userIdStr });
			
			for (String alarmId : alarmIds) {
				String[] snId = alarmId.split("-");
				Assert.isTrue(snId.length == 2, "告警ID格式有误");
				String sn = snId[0];
				Long id = Long.parseLong(snId[1]);
				alarmService.updateToRead(id, sn);
				
			}
		}
		return rs;
	}

	/**
	 * 下属某设备告警
	 */
	public Map<String, Object> listByDevice(String userIdStr, String sn, String begin, String end,
			String pageNumberStr, String pageSizeStr) {
		HttpUtil.validateNull(new String[] { "user_id", "sn" }, new String[] { userIdStr, sn });
		HttpUtil.validateLong(new String[] { "user_id" }, new String[] { userIdStr });
		Map<String, Object> rs = new HashMap<String, Object>();
		Long beginNum = HttpUtil.getLong(begin, -1L);
		Long endNum = HttpUtil.getLong(end, -1L);
		List<Alarm> alarms = alarmService.findAllByUserAndDevice(sn, beginNum, endNum);
		rs.put("alarms", alarms);
		return rs;
	}

	/**
	 * 下属告警
	 */
	public Map<String, Object> list(String userIdStr, String begin, String end, String targetIdStr, String appName) {
		HttpUtil.validateNull(new String[] { "user_id" }, new String[] { userIdStr });
		HttpUtil.validateLong(new String[] { "user_id" }, new String[] { userIdStr });
		Map<String, Object> rs = new HashMap<String, Object>();
		Long beginNum = HttpUtil.getLong(begin, -1L);
		Long endNum = HttpUtil.getLong(end, -1L);
		//Long userId = HttpUtil.getLong(userIdStr);
		List<String> deviceSns = getDeviceSns(userIdStr, targetIdStr, appName);
		//List<String> deviceSns = userDeviceService.findSnByUserId(userId);
		List<Alarm> alarms = new ArrayList<Alarm>();
		for (String sn : deviceSns) {
			alarms.addAll(alarmService.findAllByUserAndDevice(sn, beginNum, endNum));
		}
		rs.put("alarms", alarms);
		return rs;
	}
	
	private List<String> getDeviceSns(String userIdStr, String targetIdStr, String appName){
		Long userId = HttpUtil.getLong(userIdStr);
		User user = userService.get(userId);
		List<String> deviceSns = new ArrayList<String>();
		
		if (user != null && user.getType() != null && user.getType() == 1 ) {
			if(targetIdStr != null) {
				Long targetId = HttpUtil.getLong(targetIdStr);
				Assert.isTrue(userService.isAuthorized(userId, targetId, appName), UsConstants.getI18nMessage(UsConstants.USER_DENIED));
				deviceSns = userDeviceService.findSnByUserId(targetId,-1,-1);
			}else {
				deviceSns = deviceService.getSnByBusinessUser(userId, appName,-1,-1);
			}
		}else {
			deviceSns = userDeviceService.findSnByUserId(userId,-1,-1);
		}
		return deviceSns;
	}

}
