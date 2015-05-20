package com.unistrong.tracker.model;

import java.util.HashSet;
import java.util.Set;

public class OBDError {
	
	   private String deviceSn;
	   private Long receive = 0L;
		// 后台处理时间
	   private Long systime = 0L;
		
	   private Set<String> codes=new HashSet();
	   
	    public void add(String code){
	    	codes.add(code);
	    }
	    
		
		public Set<String> getCodes() {
			return codes;
		}
		
		
		
		

}
