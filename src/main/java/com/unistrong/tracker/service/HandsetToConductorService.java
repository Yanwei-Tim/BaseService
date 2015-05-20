package com.unistrong.tracker.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.HandsetToConductorDao;
import com.unistrong.tracker.model.HandSetToConductor;

@Service
public class HandsetToConductorService {

	@Resource
	private HandsetToConductorDao handsetToConductorDao;


	public void save(HandSetToConductor hToConductor) {
		handsetToConductorDao.save(hToConductor);
	}

	public HandSetToConductor findById(long id) {
		return handsetToConductorDao.get(id);
	}

	public void deleteByHandSetSn(String sn) {
		handsetToConductorDao.deleteByHandsetId(sn);
	}
}
