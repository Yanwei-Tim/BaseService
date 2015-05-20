package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.InstructDao;
import com.unistrong.tracker.model.Instruct;
import com.unistrong.tracker.service.cache.PositionCache;

@Service
public class InstructService {

	@Resource
	private InstructDao instructDao;

	@Resource
	private PositionCache positionCache;

	public void saveOrUpdate(Instruct entity) {
		if (entity.getType () == 2001 ||entity.getContent().indexOf("\"type\":3") < 0) {// 多边形围栏不推送
			instructDao.saveOrUpdate(entity);
		}
		positionCache.setInstruct(entity.getDeviceSn());
	}

	public Instruct get(String id) {
		return instructDao.get(id);
	}

	public List<Instruct> find(String deviceSn) {
		return instructDao.find(deviceSn);
	}

	public void delete(String deviceSn) {
		instructDao.delete(deviceSn);
	}

    public Instruct getByType (String deviceSn, int type) {
        
        List <Instruct> instructs = instructDao.getByType(deviceSn,type);
        if (instructs == null || instructs.size () == 0) {
            return null;
        }else {
            return instructs.get (0);
        }
    }

}
