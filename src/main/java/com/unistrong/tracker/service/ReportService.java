package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.ReportDao;
import com.unistrong.tracker.model.Report;

@Service
public class ReportService {

	@Resource
	private ReportDao reportDao;

	public void saveOrUpdate(Report entity) {
		reportDao.saveOrUpdate(entity);
	}

	public List<Report> find(String deviceSn, Long begin, Long end) {
		return reportDao.find(deviceSn, begin, end);
	}

}
