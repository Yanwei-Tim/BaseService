package com.unistrong.tracker.handle;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.DateUtil;
import module.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.DeviceStat;
import com.unistrong.tracker.model.UserStat;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.LoginService;
import com.unistrong.tracker.service.StatService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.service.UserService;

/**
 * @author fyq
 */
@Component
public class StatHandle {

	@Resource
	private StatService statService;

	@Resource
	private DeviceService deviceService;

	@Resource
	private LoginService loginService;

	@Resource
	private UserService userService;

	@Resource
	private UserDeviceService userDeviceService;

	private static Logger logger = LoggerFactory.getLogger(StatHandle.class);

	public Map<String, Object> getUsersAndDevices(String serviceIdStr) {

		HttpUtil.validateLong(new String[] { "service_id" },
				new String[] { serviceIdStr });
		long service_id = HttpUtil.getLong(serviceIdStr);

		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.put("user", statService.getUserCount(service_id));
		rs.put("device", statService.getDeviceCount(service_id));
		return rs;
	}

	public Map<String, Object> getDeviceStat(String serviceIdStr) {

		HttpUtil.validateLong(new String[] { "service_id" },
				new String[] { serviceIdStr });
		long service_id = HttpUtil.getLong(serviceIdStr);

		Date date = new Date();
		long begin = DateUtil.getDateBegin(date);
		long end = DateUtil.getDateEnd(date);

		// 新绑定设备
		int installDevice = userDeviceService.findBatchDevices(service_id,
				begin, end);
		// 已绑定设备
		int installTotalDevice = userDeviceService.findBatchDevices(service_id,
				1, end);
		// 设备总数量
		int totalDevice = deviceService.findByServiceUserCount(service_id);

		double d = 0.0;
		// 已绑定/总设备
		if (totalDevice > 0) {
			// d = (double) installTotalDevice / totalDevice;
			double a = (double) installTotalDevice / totalDevice;
			BigDecimal bg = new BigDecimal(a);
			d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		double dd = 0.0;
		if (installTotalDevice > 0) {
			// 新绑定/已绑定
			double aa = (double) installDevice / installTotalDevice;

			BigDecimal bg = new BigDecimal(aa);
			dd = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		}

		DeviceStat ds = new DeviceStat();
		ds.setfInstallDevice(installDevice);
		ds.setfInstallRate(d);
		ds.setfInstallTotalDevice(installTotalDevice);
		ds.setfNewInstallRate(dd);
		ds.setfServiceId(service_id);
		ds.setfTotalDevice(totalDevice);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sdate = sdf.format(date);
		Date fdate = null;
		try {
			fdate = sdf.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ds.setfStatDate(fdate);

		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("device", ds);

		return rs;
	}

	public Map<String, Object> getUserStat(String serviceIdStr) {

		HttpUtil.validateLong(new String[] { "service_id" },
				new String[] { serviceIdStr });
		long service_id = HttpUtil.getLong(serviceIdStr);

		// 今天
		Date date = new Date();
		long begin = DateUtil.getDateBegin(date);
		long end = DateUtil.getDateEnd(date);

		long yesterday_begin = DateUtil.getDateBegin(DateUtil.getNDaysAfter(
				date, -1));
		long yesterday_end = DateUtil.getDateEnd(DateUtil
				.getNDaysAfter(date, -1));

		// 上月的同日
		Date mdate = DateUtil.getLastMonthDay(date);
		String day = DateUtil.dateStr(date, "dd");
		String mday = DateUtil.dateStr(mdate, "dd");

		int user_new_month = 0;

		if (day.equalsIgnoreCase(mday)) {
			long mbegin = DateUtil.getDateBegin(mdate);
			long mend = DateUtil.getDateEnd(mdate);
			// 上月同日新增用户
			user_new_month = userService.findUsersByTime(service_id, mbegin,
					mend);
		}

		// 新增用户
		int user_new = userService.findUsersByTime(service_id, begin, end);
		// 累计用户
		int user_total = userService.findUsersByTime(service_id, 1, end);
		// 活跃用户
		int user_active = loginService.getActiveUsers(service_id, begin, end);

		// 昨日新增用户
		int user_new_yesterday = userService.findUsersByTime(service_id,
				yesterday_begin, yesterday_end);

		UserStat us = new UserStat();
		us.setfServiceId(service_id);
		us.setfUserNew(user_new);
		us.setfUserTotal(user_total);
		us.setfUserActive(user_active);
		
		
		double dayRate = 0.0;
		if (user_new_yesterday > 0) {
			double a = (double) (user_new - user_new_yesterday) / user_new_yesterday;
			BigDecimal bg = new BigDecimal(a);
			dayRate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		us.setfDayRate(dayRate);

		
		if (day.equalsIgnoreCase(mday)) {
			double monthRate = 0.0;
			if (user_new_month > 0) {
				double aa = (double) (user_new - user_new_month)
						/ user_new_month;
				
				BigDecimal bg = new BigDecimal(aa);
				monthRate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				
			}
			us.setfMonthRate(monthRate);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sdate = sdf.format(date);
		Date fdate = null;
		try {
			fdate = sdf.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		us.setfStatDate(fdate);

		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("user", us);

		return rs;
	}

	// public Map<String, Object> getDeviceStat(String userIdStr, String begin,
	// String end, Boolean isAsc) {
	//
	// HttpUtil.validateLong(new String[] { "user_id" },
	// new String[] { userIdStr });
	// Long userId = HttpUtil.getLong(userIdStr);
	//
	// boolean is_asc = HttpUtil.getBoolean(isAsc, false);
	//
	// List<DeviceStat> slist = statService.findDeviceStatByUserAndDate(
	// userId, begin, end, is_asc);
	//
	// Map<String, Object> rs = new HashMap<String, Object>();
	// rs.put("device_stat", slist);
	// return rs;
	//
	// }
	//
	// public Map<String, Object> getUserStat(String userIdStr, String begin,
	// String end, Boolean isAsc) {
	//
	// HttpUtil.validateLong(new String[] { "user_id" },
	// new String[] { userIdStr });
	// Long userId = HttpUtil.getLong(userIdStr);
	//
	// boolean is_asc = HttpUtil.getBoolean(isAsc, false);
	//
	// List<UserStat> slist = statService.findUserStatByUserAndDate(userId,
	// begin, end, is_asc);
	//
	// Map<String, Object> rs = new HashMap<String, Object>();
	// rs.put("user_stat", slist);
	// return rs;
	//
	// }
}
