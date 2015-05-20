package com.unistrong.tracker.entry.context;

import java.util.HashMap;
import java.util.Map;

import com.unistrong.tracker.entry.MapingDevice;

/**
 * @author fyq
 */
public class Strategy {

	public static Map<Integer, String> strategyMap = new HashMap<Integer, String>();
	static {
		strategyMap.put(1, "mapingUser.register");
		strategyMap.put(2, "mapingUser.checkName");
		strategyMap.put(3, "mapingUser.saveLogon");
		strategyMap.put(4, "mapingUser.updatePwd");
		strategyMap.put(5, "mapingUser.forgetPwd");
		strategyMap.put(7, "mapingDevice.editDelDevice");
		strategyMap.put(8, "mapingDevice.getDevice");
		strategyMap.put(9, "mapingAlarm.listByDevice");
		strategyMap.put(10, "mapingPosition.getLasts");
		strategyMap.put(11, "mapingDevice.list");
		strategyMap.put(12, "mapingFence.fenceEdit");
		strategyMap.put(14, "mapingSpot.getTrack");
		strategyMap.put(20, "mapingAlarm.editAlarm");
		strategyMap.put(24, "mapingSpot.getTrackPart");
		strategyMap.put(27, "mapingSpot.statistic");
		strategyMap.put(28, "mapingUser.reConn");
		strategyMap.put(29, "mapingSpot.getTrackBeginEnd");
		strategyMap.put(30, "mapingPosition.getLast");
		strategyMap.put(31, "mapingAlarm.list");
		strategyMap.put(32, "mapingPosition.pushSpot");
		strategyMap.put(33, "mapingUser.logout");
		strategyMap.put(34, "mapingVersion.checkVersion");
		strategyMap.put(35, "mapingShare.saveShare");
		strategyMap.put(36, "mapingResearch.getResearch");
		strategyMap.put(37, "mapingResearch.saveResearch");
		strategyMap.put(38, "mapingPet.statistic");
		strategyMap.put(39, "mapingDevice.feeCheck");
		
		strategyMap.put(111, "mapingUser.register_app");
	}

}
