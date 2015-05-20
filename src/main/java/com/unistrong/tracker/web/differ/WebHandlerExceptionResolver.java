package com.unistrong.tracker.web.differ;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.util.ResponseUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class WebHandlerExceptionResolver implements HandlerExceptionResolver {

	private static final Logger log = LoggerFactory.getLogger(WebHandlerExceptionResolver.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String msg = ex.getMessage();
		if (!(ex instanceof IllegalArgumentException)) {
			if (ex instanceof org.springframework.validation.BindException) {
				msg = "参数格式有误";
			} else {
				msg = "系统错误";
				log.error("error", ex);
				ex.printStackTrace();
			}
		}
		rs.put("ret", 2);
		rs.put("desc", msg);
		String cbName = request.getParameter("callback");
		if (null != cbName) {
			return new ModelAndView("", rs);
		} else {
			try {
				ResponseUtils.renderJson(response, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView();
		}
	}

}
