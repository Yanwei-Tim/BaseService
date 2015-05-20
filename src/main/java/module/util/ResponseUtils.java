/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Struts2Utils.java 624 2009-11-05 13:31:47Z calvinxiu $
 */
package module.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtils {

	// -- header 常量定义 --//
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	// -- content-type 常量定义 --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	private static final String JS_TYPE = "text/javascript";

	private static Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

	public static void render(HttpServletResponse response, final String contentType,
			final String content, final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(HttpServletResponse response, final String text,
			final String... headers) {
		render(response, TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(HttpServletResponse response, final String html,
			final String... headers) {
		render(response, HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(HttpServletResponse response, final String xml,
			final String... headers) {
		render(response, XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param map
	 *            Map对象,将被转化为json字符串.
	 * @throws Exception
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("rawtypes")
	public static void renderJson(HttpServletResponse response, final Map map,
			final String... headers) throws Exception {
		String jsonString = JsonUtils.getJson(map);
		render(response, JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @throws Exception
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(HttpServletResponse response, final Object object,
			final String... headers) throws Exception {
		String jsonString = JsonUtils.getJson(object);
		render(response, JSON_TYPE, jsonString, headers);
	}

	public static void renderJs(String cbName, HttpServletResponse response, final Object object,
			final String... headers) throws Exception {
		String jsonString = JsonUtils.getJson(object);
		jsonString = cbName + "(" + jsonString + ")";
		render(response, JS_TYPE, jsonString, headers);
	}

}
