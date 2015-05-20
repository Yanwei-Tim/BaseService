package module.util;

import org.apache.commons.lang.StringUtils;

import com.unistrong.tracker.util.UsConstants;

/**
 * 检测传值
 * 
 * @author fyq
 * @date 2013-2-27,上午11:18:59
 */
public final class HttpUtil {

	// ******************************************************

	/**
	 * 错误提示与被检测字段一一对应
	 */
	public static void validateNull(String[] errorMsgs, String[] strs) {
		if (strs != null) {
			boolean tag = false;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < strs.length; i++) {
				String value = strs[i];
				if (StringUtils.isBlank(value)) {
					tag = true;
					if (sb.length() > 0)
						sb.append(",");
					sb.append(errorMsgs[i]);
					sb.append(UsConstants.getI18nMessage(UsConstants.NOT_NULL));
				}
			}
			if (tag)
				throw new IllegalArgumentException(sb.toString());
		}
	}

	/**
	 * 必须为Long,否则异常
	 */
	public static void validateLong(String[] errorMsgs, String[] strs) {
		if (strs != null) {
			boolean tag = false;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < strs.length; i++) {
				try {
					Long.parseLong(strs[i]);
				} catch (Exception e) {
					tag = true;
					if (sb.length() > 0)
						sb.append(",");
					sb.append(errorMsgs[i]);
					sb.append(UsConstants.getI18nMessage(UsConstants.NUM_PARAM));
				}
			}
			if (tag)
				throw new IllegalArgumentException(sb.toString());
		}
	}

	/**
	 * 检测范围,包含边界
	 */
	public static void validateIntScope(String numStr, int min, int max) {
		String msg = "argument must in " + min + "-" + max;
		int num = getIntMsg(numStr, msg);
		if (num > max || num < min) {
			throw new IllegalArgumentException(msg);
		}
	}

	// ******************************************************

	/**
	 * 获取传值,空则异常
	 */
	public static Integer getInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					UsConstants.getI18nMessage(UsConstants.NUM_PARAM));
		}
	}

	/**
	 * 获取传值,空则默认
	 */
	public static Integer getInt(String str, Integer defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 重载与getInt("1",null)冲突,所以重命名
	 */
	public static Integer getIntMsg(String str, String msg) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * 获取传值,空则异常
	 */
	public static Long getLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					UsConstants.getI18nMessage(UsConstants.NUM_PARAM));
		}
	}

	/**
	 * 获取传值,空则默认
	 */
	public static Long getLong(String str, Long defaultValue) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 重载与getLong("1",null)冲突,所以重命名
	 */
	public static Long getLongMsg(String str, String msg) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * 获取传值,空则异常
	 */
	public static Double getDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					UsConstants.getI18nMessage(UsConstants.NUM_PARAM));
		}
	}

	/**
	 * 获取传值,空则默认
	 */
	public static Double getDouble(String str, Double defaultValue) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 获取传值,空则异常
	 */
	public static Float getFloat(String str) {
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					UsConstants.getI18nMessage(UsConstants.NUM_PARAM));
		}
	}

	/**
	 * 获取传值,空则默认
	 */
	public static Float getFloat(String str, Float defaultValue) {
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 获取传值,空则默认
	 */
	public static boolean getBoolean(Boolean b, boolean defaultValue) {
		if (b == null) {
			return defaultValue;
		}
		return b;
	}

	/**
	 * 获取原始类型，null则传默认值
	 */
	public static int getIntegerBasicType(Integer num, int defaultValue) {
		if (num == null) {
			return defaultValue;
		}
		return num;
	}
	
	
	/**
	 * 获取原始类型，null则传默认值
	 */
	public static long getLongBasicType(Long num, long defaultValue) {
		if (num == null) {
			return defaultValue;
		}
		return num;
	}
}
