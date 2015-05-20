package com.unistrong.tracker.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import module.util.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.OverlayHandle;
import com.unistrong.tracker.model.Fence;
import com.unistrong.tracker.model.serialize.SpotVo;
import com.unistrong.tracker.util.BufferLogic;
import com.unistrong.tracker.view.BufferForm;
import com.unistrong.tracker.view.OverlayForm;

/**
 * 缓冲区相关接口
 * @author sujinxuan
 * 2014/7/30
 */
@Controller
public class OverlayController {

	@Resource
	private OverlayHandle overlayHandle;



	/**
	 * 获取重叠区列表
	 * @param user_id
	 * @return
	 */
	@RequestMapping("/overlay.list.do")
	public Map<String, Object> list(String user_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put(BufferLogic.RET, BufferLogic.RES.RES_SUCCESS.getValue());
		rs.putAll(overlayHandle.list(user_id));
		return rs;
	}
	
	/**
	 * 设置/更新重叠区列表
	 * @param operate
	 * @param overlay_id
	 * @param user_id
	 * @param overlayFormStr
	 * @return
	 */
	@RequestMapping("/overlay.edit.do")
	public Map<String, Object> overlayEdit(HttpServletRequest request,String operate,Integer overlay_id,String user_id,String overlayFormStr) {
		Map<String, Object> rs = new HashMap<String, Object>();
       //"overlayFormStr":{"type":1,"overlayList":[{"region":null,"type":1,"radius":50243,"center":[115.496456,40.78569],"out":null,"newOverlayArrayIndex":-1},{"region":null,"type":1,"radius":29568,"center":[116.080563,40.280548],"out":null,"newOverlayArrayIndex":0},{"region":null,"type":1,"radius":8752,"center":[116.231622,40.142004],"out":null,"newOverlayArrayIndex":1}]}
		List<Fence> buffers = new ArrayList<Fence>();
		rs.put(BufferLogic.RET, BufferLogic.RES.RES_SUCCESS.getValue());
		String type = null;
		SpotVo[] regionArray = null;
		Double[] centerArray = null;
		if (BufferLogic.Operate.delete.getCode().equals(operate)) {
			rs.putAll(overlayHandle.overlayEdit(operate, overlay_id, user_id, buffers,type));
			return rs;
		}
        //"center":[115.496456,40.78569],"out":null, center 不能解析 转换成"center":“115.496456,40.78569”,"out":null,
        overlayFormStr =  overlayFormStr.replaceAll("\"center\":\\[", "\"center\":\"").replaceAll("],\"out\"", "\",\"out\"");
        OverlayForm overlayForm =  JsonUtils.str2Obj(overlayFormStr, OverlayForm.class);
		for(BufferForm bufferForm : overlayForm.getOverlayList()){
			Fence buffer = new Fence();
			if(BufferLogic.Shape.circle.getCode().equals(bufferForm.getType())){
                if(bufferForm.getCenter()!=null&&bufferForm.getCenter().split(",").length==2){
                    centerArray = new Double[]{Double.valueOf(bufferForm.getCenter().split(",")[0]),Double.valueOf(bufferForm.getCenter().split(",")[1])};
                }
				buffer.setCenter(centerArray);
				buffer.setRadius(Integer.parseInt(bufferForm.getRadius()));
			}
			if(BufferLogic.Shape.rectangle.getCode().equals(bufferForm.getType())){
				regionArray = JsonUtils.str2Obj(bufferForm.getRegion(), SpotVo[].class);
				buffer.setRegion(regionArray);
			}
			
			buffer.setType(Integer.parseInt(bufferForm.getType()));
			buffers.add(buffer);
			type = bufferForm.getType();
		}
		
		rs.putAll(overlayHandle.overlayEdit(operate, overlay_id, user_id, buffers, type));
		return rs;
	}
}
