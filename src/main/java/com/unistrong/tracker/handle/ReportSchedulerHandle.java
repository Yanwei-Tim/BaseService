package com.unistrong.tracker.handle;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.util.DogUtil;
import com.unistrong.tracker.handle.util.SpotToWeb;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.PetReport;
import com.unistrong.tracker.model.PetSport;
import com.unistrong.tracker.model.Report;
import com.unistrong.tracker.model.Spot;
import com.unistrong.tracker.service.AlarmService;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.PetReportService;
import com.unistrong.tracker.service.PetSportService;
import com.unistrong.tracker.service.ReportService;
import com.unistrong.tracker.service.SpotService;

/**
 * @author fyq
 */
@Component
public class ReportSchedulerHandle {

	@Resource
	private DeviceService deviceService;

	@Resource
	private SpotService spotService;

	@Resource
	private AlarmService alarmService;

	@Resource
	private ReportService reportService;

	@Resource
	private PetReportService petReportService;

	@Resource
	private PetSportService petSportService;
	
	/**
	 * 设备,天统计
	 */
	public void fixed(long begin) {
		long end=begin+3600*24*1000;
		List<Device> deviceSns = deviceService.findAll();
		for (Device device : deviceSns) {
			String deviceSn = device.getSn();
			if (device.getType() == 2) {
				List<PetSport> petSportList = petSportService.getSportList(deviceSn, begin, end);
				PetReport petReport = DogUtil.caculate(device, petSportList, begin);
				petReportService.saveOrUpdate(petReport);
				continue;
			}
			List<Spot> list = null;
			if(device.getType() == 1){
				list = spotService.getTrack(deviceSn,  begin, end);
			}else{
				list = spotService.getTrackGps(deviceSn,  begin, end);
			}
			Report dayReport = new Report(deviceSn, begin);
			SpotToWeb.statisticToday(list, dayReport, device.getType());
			SpotToWeb.statisticTodaySpeed(list, dayReport,device.getType());
			Long speeding = alarmService.overSpeedTime(deviceSn, begin, end);// 超速次数
			long outFence = alarmService.outFenceTime(deviceSn, begin, end);//出围栏次数
			dayReport.setSpeeding(speeding.intValue());
			dayReport.setOutFence((int) outFence);
			reportService.saveOrUpdate(dayReport);
			
		}
	}

	

}
