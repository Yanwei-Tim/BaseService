package com.unistrong.tracker.util;

import java.util.Properties;

public enum UsConstants {
	
	
	/**
	 * 抖动 间隔 1000 * 60 * 10分
	 */
	//public static final int INTERVAL = 600000;
	
	/**
	 * 抖动 阈值
	 */
	//public static final Double THRESHOLD = 0.000001;
	
	/**
	 * token过期时间 1000*60*30分=1200000 1000*60*60*24*7天=604800000
	 */
	//public static final long EXPIRE = 604800000;
	
	DEVICE_REGISTERED ("设备已注册"),

	DEVICE_FENCE_EXIST ("设备已有围栏"),

	 USER_NOTDEVICE ( "用户无此设备"),
	
	 USER_REGISTERED ( "用户名已注册"),

	 EMAIL_REGISTERED ( "邮箱已注册"),

	 PHONE_REGISTERED ( "电话号码已注册"),

	 THIRD_REGISTERED ( "第三方账号已被注册"),

	 THIRD_NULL ( "第三方账号不存在"),

	 USER_NOT_EXIST ( "用户不存在"),

	 USER_PW_ERROR ( "密码错误"),
	
	 USER_DENIED ( "您无权操作"),

	 NOT_NULL ( "不能为空"),
	
	 SUBMITTED ( "您已提交问卷，感谢您的配合！"),
	
	 NOTMORETHAN ( "结束日期不能大于当天"),
	
	 PIC_NOT_EXIST ( "图片不存在"),
	
	 PARAM_ERROR ( "参数有误"),
	
	 NUM_PARAM ( "参数必须为数字"),
	
	 DISTANCE_UNIT ( "公里");
	
	
	private String cn;
	
	private UsConstants(String cn){
		this.cn=cn;
	}
	
	public String getCn() {
		return cn;
	}

	public static String getI18nMessage(UsConstants type,Language language){
		if(language==null||language.equals(Language.zh_CN)){
			return type.cn;
		}else{
			Properties p=PropertiesUtil.getInstance().getProperties(language.name());
			if(p!=null){
				return p.getProperty(type.name());
			}else{
				return "";
			}
			
		}
		
	}
	
	public static String getI18nMessage(UsConstants type){
		return getI18nMessage(type, LanguageUtil.get());
	}
	
}
