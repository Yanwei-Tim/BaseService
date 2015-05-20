/**
 * 
 */
package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.Assert;
import module.util.HttpUtil;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Research;
import com.unistrong.tracker.model.ResearchAnswers;
import com.unistrong.tracker.model.ResearchTopic;
import com.unistrong.tracker.service.ResearchAnswersService;
import com.unistrong.tracker.service.ResearchService;
import com.unistrong.tracker.service.ResearchTopicService;
import com.unistrong.tracker.util.Logic;
import com.unistrong.tracker.util.UsConstants;

/**
 * @author fss
 * 
 */
@Component
public class ResearchHandle {

	@Resource
	private ResearchService researchService;

	@Resource
	private ResearchTopicService researchTopicService;

	@Resource
	private ResearchAnswersService researchAnswersService;

	private static final long interval = 24 * 60 * 60 * 1000;

	public Map<String, Object> saveResearch(String userIdStr, List<Map<String, Object>> answers) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Long userId = HttpUtil.getLong(userIdStr);
		String topics = "";
		for (int i = 0; i < answers.size(); i++) {
			Map<String, Object> map = answers.get(i);
			String content = (String) map.get("content");
			Integer topicId = (Integer) map.get("topic_id");
			topics += (topicId + ",");
			ResearchAnswers answer = researchAnswersService.getByUserAndTopic(userId, topicId.longValue());
			if(answer != null) {
				rs.put(Logic.RET, 3);
				rs.put(Logic.DESC, UsConstants.SUBMITTED);
				return rs;
			}
			answer = new ResearchAnswers();
			answer.setResult(content);
			answer.setTopicId(topicId.longValue());
			answer.setUserId(userId);
			researchAnswersService.saveOrUpdate(answer);
		}
		Research research = researchService.get(userId);
		Assert.notNull(research,  UsConstants.getI18nMessage(UsConstants.NOT_NULL));
		research.setStatus(1);
		research.setTime(Calendar.getInstance().getTimeInMillis());
		research.setTopics(topics.substring(0, topics.length() - 1));
		researchService.saveOrUpdate(research);

		return rs;
	}

	public Map<String, Object> getResearch(String userIdStr) {
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpUtil.validateLong(new String[] { "user_id" }, new String[] { userIdStr });
		Long userId = HttpUtil.getLong(userIdStr);

		Research research = researchService.get(userId);

		List<ResearchTopic> list = new ArrayList<ResearchTopic>();
		if (research == null) {
			research = new Research();
			research.setUserId(userId);
			research.setStatus(2);
			research.setTime(Calendar.getInstance().getTimeInMillis());
			researchService.saveOrUpdate(research);
			list = researchTopicService.listAll();
		} else {
			long now = Calendar.getInstance().getTimeInMillis();
			if (research.getStatus() == 2 && now - research.getTime() > interval) {
				research.setTime(now);
				researchService.saveOrUpdate(research);
				list = researchTopicService.listAll();
			}
		}

		rs.put("research", list);
		return rs;
	}

}
