package com.unistrong.tracker.handle.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import module.util.JsonUtils;

import com.unistrong.tracker.model.Fence;
import com.unistrong.tracker.model.Instruct;
import com.unistrong.tracker.model.Protocol;
import com.unistrong.tracker.model.serialize.SpeetInstructVo;

public class InstructProtocol {

	/**
	 * 下发指令:上传间隔
	 */
	public static Protocol tick(String deviceSn, int tick) {
		Protocol protocol = new Protocol();// 下发指令:上传间隔
		protocol.setTarget(2);
		protocol.setDuid(deviceSn);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 202);
		cmdObj.put("ret", 1);
		cmdObj.put("duid", deviceSn);
		cmdObj.put("deviceType", "M2616");
		cmdObj.put("interval", tick);
		cmdList.add(cmdObj);
		protocol.setCmdList(cmdList);
		return protocol;
	}

	/**
	 * 下发指令:sos号码
	 */
	public static Protocol sos(String deviceSn, String sosNum) {
		Protocol protocol = new Protocol();// 下发指令:sos号码
		protocol.setTarget(2);
		protocol.setDuid(deviceSn);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 204);
		cmdObj.put("ret", 1);
		cmdObj.put("duid", deviceSn);
		cmdObj.put("deviceType", "M2616");
		String[] sosNums = sosNum.split(",");
		cmdObj.put("nums", sosNums);
		cmdList.add(cmdObj);
		protocol.setCmdList(cmdList);
		return protocol;
	}

	/**
	 * 下发指令:超速 最大值-超速开/关
	 */
	public static Protocol hb(String deviceSn, int open, int max) {
		Protocol protocol = new Protocol();// 下发指令:超速最大值
		protocol.setTarget(2);
		protocol.setDuid(deviceSn);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 203);
		cmdObj.put("ret", 1);
		cmdObj.put("duid", deviceSn);
		cmdObj.put("deviceType", "M2616");
		cmdObj.put("switch", open);
		cmdObj.put("min", 0);
		cmdObj.put("max", max);
		cmdList.add(cmdObj);
		protocol.setCmdList(cmdList);
		return protocol;
	}

	/**
	 * 下发指令:围栏
	 */
	public static Protocol fence(String deviceSn, Fence formFence) {
		Protocol protocol = new Protocol();// 下发指令
		protocol.setTarget(2);
		protocol.setDuid(deviceSn);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 201);
		cmdObj.put("ret", 1);
		cmdObj.put("duid", deviceSn);
		cmdObj.put("deviceType", "MT90");
		cmdObj.put("condition", formFence.getOut());
		cmdObj.put("type", formFence.getType());
		if (Fence.TYPE_CIRCLE == formFence.getType()) {
			cmdObj.put("center", formFence.getCenter());
			cmdObj.put("radius", formFence.getRadius());
		} else if (Fence.TYPE_POLYGON == formFence.getType()) {
			cmdObj.put("region", JsonUtils.obj2Str(formFence.getRegion()));
			// TODO 重构后使用这种方式
			// cmdObj.put("region", formFence.getRegion());
			// M2616接收方式
			// SpotVo[] spotVos = (SpotVo[])setMap.get("region");
		} else {
			throw new IllegalArgumentException("围栏类型错误:" + formFence.getType());
		}
		cmdList.add(cmdObj);
		protocol.setCmdList(cmdList);
		return protocol;
	}

	/**
	 * 下发指令:重启
	 */
	public static Protocol reset(String deviceSn) {
		Protocol protocol = new Protocol();// 下发指令
		protocol.setTarget(2);
		protocol.setDuid(deviceSn);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 206);
		cmdObj.put("ret", 1);
		cmdObj.put("duid", deviceSn);
		cmdObj.put("deviceType", "M2616");
		cmdList.add(cmdObj);
		protocol.setCmdList(cmdList);
		return protocol;
	}

	/**
	 * 下发指令:恢复出厂
	 */
	public static Protocol restore(String deviceSn) {
		Protocol protocol = new Protocol();// 下发指令
		protocol.setTarget(2);
		protocol.setDuid(deviceSn);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 205);
		cmdObj.put("ret", 1);
		cmdObj.put("duid", deviceSn);
		cmdObj.put("deviceType", "M2616");
		cmdList.add(cmdObj);
		protocol.setCmdList(cmdList);
		return protocol;
	}

	/**
	 * 下发指令:围栏开关
	 */
	public static Protocol fenceSwitch(String deviceSn, int fenceSwitch) {
		Protocol protocol = new Protocol();// 下发指令:上传间隔
		protocol.setTarget(2);
		protocol.setDuid(deviceSn);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 207);
		cmdObj.put("ret", 1);
		cmdObj.put("duid", deviceSn);
		cmdObj.put("deviceType", "M2616");
		cmdObj.put("switch", fenceSwitch);
		cmdList.add(cmdObj);
		protocol.setCmdList(cmdList);
		return protocol;
	}

	/**
	 * 下发指令:移动开关
	 */
	public static Protocol moveSwitch(String deviceSn, int moveSwitch) {
		Protocol protocol = new Protocol();// 下发指令:上传间隔
		protocol.setTarget(2);
		protocol.setDuid(deviceSn);
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("cmd", 208);
		cmdObj.put("ret", 1);
		cmdObj.put("duid", deviceSn);
		cmdObj.put("deviceType", "M2616");
		cmdObj.put("switch", moveSwitch);
		cmdList.add(cmdObj);
		protocol.setCmdList(cmdList);
		return protocol;
	}

	/**
	 * 下发指令
	 * 
	 * 上线时读取离线指令
	 */
	public static Protocol instruct(Instruct instruct, String deviceSn) {
		int type = instruct.getType();
		if (1 == type) {
			Fence formFence = null;
			String value = instruct.getContent();
			formFence = JsonUtils.str2Obj(value, Fence.class);
			return fence(deviceSn, formFence);
		} else if (2 == type) {
			return tick(deviceSn, Integer.parseInt(instruct.getContent()));
		} else if (3 == type) {
			SpeetInstructVo siv = null;
			siv = JsonUtils.str2Obj(instruct.getContent(), SpeetInstructVo.class);
			return hb(deviceSn, siv.getOpen(), siv.getMax());
		} else if (4 == type) {
			return sos(deviceSn, instruct.getContent());
		} else if (5 == type) {
			return reset(deviceSn);
		} else if (6 == type) {
			return restore(deviceSn);
		} else if (7 == type) {
			return fenceSwitch(deviceSn, Integer.parseInt(instruct.getContent()));
		} else if (8 == type) {
			return moveSwitch(deviceSn, Integer.parseInt(instruct.getContent()));
		} else {
			throw new IllegalArgumentException("指令类型错误");
		}
	}

}
