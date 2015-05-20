package com.unistrong.tracker.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.util.AbsolutePath;
import module.util.Assert;
import module.util.JsonUtils;
import module.util.ResponseUtils;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.unistrong.tracker.entry.context.EntryMaping;
import com.unistrong.tracker.handle.i18n.MessageManager;
import com.unistrong.tracker.handle.manage.MailConf;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.Protocol;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.util.ImageUtil;
import com.unistrong.tracker.util.Logic;
import com.unistrong.tracker.util.UsConstants;
import com.unistrong.tracker.util.upload.Html5Upload;
import com.unistrong.tracker.util.upload.NormalFormUpload;
import com.unistrong.tracker.util.upload.UploadImageInterface;

@Controller
@RequestMapping(value = "/image")
public class ImageController {

	private static Logger logger = LoggerFactory
			.getLogger(ImageController.class);

	@Resource
	private EntryMaping entryMaping;

	@Resource
	private MailConf mailConf;

	@Resource
	private DeviceService deviceService;

	private final long sizeMax = 4194304;

	/** 修改头像尺寸 */
	@RequestMapping(value = "/cut.do")
	public Map<String, Object> cutImg(HttpServletRequest request,
			String device_sn, Long user_id, String cutParam,
			HttpServletResponse response) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		Device entity = deviceService.findBySnAndUser(device_sn, user_id);
		Assert.notNull(entity,
				UsConstants.getI18nMessage(UsConstants.USER_NOTDEVICE));
		Long stamp = new Date().getTime();
		entity.setStamp(stamp);
		int[] cut = null;// 裁剪的参数
		if (!StringUtils.isBlank(cutParam)) {
			String[] cutArry = cutParam.split(",");
			if (cutArry.length == 4) {
				cut = new int[4];
				for (int i = 0; i < cutArry.length; i++) {
					cut[i] = Integer.valueOf(cutArry[i]);
				}
			}
		}

		File iconFile = new File(mailConf.getIcon() + entity.getIcon());
		if (!iconFile.exists()) {
			logger.error(iconFile.getAbsolutePath() + "不存在");
			rs.put(Logic.RET, 2);
			rs.put(Logic.DESC, "图片不存在");
		} else {
			String ret = ImageUtil.cutImage(iconFile.getAbsolutePath(), cut[0],
					cut[1], cut[2], cut[3]);
			rs.put("device_sn", device_sn);
			if (ret == null) {
				deviceService.saveOrUpdate(entity);
				rs.put("stamp", stamp);
				rs.put("icon", entity.getIcon());
				rs.put(Logic.RET, 1);
				rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());
			} else {
				rs.put(Logic.RET, 2);
				rs.put(Logic.DESC, ret);
			}
		}

		return rs;
	}

	/** 设备选择系统内置头像 */
	@RequestMapping(value = "/updateDeviceIcon.do")
	public Map<String, Object> updateDeviceIcon(HttpServletRequest request,
			String device_sn, Long user_id, String icon) {
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			Device entity = deviceService.findBySnAndUser(device_sn, user_id);
			Assert.notNull(entity,
					UsConstants.getI18nMessage(UsConstants.USER_NOTDEVICE));
			String root = mailConf.getIcon();
			File iconFile = new File(root + icon);
			if (!iconFile.exists()) {
				throw new Exception("文件名有误，服务器上不存在");
			}
			Long stamp = new Date().getTime();
			entity.setStamp(stamp);
			entity.setIcon(icon);
			deviceService.saveOrUpdate(entity);
			rs.put("stamp", stamp);
			rs.put("icon", entity.getIcon());
			rs.put(Logic.RET, 1);
			rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());
		} catch (Exception e) {
			rs.put(Logic.RET, 2);
			rs.put(Logic.DESC, e.getMessage());
		}
		return rs;
	}

	/** 设置设备头像 */
	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();

		Protocol protocol = new Protocol();
		try {
			String root = mailConf.getIcon();// this.uploadInit()+ "/";
			String contentType = request.getContentType();
			UploadImageInterface upload = null;
			if (contentType.equals("application/octet-stream")) {// html5、flash
				upload = new Html5Upload(request, response, sizeMax, root);
			} else {// 表单
				upload = new NormalFormUpload(request, response, sizeMax, root);
			}

			Long user_id = upload.getUserId();
			Device entity = deviceService.findBySnAndUser(upload.getDeviceSn(),
					user_id);

			Assert.notNull(entity,
					UsConstants.getI18nMessage(UsConstants.USER_NOTDEVICE));
			upload.writeFile();
			entity.setIcon(upload.getIcon());
			Long stamp = new Date().getTime();
			entity.setStamp(stamp);
			deviceService.saveOrUpdate(entity);
			rs.put("device_sn", entity.getSn());
			rs.put("stamp", stamp);
			rs.put("icon", upload.getIcon());
			rs.put(Logic.RET, 1);
			rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());
		} catch (SizeLimitExceededException e) {
			rs.put(Logic.RET, 2);
			rs.put(Logic.DESC, "文件不能超过" + sizeMax / 1024 / 1024 + "M");
		} catch (Exception e) {
			rs.put(Logic.RET, 2);
			rs.put(Logic.DESC, e.getMessage());
		}
		protocol.setTarget(1);
		protocol.setDuid("1");
		// protocol.setUserId("");
		protocol.setToken("1");
		List<Map<String, Object>> cmdList = new ArrayList<Map<String, Object>>();
		rs.put("cmd", 16);
		cmdList.add(rs);
		protocol.setCmdList(cmdList);
		logger.info(JsonUtils.obj2Str(protocol));
		String jsonString = JsonUtils.getJson(protocol);
		ResponseUtils.renderText(response, jsonString);// 兼容ie
	}

	/** 文件保存位置,不存在则新建 */
	public File uploadInit() {
		File dirFile = new File(AbsolutePath.absolutePath("../../upload"));
		if (!dirFile.exists())
			dirFile.mkdirs();
		return dirFile;
	}

}
