package com.unistrong.tracker.handle.manual;

import java.util.Date;

import module.util.DateUtil;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.service.DeviceService;

/**
 * @author fss
 */
public class StartAddDevice {

	public static Device initDevice(Device device) {
		
		device.setType(1);
		device.setIcon("1.png");
		device.setName(device.getSn());
		device.setStamp(-1L);
		device.setBegin(new Date().getTime());
		device.setEnd(DateUtil.getYesterday(365).getTime());

		device.setHardware("M2616");
		device.setCar("");
		device.setPhone("");
		device.setGender(1);
		device.setAge(20);
		device.setEmail("");
		device.setHeight(165f);
		device.setWeight(110f);

		device.setFenceSwitch(2);
		device.setMoveSwitch(2);
		device.setSpeedingSwitch(1);
		device.setTick(30);
		device.setSpeedThreshold(120);
		device.setBattery(80);
		device.setFlow(1F);
		device.setSosNum("");
		return device;
	}

	public static void addDevice(DeviceService deviceService) {
		Device entity = new Device("354188000000001");
		initDevice(entity);
		deviceService.saveOrUpdate(entity);
	}

	public static void addDeviceFor(DeviceService deviceService) {
		Long sn = 12345678901000L;
		for (int i = 0; i < 3; i++) {
			Device device = new Device(sn.toString());
			initDevice(device);
			deviceService.saveOrUpdate(device);
			sn += 1;
		}
	}

	public static void main(String[] args) {
		try {
			String path = "com/unistrong/tracker/handle/manual/serviseDao.xml";
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(path);
			DeviceService deviceService = (DeviceService) context.getBean("deviceService");
			addDevice(deviceService);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.exit(0);
	}

}
