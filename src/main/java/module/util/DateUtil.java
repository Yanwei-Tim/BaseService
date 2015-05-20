package module.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author fyq pattern:y.M.d h:m:s S
 */
public class DateUtil {

	/** yyyyMMdd HH:mm:ss */
	public static String DEF_FORMAT = "yyyyMMdd HH:mm:ss";

	/** yyyy-MM-dd HH:mm:ss */
	public static String ALL_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 字符转date
	 */
	public static Date strDate(String str, String pattern)
			throws ParseException {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.parse(str);
	}

	/**
	 * 长整转date
	 */
	public Date longDate(Long num) {
		return new Date(num);
	}

	/**
	 * 时间转字符
	 */
	public static String dateStr(Date date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 长整型转字符
	 */
	public static String longStr(Long num, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(num);
	}

	/**
	 * 字符转长整型
	 */
	public static Long strLong(String str, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(str).getTime();
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 当前时间 方式二
	 */
	public static Date nowDate() {
		return Calendar.getInstance().getTime();
	}

	public static Date addMinute(Date date, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}

	/**
	 * 前后几天的零点,昨天-1
	 */
	public static Date getYesterday(int num) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, num);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static long hourBetweenTowDate(Date d1, Date d2) {
		return (d2.getTime() - d1.getTime()) / 3600000L;
	}

	public static long secondBetweenTowDate(Date d1, Date d2) {
		return (d2.getTime() - d1.getTime()) / 1000L;
	}

	/**
	 * c1和c2的月份差
	 * 
	 * @param c1
	 * @param c2
	 * @return 月份差
	 */
	public static int getMonthDiff(Calendar c1, Calendar c2) {

		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH) + 1;

		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH) + 1;

		int month_diff = 12 * (year1 - year2) + month1 - month2;

		return month_diff;
	}


	/**
	 * 当前时间 N天之后的日期
	 */
	public static Date getNDaysAfter(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 指定时间N天之后的日期
	 */
	public static Date getNDaysAfter(Date d, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 某天的最早时间
	 */
	public static long getDateBegin(Date d) {
		return getDateAfterFormat(d, "yyyyMMdd");
	}

	/**
	 * 某天的最晚时间
	 */
	public static long getDateEnd(Date d) {
		return getDateAfterFormat(d, "yyyyMMdd") + 24 * 60 * 60 * 1000 - 1;
	}

	// public static String getDayByDate(Date d, String format) {
	// DateFormat df = new SimpleDateFormat(format);
	// return df.format(d);
	// }

	/**
	 * 返回指定时间在格式化之后的时间
	 * 
	 * @param d
	 * @param format
	 * @return
	 */
	public static long getDateAfterFormat(Date d, String format) {
		DateFormat df = new SimpleDateFormat(format);
		String nd = df.format(d);
		try {
			return df.parse(nd).getTime();
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Date getDateAfterFormat(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal.getTime();
	}

	public static Date getLastMonthDay(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 是否同一天
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static boolean isSameDay(long t1, long t2) {

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(t1);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(t2);

		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH) + 1;
		int day1 = c1.get(Calendar.DATE);

		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH) + 1;
		int day2 = c2.get(Calendar.DATE);

		if (year1 == year2 && month1 == month2 && day1 == day2) {
			return true;
		}

		return false;
	}
	
	public static void main(String[] args) {
		// Date yesterday = getYesterday(-1);
		// System.out.println(dateStr(yesterday, "yyyy.MM.dd HH:mm:ss"));
		// System.out.println(yesterday.getTime());
		//
		// // System.out.println(longStr(0L, "yyyy-MM-dd HH:mm:ss"));
		// DateFormat df = new SimpleDateFormat(ALL_FORMAT);
		// // System.out.println(df.format(getNDaysAfter(-1)));
		//
		// Date d = new Date();
		// System.out.println(d.getTime());
		// System.out.println(getDateAfterFormat(d, "yyyyMMdd"));
		// System.out.println(df.format(getDateAfterFormat(
		// getDateAfterFormat(d, "yyyyMMdd")).getTime()));
		// System.out.println(getNDaysAfter(new Date(), 1));

		// long begin = getDateBegin(d);
		// long end = getDateEnd(d);
		//
		// System.out.println(longStr(begin, ALL_FORMAT));
		// System.out.println(longStr(end, ALL_FORMAT));

		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date d = new Date();
		// String dd = format.format(d);
		// Date ddd;
		// try {
		// ddd = format.parse(dd);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }

		boolean day = isSameDay(1410316002868L, 1410316002869L);

		System.out.println(day);
	}
}
