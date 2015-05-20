package com.unistrong.tracker.handle.i18n;

/**
 * @author fyq
 */
public class MessageCn implements Message {

	/**
	 * 更新成功
	 */
	public String UPDATE_SUCCESS() {
		return "更新成功";
	}

	/**
	 * 删除成功
	 */
	public String DELETE_SUCCESS() {
		return "删除成功";
	}

	/**
	 * 重启成功
	 */
	public String RESTART_SUCCESS() {
		return "重启成功";
	}

	/**
	 * 重置成功
	 */
	public String RESET_SUCCESS() {
		return "重置成功";
	}

	/**
	 * 设备未导入平台
	 */
	public String DEVICE_NOT_IMPORT() {
		return "设备sn不正确";// "设备未导入平台";
	}

	/**
	 * 绑定已有设备成功
	 */
	public String BIND_EXIST_DEVICE_SUCCESS() {
		return "绑定已有设备成功";
	}

	/**
	 * 重复绑定
	 */
	public String REPEAT_BIND() {
		return "重复绑定";
	}

	/**
	 * 绑定新设备成功
	 */
	public String BIND_NEW_DEVICE_SUCCESS() {
		return "绑定新设备成功";
	}

	/**
	 * 解除绑定成功
	 */
	public String UNBIND_DEVICE_SUCCESS() {
		return "解除绑定成功";
	}

	/**
	 * 千米
	 */
	public String KIL() {
		return "千米";
	}

	/**
	 * 卡路里
	 */
	public String CALORIE() {
		return "卡路里";
	}

	/**
	 * 邮件格式有误
	 */
	public String MAIL_FORMAT_INCORRECT() {
		return "邮件格式有误";
	}

	/**
	 * 注册成功
	 */
	public String REGISTER_SUCCESS() {
		return "注册成功";
	}

	/**
	 * 不可用
	 */
	public String UNAVAILABLE() {
		return "不可用";
	}

	/**
	 * 可用
	 */
	public String AVAILABLE() {
		return "可用";
	}

	public String ADD_SUCCESS() {
		return "添加成功";
	}
	
	public String UNAUTHORIZE() {
		return "无权操作";
	}
	
	public String DEVICE_FORBID() {
		return "设备已被禁用";
	}
}
