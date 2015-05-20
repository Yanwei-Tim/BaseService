package com.unistrong.tracker.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.NoticeHandle;

@Controller
public class NoticeController {
	
	@Resource
	private NoticeHandle handle;
	
	@RequestMapping("/notice.do")
	public Map<String, Object> notice(String user_id){
		Map<String, Object> rs =handle.read(user_id,"web");
		rs.put("ret", 1);
		return rs;
	}
	
	
}
