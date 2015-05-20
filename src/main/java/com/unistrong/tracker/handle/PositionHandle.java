package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.Assert;
import module.util.HttpUtil;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.dao.UserDao;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.model.User;
import com.unistrong.tracker.service.AlarmService;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.PositionService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.service.UserService;
import com.unistrong.tracker.service.cache.UserCache;
import com.unistrong.tracker.util.UsConstants;

/**
 * @author fyq
 */
@Component
public class PositionHandle {

	@Resource
	private PositionService positionService;

	@Resource
	private UserDeviceService userDeviceService;

	@Resource
	private AlarmService alarmService;

	@Resource
	private UserService userService;

	@Resource
	private DeviceService deviceService;

	@Resource
	private UserCache userCache;

	/** 是否推送位置 */
	public Map<String, Object> pushSpot(String duid, String push,
			String userId, String appName) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Boolean isPush = "1".equals(push);

		if (appName == null) {
			appName = userService.getAppName(Long.valueOf(userId));
		}
		if (appName == null) {
			appName = "M2616_BD";
		}
		if (isPush) {
			userCache.removeBackendPhone(userId, duid, appName);
		} else {
			userCache.addBackendPhone(userId, duid, appName);
		}
		return rs;
	}

	/** 设备最新位置 */
	public Map<String, Object> getLast(String userIdStr, String deviceSn,
			String targetIdStr, String appName) {
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });
		Map<String, Object> rs = new HashMap<String, Object>();
		List<String> deviceSns = getDeviceSns(userIdStr, targetIdStr, appName);
		Assert.isTrue(deviceSns.contains(deviceSn),
				UsConstants.getI18nMessage(UsConstants.USER_NOTDEVICE));
		Position position = positionService.get(deviceSn);
		if (null != position)
			position.setAlarm(alarmService.findCount(position.getDeviceSn()));
		rs.put("status", position);
		return rs;
	}

	/** 设备最新位置 */
	public Map<String, Object> getLast(String userIdStr, String deviceSn) {
		
		HttpUtil.validateNull(new String[] { "user_id", "sn"},
				new String[] { userIdStr, deviceSn});
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });
		
		Map<String, Object> rs = new HashMap<String, Object>();

		Device device = deviceService.findBySnAndServiceUser(deviceSn,
				Long.valueOf(userIdStr));

		Assert.notNull(device,
				UsConstants.getI18nMessage(UsConstants.USER_NOTDEVICE));

		Position position = positionService.get(deviceSn);
		if (null != position)
			position.setAlarm(alarmService.findCount(position.getDeviceSn()));
		rs.put("status", position);
		return rs;
	}

	/** 下属所有最新位置 */
	public Map<String, Object> getLasts(String userIdStr, String targetIdStr, String appName,String page_number, String page_size) {
		HttpUtil.validateLong(new String[] { "user_id" }, new String[] { userIdStr });

		Long userId = HttpUtil.getLong(userIdStr);
		int pageNumber = HttpUtil.getInt(page_number, -1);
		int pageSize = HttpUtil.getInt(page_size, -1);
		User user = userService.get(userId);
		Map<String, Object> rs = new HashMap<String, Object>();
		List<String> deviceSns = new ArrayList<String>();

		if (user != null && user.getType() != null && user.getType() == 1) {
			if (targetIdStr != null) {
				Long targetId = HttpUtil.getLong(targetIdStr);
				Assert.isTrue(
						userService.isAuthorized(userId, targetId, appName),
						UsConstants.getI18nMessage(UsConstants.USER_DENIED));
				deviceSns = userDeviceService.findSnByUserId(targetId,pageNumber,pageSize);
			} else {
				deviceSns = deviceService.getSnByBusinessUser(userId, appName,pageNumber,pageSize);
			}
		} else {
			deviceSns = userDeviceService.findSnByUserId(userId,pageNumber,pageSize);
		}
		List<Position> positions = new ArrayList<Position>();
		for (String deviceSn : deviceSns) {
			Position position = positionService.get(deviceSn);
			if (null != position)
				position.setAlarm(alarmService.findCount(position.getDeviceSn()));
			positions.add(position);
		}

		rs.put("statuss", positions);
		return rs;
	}

	private List<String> getDeviceSns(String userIdStr, String targetIdStr,
			String appName) {
		Long userId = HttpUtil.getLong(userIdStr);
		User user = userService.get(userId);
		List<String> deviceSns = new ArrayList<String>();

		if (user != null && user.getType() != null && user.getType() == 1) {
			if (targetIdStr != null) {
				Long targetId = HttpUtil.getLong(targetIdStr);
				Assert.isTrue(
						userService.isAuthorized(userId, targetId, appName),
						UsConstants.getI18nMessage(UsConstants.USER_DENIED));
				deviceSns = userDeviceService.findSnByUserId(targetId,-1,-1);
			} else {
				deviceSns = deviceService.getSnByBusinessUser(userId, appName,-1,-1);
			}
		} else {
			deviceSns = userDeviceService.findSnByUserId(userId,-1,-1);
		}
		return deviceSns;
	}
}
