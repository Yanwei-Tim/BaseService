package com.unistrong.tracker.handle.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import module.util.DateUtil;
import module.util.DistanceUtil;
import module.util.NumUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unistrong.tracker.model.Alarm;
import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.model.Protocol;
import com.unistrong.tracker.model.Spot;

public class SpotDo {

	/** 抖动 间隔 1000 * 60 * 1分钟 */
	public static final int INTERVAL = 60000;

	/** 抖动 阈值:0.000001 */
	public static final Double THRESHOLD = 0.000001;
	
	private static final Logger logger = LoggerFactory.getLogger(SpotDo.class);

		
	/** 判断是否为重复点 */
	public static boolean repeat(Position entityPosition, Position formPosition) {
		double lastLng = entityPosition.getLng();
		double lastLat = entityPosition.getLat();
		double lng = formPosition.getLng();
		double lat = formPosition.getLat();
		boolean lngTag = Math.abs(lng - lastLng) < THRESHOLD;
		boolean latTag = Math.abs(lat - lastLat) < THRESHOLD;
		return lngTag && latTag;
	}

	/** 更新最新位置:累积停留时间,变为上线 */
	public static void repeatHandle(Position entityPosition, Position formPosition) {
		int add = (int) ((formPosition.getReceive() - entityPosition.getReceive()) / 1000);
		if (add > 0) {
			entityPosition.setStayed(add);
		}
		entityPosition.setSpeed(formPosition.getSpeed());
	}

	/** 保存轨迹:{上传的属性:Position转为Spot} */
	public static Spot form(Spot spot, Position position) {
		spot.setDeviceSn(position.getDeviceSn());
		spot.setReceive(position.getReceive());
		spot.setLng(position.getLng());
		spot.setLat(position.getLat());
		spot.setInfo(position.getInfo());
		spot.setDirection(position.getDirection());
		spot.setSpeed(position.getSpeed());
		spot.setMode(position.getMode());
		spot.setSystime(DateUtil.nowDate().getTime());
		return spot;
	}

	/** 保存轨迹:{计算的属性:获取停留时间,计算里程} */
	public static Spot calculate(Spot spot, Position entityPosition, Position formPosition) {
		spot.setStayed(entityPosition.getStayed());
		if (entityPosition.getStatus() == 1) {// 在线
			double distanceAdd = DistanceUtil.distance(formPosition.getLng(),
					formPosition.getLat(), entityPosition.getLng(), entityPosition.getLat());// 里程
			spot.setDistance(NumUtil.round(distanceAdd / 1000, 1));// KM
		}
		return spot;
	}

	/** 更新最新位置:上传的属性 */
	public static void formPosition(Position entityPosition, Position formPosition) {
		entityPosition.setReceive(formPosition.getReceive());
		entityPosition.setDirection(formPosition.getDirection());
		entityPosition.setSpeed(formPosition.getSpeed());
		entityPosition.setMode(formPosition.getMode());
		entityPosition.setLat(formPosition.getLat());
		entityPosition.setLng(formPosition.getLng());
		entityPosition.setBattery(formPosition.getBattery());
		entityPosition.setFlow(formPosition.getFlow());
	}

	/** 更新最新位置:{计算的属性:停留时间清0,状态改为上线} */
	public static void calculatePosition(Position entityPosition, Position formPosition) {
		entityPosition.setStayed(0);
		entityPosition.setStatus(1);
	}

	/** 推送最新点 */
	public static Protocol positionProtocol(Position entityPosition, String duid) {
		Protocol protocolPush = new Protocol();
		protocolPush.setTarget(1);
		protocolPush.setDuid(duid);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 10);// 401-->10
		cmdObj.put("ret", 1);
		List<Position> statuss = new ArrayList<Position>();
		statuss.add(entityPosition);
		cmdObj.put("device_statuss", statuss);
		cmdList.add(cmdObj);
		protocolPush.setCmdList(cmdList);
		return protocolPush;
	}

	/** 推送告警 */
	public static Protocol alarmProtocol(Alarm entity, String duid) {
		Protocol protocol = new Protocol();
		// 设备所属用户列表
		protocol.setTarget(1);
		protocol.setDuid(duid);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 9);// 402-->9
		cmdObj.put("ret", 1);
		List<Alarm> alarms = new ArrayList<Alarm>();
		// entity.setAddr(getAddr(entity.getLng(), entity.getLat()));
		alarms.add(entity);
		cmdObj.put("alarms", alarms);
		cmdList.add(cmdObj);
		protocol.setCmdList(cmdList);
		return protocol;
	}

	
	
	
}
