package com.unistrong.tracker.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	
	private static Map<String,Properties> files=new HashMap();
	private static PropertiesUtil _instnce;
	
	private PropertiesUtil(){
	}
	
	public static PropertiesUtil getInstance(){
		if(_instnce==null){
			_instnce=new PropertiesUtil();
		}
		return 	_instnce;
	}
	
	public Properties getProperties(String filename){
		Properties ps=files.get(filename);
		if(ps!=null){
			return ps;
		}
	    InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(filename+".properties");  
	    ps = new Properties();  
	    //加载properties资源文件  
	    try {
			ps.load(is);
			files.put(filename, ps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return ps;
	}
	
    
}
