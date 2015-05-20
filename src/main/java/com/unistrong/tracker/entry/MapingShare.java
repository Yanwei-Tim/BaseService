/**
 * 
 */
package com.unistrong.tracker.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.ShareHandle;
import com.unistrong.tracker.model.Protocol;

/**
 * @author fss
 * 
 */
@Component
public class MapingShare {

	@Resource
	private ShareHandle shareHandle;

	public Map<String, Object> saveShare(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		//String userId = Opt.getByMap(cmd, "user_id");
		@SuppressWarnings("unchecked")
		ArrayList<String> deviceSns = (ArrayList<String>)cmd.get("device_sns");
		//String[] sns = new String[deviceSns.size()];
		Set<String> deviceSet = new HashSet<String>();
		for(int i =0;i<deviceSns.size();i++) {
			String sn = deviceSns.get(i);
			if(sn != null)
				deviceSet.add(sn);
		}
		String begin = Opt.getByMap(cmd, "begin");
		String end = Opt.getByMap(cmd, "end");
		String privacyType = Opt.getByMap(cmd, "privacy_type");
		String publish = Opt.getByMap(cmd, "publish");
		String expire = Opt.getByMap(cmd, "expire");
		String contentType = Opt.getByMap(cmd, "content_type");
		String act = Opt.getByMap(cmd, "act");
		String mapType = Opt.getByMap(cmd, "map_type");
		rs.putAll(shareHandle.saveShare(protocol.getUserId(), deviceSet, begin, end, privacyType, publish, expire,
				contentType, act, mapType));

		return rs;

	}
}
