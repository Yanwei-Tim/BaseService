package com.unistrong.tracker.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.model.Instruct;
import com.unistrong.tracker.model.OBD;
import com.unistrong.tracker.model.OBDError;
import com.unistrong.tracker.model.ObdErrorDictionary;
import com.unistrong.tracker.service.InstructService;
import com.unistrong.tracker.service.OBDService;
import com.unistrong.tracker.service.cache.OBDCache;

@Controller
public class OBDController {
	
	@Resource
	private OBDService service;
	@Resource
	private InstructService instructService;
	@Resource
	private OBDCache cache;
	
	
	@RequestMapping("/obd.last.do")
	public Map<String, Object> obd(String user_id, String device_sn) {
		
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		
		OBD obd=cache.getOBD(device_sn);
		if(obd!=null){
			obd.setSystime(new Date().getTime());
			service.save(obd);
		}
		rs.put("obd", obd);
		return rs;
		
	}
	
	
	@RequestMapping("/obderr.last.do")
	public Map<String, Object> obdErr(String user_id, String device_sn) {
		
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		Map<String, ObdErrorDictionary> errorMap=null;
		OBDError error=cache.getOBDErr(device_sn);
		if(error!=null){
			errorMap=new HashMap<String, ObdErrorDictionary>();
			for(String c:error.getCodes()){
				ObdErrorDictionary code=service.getErrorMapping(c);
				errorMap.put(c, code);
			}
		}
		
		rs.put("error", errorMap);
		return rs;
		
	}
	
	
	@RequestMapping("/obderr.mapping.do")
	public Map<String, Object> errMapping(String code) {
		
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		List<ObdErrorDictionary> li=new ArrayList<ObdErrorDictionary>();
		if(code.indexOf(",")!=-1){
			String[] codes=code.split(",");
			for(String c:codes){
				ObdErrorDictionary error=service.getErrorMapping(c);
				li.add(error);
			}
		}else{
			ObdErrorDictionary error=service.getErrorMapping(code);
			li.add(error);
		}
		
		rs.put("dictionary", li);
		return rs;
	}
	
	@RequestMapping("/obderr.clear.do")
	public Map<String, Object> clearErr(String user_id, String device_sn) {
		
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("ret", 1);
		cache.removeOBDErr(device_sn);
		//发送指令
		Instruct  instruct=new Instruct ();
		instruct.setDeviceSn(device_sn);
		instruct.setType(11);
		instruct.setReply(0);
		instruct.setId(device_sn + "-11" );
		instructService.saveOrUpdate(instruct);
		
		return rs;
		
	}
	
	
}
