/**
 * 
 */
package com.unistrong.tracker.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.ResearchHandle;
import com.unistrong.tracker.model.Protocol;

/**
 * @author fss
 * 
 */
@Component
public class MapingResearch {
	
	@Resource
	private ResearchHandle researchHandle;

	public Map<String, Object> saveResearch(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> answers = (ArrayList<Map<String, Object>>)cmd.get("answers");
		
		rs.putAll(researchHandle.saveResearch(protocol.getUserId(), answers));
		return rs;

	}
	
	public Map<String, Object> getResearch(Map<String, Object> cmd, Protocol protocol) {
		Map<String, Object> rs = new HashMap<String, Object>();
//		List<ResearchTopic> list = new ArrayList<ResearchTopic>();
//		ResearchTopic researchTopic = new ResearchTopic();
//		researchTopic.setId(1000l);
//		researchTopic.setName("请问你购买跟屁虫是用来做什么？");
//		researchTopic.setContent("A.私家车管理;B.公车管理;C.其他");
//		researchTopic.setType(1);
//		
//		ResearchTopic researchTopic2 = new ResearchTopic();
//		researchTopic2.setId(1001l);
//		researchTopic2.setName("是什么原因打动了您，选择购买跟屁虫？");
//		researchTopic2.setContent("A.产品安装方便;B.没有平台服务费;C.北斗公司可托付;D.可以使用手机客户端软件;E.产品的功能全面;F.价格不错;G.赠送SIM卡;H.其他");
//		researchTopic2.setType(2);
//		
//		list.add(researchTopic);
//		list.add(researchTopic2);
//		Map<String, Object> rs2 = new HashMap<String, Object>();
//		rs2.put("research", list);
		rs.putAll(researchHandle.getResearch(protocol.getUserId()));

		return rs;

	}
}
