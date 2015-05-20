/**
 * 
 */
package com.unistrong.tracker.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.ResearchHandle;

/**
 * @author fss
 *
 */
@Controller
public class ResearchController {
	
	@Resource
	private ResearchHandle researchHandle;
	
	@RequestMapping("/save.research.do")
	public Map<String, Object> saveResearch(String user_id, String answers){
		Map<String, Object> rs = new HashMap<String, Object>();
	
		rs.put("ret", 1);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> answerList = JsonUtils.str2Obj(answers, ArrayList.class);
		rs.putAll(researchHandle.saveResearch(user_id, answerList));
		
		return rs;
		
	}
	
	@RequestMapping("/get.research.do")
	public Map<String, Object> getResearch(String user_id){
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		rs.putAll(researchHandle.getResearch(user_id));
		
		return rs;
		
	}
}
