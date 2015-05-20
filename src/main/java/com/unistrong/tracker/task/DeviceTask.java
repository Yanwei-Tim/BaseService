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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.DeviceStat;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.StatService;
import com.unistrong.tracker.service.UserDeviceService;

@Component
public class DeviceTask {

	@Resource
	private DeviceService deviceService;

	@Resource
	private UserDeviceService userDeviceService;

	@Resource
	private StatService statService;

	private static Logger logger = LoggerFactory.getLogger(DeviceTask.class);

	@SuppressWarnings("rawtypes")
	public void stat() {

		Date now = new Date();
		Date date = DateUtil.getNDaysAfter(now, -1);
		long begin = DateUtil.getDateBegin(date);
		long end = DateUtil.getDateEnd(date);

		// 新绑定设备
		List<Map> newDevice = userDeviceService.findBatchDevices(begin, end);

		// 已绑定设备
		List<Map> achieveDeviceCount = userDeviceService.findBatchDevices(1,
				end);

		// 设备总数量
		List<Map> deviceCount = deviceService.findDevicesCount();

		Map<Long, Integer> map_count = new HashMap<Long, Integer>();
		Map<Long, Integer> map_achieve_count = new HashMap<Long, Integer>();
		Map<Long, Integer> map_new = new HashMap<Long, Integer>();

		for (Map m : deviceCount) {
			long sid = Long.valueOf(m.get("sid").toString());
			int count = Integer.valueOf(m.get("count").toString());
			map_count.put(sid, count);
		}

		for (Map m : achieveDeviceCount) {
			long sid = Long.valueOf(m.get("sid").toString());
			int count = Integer.valueOf(m.get("count").toString());
			map_achieve_count.put(sid, count);
		}

		for (Map m : newDevice) {
			long sid = Long.valueOf(m.get("sid").toString());
			int count = Integer.valueOf(m.get("count").toString());
			map_new.put(sid, count);
		}

		List<DeviceStat> list = new ArrayList<DeviceStat>();

		for (Map.Entry<Long, Integer> entry : map_count.entrySet()) {

			Long serviceId = entry.getKey();

			int totalDevice = HttpUtil.getIntegerBasicType(entry.getValue(), 0);
			int installTotalDevice = HttpUtil.getIntegerBasicType(
					map_achieve_count.get(serviceId), 0);
			int installDevice = HttpUtil.getIntegerBasicType(
					map_new.get(serviceId), 0);

			// DecimalFormat df = new DecimalFormat("0.00%");

			double d = 0.0;
			// 已绑定/总设备
			if (totalDevice > 0) {
				d = (double) installTotalDevice / totalDevice;
			}

			double dd = 0.0;
			if (installTotalDevice > 0) {
				// 新绑定/已绑定
				dd = (double) installDevice / installTotalDevice;
			}

			// String installRate = df.format(d);
			// String newInstallDeviceRate = df.format(dd);

			DeviceStat ds = new DeviceStat();
			ds.setfInstallDevice(installDevice);
			ds.setfInstallRate(d);
			ds.setfInstallTotalDevice(installTotalDevice);
			ds.setfNewInstallRate(dd);
			ds.setfServiceId(serviceId);
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

			DeviceStat ds_now = new DeviceStat();
			ds_now.setfInstallDevice(0);
			ds_now.setfInstallRate(0.0);
			ds_now.setfInstallTotalDevice(0);
			ds_now.setfNewInstallRate(0.0);
			ds_now.setfServiceId(serviceId);
			ds_now.setfTotalDevice(0);

			String nowdate = sdf.format(now);
			Date nfdate = null;
			try {
				nfdate = sdf.parse(nowdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ds_now.setfStatDate(nfdate);

			list.add(ds);
			list.add(ds_now);
		}

		statService.saveDeviceStat(list);

	}

	public static void main(String[] args) {

		// ApplicationContext ctx = new ClassPathXmlApplicationContext(
		// "ContextData.xml");
		// deviceService = (DeviceService) ctx.getBean("deviceService");
		//
		// userDeviceService = (UserDeviceService) ctx
		// .getBean("userDeviceService");
		//
		// statService = (StatService) ctx.getBean("statService");
		//
		// Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, -300);
		//
		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// for (int i = 300; i <= 300; i++) {
		// Date now = DateUtil.getNDaysAfter(cal.getTime(), i);
		// System.out.println(format.format(now));
		// new DeviceTask().stat(now);
		// }

	}
}
