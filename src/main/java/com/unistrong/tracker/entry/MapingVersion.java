/**
 * 
 */
package com.unistrong.tracker.entry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.NoticeHandle;
import com.unistrong.tracker.model.Protocol;

/**
 * @author fss
 * 
 */
@Component
public class MapingVersion {

	@Resource
	private NoticeHandle noticeHandle;

	public Map<String, Object> checkVersion(Map<String, Object> cmd,Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String versionCode = Opt.getByMap(cmd, "version");
		String platform = Opt.getByMap(cmd, "platform");
		
		rs.putAll(noticeHandle.read(protocol.getUserId(), platform));

		return rs;
	}
}
