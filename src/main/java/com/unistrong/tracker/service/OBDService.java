package com.unistrong.tracker.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.OBDDao;
import com.unistrong.tracker.dao.OBDErrorDictionaryDao;
import com.unistrong.tracker.model.OBD;
import com.unistrong.tracker.model.ObdErrorDictionary;

@Service
public class OBDService {
	@Resource
	private OBDDao dao;
	
	@Resource
	private OBDErrorDictionaryDao errorDictionayDao;
	
	private OBD getObd(String deviceSn, Long receive) {
		return dao.getOBD(deviceSn,  receive);
	}
	
	public ObdErrorDictionary getErrorMapping(String code){
		return errorDictionayDao.getErrorMapping(code);
	}
	
	public void save(OBD obd){
		if(getObd(obd.getDeviceSn(),obd.getReceive())==null){
			dao.save(obd);
		}
		
	}
	
	
}
