/**
 * 
 */
package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.PetSportDao;
import com.unistrong.tracker.model.PetSport;

/**
 * @author fss
 * 
 */
@Service
public class PetSportService {

	@Resource
	private PetSportDao petSportDao;

	public void saveOrUpdate(PetSport petSport) {
		petSportDao.saveOrUpdate(petSport);
	}

	public void delete(PetSport petSport) {
		petSportDao.deleteObject(petSport);
	}

	public List<PetSport> getSportList(String deviceSn, Long begin, Long end) {
		List<PetSport> list = petSportDao.getSportList(deviceSn, begin, end);
		return list;
	}
}
