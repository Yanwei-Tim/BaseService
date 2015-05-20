package com.unistrong.tracker.handle.i18n;

import com.unistrong.tracker.util.Language;
import com.unistrong.tracker.util.LanguageUtil;

/**
 * @author fyq
 */
public class MessageManager {
	
	private static Message getMessage(){
		Language l=LanguageUtil.get();
		if(l==null||l.equals(Language.zh_CN)){
			return new MessageCn();
		}else{
			return new MessageEn();
		}
		
	}
	
	/**
	 * 添加成功
	 */
	public static String ADD_SUCCESS() {
		return getMessage().ADD_SUCCESS();
	}
	
	/**
	 * 更新成功
	 */
	public static String UPDATE_SUCCESS() {
		return getMessage().UPDATE_SUCCESS();
	}

	/**
	 * 删除成功
	 */
	public static String DELETE_SUCCESS() {
		return getMessage().DELETE_SUCCESS();
	}

	/**
	 * 重启成功
	 */
	public static String RESTART_SUCCESS() {
		return getMessage().RESTART_SUCCESS();
	}

	/**
	 * 重置成功
	 */
	public static String RESET_SUCCESS() {
		return getMessage().RESET_SUCCESS();
	}

	/**
	 * 设备未导入平台
	 */
	public static String DEVICE_NOT_IMPORT() {
		return getMessage().DEVICE_NOT_IMPORT();
	}

	/**
	 * 绑定已有设备成功
	 */
	public static String BIND_EXIST_DEVICE_SUCCESS() {
		return getMessage().BIND_EXIST_DEVICE_SUCCESS();
	}

	/**
	 * 重复绑定
	 */
	public static String REPEAT_BIND() {
		return getMessage().REPEAT_BIND();
	}

	/**
	 * 绑定新设备成功
	 */
	public static String BIND_NEW_DEVICE_SUCCESS() {
		return getMessage().BIND_NEW_DEVICE_SUCCESS();
	}

	/**
	 * 解除绑定成功
	 */
	public static String UNBIND_DEVICE_SUCCESS() {
		return getMessage().UNBIND_DEVICE_SUCCESS();
	}

	/**
	 * 千米
	 */
	public static String KIL() {
		return getMessage().KIL();
	}

	/**
	 * 卡路里
	 */
	public static String CALORIE() {
		return getMessage().CALORIE();
	}

	/**
	 * 邮件格式有误
	 */
	public static String MAIL_FORMAT_INCORRECT() {
		return getMessage().MAIL_FORMAT_INCORRECT();
	}

	/**
	 * 注册成功
	 */
	public static String REGISTER_SUCCESS() {
		return getMessage().REGISTER_SUCCESS();
	}

	/**
	 * 不可用
	 */
	public static String UNAVAILABLE() {
		return getMessage().UNAVAILABLE();
	}

	/**
	 * 可用
	 */
	public static String AVAILABLE() {
		return getMessage().AVAILABLE();
	}

	
	/**
	 * 未授权
	 */
	public static String UNAUTHORIZE() {
		return getMessage().UNAUTHORIZE();
	}
	
	/**
	 * 设备禁用
	 */
	public static String DEVICE_FORBID() {
		return getMessage().DEVICE_FORBID();
	}


}
