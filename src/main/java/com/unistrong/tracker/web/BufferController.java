package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.unistrong.tracker.util.BufferLogic;
import module.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.BufferHandle;
import com.unistrong.tracker.model.serialize.SpotVo;

/**
 * 缓冲区相关接口
 * @author sujinxuan
 * 2014/7/30
 */
@Controller
public class BufferController {

	@Resource
	private BufferHandle bufferHandle;


	/**
	 * 获取缓冲区列表
	 */
	@RequestMapping("/buffer.list.do")
	public Map<String, Object> list(String user_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put(BufferLogic.RET, BufferLogic.RES.RES_SUCCESS.getValue());
		rs.putAll(bufferHandle.list(user_id));
		return rs;
	}
	
	/**
	 * 设置/更新缓冲区
	 * @param operate
	 * @param buffer_id
	 * @param user_id
	 * @param name
	 * @param region
	 * @param radius
	 * @param center
	 * @return
	 */
	@RequestMapping("/buffer.edit.do")
	public Map<String, Object> bufferEdit(String operate,Integer buffer_id, String type, String user_id, String name,
			String region, String radius, String center) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put(BufferLogic.RET, BufferLogic.RES.RES_SUCCESS.getValue());
		SpotVo[] regionArray = null;
		Double[] centerArray = null;
		if (BufferLogic.Operate.delete.getCode().equals(operate)) {
			rs.putAll(bufferHandle.bufferEdit(operate,buffer_id, user_id, name, regionArray, type,
					radius, centerArray));
			return rs;
		}
		if (BufferLogic.Shape.circle.getCode().equals(type)) {
			try {
				centerArray = JsonUtils.str2Obj(center, Double[].class);
			} catch (Exception ex) {
				rs.put(BufferLogic.RET, BufferLogic.RES.RES_ERROR);
				rs.put(BufferLogic.DESC, "圆型围栏格式有误(参考[1.0,2.2])");
				return rs;
			}
		} else {
			try {
				regionArray = JsonUtils.str2Obj(region, SpotVo[].class);
			} catch (Exception ex) {
				rs.put(BufferLogic.RET, BufferLogic.RES.RES_ERROR);
				rs.put(BufferLogic.DESC, "矩形围栏格式有误(参考[{'lat':1, 'lng':1},...],左下第一个点,顺时针)");
				return rs;
			}
		}
		rs.putAll(bufferHandle.bufferEdit(operate, buffer_id,user_id, name, regionArray, type, radius,
				centerArray));
		return rs;
	}
}
