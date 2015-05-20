package com.unistrong.tracker.handle;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import module.util.Assert;
import module.util.HttpUtil;

import com.unistrong.tracker.handle.manual.StartAddDevice;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.User;
import com.unistrong.tracker.model.UserDevice;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.service.UserService;

@Component
public class BackHandle {

	@Resource
	private UserService userService;

	@Resource
	private DeviceService deviceService;

	@Resource
	private UserDeviceService userDeviceService;

	/**
	 * 入库绑定
	 */
	public Map<String, Object> toDbUser(String userName, String deviceSn, String type) {
		HttpUtil.validateNull(new String[] { "userName", "deviceSn" }, new String[] { userName,
				deviceSn });
		Map<String, Object> rs = new HashMap<String, Object>();
		Device device = deviceService.get(deviceSn);
		Assert.isNull(device, "请误重复入库");
		User entityUser = userService.findByName(userName);
		Assert.notNull(entityUser, "用户不存在");
		Device form = new Device(deviceSn);
		StartAddDevice.initDevice(form);
		deviceService.saveOrUpdate(form);
		UserDevice userDevice = new UserDevice();
		userDevice.setDeviceSn(deviceSn);
		userDevice.setUserId(entityUser.getId());
		userDeviceService.saveOrUpdate(userDevice);
		return rs;
	}

}
