package com.unistrong.tracker.entry.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import com.unistrong.tracker.model.Protocol;

public class Opt {

	public static Protocol getAuthProtocol(Protocol protocol, Map<String, Object> rs,
			List<Map<String, Object>> rss) {
		rs.put("ret", 9);
		rs.put("desc", "您长时间未操作，为保护帐号安全，请重新登陆");
		rss.add(rs);
		protocol.setCmdList(rss);
		return protocol;
	}

	public static String getStack(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return "\r\n" + sw.toString() + "\r\n";
	}

	public static <T> String getByMap(Map<String, T> cmd, String key) {
		try {
			return cmd.get(key).toString();
		} catch (Exception e) {
			return null;
		}
	}

}
