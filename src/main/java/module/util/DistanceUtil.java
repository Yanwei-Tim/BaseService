package module.util;

public class DistanceUtil {

	/**
	 * 地球半径
	 */
	private static final double EARTH_RADIUS = 6378137;

	/**
	 * 由纬度计算长度
	 */
	private static double rad(double lat) {
		return lat * Math.PI / 180.0;
	}

	/**
	 * 两点(经纬度)间距离,单位为米
	 */
	public static double distance(double lng1, double lat1, double lng2,
			double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 两点坐标点间距离,单位为米
	 */
	public static double indoor_distance(double x1, double y1, double x2,
			double y2) {

		// 两点之间的距离公式为 d=√[(x1-x2)²+(y1-y2)²]

		double s = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
		s = Math.round(s * 10000) / 10000;
		return s;
	}

}
