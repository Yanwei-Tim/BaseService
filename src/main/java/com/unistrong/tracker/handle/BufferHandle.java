package com.unistrong.tracker.handle;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import module.util.HttpUtil;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.i18n.MessageManager;
import com.unistrong.tracker.model.Buffer;
import com.unistrong.tracker.model.Fence;
import com.unistrong.tracker.model.serialize.SpotVo;
import com.unistrong.tracker.service.BufferService;
import com.unistrong.tracker.util.BufferLogic;

/**
 * 
 * @author sujinxuan
 *
 */
@Component
public class BufferHandle {

	@Resource
	private BufferService bufferService;
	

	// *****************************************
	/**
	 * 查询缓冲区列表
	 */
	public Map<String, Object> list(String userIdStr) {
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });
		Map<String, Object> rs = new HashMap<String, Object>();
		Long userId = HttpUtil.getLong(userIdStr);
		List<Buffer> buffers = bufferService.findByUser(userId);
		rs.put("buffers", buffers);
		return rs;
	}
	
	/**
	 * 删除 新增 修改 缓冲区
	 */
	public Map<String, Object> bufferEdit(String operate,Integer buffer_id,String userIdStr, String name,		// TODO:name没有使用.
			SpotVo[] region, String type, String radius, Double[] center) {
		HttpUtil.validateNull(new String[] { "operate", "user_id",}, new String[] {
				operate, userIdStr});
		//Integer operateInt = HttpUtil.getInt(operate);
		Long userId = HttpUtil.getLong(userIdStr);
		if (BufferLogic.Operate.delete.getCode().equals(operate)) {// 删除缓冲区
			return delete(buffer_id);
		} else {// 新增或修改缓冲区
			HttpUtil.validateNull(new String[] { "type" }, new String[] { type });
			Fence formBuffer = new Fence();
			formBuffer.setOut(null);
			formBuffer.setType(HttpUtil.getInt(type));
			formBuffer.setCenter(center);
			formBuffer.setRadius(HttpUtil.getInt(radius, null));
			formBuffer.setRegion(region);
			return saveOrUpdate(userId,buffer_id,formBuffer);
		}
		
	}
	
	/**
	 * 添加或更新缓冲区
	 * @param userId
	 * @param formBuffer
	 * @return
	 */
	private Map<String, Object> saveOrUpdate(Long userId,Integer buffer_id,Fence formBuffer) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Buffer buffer=new Buffer();
		if(buffer_id!=null){
			buffer.setBufferId(buffer_id);
		}
        buffer.setCreateTime(new Date().getTime());
		buffer.setUserId(userId);
		buffer.setBuffer(formBuffer);
		bufferService.saveOrUpdate(buffer);
		rs.put("buffer", formBuffer);
		rs.put(BufferLogic.DESC, MessageManager.UPDATE_SUCCESS());
		return rs;
	}
	
	/**
	 * 删除缓冲区
	 * @param buffer_id
	 * @return
	 */
	private Map<String, Object> delete(Integer buffer_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Buffer buffer=bufferService.findById(buffer_id);
		bufferService.delete(buffer);
		rs.put(BufferLogic.DESC, MessageManager.DELETE_SUCCESS());
		return rs;
	}

}
