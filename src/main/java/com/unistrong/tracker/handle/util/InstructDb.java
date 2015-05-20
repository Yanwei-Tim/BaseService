package com.unistrong.tracker.handle.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import module.util.JsonUtils;

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.Fence;
import com.unistrong.tracker.model.Instruct;
import com.unistrong.tracker.model.serialize.SpeetInstructVo;

public class InstructDb {

	public static String instructId(String deviceSn, String type) {
		return deviceSn + "-" + type;
	}

	/**
	 * 上传间隔
	 */
	public static Instruct tick(Device form, Device entity) {
		int type = 2;
		Instruct formInstruct = new Instruct();
		formInstruct.setId(form.getSn() + "-" + type);
		formInstruct.setDeviceSn(form.getSn());
		formInstruct.setType(type);
		formInstruct.setReply(0);// 新指令 未回复
		formInstruct.setContent("" + form.getTick());
		formInstruct.setOriginal("" + entity.getTick());
		return formInstruct;
	}

	/**
	 * sos号码
	 */
	public static Instruct sos(Device form, Device entity) {
		int type = 4;
		Instruct formInstruct = new Instruct();
		formInstruct.setId(form.getSn() + "-" + type);
		formInstruct.setDeviceSn(form.getSn());
		formInstruct.setType(type);
		formInstruct.setReply(0);// 新指令 未回复
		formInstruct.setContent(form.getSosNum());
		formInstruct.setOriginal(entity.getSosNum());
		return formInstruct;
	}

	/**
	 * 超速
	 */
	public static Instruct speed(Device form, Device entity) {
		int type = 3;
		// T2 超速 速度限制修改 xubin modify 2014-12-16 begin
		String hardWare = entity.getHardware();
		if ("T2".equals(hardWare)) {
			int SpeedThresholdForm = form.getSpeedThreshold();
			int SpeedThresholdEntity = entity.getSpeedThreshold();
			BigDecimal a = new BigDecimal(SpeedThresholdForm * 1.852).setScale(
					0, BigDecimal.ROUND_HALF_UP);
			SpeedThresholdForm = a.intValue();
			BigDecimal b = new BigDecimal(SpeedThresholdEntity * 1.852)
					.setScale(0, BigDecimal.ROUND_HALF_UP);
			SpeedThresholdEntity = b.intValue();
		}
		// T2 超速 速度限制修改 xubin modify 2014-12-16 end
		SpeetInstructVo siv = new SpeetInstructVo(form.getSpeedingSwitch(), 0,
				form.getSpeedThreshold());
		SpeetInstructVo sivEntity = new SpeetInstructVo(
				entity.getSpeedingSwitch(), 0, entity.getSpeedThreshold());
		String content = JsonUtils.obj2Str(siv);
		String contentEntity = JsonUtils.obj2Str(sivEntity);
		Instruct formInstruct = new Instruct();
		formInstruct.setId(form.getSn() + "-" + type);
		formInstruct.setDeviceSn(form.getSn());
		formInstruct.setType(type);
		formInstruct.setReply(0);// 新指令 未回复
		formInstruct.setContent(content);
		formInstruct.setOriginal(contentEntity);
		return formInstruct;
	}

	/**
	 * 围栏
	 */
	public static Instruct fence(Device entity, Fence formFence,
			Fence entityFence) {
		int type = 1;
		String content = JsonUtils.obj2Str(formFence);
		String contentEntity = JsonUtils.obj2Str(entityFence);
		Instruct formInstruct = new Instruct();
		formInstruct.setId(entity.getSn() + "-" + type);
		formInstruct.setDeviceSn(entity.getSn());
		formInstruct.setType(type);
		Integer reply = 0;
		if (entity.getType() != null && entity.getType() == 2
				&& "M2616".equals(entity.getHardware()))
			reply = 1;
		formInstruct.setReply(reply);// 新指令 未回复
		formInstruct.setContent(content);
		formInstruct.setOriginal(contentEntity);
		return formInstruct;
	}

	/**
	 * 围栏开关
	 */
	public static Instruct fenceSwitch(Device form, Device entity) {
		int type = 7;
		Instruct formInstruct = new Instruct();
		formInstruct.setId(form.getSn() + "-" + type);
		formInstruct.setDeviceSn(form.getSn());
		formInstruct.setType(type);
		Integer reply = 0;
		// 宠物设备不支持围栏指令(服务器端判断)
		if (entity.getType() != null && entity.getType() == 2
				&& "M2616".equals(entity.getHardware()))
			reply = 1;
		formInstruct.setReply(reply);// 新指令 未回复
		formInstruct.setContent("" + form.getFenceSwitch());
		formInstruct.setOriginal("" + entity.getFenceSwitch());
		return formInstruct;
	}

	/**
	 * 移动报警开关
	 */
	public static Instruct moveSwitch(Device form, Device entity) {
		int type = 8;
		Instruct formInstruct = new Instruct();
		formInstruct.setId(form.getSn() + "-" + type);
		formInstruct.setDeviceSn(form.getSn());
		formInstruct.setType(type);
		formInstruct.setReply(0);// 新指令 未回复
		formInstruct.setContent("" + form.getMoveSwitch());
		formInstruct.setOriginal("" + entity.getFenceSwitch());
		return formInstruct;
	}

	/**
	 * 移动报警开关
	 */
	public static Instruct reboot(String deviceSn) {
		int type = 5;
		Instruct formInstruct = new Instruct();
		formInstruct.setId(deviceSn + "-" + type);
		formInstruct.setDeviceSn(deviceSn);
		formInstruct.setType(type);
		formInstruct.setReply(0);// 新指令 未回复
		formInstruct.setContent("");
		formInstruct.setOriginal("");
		return formInstruct;
	}

	public static String arrayDb(String array) {
		array = StringUtils.trim(array);
		if (null == array)
			return null;
		array = array.replaceAll("\\[", "");
		array = array.replaceAll("\\]", "");
		array = StringUtils.trim(array);
		return array;
	}

}
