package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.HttpUtil;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.ApiDoc;
import com.unistrong.tracker.model.ApiDocDetail;
import com.unistrong.tracker.model.ApiModel;
import com.unistrong.tracker.service.ApiService;

/**
 * 
 * @author zhangxianpeng
 * 
 */
@Component
public class ApiHandle {

	@Resource
	private ApiService apiService;

	// *****************************************

	public Map<String, Object> getAllApi(String userIdStr,
			String pageNumberStr, String pageSizeStr) {

		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });

		int pageNumber = HttpUtil.getInt(pageNumberStr, -1);
		int pageSize = HttpUtil.getInt(pageSizeStr, -1);

		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("apis", apiService.getApis(pageNumber, pageSize));
		return rs;
	}

	public Map<String, Object> getAllDocs(String userIdStr,
			String pageNumberStr, String pageSizeStr) {

		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });

		int pageNumber = HttpUtil.getInt(pageNumberStr, -1);
		int pageSize = HttpUtil.getInt(pageSizeStr, -1);

		Map<String, Object> rs = new HashMap<String, Object>();

		Map<Long, String> models = apiService
				.getModelsMap(pageNumber, pageSize);
		List<ApiDoc> docs = apiService.getDocs(pageNumber, pageSize);

		
		Map<String, List<ApiDoc>> map = new HashMap<String, List<ApiDoc>>();
		String last_key = null;
		List<ApiDoc> last_list = null;

		if (docs != null && docs.size() > 0) {
			for (ApiDoc doc : docs) {
				String key = models.get(doc.getModelId());
				if (last_key == null || !last_key.equalsIgnoreCase(key)) {
					last_list = new ArrayList<ApiDoc>();
					last_key = key;
					map.put(key, last_list);
				}
				last_list.add(doc);
			}
		}

		rs.put("apis", map);
		return rs;
	}

	public Map<String, Object> getAllDocsDetail(String userIdStr,
			String pageNumberStr, String pageSizeStr, String docIdStr) {

		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });

		HttpUtil.validateLong(new String[] { "doc_id" },
				new String[] { docIdStr });

		int doc_id = Integer.valueOf(docIdStr);

		int pageNumber = HttpUtil.getInt(pageNumberStr, -1);
		int pageSize = HttpUtil.getInt(pageSizeStr, -1);

		Map<String, Object> rs = new HashMap<String, Object>();

		List<ApiDocDetail> request_param = apiService.getDocsDetail(doc_id, 1,
				pageNumber, pageSize);
		List<ApiDocDetail> return_desc = apiService.getDocsDetail(doc_id, 2,
				pageNumber, pageSize);
		List<ApiDocDetail> return_json = apiService.getDocsDetail(doc_id, 3,
				pageNumber, pageSize);

		rs.put("request_param", request_param);
		rs.put("return_desc", return_desc);
		rs.put("return_json", return_json);

		return rs;
	}
}
