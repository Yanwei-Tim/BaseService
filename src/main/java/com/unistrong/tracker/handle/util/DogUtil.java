/**
 * 
 */
package com.unistrong.tracker.handle.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import module.util.DateUtil;

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.PetReport;
import com.unistrong.tracker.model.PetSport;
import com.unistrong.tracker.model.serialize.SpeedVo;
import com.unistrong.tracker.util.Language;
import com.unistrong.tracker.util.LanguageUtil;

/**
 * @author fss
 * 
 */
public class DogUtil {

	private static final Double interval = 30D;
	private static final String[] title = new String[] { "休息", "轻度运动", "剧烈运动" };
	private static final String[] en_title = new String[] { "rest", "mild sport", "strenuous sport" };

	public static List<SpeedVo> getPie(List<PetReport> petReportList) {
		String[] t = Language.en_US.equals(LanguageUtil.get()) ? en_title : title;

		SpeedVo vo1 = new SpeedVo(t[0]);
		SpeedVo vo2 = new SpeedVo(t[1]);
		SpeedVo vo3 = new SpeedVo(t[2]);
		double rest = 0;
		double slow = 0;
		double fast = 0;
		for (PetReport report : petReportList) {
			rest += report.getRest();
			slow += report.getSlow();
			fast += report.getFast();
		}
		vo1.setPercent(rest / petReportList.size());
		vo2.setPercent(slow / petReportList.size());
		vo3.setPercent(fast / petReportList.size());
		List<SpeedVo> vos = new ArrayList<SpeedVo>();
		vos.add(vo1);
		vos.add(vo2);
		vos.add(vo3);
		return vos;
	}

	public static boolean areSameDay(long dateA, long dateB) {
		Calendar calDateA = Calendar.getInstance();
		calDateA.setTimeInMillis(dateA);

		Calendar calDateB = Calendar.getInstance();
		calDateB.setTimeInMillis(dateB);

		return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
				&& calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
				&& calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}

	public static PetReport caculate(Device device, List<PetSport> petSportList, Long day) {

		double weight = device.getWeight();
		double V1 = 1.09 * Math.pow(weight, 0.222);
		double V2 = 1.54 * Math.pow(weight, 0.216);
		double V3 = 2.78 * Math.pow(weight, 0.176);
		double rest = 0;
		double slow = 0;
		double fast = 0;
		int energy = 0;
		PetReport report = new PetReport(device.getSn(), day);
		double dayLong = 24 * 60 * 60 * 1000;
		for (int i = 1; i < petSportList.size(); i++) {
			PetSport petSport = petSportList.get(i);
			PetSport lastPetSport = petSportList.get(i - 1);
			int steps = petSport.getSteps() - lastPetSport.getSteps();
			double speed = petSport.getSpeed();
			Long lastReceive = lastPetSport.getReceive();
			if(lastReceive < day)
				lastReceive = day;
			Long receive = petSport.getReceive() - lastReceive;
			double rate = receive.doubleValue() / dayLong * 100;
			if (speed > 0 && speed < V1) {
				energy += (int) (1.27 * steps * Math.pow(weight, 0.892));
			} else if (speed > V1 && speed < V2) {
				slow += rate;
				energy += (int) (1.28 * steps * Math.pow(weight, 0.954));
			} else if (speed > V2 && speed < V3) {
				slow += rate;
				energy += (int) (1.72 * steps * Math.pow(weight, 0.86));
			} else {
				fast += rate;
				energy += (int) (2.07 * steps * Math.pow(weight, 0.982));
			}
		}
		fast=(int)fast;
		slow = (int)slow;
		if(fast>=100){
			fast =100;
			slow = 0;
			rest = 0;
		}else if(fast+slow >=100){//交叉补传的时间段大于一天的情况
			rest = 0;
			slow = 100-fast;
		}else if (fast+slow <=0){
			rest = 0;
		}else {
			rest = 100 - fast - slow;
		}
		report.setEnergy(energy);
		report.setFast(fast);
		report.setRest(rest);
		report.setSlow(slow);

		return report;
	}

	/** 
	 * 单天运动详情统计
	 * 
	 * @param device
	 * @param petSportList
	 * @param begin
	 * @param end
	 * @return
	 */
	public static List<PetReport> getDayDetail(Device device, List<PetSport> petSportList,
			long begin, long end) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(end);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int minutes = hour * 60 + minute;//总分钟数
		double weight = device.getWeight();
		String sn = device.getSn();
		Map<Integer, PetReport> table = new HashMap<Integer, PetReport>();
		long intervalLong = interval.longValue() * 60 * 1000;
		for (int i = 0; i < minutes / interval; i++) {
			PetReport report = new PetReport(sn, begin + (i + 1) * intervalLong);
			table.put(i, report);
		}

		List<PetReport> reports = new ArrayList<PetReport>();
		for (int i = 1; i < petSportList.size(); i++) {
			PetSport petSport = petSportList.get(i);
			PetSport lastPetSport = petSportList.get(i - 1);
			float num = (float) (petSport.getReceive() - begin) / intervalLong;
			int j = (int) Math.ceil(num) - 1;
			PetReport report = table.get(j);
			int steps = petSport.getSteps() - lastPetSport.getSteps();
			int energy = getEnergy(weight, petSport.getSpeed(), steps);
			int total = report.getEnergy();
			total += energy;
			report.setEnergy(total);
			table.put(j, report);
		}
		reports.addAll(table.values());
		return reports;
	}

	public static int getEnergy(double weight, double speed, int steps) {
		double V1 = 1.09 * Math.pow(weight, 0.222);
		double V2 = 1.54 * Math.pow(weight, 0.216);
		double V3 = 2.78 * Math.pow(weight, 0.176);
		int energy = 0;
		if (speed > 0 && speed < V1) {
			energy = (int) (1.27 * steps * Math.pow(weight, 0.892));
		} else if (speed > V1 && speed < V2) {
			energy = (int) (1.28 * steps * Math.pow(weight, 0.954));
		} else if (speed > V2 && speed < V3) {
			energy = (int) (1.72 * steps * Math.pow(weight, 0.86));
		} else {
			energy = (int) (2.07 * steps * Math.pow(weight, 0.982));
		}
		return energy;
	}

	public static int getRightSport(Device device) {
		int energy = 0;
		String figure = device.getDogFigure();
		double weight = device.getWeight();
		if (figure != null) {
			if (figure.equals("XS")) {
				energy = (int) (277.5 * Math.pow(weight, 0.989) / (1.64 + 0.97 * Math.pow(weight,
						0.216)));
			} else if (figure.equals("S")) {
				energy = (int) (462.5 * Math.pow(weight, 0.989) / (1.51 + 0.75 * Math.pow(weight,
						0.216)));
			} else if (figure.equals("L")) {
				energy = (int) (1110 * Math.pow(weight, 0.989));
			} else {
				energy = (int) (647.5 * Math.pow(weight, 0.989) / (1.06 + 0.52 * Math.pow(weight,
						0.216)));
			}
		}
		Long birth = device.getBirth();
		Long now = Calendar.getInstance().getTimeInMillis();
		int month = new Long((now - birth) / (30 * 24 * 60 * 60 * 1000)).intValue();
		if (month < 4 || (month > 5 && figure.equals("L")) || (month > 7 && figure.contains("S"))) {
			energy -= 0.2 * energy;
		}
		return energy;
	}

	public static void main(String[] args) {
		String date1 = DateUtil.longStr(1400076000000l, DateUtil.DEF_FORMAT);
		System.out.println(date1);
	}
}
