package com.unistrong.tracker.web;

import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.util.JsonUtils;
import module.util.ResponseUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.entry.context.EntryMaping;
import com.unistrong.tracker.entry.context.Strategy;
import com.unistrong.tracker.model.Protocol;
import com.unistrong.tracker.service.LoginService;

/**
 * @author fyq
 */
@Controller
public class EntryController {

	private static Logger logger = LoggerFactory
			.getLogger(EntryController.class);

	@Resource
	private EntryMaping entryMaping;
	
	@Resource
	private LoginService loginService;

	/** 入口,根据cmd判断 */
	@RequestMapping("/tracker.do")
	public Map<String, Object> tracker(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Protocol protocol = new Protocol();
		try {
			InputStream inputStream = request.getInputStream();
			StringBuffer sb = new StringBuffer();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = inputStream.read(b)) != -1) {
				String temp = new String(b, 0, i, "utf-8");
				sb.append(temp);
			}
			inputStream.close();

			protocol = JsonUtils.json2Obj(sb.toString(), Protocol.class);
			Integer cmd = (Integer) protocol.getCmdList().get(0).get("cmd");
			logger.info("inputParam:[" + Strategy.strategyMap.get(cmd) + "]"
					+ sb.toString());
			
			
			// 获取ip地址
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			
			protocol.setIp(ip);
			
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtils.renderJson(response, "协议格式有误");
			return null;
		}
		entryMaping.operate(protocol);
		logger.info("outputParam:" + JsonUtils.obj2Str(protocol));
		ResponseUtils.renderJson(response, protocol);
		return null;
	}

}
