package com.unistrong.tracker.util;

import java.util.Map;

import module.util.HttpContent;
import module.util.JsonUtils;

import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Baidu {

	private static final Logger log = LoggerFactory.getLogger(Baidu.class);

	public static void main(String[] args) throws Exception {
		Double lngDouble = 116.4985916018D;
		Double latDouble = 39.9798650053D;
		Double[] lngLat = getBaidu(lngDouble, latDouble);
		System.out.println(lngLat[0]);
		System.out.println(lngLat[1]);
	}

	/** gps坐标转换 为 百度坐标 */
	public static Double[] getBaidu(Double lngDouble, Double latDouble) {
		Double lng = 0D;
		Double lat = 0D;
		try {
			String url = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=%.6f&y=%.6f";
			url = String.format(url, lngDouble, latDouble);
			String content = HttpContent.get(url);
			Map<String, Object> map = JsonUtils.str2Map(content);
			String lngString = map.get("x").toString();
			String latString = map.get("y").toString();
			byte[] bslng = Base64.decodeBase64(lngString.getBytes());
			byte[] bslat = Base64.decodeBase64(latString.getBytes());
			lng = Double.valueOf(new String(bslng));
			lat = Double.valueOf(new String(bslat));
		} catch (Exception ex) {
			log.error("gps2baidu", ex);
		}
		Double[] lngLat = new Double[] { lng, lat };
		return lngLat;
	}
}
