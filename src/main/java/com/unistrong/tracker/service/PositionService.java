package com.unistrong.tracker.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.PositionDao;
import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.service.cache.PositionCache;

@Service
public class PositionService {

	@Resource
	private PositionCache positionCache;

	@Resource
	private PositionDao positionDao;

	public void saveOrUpdate(Position entity) {
		positionDao.saveOrUpdate(entity);
	}

	public Position findByDevice(String sn) {
		return positionDao.findByDevice(sn);
	}

	public List<Position> findAll() {
		return positionDao.listAll();
	}

	public void set(Position entity) {
		entity.setSystime(Calendar.getInstance().getTimeInMillis());
		if(entity.getReceive().equals(0)){//避免未传数据的设备时间显示1970
			entity.setReceive(entity.getSystime());
		}
		positionCache.setPosition(entity.getDeviceSn(), entity);
	}
	
	
	public Position get(String sn) {
		Position position = positionCache.getPosition(sn);
		if (null == position) {
			position = positionDao.findByDevice(sn);
			if (null == position) {
				position = new Position(sn);
				set(position);
			} else {// 新增:淘淘设备一直在线的bug处理
				Long receive = position.getReceive();
				// 259200000L=3天;3*24*60*60*1000
				if (System.currentTimeMillis() - receive > 259200000L) {
					position.setStatus(2);
				}
			}
		}else if(position.getReceive()==0){//避免未传数据的设备时间显示1970
			position.setReceive(position.getSystime());
		}
		return position;
	}
	
	public Position getOrNull(String sn) {
		return positionCache.getPosition(sn);
	}

}
