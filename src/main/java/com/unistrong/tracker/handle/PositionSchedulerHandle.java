package com.unistrong.tracker.handle;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.PositionService;

/**
 * @author fyq
 */
@Component
public class PositionSchedulerHandle {

	@Resource
	private PositionService positionService;

	@Resource
	private DeviceService deviceService;

	public void fixed() {
		List<String> deviceSns = deviceService.findAllSn();
		for (String deviceSn : deviceSns) {
			Position position = positionService.getOrNull(deviceSn);
			if (null != position)
				positionService.saveOrUpdate(position);
		}
	}

	public void unFixed() {
	}

}
