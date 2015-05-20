package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.SpotDao;
import com.unistrong.tracker.model.Spot;

/**
 * @author fyq
 */
@Service
public class SpotService {

	@Resource
	private SpotDao spotDao;

	/**
	 * 新增或修改设备状态
	 */
	public void saveOrUpdate(Spot entity) {
		spotDao.saveOrUpdate(entity);
	}

	public List<Spot> getTrack(String sn, Long begin, Long end) {
		return spotDao.getTrack(sn, begin, end);
	}
	
	public List<Spot> getTrackGps(String sn, Long begin, Long end) {
		return spotDao.getTrackGps(sn, begin, end);
	}
	
	public Spot getSpot(String deviceSn, Long receive) {
		return spotDao.getSpot(deviceSn, receive);
	}
	
	public Spot getSpot(String deviceSn,boolean isAsc) {
		return spotDao.getSpot(deviceSn,isAsc);
	}
	
	public int deleteByDeviceSn(String deviceSn) {
		return spotDao.deleteByDeviceSn(deviceSn);
	}
	
	public Spot getBeginSpot(String deviceSn) {
		return spotDao.getBeginSpot(deviceSn);
	}
}
