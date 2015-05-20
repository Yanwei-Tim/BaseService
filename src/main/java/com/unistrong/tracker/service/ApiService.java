package com.unistrong.tracker.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.ApiDao;
import com.unistrong.tracker.dao.ApiDocDao;
import com.unistrong.tracker.dao.ApiDocDetailDao;
import com.unistrong.tracker.dao.ApiModelDao;
import com.unistrong.tracker.model.Api;
import com.unistrong.tracker.model.ApiDoc;
import com.unistrong.tracker.model.ApiDocDetail;
import com.unistrong.tracker.model.ApiModel;

/**
 * 
 * @author zhangxianpeng
 * 
 */
@Service
public class ApiService {

	@Resource
	private ApiDao apiDao;
	@Resource
	private ApiDocDao apiDocDao;
	@Resource
	private ApiModelDao apiModelDao;
	@Resource
	private ApiDocDetailDao apiDocDetailDao;

	public List<Api> getApis(int pageNumber, int pageSize) {
		return apiDao.getApis(pageNumber, pageSize);
	}

	public List<ApiDoc> getDocs(int pageNumber, int pageSize) {
		return apiDocDao.getDocs(pageNumber, pageSize);
	}

	public List<ApiDocDetail> getDocsDetail(int doc_id, int pageNumber,
			int pageSize) {
		return apiDocDetailDao.getDocsDetail(doc_id, pageNumber, pageSize);
	}

	public List<ApiDocDetail> getDocsDetail(int doc_id, int rtype,
			int pageNumber, int pageSize) {
		return apiDocDetailDao.getDocsDetail(doc_id, rtype, pageNumber,
				pageSize);
	}

	public List<ApiModel> getModels(int pageNumber, int pageSize) {
		return apiModelDao.getModels(pageNumber, pageSize);
	}

	public Map<Long, String> getModelsMap(int pageNumber, int pageSize) {
		Map<Long, String> m = new HashMap<Long, String>();
		List<ApiModel> list = getModels(pageNumber, pageSize);
		if (list != null && list.size() > 0) {
			for (ApiModel model : list) {
				m.put(model.getId(), model.getModelName());
			}
		}
		return m;
	}
}