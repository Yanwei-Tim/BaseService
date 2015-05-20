/**
 * 
 */
package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.HttpUtil;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.util.DogUtil;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.PetReport;
import com.unistrong.tracker.model.PetSport;
import com.unistrong.tracker.model.serialize.SpeedVo;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.PetReportService;
import com.unistrong.tracker.service.PetSportService;
import com.unistrong.tracker.service.UserService;

/**
 * @author fss
 * 
 */
@Component
public class PetHandle {

	private static final long day = 24 * 60 * 60 * 1000;

	@Resource
	private UserService userService;

	@Resource
	private DeviceService deviceService;

	@Resource
	private PetSportService petSportService;

	@Resource
	private PetReportService petReportService;

	public Map<String, Object> getSportStatic(String userIdStr, String deviceSn, String begin,
			String end) {
		Map<String, Object> rs = new HashMap<String, Object>();

		HttpUtil.validateNull(new String[] { "sn" }, new String[] { deviceSn });
		HttpUtil.validateLong(new String[] { "begin", "end" }, new String[] { begin, end });
		Long beginLong = HttpUtil.getLong(begin);
		Long endLong = HttpUtil.getLong(end);
		Device device = deviceService.get(deviceSn);
		List<PetReport> charReport = getCharReport(device, beginLong, endLong);
		List<PetReport> pieReport = getPieReport(device, beginLong, endLong);
		List<SpeedVo> distributed = DogUtil.getPie(pieReport);
		rs.put("distributed", distributed);
		rs.put("statistic", charReport);
		rs.put("standard", DogUtil.getRightSport(device));
		rs.put("desc", "success");
		return rs;
	}

	//获取趋势图（折线图）
	public List<PetReport> getCharReport(Device device, Long begin, Long end) {
		List<PetReport> charReport = new ArrayList<PetReport>();
		if (end - begin <= day) {// 一天的
			List<PetSport> petSportList = petSportService.getSportList(device.getSn(), begin, end);
			charReport = DogUtil.getDayDetail(device, petSportList, begin, end);
		} else {// 多天的
			begin = Long.valueOf((begin+"").substring(0, 10))*1000;
			charReport = petReportService.find(device.getSn(), begin, end);
		}
		return charReport;
	}

	//获取分布图（饼图）
	public List<PetReport> getPieReport(Device device, Long begin, Long end) {
		List<PetReport> pieReport = new ArrayList<PetReport>();
		if (DogUtil.areSameDay(Calendar.getInstance().getTimeInMillis(), begin) && end - begin <= day) {// 今天，需要计算
			List<PetSport> petSportList = petSportService.getSportList(device.getSn(), begin, end);
			PetReport petReport = DogUtil.caculate(device, petSportList, begin);
			pieReport.add(petReport);
		} else {// 历史
			begin = Long.valueOf((begin + "").substring(0, 10)) * 1000;
			pieReport = petReportService.find(device.getSn(), begin, end);
		}
		return pieReport;
	}
}
