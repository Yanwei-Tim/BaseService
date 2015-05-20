package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.ApiHandle;

/**
 * api信息
 * 
 * @author zhangxianpeng
 * 
 */
@Controller
public class ApiController {

	@Resource
	private ApiHandle apiHandle;

	@RequestMapping("/api.all.do")
	public Map<String, Object> getAllApi(String user_id, String page_number,
			String page_size) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(apiHandle.getAllApi(user_id, page_number, page_size));
		return rs;
	}

	@RequestMapping("/api.doc.do")
	public Map<String, Object> getAllDocsApi(String user_id,
			String page_number, String page_size) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(apiHandle.getAllDocs(user_id, page_number, page_size));
		return rs;
	}

	@RequestMapping("/api.doc.detail.do")
	public Map<String, Object> getAllDocsDetailApi(String user_id,
			String page_number, String page_size, String doc_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(apiHandle.getAllDocsDetail(user_id, page_number, page_size,
				doc_id));
		return rs;
	}
}
