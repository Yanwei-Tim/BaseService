package com.unistrong.tracker.handle.i18n;

/**
 * @author fyq
 */
public interface Message {

	/**
	 * 添加成功
	 */
	public String ADD_SUCCESS();

	/**
	 * 更新成功
	 */
	public String UPDATE_SUCCESS();

	/**
	 * 删除成功
	 */
	public String DELETE_SUCCESS();

	/**
	 * 重启成功
	 */
	public String RESTART_SUCCESS();

	/**
	 * 重置成功
	 */
	public String RESET_SUCCESS();

	/**
	 * 设备未导入平台
	 */
	public String DEVICE_NOT_IMPORT();

	/**
	 * 绑定已有设备成功
	 */
	public String BIND_EXIST_DEVICE_SUCCESS();

	/**
	 * 重复绑定
	 */
	public String REPEAT_BIND();

	/**
	 * 绑定新设备成功
	 */
	public String BIND_NEW_DEVICE_SUCCESS();

	/**
	 * 解除绑定成功
	 */
	public String UNBIND_DEVICE_SUCCESS();

	/**
	 * 千米
	 */
	public String KIL();

	/**
	 * 卡路里
	 */
	public String CALORIE();

	/**
	 * 邮件格式有误
	 */
	public String MAIL_FORMAT_INCORRECT();

	/**
	 * 注册成功
	 */
	public String REGISTER_SUCCESS();

	/**
	 * 不可用
	 */
	public String UNAVAILABLE();

	/**
	 * 可用
	 */
	public String AVAILABLE();

	/**
	 * 未授权
	 */
	public String UNAUTHORIZE();

	/**
	 * 设备禁用
	 */
	public String DEVICE_FORBID();
}
