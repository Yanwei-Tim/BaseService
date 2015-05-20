/**
 * 
 */
package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import module.util.Assert;
import module.util.HttpUtil;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.manual.Config;
import com.unistrong.tracker.handle.util.SpotDo;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.model.Share;
import com.unistrong.tracker.model.Spot;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.PositionService;
import com.unistrong.tracker.service.ShareService;
import com.unistrong.tracker.service.SpotService;
import com.unistrong.tracker.util.UsConstants;

/**
 * @author fss
 * 
 */
@Component
public class ShareHandle {

	@Resource
	private ShareService shareService;

	@Resource
	private SpotService spotService;

	@Resource
	private PositionService positionService;

	@Resource
	private DeviceService deviceService;
	
	@Resource
	private Config config;

	public Map<String, Object> saveShare(String userIdStr, Set<String> deviceSns, String begin,
			String end, String privacyType, String publish, String expire, String contentTypeStr,
			String actStr, String mapType) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpUtil.validateNull(new String[] { "content_type" }, new String[] { contentTypeStr });
		Long userId = HttpUtil.getLong(userIdStr);
		Long beginLong = HttpUtil.getLong(begin, null);
		Long endLong = HttpUtil.getLong(end, null);
		Integer privacyTypeInt = HttpUtil.getInt(privacyType, 1);// 1：公开（微博、微信）；2：所有好友；3：部分好友
		Long publishLong = HttpUtil.getLong(publish, Calendar.getInstance().getTimeInMillis());
		Long actLong = HttpUtil.getLong(actStr, Calendar.getInstance().getTimeInMillis());
		Long expireLong = HttpUtil.getLong(expire, Long.MAX_VALUE);
		Integer contentType = HttpUtil.getInt(contentTypeStr, 1);// 1：位置；2：轨迹
		//HttpUtil.validateNull(deviceSns, new String[] { "device_sns" });
		Assert.notNull(deviceSns,  UsConstants.getI18nMessage(UsConstants.NOT_NULL));
		if(mapType == null || mapType.equals(""))
			mapType="BMap";
		String sns = "";
		for (String deviceSn : deviceSns) {

			sns += deviceSn + ",";
		}

		Share share = new Share();
		share.setUserId(userId);
		share.setDeviceSn(sns.substring(0, sns.length() - 1));
		share.setBegin(beginLong);
		share.setEnd(endLong);
		share.setPrivacyType(privacyTypeInt);
		share.setPublish(publishLong);
		share.setAct(actLong);
		share.setExpire(expireLong);
		share.setContentType(contentType);
		share.setMapType(mapType);
		if (contentType == 1) {
			String locationTime = "";
			for (String deviceSn : deviceSns) {
				Position pos = positionService.get(deviceSn);
				if (pos != null)
					locationTime += pos.getReceive() + ",";
			}
			share.setLocationTime(locationTime.substring(0, locationTime.length() - 1));
		}

		Long shareId = shareService.save(share);
		rs.put("url", config.getShare()+"?share_id=" + shareId + "/");

		return rs;
	}

	public Map<String, Object> getTrackByShare(String shareIdStr, String userIdStr) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpUtil.validateNull(new String[] { "share_id" }, new String[] { shareIdStr });
		HttpUtil.validateLong(new String[] { "share_id" }, new String[] { shareIdStr });
		Long shareId = HttpUtil.getLong(shareIdStr);
		Share share = shareService.get(shareId);
		Assert.notNull(share,  UsConstants.getI18nMessage(UsConstants.NOT_NULL));
		Calendar calendar = Calendar.getInstance();
		Long now = calendar.getTimeInMillis();

		if (now < share.getExpire()) {
			if (share.getPrivacyType() == 1) {// 公开的
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				if (share.getContentType() == 1) {// 位置
					String[] deviceSns = share.getDeviceSn().split(",");
					String[] locationTime = share.getLocationTime().split(",");
					for (int i = 0; i < deviceSns.length; i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						String deviceSn = deviceSns[i];
						Device device = deviceService.get(deviceSn);
						Assert.notNull(device,  UsConstants.getI18nMessage(UsConstants.USER_NOTDEVICE));
						String name = device.getName();
						Long receive = HttpUtil.getLong(locationTime[i], 0l);
						Position pos = positionService.get(deviceSn);
						Spot spot = new Spot();
						if (pos != null && pos.getReceive().equals(receive)) {
							SpotDo.form(spot, pos);
						} else {
							spot = spotService.getSpot(deviceSn, receive);
						}
						map.put("name", name);
						map.put("spot", spot);
						map.put("dt", device.getType());
						list.add(map);
					}
				} else if (share.getContentType() == 2) {// 轨迹
					List<Spot> spots = spotService.getTrack(share.getDeviceSn(), share.getBegin(), share.getEnd());

					Device device = deviceService.get(share.getDeviceSn());
					Assert.notNull(device,  UsConstants.getI18nMessage(UsConstants.USER_NOTDEVICE));
					String name = device.getName();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", name);
					map.put("spot", spots);
					map.put("dt", device.getType());
					list.add(map);
				}
				rs.put("type", share.getContentType());
				rs.put("content", list);
			} else {// 仅限好友
				rs.put("content", "要携带用户token噢！");
			}
		}
		return rs;
	}
}
