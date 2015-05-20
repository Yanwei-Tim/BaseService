package com.unistrong.tracker.entry.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.util.Opt;
import com.unistrong.tracker.handle.UserHandle;
import com.unistrong.tracker.model.Protocol;

/**
 * @author fyq
 */
@Component
public class EntryMaping {

	private static Logger logger = LoggerFactory.getLogger(EntryMaping.class);

	@Resource
	private UserHandle userHandle;

	@Resource
	private Maping maping;

	// ****************************************

	public Protocol operate(Protocol protocol) {
		List<Map<String, Object>> rss = new ArrayList<Map<String, Object>>();
		String userIdStr = protocol.getUserId();
		String token = protocol.getToken();
		for (Map<String, Object> cmd : protocol.getCmdList()) {
			Map<String, Object> rs = new HashMap<String, Object>();
			Integer cdmInt = -1;
			try {
				cdmInt = HttpUtil.getIntMsg(Opt.getByMap(cmd, "cmd"), "cmd必须为数字");
				rs.put("ret", 1);
				rs.put("desc", "success");
				rs.put("cmd", cdmInt);
				boolean tag = userHandle.authToken(cdmInt, userIdStr, token);
				if (!tag) {
					return Opt.getAuthProtocol(protocol, rs, rss);
				}
				rs.putAll(maping.cmdDo(cmd, cdmInt, protocol));
			} catch (Exception ex) {
				if (ex instanceof IllegalArgumentException) {
					rs.put("ret", 2);
					rs.put("desc", ex.getMessage());
				} else {
					logger.error("error:", ex);
					rs.put("ret", 2);
					// rs.put("desc", ex.getMessage());
					rs.put("desc", "服务异常");
				}
			}
			rss.add(rs);
			protocol.setCmdList(rss);
		}
		return protocol;
	}
}
