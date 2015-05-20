package com.unistrong.tracker.web.differ;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.util.Assert;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.unistrong.tracker.handle.UserHandle;
import com.unistrong.tracker.service.cache.UserCache;
import com.unistrong.tracker.util.Language;
import com.unistrong.tracker.util.LanguageUtil;

public class AuthInterceptor implements HandlerInterceptor {
	
	private UserHandle userHandle;

	private UserCache userCache;

	private List<String> notAuths = new ArrayList<String>();

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		return true;
//		boolean haslogin = auth(request);
//		if (haslogin) {
//			// 获取语言信息
//			String userIdStr = request.getParameter("user_id");
//			String language = request.getParameter("language");
//			if (!StringUtils.isBlank(language)) {
//				LanguageUtil.set(Language.valueOf(language));
//			} else if (!StringUtils.isBlank(userIdStr)) {
//				language = userCache.getUserLanguage(userIdStr);
//				if (language != null) {
//					LanguageUtil.set(Language.valueOf(language));
//				}
//			}
//
//		}
//		
//		return haslogin;
	}

	/**
	 * 认证
	 */
	private boolean auth(HttpServletRequest request) throws Exception {
		// 是否在"不需认证URL列表"中 起
		String url = request.getServletPath();// getPathInfo() getServletPath()
		boolean tag = false;
		for (String notAuth : notAuths) {
			if (url.equals(notAuth)) {
				tag = true;
				break;
			}
		}
		if (tag)
			return true;
		// 是否在"不需认证URL列表"中 终

		String token = request.getParameter("token");
		String userIdStr = request.getParameter("user_id");
		Assert.isTrue(userHandle.authToken(userIdStr, token), "token error");

		return true;
	}

	// *********************************************

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LanguageUtil.unset();
	}

	public void setNotAuths(List<String> notAuths) {
		this.notAuths = notAuths;
	}

	public void setUserHandle(UserHandle userHandle) {
		this.userHandle = userHandle;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

}
