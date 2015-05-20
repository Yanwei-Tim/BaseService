package com.unistrong.tracker.web.filter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.util.ResponseUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unistrong.tracker.handle.manage.MailConf;

public class PictureFilter implements Filter {

	private static Logger log = LoggerFactory.getLogger(PictureFilter.class);

	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		// request.getSession().getServletContext()
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(this.filterConfig.getServletContext());
		MailConf mailConf = (MailConf) webApplicationContext.getBean("mailConf");
		// UserHandle userHandle = (UserHandle)
		// webApplicationContext.getBean("userHandle");
		String url = request.getServletPath();
		// boolean tag = url.matches("^/upload/\\S+((jpg)|(png))$");
		String deviceSn = pattern(url);
		if (null != deviceSn) {
			String userIdStr = request.getParameter("user_id");
			String token = request.getParameter("token");
			log.info("user_id-token:" + userIdStr + ";" + token);
			// userHandle.authToken(userIdStr, token);
			Boolean tag = true;
			if (tag) {
				int index = url.lastIndexOf("/");
				String icon = url.substring(index + 1);

				InputStream inputStream = new FileInputStream(mailConf.getIcon() + icon);
				byte[] byteArray = new byte[inputStream.available()];
				inputStream.read(byteArray);
				inputStream.close();

				OutputStream out = response.getOutputStream();
				out.write(byteArray);
				out.close();
			} else {
				try {
					ResponseUtils.renderJson(((HttpServletResponse) response), "token有误");
				} catch (Exception ex) {
					log.error("", ex);
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public static String pattern(String candidate) {
		String regex = "";
		String result = null;
		Pattern pattern = null;
		Matcher matcher = null;
		regex = "(?<=/upload/).*?(?=(\\.jpg)|(\\.png))";// 非贪婪
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(candidate);
		while (matcher.find()) {
			result = matcher.group();
		}
		return result;
		// System.out.println(pattern("/upload/11.png"));
	}
}
