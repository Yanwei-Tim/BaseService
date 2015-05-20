package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.PetReportDao;
import com.unistrong.tracker.model.PetReport;

@Service
public class PetReportService {

	@Resource
	private PetReportDao petReportDao;

	public void saveOrUpdate(PetReport entity) {
		petReportDao.saveOrUpdate(entity);
	}

	public List<PetReport> find(String deviceSn, Long begin, Long end) {
		return petReportDao.find(deviceSn, begin, end);
	}

}
