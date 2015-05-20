package com.unistrong.tracker.handle.util;

import java.util.ArrayList;
import java.util.List;

import module.util.DistanceUtil;
import module.util.NumUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unistrong.tracker.model.Report;
import com.unistrong.tracker.model.Spot;
import com.unistrong.tracker.model.serialize.SpeedVo;
import com.unistrong.tracker.service.OrbitFilterFactory;

public class SpotToWeb {
	
	private static Logger logger = LoggerFactory.getLogger(SpotToWeb.class);
	
	
	private final static int cutSecondLength = 1200000;// 分段时间间隔  20分钟*60*1000=1200000
	private final static int mincutSecondLength = 600000;// 最小分段时间间隔 5分钟
														// 
	private final static int cutDistanceLength = 2000;// 分段距离
	
	
	private final static int[] car = new int[] { 30, 60, 100 };
	
	private final static int[] person = new int[] { 5, 15, 40 };
	
	/** 分段 进入数据倒序(大->小) */
	public static List<List<Spot>> getPartList(List<Spot> listAll, int deviceType) {
		List<List<Spot>> rs = new ArrayList<List<Spot>>();
		List<Spot> listLast = null;// 最后一段
		Spot last = null;// 最后一段 的 最后一点
		double lastDistance=0;
		for (Spot spot : listAll) {
			double thisDistance=0;
			if(last!=null){
				thisDistance=getDistance(spot,last);
			}
			
			//logger.debug(DateUtil.longStr(spot.getReceive(), "MM-dd:HH:mm:ss")+"  lastDistance:"+lastDistance+",thisDistance:"+thisDistance);
			
			if (last==null||Math.abs(spot.getReceive() - last.getReceive() )> cutSecondLength||(Math.abs(spot.getReceive() - last.getReceive() )>mincutSecondLength&&lastDistance>3&&thisDistance>cutDistanceLength&&thisDistance>lastDistance*6)) {
				List<Spot> listNew = new ArrayList<Spot>();// 新开一段
				listNew.add(spot);
				
				if(listLast!=null){
					//logger.debug("listLast.size:"+listLast.size());
				}
				
				listLast = listNew;
				rs.add(listNew);
				lastDistance=0;
			} else {
				if(deviceType != 1 && thisDistance<70){
					continue;
				}
				listLast.add(spot);
				lastDistance=thisDistance;
			}
			last=spot;
		}
		
		if(deviceType == 1){
			OrbitFilterFactory.carFilter(rs);
		}else{
			OrbitFilterFactory.lowSpeedFilter(rs);
		}
		return rs;
	}
	

	

	public static double getDistance(Spot uploadPosition, Spot lastPosition) {
		double distanceAdd = DistanceUtil.distance(uploadPosition.getLng(),
				uploadPosition.getLat(), lastPosition.getLng(), lastPosition.getLat());// 里程
		return distanceAdd;
	}

	// --------------------------------------------------------------

	/** 里程等当天统计 */
	public static Report statisticToday(List<Spot> list, Report report, int deviceType) {
		if (null == list || list.size() < 1)
			return report;
		double distanceSum = 0D;
		float stayedSum = 0F;

		List<List<Spot>> partList = getPartList(list, deviceType);

		for (List<Spot> list2 : partList) {
			distanceSum += getPartDistance(list2);
			stayedSum += getPartStay(list2);
		}

		// 转小时,除以60*60=3600
		report.setStop(NumUtil.round(stayedSum / 3600, 1));
		// 小数点后一位
		report.setDistance(distanceSum);
		return report;
	}

	/**
	 * 获取每一段的里程，实时计算 数据量大会稀释数据
	 * 
	 * @param list
	 * @return
	 * 公里
	 */
	public static double getPartDistance(List<Spot> list) {
		double ret = 0D;
		int maxPoint = 10 * 60 * 12; // 一段允许的最大数据（按10小时，5S一个点计算）
		int step = 1;
		int maxIndex = list.size() - 1;
		if (list.size() > maxPoint) {
			step = (int) Math.floor(list.size() / maxPoint);
			maxIndex = maxPoint * step;
		}
		Spot lastspot = list.get(0);
		for (int i = step; i <= maxIndex; i += step) {
			Spot spot = list.get(i);
			// if ("A".equals(spot.getMode())) {
			ret += getDistance(spot, lastspot);
			lastspot = spot;
			// }
		}
		// 不够一个步长的数据
		if (maxIndex < list.size() - 1) {
			for (int i = maxIndex + 1; i < list.size(); i++) {
				Spot spot = list.get(i);
				// if ("A".equals(spot.getMode())) {
				ret += getDistance(spot, list.get(i - 1));
				// }
			}
		}
		ret = NumUtil.round(ret / 1000, 2);
		return ret;
	}

	/**
	 * 获取每一段的停留时间
	 * 
	 * @param list
	 * @return
	 */
	public static double getPartStay(List<Spot> list) {
		double ret = 0D;
		for (Spot spot : list) {
			Integer stayed = spot.getStayed();
			if (null != stayed) {
				ret += stayed;
			}
		}

		return ret;
	}

	/** 里程等当天统计:速度段里程 */
	public static Report statisticTodaySpeed(List<Spot> list, Report report, Integer deviceType) {
		if (null == list || list.size() < 1)
			return report;
		Double distance30 = 0D;
		Double distance36 = 0D;
		Double distance61 = 0D;
		Double distance10 = 0D;

		List<List<Spot>> partList = getPartList(list, deviceType);
		int[] speedPart = getByDeviceType(deviceType);

		for (List<Spot> list2 : partList) {
			for (int i = 1; i < list2.size(); i++) {
				Spot spot = list2.get(i);
				Double speed = spot.getSpeed();
				Double distance = spot.getDistance();
				if (null != distance && "A".equals(spot.getMode()))
					if (speed < speedPart[0]) {
						distance30 += distance;
					} else if (speed >= speedPart[0] && speed <= speedPart[1]) {
						distance36 += distance;
					} else if (speed >= speedPart[1] && speed <= speedPart[2]) {
						distance61 += distance;
					} else if (speed >= speedPart[2]) {
						distance10 += distance;
					}
			}
		}
		// 3.19修改
		// for (Spot spot : list) {
		// Double speed = spot.getSpeed();
		// Double distance = spot.getDistance();
		// if (null != distance)
		// if (speed < 30L) {
		// distance30 += distance;
		// } else if (speed >= 30 && speed <= 60) {
		// distance36 += distance;
		// } else if (speed >= 60 && speed <= 100) {
		// distance61 += distance;
		// } else if (speed >= 100) {
		// distance10 += distance;
		// }
		// }
		report.setDistance10(distance10);
		report.setDistance30(distance30);
		report.setDistance36(distance36);
		report.setDistance61(distance61);
		return report;
	}

	/** 速度百分比(速度分布) */
	public static List<SpeedVo> statisticSpeed(List<Report> list,Integer deviceType) {
		if (null == list || list.size() < 1)
			return null;
		
		int[] speedPart = getByDeviceType(deviceType);
		SpeedVo speedVo30 = new SpeedVo("<"+speedPart[0]+"km");
		SpeedVo speedVo36 = new SpeedVo(speedPart[0]+"-"+speedPart[1]+"km");
		SpeedVo speedVo61 = new SpeedVo(speedPart[1]+"-"+speedPart[2]+"km");
		SpeedVo speedVo10 = new SpeedVo(">"+speedPart[2]+"km");
		Double distance30 = 0D;
		Double distance36 = 0D;
		Double distance61 = 0D;
		Double distance10 = 0D;
		for (Report report : list) {
			distance30 += report.getDistance30();
			distance36 += report.getDistance36();
			distance61 += report.getDistance61();
			distance10 += report.getDistance10();
		}
		Double sum = distance30 + distance36 + distance61 + distance10;
		if (sum < 1)
			sum = 1D;
		Double pc30 = NumUtil.round(distance30 / sum, 2) * 100;
		Double pc36 = NumUtil.round(distance36 / sum, 2) * 100;
		Double pc61 = NumUtil.round(distance61 / sum, 2) * 100;
		Double pc10 = NumUtil.round(distance10 / sum, 2) * 100;

		if (pc30 + pc36 + pc61 + pc10 != 100 && pc30 + pc36 + pc61 + pc10 != 0) {
			pc30 = 100 - pc36 - pc61 - pc10;
		}
		speedVo30.setPercent(pc30);
		speedVo36.setPercent(pc36);
		speedVo61.setPercent(pc61);
		speedVo10.setPercent(pc10);
		List<SpeedVo> speedVos = new ArrayList<SpeedVo>();
		speedVos.add(speedVo30);
		speedVos.add(speedVo36);
		speedVos.add(speedVo61);
		speedVos.add(speedVo10);
		return speedVos;
	}
	
	
	
	public static int[] getByDeviceType(Integer deviceType) {
		if(deviceType.intValue() == 1) return car;
		if(deviceType.intValue() == 3) return person;
		if(deviceType.intValue() == 4) return car;
		if(deviceType.intValue() == 5) return car;
		return null;
	}

	// --------------------------------------------------------------

	
	
}
