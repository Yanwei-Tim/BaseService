package com.unistrong.tracker.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import module.util.HttpContent;
import module.util.JsonUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unistrong.tracker.model.GeoCoordinate;

public class GeoMapUtil {

	private static final Logger log = LoggerFactory.getLogger(GeoMapUtil.class);

	private static Pattern gaddr = Pattern
			.compile("<formatted_address>([^<]+)</formatted_address>");

	private static final String BAIDU_AK = "A00ea0faad22ed06ae0b537d30ff63ff";

	private static Rectangle[] region = new Rectangle[] {
			new Rectangle(49.220400, 079.446200, 42.889900, 096.330000),
			new Rectangle(54.141500, 109.687200, 39.374200, 135.000200),
			new Rectangle(42.889900, 073.124600, 29.529700, 124.143255),
			new Rectangle(29.529700, 082.968400, 26.718600, 097.035200),
			new Rectangle(29.529700, 097.025300, 20.414096, 124.367395),
			new Rectangle(20.414096, 107.975793, 17.871542, 111.744104), };

	private static Rectangle[] exclude = new Rectangle[] {
			new Rectangle(25.398623, 119.921265, 21.785006, 122.497559),
			new Rectangle(22.284000, 101.865200, 20.098800, 106.665000),
			new Rectangle(21.542200, 106.452500, 20.487800, 108.051000),
			new Rectangle(55.817500, 109.032300, 50.325700, 119.127000),
			new Rectangle(55.817500, 127.456800, 49.557400, 137.022700),
			new Rectangle(44.892200, 131.266200, 42.569200, 137.022700), };

	/** gps坐标转换 为 百度坐标 */
	private static Double[] gps2baidu(Double lngDouble, Double latDouble) {
		Double lng = 0D;
		Double lat = 0D;
		try {
			String url = "http://api.map.baidu.com/geoconv/v1/?ak=" + BAIDU_AK
					+ "&from=1&to=5&coords=" + lngDouble + "," + latDouble;
			String content = HttpContent.get(url);

			if (!StringUtils.isEmpty(content)) {

				Map<String, Object> map = JsonUtils.str2Map(content);
				List<Map> list = (List) map.get("result");
				Map point = list.get(0);
				lng = (Double) point.get("x");
				lat = (Double) point.get("y");
			}

		} catch (Exception ex) {
			log.error("gps2baidu", ex);
		}
		Double[] lngLat = new Double[] { lng, lat };
		return lngLat;
	}

	private static class Rectangle {
		public double West;
		public double North;
		public double East;
		public double South;

		public Rectangle(double latitude1, double longitude1, double latitude2,
				double longitude2) {
			this.West = Math.min(longitude1, longitude2);
			this.North = Math.max(latitude1, latitude2);
			this.East = Math.max(longitude1, longitude2);
			this.South = Math.min(latitude1, latitude2);
		}
	}

	public static boolean IsInsideChina(GeoCoordinate pos) {
		for (int i = 0; i < region.length; i++) {
			if (InRectangle(region[i], pos)) {
				for (int j = 0; j < exclude.length; j++) {
					if (InRectangle(exclude[j], pos)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	private static boolean InRectangle(Rectangle rect, GeoCoordinate pos) {
		return rect.West <= pos.getLongitude()
				&& rect.East >= pos.getLongitude()
				&& rect.North >= pos.getLatitude()
				&& rect.South <= pos.getLatitude();
	}

	public static boolean IsInsideChinaByGoogle(GeoCoordinate pos) {
		String addr = getAddrByGoogle(pos.getLatitude(), pos.getLongitude(),
				null);
		return addr.indexOf("China") > -1 ? true : false;
	}

	/**
	 * gps坐标转百度的地址
	 * 
	 * @param lng
	 * @param lat
	 * @return
	 */
	public static String getAddrByBaidu(Double lng, Double lat) {
		String rs = "";
		String location = lat + "," + lng;
		String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + BAIDU_AK
				+ "&coordtype=wgs84ll&location=" + location
				+ "&output=json&pois=0";
		try {
			String content = HttpContent.get(url);

			if (!StringUtils.isEmpty(content)) {

				Map<String, Object> map = JsonUtils.str2Map(content);
				Map<String, Object> result = (Map<String, Object>) map
						.get("result");
				if (result != null) {
					Object address = result.get("formatted_address");
					if (address != null) {
						rs = (String) address;
					}
				}
			}

		} catch (Exception e) {
			log.error("getAddr", e);
		}
		return rs;
	}

	/**
	 * gps坐标转google的地址
	 * 
	 * @param lng
	 *            经度
	 * @param lat
	 *            纬度
	 * @return
	 */
	public static String getAddrByGoogle(Double lng, Double lat, Language lag) {
		String rs = "";
		String location = lat + "," + lng;
		String url = "http://maps.googleapis.com/maps/api/geocode/xml?latlng="
				+ location + "&sensor=false";
		if (Language.zh_CN.equals(lag)) {
			url += "&language=zh-CN";
		}
		try {
			String info = HttpContent.get(url);
			Matcher m = gaddr.matcher(info);
			if (m.find()) {
				rs = m.group(1);
			}
		} catch (Exception e) {
			log.error("getAddrByGoogle", e);
		}
		return rs;
	}

	public static void main(String[] args) {
		System.out
				.println(getAddrByGoogle(39.976017, 116.33644, Language.zh_CN));
	}

}
