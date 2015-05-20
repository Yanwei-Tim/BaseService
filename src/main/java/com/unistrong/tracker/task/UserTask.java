package com.unistrong.tracker.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.DateUtil;
import module.util.HttpUtil;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.UserStat;
import com.unistrong.tracker.service.LoginService;
import com.unistrong.tracker.service.StatService;
import com.unistrong.tracker.service.UserService;

@Component
public class UserTask {

	@Resource
	private UserService userService;

	@Resource
	private LoginService loginService;

	@Resource
	private StatService statService;

	private static Logger logger = LoggerFactory.getLogger(UserTask.class);

	public void stat() {

		Date now = new Date();
		Date date = DateUtil.getNDaysAfter(now, -1);
		long begin = DateUtil.getDateBegin(date);
		long end = DateUtil.getDateEnd(date);

		long yesterday_begin = DateUtil.getDateBegin(DateUtil.getNDaysAfter(
				date, -1));
		long yesterday_end = DateUtil.getDateEnd(DateUtil.getNDaysAfter(date,
				-1));

		// 上月的同日
		Date mdate = DateUtil.getLastMonthDay(date);
		String day = DateUtil.dateStr(date, "dd");
		String mday = DateUtil.dateStr(mdate, "dd");

		List<Map> user_new_month = new ArrayList<Map>();

		if (day.equalsIgnoreCase(mday)) {
			long mbegin = DateUtil.getDateBegin(mdate);
			long mend = DateUtil.getDateEnd(mdate);
			// 上月同日新增用户
			user_new_month = userService.findUsersByTime(mbegin, mend);
		}

		// 新增用户
		List<Map> user_new = userService.findUsersByTime(begin, end);
		// 累计用户
		List<Map> user_total = userService.findUsersByTime(1, end);
		// 活跃用户
		List<Map> user_active = loginService.getActiveUsers(begin, end);

		// 昨日新增用户
		List<Map> user_new_yesterday = userService.findUsersByTime(
				yesterday_begin, yesterday_end);

		Map<Long, Integer> map_user_new = new HashMap<Long, Integer>();
		Map<Long, Integer> map_user_total = new HashMap<Long, Integer>();
		Map<Long, Integer> map_user_active = new HashMap<Long, Integer>();
		Map<Long, Integer> map_user_new_y = new HashMap<Long, Integer>();
		Map<Long, Integer> map_user_new_month = new HashMap<Long, Integer>();

		for (Map m : user_new) {
			long sid = Long.valueOf(m.get("sid").toString());
			int count = Integer.valueOf(m.get("count").toString());
			map_user_new.put(sid, count);
		}

		for (Map m : user_total) {
			long sid = Long.valueOf(m.get("sid").toString());
			int count = Integer.valueOf(m.get("count").toString());
			map_user_total.put(sid, count);
		}

		for (Map m : user_active) {
			long sid = Long.valueOf(m.get("sid").toString());
			int count = Integer.valueOf(m.get("count").toString());
			map_user_active.put(sid, count);
		}

		for (Map m : user_new_yesterday) {
			long sid = Long.valueOf(m.get("sid").toString());
			int count = Integer.valueOf(m.get("count").toString());
			map_user_new_y.put(sid, count);
		}

		for (Map m : user_new_month) {
			long sid = Long.valueOf(m.get("sid").toString());
			int count = Integer.valueOf(m.get("count").toString());
			map_user_new_month.put(sid, count);
		}

		List<UserStat> list = new ArrayList<UserStat>();
		for (Map.Entry<Long, Integer> entry : map_user_total.entrySet()) {

			long serviceId = entry.getKey();
			int userTotal = HttpUtil.getIntegerBasicType(entry.getValue(), 0);
			int userNew = HttpUtil.getIntegerBasicType(
					map_user_new.get(serviceId), 0);
			int userActive = HttpUtil.getIntegerBasicType(
					map_user_active.get(serviceId), 0);

			int userNewY = HttpUtil.getIntegerBasicType(
					map_user_new_y.get(serviceId), 0);

			int userNewMonth = HttpUtil.getIntegerBasicType(
					map_user_new_month.get(serviceId), 0);

			UserStat us = new UserStat();
			us.setfServiceId(serviceId);
			us.setfUserNew(userNew);
			us.setfUserTotal(userTotal);
			us.setfUserActive(userActive);

			double dayRate = 0.0;
			if (userNewY > 0) {
				dayRate = (double) (userNew - userNewY) / userNewY;
			}
			us.setfDayRate(dayRate);

			if (day.equalsIgnoreCase(mday)) {
				double monthRate = 0.0;
				if (userNewMonth > 0) {
					monthRate = (double) (userNew - userNewMonth)
							/ userNewMonth;
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
			list.add(us);

			UserStat us_now = new UserStat();
			us_now.setfServiceId(serviceId);
			us_now.setfUserNew(0);
			us_now.setfUserTotal(0);
			us_now.setfUserActive(0);
			us_now.setfDayRate(0.0);
			us_now.setfMonthRate(0.0);

			String now_date = sdf.format(now);
			Date nfdate = null;
			try {
				nfdate = sdf.parse(now_date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			us_now.setfStatDate(nfdate);
			list.add(us_now);

		}

		statService.saveUserStat(list);
	}

	public static void main(String[] args) {
		//
		// ApplicationContext ctx = new ClassPathXmlApplicationContext(
		// "ContextData.xml");
		// userService = (UserService) ctx.getBean("userService");
		//
		// loginService = (LoginService) ctx
		// .getBean("loginService");
		//
		// statService = (StatService) ctx.getBean("statService");
		//
		// Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, -300);
		//
		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// for (int i = 1; i <= 300; i++) {
		// Date now = DateUtil.getNDaysAfter(cal.getTime(), i);
		// System.out.println(format.format(now));
		// new UserTask().stat(now);
		// }

	}
}
