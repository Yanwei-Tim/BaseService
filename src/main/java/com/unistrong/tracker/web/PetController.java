/**
 * 
 */
package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.PetHandle;

/**
 * @author fss
 * 
 */
@Controller
public class PetController {

	@Resource
	private PetHandle petHandle;

	@RequestMapping("/pet.statistic.do")
	public Map<String, Object> getSportStatic(String user_id, String device_sn, String begin,
			String end, String interval, String target_id, String app_name) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(petHandle.getSportStatic(user_id, device_sn, begin, end));
		return rs;
	}
}
