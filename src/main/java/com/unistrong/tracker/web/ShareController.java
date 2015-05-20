/**
 * 
 */
package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.ShareHandle;

/**
 * @author fss
 * 
 */
@Controller
public class ShareController {

	@Resource
	private ShareHandle shareHandle;

	@RequestMapping("/share.save.do")
	public Map<String, Object> saveShare(String user_id, String device_sns, String begin,
			String end, String privacy_type, String publish, String expire, String content_type,
			String act,String map_type) {
		Map<String, Object> rs = new HashMap<String, Object>();

		rs.put("ret", 1);
		Set<String> deviceSet = new HashSet<String>();
		String[] deviceSns = device_sns.split(","); 
		for(String deviceSn : deviceSns) {
			deviceSet.add(deviceSn);
		}
		rs.putAll(shareHandle.saveShare(user_id, deviceSet, begin, end, privacy_type, publish,
				expire, content_type, act, map_type));
		return rs;
	}

	@RequestMapping("/share.get.do")
	public Map<String, Object> getTrackByShare(String share_id, String user_id) {
		Map<String, Object> rs = new HashMap<String, Object>();

		rs.put("ret", 1);
		rs.putAll(shareHandle.getTrackByShare(share_id, user_id));
		return rs;
	}
}
