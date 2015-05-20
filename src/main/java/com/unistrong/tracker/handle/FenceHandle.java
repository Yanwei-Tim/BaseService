package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.Assert;
import module.util.HttpUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.i18n.MessageManager;
import com.unistrong.tracker.handle.util.InstructDb;
import com.unistrong.tracker.model.CountModel;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.DeviceFence;
import com.unistrong.tracker.model.Fence;
import com.unistrong.tracker.model.Fences;
import com.unistrong.tracker.model.IndoorFence;
import com.unistrong.tracker.model.IndoorSpotVo;
import com.unistrong.tracker.model.PetFence;
import com.unistrong.tracker.model.UserDevice;
import com.unistrong.tracker.model.serialize.SpotVo;
import com.unistrong.tracker.service.DeviceFenceInOutService;
import com.unistrong.tracker.service.DeviceFenceService;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.FenceService;
import com.unistrong.tracker.service.IndoorFenceService;
import com.unistrong.tracker.service.InstructService;
import com.unistrong.tracker.service.PositionService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.service.cache.PetFenceCache;
import com.unistrong.tracker.util.Logic;
import com.unistrong.tracker.util.UsConstants;

@Component
public class FenceHandle {

	@Resource
	private DeviceService deviceService;

	@Resource
	private FenceService fenceService;

	@Resource
	private InstructService instructService;

	@Resource
	private PositionService positionService;

	@Resource
	private PetFenceCache petFenceCache;

	@Resource
	private UserDeviceService userDeviceService;

	@Resource
	private DeviceFenceService deviceFenceService;

	@Resource
	private IndoorFenceService indoorFenceService;

	@Resource
	private DeviceFenceInOutService deviceFenceInOutService;

	/**
	 * 删除 新增 修改 围栏
	 */
	public Map<String, Object> fenceEdit(String operate,
			String userIdStr,
			String name, // TODO:name没有使用.
			SpotVo[] region, String deviceSn, String type, String radius,
			Double[] center, String out) {
		HttpUtil.validateNull(
				new String[] { "operate", "user_id", "device_sn" },
				new String[] { operate, userIdStr, deviceSn });
		Integer operateInt = HttpUtil.getInt(operate);
		Long userId = HttpUtil.getLong(userIdStr);

		UserDevice ud = userDeviceService.getBySnAndUser(deviceSn, userId);
		Assert.notNull(ud,
				UsConstants.getI18nMessage(UsConstants.USER_NOTDEVICE));

		if (3 == operateInt) {// 删除围栏
			return delete(userId, deviceSn);
		} else {// 新增或修改围栏
			HttpUtil.validateNull(new String[] { "type" },
					new String[] { type });
			Fence formFence = new Fence();
			formFence.setOut(HttpUtil.getInt(out, null));
			formFence.setType(HttpUtil.getInt(type));
			formFence.setCenter(center);
			formFence.setRadius(HttpUtil.getInt(radius, null));
			formFence.setRegion(region);
			return saveOrUpdate(userId, deviceSn, formFence);
		}
	}

	private Map<String, Object> saveOrUpdate(Long userId, String deviceSn,
			Fence formFence) {
		Map<String, Object> rs = new HashMap<String, Object>();

		Device entityDevice = deviceService.get(deviceSn);
		Fence entityFence = entityDevice.getFence();
		entityDevice.setFence(formFence);

		instructService.saveOrUpdate(InstructDb.fence(entityDevice, formFence,
				entityFence));
		deviceService.saveOrUpdate(entityDevice);
		rs.put("fence", formFence);
		rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());
		return rs;
	}

	/**
	 * CRUD:删除围栏
	 */
	private Map<String, Object> delete(Long userId, String deviceSn) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Device entityDevice = deviceService.get(deviceSn);
		entityDevice.setFence(null);
		deviceService.saveOrUpdate(entityDevice);
		petFenceCache.setPetFence(deviceSn, new PetFence());
		rs.put(Logic.DESC, MessageManager.DELETE_SUCCESS());
		return rs;
	}

	/**
	 * 添加 围栏
	 */
	public Map<String, Object> addFence(String userIdStr,
			IndoorSpotVo[] region, String deviceSn, String type, String radius,
			Double[] center, String out, String system, String indoorType) {

		HttpUtil.validateNull(new String[] { "user_id", "device_sn", "type",
				"indoor_type" }, new String[] { userIdStr, deviceSn, type,
				indoorType });

		Map<String, Object> rs = new HashMap<String, Object>();

		// 添加围栏
		IndoorFence formFence = new IndoorFence();
		formFence.setOut(HttpUtil.getInt(out, null));
		formFence.setType(HttpUtil.getInt(type));
		formFence.setCenter(center);
		formFence.setRadius(HttpUtil.getInt(radius, null));
		formFence.setRegion(region);

		Fences fence = new Fences();
		fence.setFence(formFence);
		fence.setSystem(system);
		fence.setType(HttpUtil.getInt(indoorType));

		Long fenceId = indoorFenceService.save(fence);

		if (!StringUtils.isBlank(deviceSn)) {
			// 添加围栏对应的设备
			DeviceFence devicefence = new DeviceFence();
			devicefence.setSn(deviceSn);
			devicefence.setFenceId(fenceId);
			devicefence.setFenceSwitch(1);
			deviceFenceService.saveOrUpdate(devicefence);
		}

		rs.put("fence", formFence);
		rs.put(Logic.DESC, MessageManager.UPDATE_SUCCESS());
		return rs;

	}

	/**
	 * 删除围栏
	 */
	public Map<String, Object> deleteFence(String fenceId) {
		Map<String, Object> rs = new HashMap<String, Object>();

		HttpUtil.validateNull(new String[] { "fence_id" },
				new String[] { fenceId });
		Long fence_id = HttpUtil.getLong(fenceId);

		indoorFenceService.deleteById(fence_id);
		deviceFenceService.deleteByFenceId(fence_id);

		rs.put(Logic.DESC, MessageManager.DELETE_SUCCESS());
		return rs;
	}

	/**
	 * 设备绑定围栏
	 */
	public Map<String, Object> bindFence(String fenceId, String deviceSn) {
		Map<String, Object> rs = new HashMap<String, Object>();

		HttpUtil.validateNull(new String[] { "fence_id", "device_sn" },
				new String[] { fenceId, deviceSn });

		Long fence_id = HttpUtil.getLong(fenceId);

		DeviceFence df = new DeviceFence();
		df.setFenceId(fence_id);
		df.setSn(deviceSn);
		df.setFenceSwitch(1);

		deviceFenceService.saveOrUpdate(df);

		rs.put(Logic.DESC, "绑定围栏成功!");
		return rs;
	}

	/**
	 * 进出围栏次数
	 */
	public Map<String, Object> getInOutCount(String deviceSn, String beginStr,
			String endStr, String typeStr) {
		Map<String, Object> rs = new HashMap<String, Object>();

		HttpUtil.validateNull(new String[] { "device_sn", "begin", "end",
				"type" }, new String[] { deviceSn, beginStr, endStr, typeStr });

		Long begin = HttpUtil.getLong(beginStr);
		Long end = HttpUtil.getLong(endStr);
		Integer type = HttpUtil.getInt(typeStr);

//		List<CountModel> list = deviceFenceInOutService.findBySnAndTime(
//				deviceSn, begin, end, type);
//		
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("device_sn", 1);
		map.put("fence_id", 1);
		map.put("fence_name", "机房");
		map.put("num", 3);
		
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("device_sn", 1);
		map2.put("fence_id", 2);
		map2.put("fence_name", "老人房");
		map2.put("num", 2);
		
		List<Map> listAll=new ArrayList<Map>();
		listAll.add(map);
		listAll.add(map2);
		
		rs.put("list", listAll);

		return rs;
	}

}
