package module.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.util.HttpUtil;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// dispatcher-servlet.xml

// 方式1 tracker
//<bean id="authInterceptor" class="this.class">
//<property name="notAuths">
//	<list>
//		<value>/user.register</value><!-- 注册 -->
//		<value>/user.logon</value><!-- 登陆 -->
//	</list>
//</property>
//</bean>

// 方式2 渔捞
//<mvc:annotation-driven />
//<mvc:interceptors>
//	<mvc:interceptor>
//		<mvc:mapping path="/*" />
//		<bean class="this.class" >
//			<property name="notAuths">
//				<list>
//					<value>/user.logon</value>
//					<value>/user.identify.code</value>
//				</list>
//			</property>
//			<property name="rolePerms">
//				<list>
//					<value>/ship.delete</value>
//					<value>/ship.save</value>
//				</list>
//			</property>
//		</bean>
//	</mvc:interceptor>
//</mvc:interceptors>
public class AuthInterceptor implements HandlerInterceptor {

	/**
	 * 没有登陆
	 */
	private static String AUTH_FAILED = "authentication failed(没有登陆)";

	/**
	 * 没有权限
	 */
	private static String PERM_FAILED = "permission failed(没有权限)";

	/**
	 * 不需认证URL列表
	 */
	private List<String> notAuths = new ArrayList<String>();

	/**
	 * 管理员资源列表
	 */
	private List<String> rolePerms = new ArrayList<String>();

	/**
	 * authentication permission
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if ("1".equals("1"))
			return true;
		return auth(request);
	}

	/**
	 * 认证
	 */
	private boolean auth(HttpServletRequest request) {

		// 是否在"不需认证URL列表"中 起
		String url = request.getPathInfo();// getPathInfo() getServletPath()
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
		HttpUtil.validateNull(new String[] { "token", "user_id" },
				new String[] { token, userIdStr });
		Long userId = HttpUtil.getLong(userIdStr);
		System.out.println(userId + token);

		if ("重写通过逻辑".equals("重写通过逻辑")) {
			permission(request);
			return true;
		} else
			throw new IllegalArgumentException(AUTH_FAILED);
	}

	/**
	 * 权限
	 */
	private boolean permission(HttpServletRequest request) {
		// 是否在"管理员URL列表"中 起
		String url = request.getPathInfo();
		boolean tag = false;
		for (String notAuth : rolePerms) {
			if (url.equals(notAuth)) {
				tag = true;
				break;
			}
		}
		if (!tag)
			return true;
		// 是否在"管理员URL列表"中 终

		String userIdStr = request.getParameter("user_id");
		HttpUtil.validateNull(new String[] { "user_id" },
				new String[] { userIdStr });
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });
		Long userId = HttpUtil.getLong(userIdStr);
		System.out.println(userId);

		if ("重写通过逻辑".equals("重写通过逻辑"))
			return true;
		else
			throw new IllegalArgumentException(PERM_FAILED);
	}

	// *********************************************

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	public void setNotAuths(List<String> notAuths) {
		this.notAuths = notAuths;
	}

	public void setRolePerms(List<String> rolePerms) {
		this.rolePerms = rolePerms;
	}

}
