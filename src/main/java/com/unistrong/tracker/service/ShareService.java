/**
 * 
 */
package com.unistrong.tracker.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.ShareDao;
import com.unistrong.tracker.model.Share;

/**
 * @author fss
 *
 */
@Service
public class ShareService {

	@Resource
	private ShareDao shareDao;
	
	public Long save(Share share) {
		return shareDao.save(share);
	}

	public void saveOrUpdate(Share share) {
		shareDao.save(share);
	}
	
	public void update(Share share) {
		shareDao.update(share);
	}

	public void delete(Share share) {
		shareDao.deleteObject(share);
	}
	
	public Share get(Long id) {
		return shareDao.get(id);
	}
}
