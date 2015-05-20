package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.Assert;
import module.util.DateUtil;
import module.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.i18n.MessageManager;
import com.unistrong.tracker.handle.util.IndoorSpotToWeb;
import com.unistrong.tracker.handle.util.SpotToVo;
import com.unistrong.tracker.handle.util.SpotToWeb;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.Report;
import com.unistrong.tracker.model.Spot;
import com.unistrong.tracker.model.User;
//import com.unistrong.tracker.model.WifiSpot;
import com.unistrong.tracker.model.serialize.SpeedVo;
import com.unistrong.tracker.service.AlarmService;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.InstructService;
import com.unistrong.tracker.service.OrbitFilterFactory;
import com.unistrong.tracker.service.PositionService;
import com.unistrong.tracker.service.ReportService;
import com.unistrong.tracker.service.SpotService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.service.UserService;
//import com.unistrong.tracker.service.WifiSpotService;
import com.unistrong.tracker.util.UsConstants;

/**
 * @author fyq
 */
@Component
public class SpotHandle {
	private static Logger logger = LoggerFactory.getLogger(SpotHandle.class);

	@Resource
	private PositionService positionService;

	@Resource
	private SpotService spotService;

	@Resource
	private DeviceService deviceService;

	@Resource
	private AlarmService alarmService;

	@Resource
	private UserService userService;

	@Resource
	private UserDeviceService userDeviceService;

	@Resource
	private InstructService instructService;

	@Resource
	private ReportService reportService;

	private final static int cutSecondLength = 1200000;// 分段时间间隔
														// 20分钟*60*1000=1200000
	private final static int mincutSecondLength = 600000;// 最小分段时间间隔 5分钟
	private final static int cutDistanceLength = 2000;// 分段距离

	// ******************************************

	/**
	 * 查询时间段的轨迹(一系列点)
	 */
	public Map<String, Object> getTrack(String sn, String begin, String end,
			String part,String appName) {
		HttpUtil.validateNull(new String[] { "sn" }, new String[] { sn });
		HttpUtil.validateLong(new String[] { "begin", "end" }, new String[] {
				begin, end });

		Long beginLong = HttpUtil.getLong(begin);
		Long endLong = HttpUtil.getLong(end);

		Map<String, Object> rs = new HashMap<String, Object>();
		Device device = deviceService.get(sn);
		List<Spot> list = null;
		if (device.getType() == 1) {
			list = spotService.getTrack(sn, beginLong, endLong);
		} else {
			list = spotService.getTrackGps(sn, beginLong, endLong);
		}
		if ("Y".equals(part)) {

			List<List<Spot>> rsPart = SpotToWeb.getPartList(list,
					device.getType());

			rs.put("track", rsPart);
			rs.put("part", 1);
		} else {

			if (device.getType() != 1) {
				Iterator<Spot> it = list.iterator();
				Spot lastSpot = null;
				while (it.hasNext()) {
					Spot spot = it.next();
					double thisDistance = 0;
					if (lastSpot != null) {
						thisDistance = SpotToWeb.getDistance(spot, lastSpot);
						if (thisDistance < 70) {
							it.remove();
						} else {
							lastSpot = spot;
						}
					} else {
						lastSpot = spot;
					}
				}
			}
			rs.put("track", list);
			rs.put("part", 0);
		}
		return rs;
	}

	/**
	 * 车牌号查轨迹
	 */
	public Map<String, Object> getParkForVehicle(String userIdStr,
			String licenseNum, String begin, String end, String targetIdStr,
			String appName) {

		HttpUtil.validateNull(new String[] { "license_num" },
				new String[] { licenseNum });
		HttpUtil.validateLong(new String[] { "user_id", "begin", "end" },
				new String[] { userIdStr, begin, end });
		String sn = deviceService.getSnByLicenseNum(licenseNum);// 根据车牌号取到设备号
		return getPark(userIdStr, sn, begin, end, targetIdStr, appName);
	}

	/**
	 * 分段,起终点
	 */
	public Map<String, Object> getPark(String userIdStr, String sn,
			String begin, String end, String targetIdStr, String appName) {
		HttpUtil.validateNull(new String[] { "sn" }, new String[] { sn });
		HttpUtil.validateLong(new String[] { "user_id", "begin", "end" },
				new String[] { userIdStr, begin, end });
		Long userId = HttpUtil.getLong(userIdStr);
		Long beginLong = HttpUtil.getLong(begin);
		Long endLong = HttpUtil.getLong(end);
		Map<String, Object> rs = new HashMap<String, Object>();

		userId = getUserId(userIdStr, targetIdStr, appName, sn);

		Device device = deviceService.get(sn);
		List<Spot> list = null;
		if (device.getType() == 1) {
			list = spotService.getTrack(sn, beginLong, endLong);
		} else {
			list = spotService.getTrackGps(sn, beginLong, endLong);
		}

		List<List<Spot>> rsPart = SpotToWeb.getPartList(list, device.getType());

		if (appName == null || appName.equals("")) {
			rs.put("track", SpotToVo.listSavOld(rsPart));
		} else {
			rs.put("track", SpotToVo.listSav(rsPart));
		}

		rs.put("part", 1);
		return rs;
	}

	/**
	 * 统计
	 */
	public Map<String, Object> statistic(String userIdStr, String deviceSn,
			String begin, String end, String targetIdStr, String appName) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpUtil.validateNull(new String[] { "sn" }, new String[] { deviceSn });
		HttpUtil.validateLong(new String[] { "begin", "end" }, new String[] {
				begin, end });
		Long userId = HttpUtil.getLong(userIdStr);
		Long beginLong = HttpUtil.getLong(begin);
		Long endLong = HttpUtil.getLong(end);
		Device device = deviceService.get(deviceSn);
		String unit = device.getType() == 2 ? MessageManager.CALORIE()
				: MessageManager.KIL();

		Long now = System.currentTimeMillis();
		String str = DateUtil.longStr(now, "yyyyMMdd");
		Long todayLong = DateUtil.strLong(str, "yyyyMMdd");
		List<Report> reports = new ArrayList<Report>();
		if (endLong >= todayLong) {
			userId = getUserId(userIdStr, targetIdStr, appName, deviceSn);
			List<Spot> list = null;
			if (device.getType() == 1) {
				list = spotService.getTrack(deviceSn, todayLong, endLong);
			} else {
				list = spotService.getTrackGps(deviceSn, todayLong, endLong);
			}
			Report today = new Report(deviceSn, todayLong);
			SpotToWeb.statisticTodaySpeed(list, today, device.getType());
			SpotToWeb.statisticToday(list, today, device.getType());
			Long overSpeedTime = alarmService.overSpeedTime(deviceSn,
					todayLong, endLong);// 超速次数
			today.setSpeeding(overSpeedTime.intValue());
			long outFence = alarmService.outFenceTime(deviceSn, todayLong,
					endLong);
			today.setOutFence((int) outFence);
			reports.add(today);
		}
		String beginLongStr = DateUtil.longStr(beginLong, "yyyyMMdd");
		Long beginLongDay = DateUtil.strLong(beginLongStr, "yyyyMMdd");
		reports.addAll(reportService.find(deviceSn, beginLongDay, endLong));
		int sumSpeeding = 0;
		for (Report report : reports) {
			sumSpeeding += report.getSpeeding();
		}
		List<SpeedVo> speedVos = SpotToWeb.statisticSpeed(reports,
				device.getType());
		long outFence = alarmService.outFenceTime(deviceSn, beginLongDay,
				endLong);
		rs.put("mileages", reports);// 当天里程 当天超速次数(当天停止时间 当天速度百分比)
		rs.put("speeding", sumSpeeding);// 总超速次数
		rs.put("outfence", outFence);
		rs.put("distributed", speedVos);// 总速度百分比
		rs.put("unit", unit);// 单位
		return rs;
	}

	private Long getUserId(String userIdStr, String targetIdStr,
			String appName, String sn) {
		Long id = null;
		Long userId = HttpUtil.getLong(userIdStr);
		User user = userService.get(userId);

		if (user != null && user.getType() != null && user.getType() == 1) {
			if (targetIdStr != null) {
				Long targetId = HttpUtil.getLong(targetIdStr);
				Assert.isTrue(
						userService.isAuthorized(userId, targetId, appName),
						UsConstants.getI18nMessage(UsConstants.USER_DENIED));
				id = targetId;
			} else {
				List<Long> list = userDeviceService.findUserIdBySn(sn);
				if (list != null && list.size() > 0)
					id = list.get(0);
			}
		} else {
			id = userId;
		}
		return id;
	}

	/**
	 * 查询时间段的轨迹(一系列点)
	 */
	public Map<String, Object> getIndoorTrack(String sn, String begin,
			String end, String part, String appName) {
		HttpUtil.validateNull(new String[] { "sn" }, new String[] { sn });
		HttpUtil.validateLong(new String[] { "begin", "end" }, new String[] {
				begin, end });

		Long beginLong = HttpUtil.getLong(begin);
		Long endLong = HttpUtil.getLong(end);

		Map<String, Object> rs = new HashMap<String, Object>();
		List<Spot> list = spotService.getTrack(sn, beginLong, endLong);

		rs.put("track", list);
		rs.put("part", 0);
		return rs;
	}

	/**
	 * 分段,起终点
	 */
	public Map<String, Object> getIndoorPark(String userIdStr, String sn,
			String begin, String end, String targetIdStr, String appName) {
		HttpUtil.validateNull(new String[] { "sn" }, new String[] { sn });
		HttpUtil.validateLong(new String[] { "user_id", "begin", "end" },
				new String[] { userIdStr, begin, end });
		Long userId = HttpUtil.getLong(userIdStr);
		Long beginLong = HttpUtil.getLong(begin);
		Long endLong = HttpUtil.getLong(end);
		Map<String, Object> rs = new HashMap<String, Object>();

		userId = getUserId(userIdStr, targetIdStr, appName, sn);

		Device device = deviceService.get(sn);

		List<Spot> list = spotService.getTrack(sn, beginLong, endLong);

		List<List<Spot>> rsPart = IndoorSpotToWeb.getPartList(list,
				device.getType());
		
		if (appName == null || appName.equals("")) {
			rs.put("track", SpotToVo.listSavOldIndoor(rsPart));
		} else {
			rs.put("track", SpotToVo.listSavIndoor(rsPart));
		}

		rs.put("part", 1);
		return rs;
	}

}
