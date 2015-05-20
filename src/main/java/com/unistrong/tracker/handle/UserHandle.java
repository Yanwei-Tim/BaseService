package com.unistrong.tracker.handle;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import module.util.Assert;
import module.util.EntityUtil;
import module.util.HttpUtil;
import module.util.SpecialStrUtil;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.i18n.MessageManager;
import com.unistrong.tracker.handle.manage.MailManage;
import com.unistrong.tracker.handle.util.SendMail;
import com.unistrong.tracker.model.GeoCoordinate;
import com.unistrong.tracker.model.Login;
import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.model.User;
import com.unistrong.tracker.service.LoginService;
import com.unistrong.tracker.service.UserService;
import com.unistrong.tracker.service.cache.IosTokenCache;
import com.unistrong.tracker.service.cache.UserCache;
import com.unistrong.tracker.util.GeoMapUtil;
import com.unistrong.tracker.util.Logic;
import com.unistrong.tracker.util.UsConstants;

/**
 * @author fyq
 */
@Component
public class UserHandle {

	@Resource
	private MailManage mailManage;

	@Resource
	private UserCache userCache;

	@Resource
	private IosTokenCache iosTokenCache;

	@Resource
	private UserService userService;

	@Resource
	private LoginService loginService;

	@Resource
	private AlarmHandle alarmHandle;

	@Resource
	private PositionHandle positionHandle;

	private static Logger logger = LoggerFactory.getLogger(UserHandle.class);

	/** 注册 sina_123/123 */
	public Map<String, Object> register(String type, String name, String pwd,
			String phone, String iosToken) {
		HttpUtil.validateNull(new String[] { "type", "name", "pwd" },
				new String[] { type, name, pwd });
		HttpUtil.validateIntScope(type, 1, 6);
		User userForm = new User();
		userForm.setName(name);
		userForm.setPassword(pwd);
		if ("1".equals(type)) {// 1：电话
			userForm.setPhone(name);
		} else if ("2".equals(type)) {// 2：邮箱
			userForm.setEmail(name);
			userForm.setPhone(phone);
		} else if ("4".equals(type)) {// 4：QQ
			String thirdId = name.substring(name.indexOf("_") + 1);
			userForm.setQqId(thirdId);
		} else if ("5".equals(type)) {// 5：新浪
			String thirdId = name.substring(name.indexOf("_") + 1);
			userForm.setSinaId(thirdId);
		} else if ("6".equals(type)) {// 6：人人
			String thirdId = name.substring(name.indexOf("_") + 1);
			userForm.setRrId(thirdId);
		}
		return saveOrUpdate(userForm, null, null, iosToken);
	}

	/** 注册 (加上昵称) */
	public Map<String, Object> register(String type, String name, String pwd,
			String phone, String iosToken, String nick_name, String service_id,
			String plat_form, String business) {

		HttpUtil.validateNull(
				new String[] { "type", "name", "pwd", "plat_form" },
				new String[] { type, name, pwd, plat_form });

		HttpUtil.validateIntScope(type, 1, 6);
		HttpUtil.validateIntScope(plat_form, 1, 2);

		int platform = Integer.parseInt(plat_form);

		User userForm = new User();
		userForm.setName(name);
		userForm.setPassword(pwd);
		if ("1".equals(type)) {// 1：电话
			userForm.setPhone(name);
		} else if ("2".equals(type)) {// 2：邮箱
			userForm.setEmail(name);
			userForm.setPhone(phone);
		} else if ("4".equals(type)) {// 4：QQ
			String thirdId = name.substring(name.indexOf("_") + 1);
			userForm.setQqId(thirdId);
		} else if ("5".equals(type)) {// 5：新浪
			String thirdId = name.substring(name.indexOf("_") + 1);
			userForm.setSinaId(thirdId);
		} else if ("6".equals(type)) {// 6：人人
			String thirdId = name.substring(name.indexOf("_") + 1);
			userForm.setRrId(thirdId);
		}

		userForm.setNick(nick_name);// 昵称
		userForm.setPlatform(platform);// 所属平台
		userForm.setTime(new Date().getTime());// 时间

		if (platform == 2) {// 前台用户
			HttpUtil.validateIntScope(business, 0, 1);
			HttpUtil.validateLong(new String[] { "service_id" },
					new String[] { service_id });

			int isbusiness = Integer.valueOf(business);
			long service_user_id = Long.parseLong(service_id);

			String appName = userService.getAppName(service_user_id);

			userForm.setAppName(appName);
			userForm.setServiceId(service_user_id);// 服务用户id
			userForm.setType(isbusiness);// 是否是企业（0否，1是）
			if (isbusiness == 1) {
				userForm.setParentId(service_user_id);
				userForm.setRole(2);// 普通用户
			}
		}

		return save(userForm, iosToken);
	}

	private Map<String, Object> save(User form, String iosToken) {

		Map<String, Object> rs = new HashMap<String, Object>();

		User entity = new User();
		User userTmp = userService.findByName(form.getName());
		Assert.isNull(userTmp,
				UsConstants.getI18nMessage(UsConstants.USER_REGISTERED));

		// 密码处理 起
		String formPwd = form.getPassword();
		form.setPassword(SpecialStrUtil.getMd5(formPwd));
		// 密码处理 终

		// db唯一性校验 起
		if (null != form.getName()) {
			Assert.isTrue(userService.isUnique("name", form.getName(), null),
					UsConstants.getI18nMessage(UsConstants.USER_REGISTERED));
		}
		if (null != form.getEmail()) {
			String regex = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
			Assert.isTrue(form.getEmail().matches(regex),
					MessageManager.MAIL_FORMAT_INCORRECT());
			Assert.isTrue(userService.isUnique("email", form.getEmail(), null),
					UsConstants.getI18nMessage(UsConstants.EMAIL_REGISTERED));
		}
		// if (null != form.getPhone()) {
		// Assert.isTrue(userService.isUnique("phone", form.getPhone(), null),
		// UsConstants.getI18nMessage(UsConstants.PHONE_REGISTERED));
		// }
		// db唯一性校验 终

		BeanUtils.copyProperties(form, entity,
				EntityUtil.nullField(User.class, form));

		userService.saveOrUpdate(entity);

		User u = userService.findByName(form.getName());

		String app_name = "";
		if (u.getPlatform() == 1) {
			app_name = "APP_" + u.getId();
			u.setAppName(app_name);
			u.setServiceId(u.getId());
			userService.saveOrUpdate(u);
		}

		String token = SpecialStrUtil.getUuid();
		userCache.setUserToken(entity.getId().toString(), token);
		rs.put("token", token);

		if (null != iosToken && !"".equals(iosToken)) {
			Long lastUserId = iosTokenCache.getTokenUser(iosToken);
			if (lastUserId != null && lastUserId > 0) {
				iosTokenCache.removeIosToken(lastUserId, iosToken);
			}
			iosTokenCache.setTokenUser(entity.getId(), iosToken);
			iosTokenCache.addIosToken(entity.getId(), iosToken);
		}
		rs.put("user_id", entity.getId());
		rs.put("app_name", app_name);
		rs.put(Logic.DESC, MessageManager.REGISTER_SUCCESS());
		return rs;
	}

	public Map<String, Object> register_app(String type, String name,
			String pwd, String phone, String iosToken, String app_name) {
		HttpUtil.validateNull(
				new String[] { "type", "name", "pwd", "app_name" },
				new String[] { type, name, pwd, app_name });
		HttpUtil.validateIntScope(type, 1, 6);
		User userForm = new User();
		userForm.setName(name);
		userForm.setPassword(pwd);
		userForm.setAppName(app_name);
		if ("1".equals(type)) {// 1：电话
			userForm.setPhone(name);
		} else if ("2".equals(type)) {// 2：邮箱
			userForm.setEmail(name);
			userForm.setPhone(phone);
		} else if ("4".equals(type)) {// 4：QQ
			String thirdId = name.substring(name.indexOf("_") + 1);
			userForm.setQqId(thirdId);
		} else if ("5".equals(type)) {// 5：新浪
			String thirdId = name.substring(name.indexOf("_") + 1);
			userForm.setSinaId(thirdId);
		} else if ("6".equals(type)) {// 6：人人
			String thirdId = name.substring(name.indexOf("_") + 1);
			userForm.setRrId(thirdId);
		}
		return saveOrUpdate(userForm, null, null, iosToken);
	}

	/**
	 * CRUD:新增或修改用户
	 * 
	 * 传过来的老密码为: MD5(原始密码二次MD5+时间戳)
	 * 
	 * 传过来的新密码为: 一次MD5
	 */
	private Map<String, Object> saveOrUpdate(User form, String oldPwd,
			String ts, String iosToken) {
		Map<String, Object> rs = new HashMap<String, Object>();
		User entity = null;
		Long userFormId = form.getId();
		if (null == userFormId) {// new
			entity = new User();
			User userTmp = userService.findByName(form.getName());
			Assert.isNull(userTmp,
					UsConstants.getI18nMessage(UsConstants.USER_REGISTERED));
		} else {// update
			entity = userService.get(userFormId);
			Assert.notNull(entity,
					UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
			form.setName(null);// 用户名不充许修改
		}
		// 密码处理 起
		String formPwd = form.getPassword();
		if (null != formPwd) {// 新增或修改密码
			form.setPassword(SpecialStrUtil.getMd5(formPwd));
			if (null != userFormId) {// 修改密码
				String dbOldPwd = entity.getPassword();
				String dbOldPwdTs = SpecialStrUtil.getMd5(dbOldPwd
						+ HttpUtil.getLong(ts));
				Assert.equals(dbOldPwdTs, oldPwd,
						UsConstants.getI18nMessage(UsConstants.USER_PW_ERROR));
			}
		}
		// 密码处理 终
		// db唯一性校验 起
		if (null != form.getName()) {
			Assert.isTrue(
					userService.isUnique("name", form.getName(), userFormId),
					UsConstants.getI18nMessage(UsConstants.USER_REGISTERED));
		}
		if (null != form.getEmail()) {
			String regex = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
			Assert.isTrue(form.getEmail().matches(regex),
					MessageManager.MAIL_FORMAT_INCORRECT());
			Assert.isTrue(
					userService.isUnique("email", form.getEmail(), userFormId),
					UsConstants.getI18nMessage(UsConstants.EMAIL_REGISTERED));
		}
		if (null != form.getPhone()) {
			Assert.isTrue(
					userService.isUnique("phone", form.getPhone(), userFormId),
					UsConstants.getI18nMessage(UsConstants.PHONE_REGISTERED));
		}
		// db唯一性校验 终
		BeanUtils.copyProperties(form, entity,
				EntityUtil.nullField(User.class, form));
		userService.saveOrUpdate(entity);
		if (null == userFormId) {// new
			String token = SpecialStrUtil.getUuid();
			userCache.setUserToken(entity.getId().toString(), token);
			rs.put("token", token);
		}
		if (null != iosToken && !"".equals(iosToken)) {
			Long lastUserId = iosTokenCache.getTokenUser(iosToken);
			if (lastUserId != null && lastUserId > 0) {
				iosTokenCache.removeIosToken(lastUserId, iosToken);
			}
			iosTokenCache.setTokenUser(entity.getId(), iosToken);
			iosTokenCache.addIosToken(entity.getId(), iosToken);
		}
		rs.put("user_id", entity.getId());
		rs.put(Logic.DESC, MessageManager.REGISTER_SUCCESS());
		return rs;
	}

	/**
	 * IOS注销
	 */
	public Map<String, Object> logout(String userIdStr, String iosToken) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Long userId = HttpUtil.getLong(userIdStr);
		iosTokenCache.removeIosToken(userId, iosToken);
		return rs;
	}

	/**
	 * 登陆
	 */
	public Map<String, Object> saveLogon(String name, String pwd, String ts,
			String lastnum, String duid, String iosToken, String appName) {
		HttpUtil.validateNull(new String[] { "name", "pwd", "ts" },
				new String[] { name, pwd, ts });
		HttpUtil.validateLong(new String[] { "ts" }, new String[] { ts });
		Long tsLong = HttpUtil.getLong(ts);
		Map<String, Object> resMap = new HashMap<String, Object>();
		User entity = null;
		if (appName == null || (appName != null && appName.equals("M2616_BD"))) {
			entity = userService.findByName(name);
		} else {
			entity = userService.findByName(name, appName);
		}
		if (null == entity) {// ret特殊
			resMap.put("ret", 3);
			resMap.put("desc",
					UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
			return resMap;
		}

		String dbOldPwdTs = SpecialStrUtil
				.getMd5(entity.getPassword() + tsLong);
		Assert.equals(dbOldPwdTs, pwd,
				UsConstants.getI18nMessage(UsConstants.USER_PW_ERROR));

		Long userId = entity.getId();
		String token = userCache.getUserToken(String.valueOf(userId));
		Integer role = entity.getRole();
		String nick = entity.getNick();
		String app = entity.getAppName();
		Integer type = entity.getType();
		if (token == null) {
			token = SpecialStrUtil.getUuid();
		}

		Long service_id = entity.getServiceId();
		Integer user_type = entity.getUserType();

		// 保存到登录表
		Login l = new Login();
		l.setTime(new Date().getTime());
		l.setUserId(userId);
		l.setServiceId(service_id);
		loginService.saveOrUpdate(l);

		if (service_id != null && userId == service_id) {
			service_id = null;
		}

		resMap.put("service_id", service_id);
		resMap.put("user_type", user_type);

		userCache.setUserToken(String.valueOf(userId), token);
		resMap.put("token", token);
		resMap.put("user_id", userId);
		resMap.put("type", type);
		if (appName != null && !appName.equals("M2616_BD")) {
			resMap.put("role", role);
			resMap.put("nick", nick);
			resMap.put("app_name", app);
		}

		if (null != lastnum && !"".equals(lastnum))
			resMap.putAll(alarmHandle.list("" + userId, lastnum, "-1", null,
					appName));

		if (null != iosToken && !"".equals(iosToken)) {
			iosTokenCache.addIosToken(userId, iosToken);
		}
		resMap.put("allinchina", inchina(userId));
		return resMap;
	}
	
	
	/**
	 * 登陆
	 */
	public Map<String, Object> saveLogon(String name, String pwd, String ts,
			String lastnum, String duid, String iosToken, String appName,
			String ip) {
		HttpUtil.validateNull(new String[] { "name", "pwd", "ts" },
				new String[] { name, pwd, ts });
		HttpUtil.validateLong(new String[] { "ts" }, new String[] { ts });
		Long tsLong = HttpUtil.getLong(ts);
		Map<String, Object> resMap = new HashMap<String, Object>();
		User entity = null;
		try {
		    if (appName == null || (appName != null && appName.equals("M2616_BD"))) {
                entity = userService.findByName(name);
            } else {
                entity = userService.findByName(name, appName);
            }
		    
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		   
		if (null == entity) {// ret特殊
			resMap.put("ret", 3);
			resMap.put("desc",
					UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
			return resMap;
		}
		String dbOldPwdTs = SpecialStrUtil
				.getMd5(entity.getPassword() + tsLong);
		Assert.equals(dbOldPwdTs, pwd,
				UsConstants.getI18nMessage(UsConstants.USER_PW_ERROR));

		Long userId = entity.getId();
		String token = userCache.getUserToken(String.valueOf(userId));
		Integer role = entity.getRole();
		String nick = entity.getNick();
		String app = entity.getAppName();
		Integer type = entity.getType();
		if (token == null) {
			token = SpecialStrUtil.getUuid();
		}

		userCache.setUserToken(String.valueOf(userId), token);
		resMap.put("token", token);
		resMap.put("user_id", userId);
		resMap.put("type", type);
		if (appName != null && !appName.equals("M2616_BD")) {
			resMap.put("role", role);
			resMap.put("nick", nick);
			resMap.put("app_name", app);
		}

		if (null != lastnum && !"".equals(lastnum))
			resMap.putAll(alarmHandle.list("" + userId, lastnum, "-1", null,
					appName));

		if (null != iosToken && !"".equals(iosToken)) {
			iosTokenCache.addIosToken(userId, iosToken);
		}
		try {
			resMap.put("allinchina", inchina(userId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 保存到登录表
		// Login l = new Login();
		// l.setTime(new Date().getTime());
		// l.setUserId(userId);
		// l.setServiceId(0L);
		// l.setIp(ip);
		// loginService.saveOrUpdate(l);

		return resMap;
	}
	
	

	/**
	 * portal登录
	 */
	public Map<String, Object> saveBackLogon(String name, String pwd, String ts) {
		HttpUtil.validateNull(new String[] { "name", "pwd", "ts" },
				new String[] { name, pwd, ts });
		HttpUtil.validateLong(new String[] { "ts" }, new String[] { ts });
		Long tsLong = HttpUtil.getLong(ts);
		Map<String, Object> resMap = new HashMap<String, Object>();
		User entity = userService.findByName(name);

		if (null == entity) {
			resMap.put("ret", 3);
			resMap.put("desc",
					UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
			return resMap;
		}
		String dbOldPwdTs = SpecialStrUtil
				.getMd5(entity.getPassword() + tsLong);
		Assert.equals(dbOldPwdTs, pwd,
				UsConstants.getI18nMessage(UsConstants.USER_PW_ERROR));

		Long userId = entity.getId();
		String nick = entity.getNick();
		String app = entity.getAppName();
		String api_key = entity.getApiKey();
		Integer user_type = entity.getUserType();
		Integer user_mode = entity.getUserMode();

		String token = userCache.getUserToken(String.valueOf(userId));
		if (token == null) {
			token = SpecialStrUtil.getUuid();
		}
		userCache.setUserToken(String.valueOf(userId), token);

		resMap.put("token", token);
		resMap.put("user_id", userId);
		resMap.put("nick", nick);
		resMap.put("app_name", app);
		resMap.put("user_mode", user_mode);
		resMap.put("user_type", user_type);
		resMap.put("api_key", api_key);

		return resMap;
	}

	private boolean inchina(Long user_id) {
		Map map = this.positionHandle.getLasts(user_id + "", null, null, "-1",
				"-1");
		List<Position> ps = (List) map.get("statuss");
		boolean inchina = true;
		if ((ps != null)) {

			for (Position p : ps) {
				inchina = GeoMapUtil.IsInsideChina(new GeoCoordinate(p.getLng()
						.doubleValue(), p.getLat().doubleValue()));
				if (!inchina) {
					break;
				}
			}
		}
		return inchina;
	}

	/**
	 * 查询用户名是否可用(返回1为可用)
	 */
	public Map<String, Object> checkName(String name) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		HttpUtil.validateNull(new String[] { "username" },
				new String[] { name });
		User user = userService.findByName(name);
		Assert.isNull(user, MessageManager.UNAVAILABLE());
		resMap.put("desc", MessageManager.AVAILABLE());
		return resMap;
	}

	/**
	 * 更新密码
	 */
	public Map<String, Object> updatePwd(String userIdStr, String oldPwd,
			String newPwd, String ts) {

		HttpUtil.validateNull(new String[] { "user_id", "oldPwd", "newPwd" },
				new String[] { userIdStr, oldPwd, newPwd });
		HttpUtil.validateLong(new String[] { "ts", "user_id" }, new String[] {
				ts, userIdStr });

		Long userId = HttpUtil.getLong(userIdStr);

		User entity = userService.get(userId);
		Assert.notNull(entity,
				UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));

		// 旧密码是否正确
		String dbOldPwd = entity.getPassword();
		String dbOldPwdTs = SpecialStrUtil.getMd5(dbOldPwd
				+ HttpUtil.getLong(ts));
		Assert.equals(dbOldPwdTs, oldPwd,
				UsConstants.getI18nMessage(UsConstants.USER_PW_ERROR));

		// 设置新密码
		String pwd = SpecialStrUtil.getMd5(newPwd);
		entity.setPassword(pwd);

		userService.saveOrUpdate(entity);

		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("user_id", entity.getId());
		rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());
		return rs;
	}

	/**
	 * 更改昵称
	 */
	public Map<String, Object> updateNick(String userIdStr, String nick) {
		HttpUtil.validateNull(new String[] { "user_id" },
				new String[] { userIdStr });
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });
		Long userId = HttpUtil.getLong(userIdStr);

		User entity = userService.get(userId);
		Assert.notNull(entity,
				UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));

		entity.setNick(nick);

		userService.saveOrUpdate(entity);

		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("user_id", entity.getId());
		rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());
		return rs;
	}

	/**
	 * 忘记密码
	 */
	public Map<String, Object> forgetPwd(String userName, String pwdToken,
			String pwd) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpUtil.validateNull(new String[] { "userName" },
				new String[] { userName });
		User entity = userService.findByName(userName);
		String email = entity.getEmail();
		if (null == pwdToken || "".equals(pwdToken)) {
			pwdToken = SpecialStrUtil.getUuid();
			userCache.setPwdToken(userName, pwdToken);
			SendMail sm = new SendMail(mailManage, userName, email, pwdToken);
			sm.start();
			rs.put(Logic.DESC, "找回密码邮件已发送,请登陆邮箱进行后续操作");
			return rs;
		} else {
			HttpUtil.validateNull(new String[] { "pwd" }, new String[] { pwd });
			String pwdTokenMem = userCache.getPwdToken(userName);
			if (pwdToken.equals(pwdTokenMem)) {
				String password = SpecialStrUtil.getMd5(pwd);
				entity.setPassword(password);
				userService.saveOrUpdate(entity);
				rs.put(Logic.DESC, "修改成功");
			} else {
				rs.put(Logic.DESC, "过期");
			}
			return rs;
		}
	}

	/**
	 * 获取用户目录树
	 */
	public Map<String, Object> getUserTree(String userId, String appName) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpUtil.validateNull(new String[] { "user_id", "app_name" },
				new String[] { userId, appName });
		Long userIdLong = HttpUtil.getLong(userId);
		List<User> list = userService.getUserDown(userIdLong, appName);
		rs.put("user_tree", list);
		return rs;
	}

	/**
	 * 企业用户新增、修改、删除
	 * 
	 * @return
	 */
	public Map<String, Object> editUser(String userId, String username,
			String email, String pwd, String phone, String parentId,
			String role, String nick, String appName, String real,
			String address, String operate, String targetId, String type,
			String ts) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpUtil.validateNull(new String[] { "operate" },
				new String[] { operate });
		Integer operateInt = HttpUtil.getInt(operate);
		String regex = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";

		if (operateInt == 1) {// 注册
			HttpUtil.validateNull(
					new String[] { "username", "pwd", "parent_id", "role",
							"nick", "app_name", "type", "user_id" },
					new String[] { username, pwd, parentId, role, nick,
							appName, type, userId });

			Long parentIdLong = HttpUtil.getLong(parentId);
			Integer roleInt = HttpUtil.getInt(role);
			Integer typeInt = HttpUtil.getInt(type);

			User userTmp = userService.findByName(username);
			Assert.isNull(userTmp,
					UsConstants.getI18nMessage(UsConstants.USER_REGISTERED));

			// Assert.isTrue(userService.isUnique("phone", phone, null),
			// UsConstants.PHONE_REGISTERED);
			if (email != null) {
				Assert.isTrue(email.matches(regex),
						MessageManager.MAIL_FORMAT_INCORRECT());
				Assert.isTrue(userService.isUnique("email", email, null),
						UsConstants
								.getI18nMessage(UsConstants.EMAIL_REGISTERED));
			}
			User user = new User();
			user.setAddress(address);
			// user.setAppName(appName);
			user.setEmail(email);
			user.setName(username);
			user.setNick(nick);
			user.setPassword(SpecialStrUtil.getMd5(pwd));
			user.setPhone(phone);
			user.setReal(real);
			user.setParentId(parentIdLong);
			user.setRole(roleInt);
			user.setType(typeInt);
			user.setTime(Calendar.getInstance().getTimeInMillis());

			Long user_id = HttpUtil.getLong(userId);
			User en = userService.get(user_id);

			user.setAppName(en.getAppName());
			user.setServiceId(en.getServiceId());
			user.setPlatform(2);// 前台用户

			userService.saveOrUpdate(user);
			rs.put("user", user);

		} else if (operateInt == 2) {// 修改
			User user = new User();
			user.setAddress(address);
			user.setNick(nick);
			user.setReal(real);
			HttpUtil.validateNull(new String[] { "user_id", "target_id",
					"app_name", "pwd", "ts" }, new String[] { userId, targetId,
					appName, pwd, ts });

			Long userIdLong = HttpUtil.getLong(userId);
			Long targetIdLong = HttpUtil.getLong(targetId);
			Long tsLong = HttpUtil.getLong(ts);
			User admin = userService.get(userIdLong);
			Assert.notNull(admin,
					UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
			String dbOldPwdTs = SpecialStrUtil.getMd5(admin.getPassword()
					+ tsLong);
			Assert.equals(dbOldPwdTs, pwd,
					UsConstants.getI18nMessage(UsConstants.USER_PW_ERROR));

			User entity = userService.get(targetIdLong);
			Assert.notNull(entity,
					UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
			Assert.isTrue(
					userService.isAuthorized(userIdLong, targetIdLong, appName),
					UsConstants.getI18nMessage(UsConstants.USER_DENIED));

			if (null != email) {
				Assert.isTrue(email.matches(regex),
						MessageManager.MAIL_FORMAT_INCORRECT());
				Assert.isTrue(userService.isUnique("email", email, userIdLong),
						UsConstants
								.getI18nMessage(UsConstants.EMAIL_REGISTERED));
				user.setEmail(email);
			}
			if (null != phone) {

				user.setPhone(phone);
			}
			if (role != null) {
				Integer roleInt = HttpUtil.getInt(role);
				user.setRole(roleInt);
			}
			BeanUtils.copyProperties(user, entity,
					EntityUtil.nullField(User.class, user));
			userService.saveOrUpdate(entity);
			rs.put("user", entity);

		} else if (operateInt == 3) {// 删除

			HttpUtil.validateNull(new String[] { "user_id", "target_id",
					"app_name", "pwd", "ts" }, new String[] { userId, targetId,
					appName, pwd, ts });

			Long userIdLong = HttpUtil.getLong(userId);
			Long targetIdLong = HttpUtil.getLong(targetId);

			Long tsLong = HttpUtil.getLong(ts);
			User admin = userService.get(userIdLong);
			Assert.notNull(admin,
					UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
			String dbOldPwdTs = SpecialStrUtil.getMd5(admin.getPassword()
					+ tsLong);
			Assert.equals(dbOldPwdTs, pwd,
					UsConstants.getI18nMessage(UsConstants.USER_PW_ERROR));
			Assert.isTrue(userIdLong.longValue() != targetIdLong.longValue(),
					UsConstants.getI18nMessage(UsConstants.USER_DENIED));
			Assert.isTrue(
					userService.isAuthorized(userIdLong, targetIdLong, appName),
					UsConstants.getI18nMessage(UsConstants.USER_DENIED));
			userService.delete(targetIdLong);
		}
		return rs;
	}

	public Map<String, Object> setPwd(String userIdStr, String pwd, String ts,
			String targetIdStr, String targetPwd, String appName) {
		Map<String, Object> rs = new HashMap<String, Object>();

		HttpUtil.validateNull(new String[] { "user_id", "target_id",
				"app_name", "pwd", "ts" }, new String[] { userIdStr,
				targetIdStr, appName, pwd, ts });

		Long userIdLong = HttpUtil.getLong(userIdStr);
		Long targetIdLong = HttpUtil.getLong(targetIdStr);

		Long tsLong = HttpUtil.getLong(ts);
		User admin = userService.get(userIdLong);
		Assert.notNull(admin,
				UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
		String dbOldPwdTs = SpecialStrUtil.getMd5(admin.getPassword() + tsLong);
		Assert.equals(dbOldPwdTs, pwd,
				UsConstants.getI18nMessage(UsConstants.USER_PW_ERROR));
		Assert.isTrue(
				userService.isAuthorized(userIdLong, targetIdLong, appName),
				UsConstants.getI18nMessage(UsConstants.USER_DENIED));

		User target = userService.get(targetIdLong);
		Assert.notNull(target,
				UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
		if (targetPwd == null)
			targetPwd = SpecialStrUtil.getMd5("abc123");
		target.setPassword(SpecialStrUtil.getMd5(targetPwd));
		userService.saveOrUpdate(target);
		rs.put("user", target);
		return rs;
	}

	/**
	 * 认证
	 */
	public boolean authToken(String userIdStr, String token) {
		HttpUtil.validateNull(new String[] { "token", "user_id" },
				new String[] { token, userIdStr });
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });
		Long userId = HttpUtil.getLong(userIdStr);
		String tokenSys = userCache.getUserToken(String.valueOf(userId));
		logger.debug("Cache.getUserToken:" + tokenSys + ",param token:" + token);
		return token.equals(tokenSys);
	}

	public boolean authToken(int cmdInt, String userIdStr, String token) {
		int[] notAuth = new int[] { 1, 2, 3, 5, 10, 17, 21, 22, 23, 25, 32, 34,
				111 };
		if (ArrayUtils.contains(notAuth, cmdInt)) {
			return true;
		} else {
			return authToken(userIdStr, token);
		}
	}

	/**
	 * 设置用户模式(开发者模式or后台模式)
	 */
	public Map<String, Object> setUserMode(String userIdStr, String user_mode) {

		Map<String, Object> rs = new HashMap<String, Object>();

		HttpUtil.validateNull(new String[] { "user_id", "user_mode" },
				new String[] { userIdStr, user_mode });
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });
		HttpUtil.validateIntScope(user_mode, 1, 2);

		Long userId = HttpUtil.getLong(userIdStr);
		Integer modeId = HttpUtil.getInt(user_mode);

		User user = userService.get(userId);
		Assert.notNull(user,
				UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
		user.setUserMode(modeId);
		userService.saveOrUpdate(user);

		rs.put("user_id", user.getId());
		rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());

		return rs;

	}

	/**
	 * 设置用户种类(企业or个人)
	 */
	public Map<String, Object> setUserType(String userIdStr, String user_type) {

		Map<String, Object> rs = new HashMap<String, Object>();

		HttpUtil.validateNull(new String[] { "user_id", "user_type" },
				new String[] { userIdStr, user_type });
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });
		HttpUtil.validateIntScope(user_type, 1, 2);

		Long userId = HttpUtil.getLong(userIdStr);
		Integer typeId = HttpUtil.getInt(user_type);

		User user = userService.get(userId);
		Assert.notNull(user,
				UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));

		user.setUserType(typeId);

		if (typeId == 1) {// 企业网站
			user.setType(1);// 企业用户
			user.setRole(1);// 管理员
		}
		userService.saveOrUpdate(user);

		rs.put("user_id", user.getId());
		rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());

		return rs;
	}

	/**
	 * 开发者模式中，设置用户apikey
	 */
	public Map<String, Object> setUserApiKey(String userIdStr, String api_key) {

		Map<String, Object> rs = new HashMap<String, Object>();

		HttpUtil.validateNull(new String[] { "user_id", "api_key" },
				new String[] { userIdStr, api_key });
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });

		Long userId = HttpUtil.getLong(userIdStr);

		User user = userService.get(userId);
		Assert.notNull(user,
				UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));
		user.setApiKey(api_key);
		userService.saveOrUpdate(user);

		rs.put("user_id", user.getId());
		rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());

		return rs;
	}

	public Map<String, Object> getUserIdByName(String name) {

		Map<String, Object> rs = new HashMap<String, Object>();

		HttpUtil.validateNull(new String[] { "name" }, new String[] { name });

		User user = userService.findByName(name);
		Assert.notNull(user,
				UsConstants.getI18nMessage(UsConstants.USER_NOT_EXIST));

		rs.put("user_id", user.getId());
		return rs;
	}

}
