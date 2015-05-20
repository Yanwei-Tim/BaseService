package com.unistrong.tracker.handle;

import java.util.*;

import javax.annotation.Resource;

import module.util.HttpUtil;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.i18n.MessageManager;
import com.unistrong.tracker.handle.util.LngLatUtil;
import com.unistrong.tracker.model.Fence;
import com.unistrong.tracker.model.Overlay;
import com.unistrong.tracker.model.serialize.SpotVo;
import com.unistrong.tracker.service.OverlayService;
import com.unistrong.tracker.util.BufferLogic;

/**
 * 
 * @author sujinxuan
 *
 */
@Component
public class OverlayHandle {

	@Resource
	private OverlayService overlayService;
	

	/**
	 * 查询重叠区列表
	 * @param userIdStr
	 * @return
	 */
	public Map<String, Object> list(String userIdStr) {
		HttpUtil.validateLong(new String[] { "user_id" },
				new String[] { userIdStr });
		Map<String, Object> rs = new HashMap<String, Object>();
		Long userId = HttpUtil.getLong(userIdStr);
		List<Overlay> overlays = overlayService.findByUser(userId);
		rs.put("overlays", overlays);
		return rs;
	}
	
	/**
	 * 删除 新增 修改 重叠区
	 */
	public Map<String, Object> overlayEdit(String operate,Integer overlay_id,String userIdStr,List<Fence> buffers,String type) {
		HttpUtil.validateNull(new String[] { "operate", "user_id",}, new String[] {
				operate, userIdStr});
		Long userId = HttpUtil.getLong(userIdStr);
		if (BufferLogic.Operate.delete.getCode().equals(operate)) {// 删除重叠区
			return delete(overlay_id);
		} else {// 新增或修改缓冲区			
			return saveOrUpdate(type,userId,overlay_id,buffers);
		}
		
	}
	
	/**
	 * 添加或更新重叠区
	 * @param userId
	 * @param overlayList
	 * @return
	 */
	private Map<String, Object> saveOrUpdate(String type,Long userId,Integer overlay_id,List<Fence> overlayList) {
		Map<String, Object> rs = new HashMap<String, Object>();
		if(overlayList!=null && (overlayList.size()>3 || overlayList.size()<2)){//只支持2-3个缓冲区生成重叠区
			rs.put(BufferLogic.RET, BufferLogic.RES.RES_ERROR.getValue());
			rs.put(BufferLogic.DESC, "只能选择2-3个缓冲区生成重叠区！");
			return rs;
		}else{
			if(overlayList!= null){
				boolean flag = true;
				if(BufferLogic.Shape.circle.getCode().equals(type)){
					for(int i=0;i<overlayList.size();i++){
						Fence f_overlay = overlayList.get(i);
						for(int j=i+1;j<overlayList.size();j++){
							Fence s_overlay =  overlayList.get(j);
							double sum_radius =  f_overlay.getRadius() + s_overlay.getRadius();
							double distance =  LngLatUtil.distanceByLngLat(f_overlay.getCenter()[0], f_overlay.getCenter()[1],s_overlay.getCenter()[0],s_overlay.getCenter()[1]);
							if(sum_radius <= distance){ //2个缓冲区不相交
								flag = false;
							}
						}
						
					}
				}else if(BufferLogic.Shape.rectangle.getClass().equals(type)){
					for(int i=0;i<overlayList.size();i++){
						Set<Double> lngs = new HashSet<Double>();//经度集合
						Set<Double> lats = new HashSet<Double>();//纬度集合
						SpotVo[] f_region = overlayList.get(i).getRegion();
						for(SpotVo f_spotVo : f_region){
							lngs.add(f_spotVo.getLng());
							lats.add(f_spotVo.getLat());	
						}
						for(int j=i+1;j<overlayList.size();j++){
							Set<Double> lng = new HashSet<Double>();//经度集合
							Set<Double> lat = new HashSet<Double>();//纬度集合
							SpotVo[] s_region = overlayList.get(j).getRegion();
							for(SpotVo s_spotVo : s_region){
								lng.add(s_spotVo.getLng());
								lat.add(s_spotVo.getLat());	
							}
							lng.addAll(lngs);
							lat.addAll(lats);
							boolean isOverlayRec = LngLatUtil.isOverlayRectangle(lng, lat, f_region, s_region);
							if(!isOverlayRec){
								flag=false;
							}
						}	
						
					}
				}
			    	
				
				if(flag){
					Overlay overlay=new Overlay();
					if(overlay_id!=null){
						overlay.setOverlayId(overlay_id);
					}
                    overlay.setCreateTime(new Date().getTime());
					overlay.setUserId(userId);
					overlay.setOverlay(overlayList);
					overlayService.saveOrUpdate(overlay);
					rs.put("overlay", overlay);
					rs.put(BufferLogic.DESC, MessageManager.UPDATE_SUCCESS());
				}else{
					rs.put(BufferLogic.DESC, "所选缓冲区无重叠区域！");
				}
			}
			return rs;
		}
		
	}
	
	/**
	 * 删除重叠区
	 * @param overlay_id
	 * @return
	 */
	private Map<String, Object> delete(Integer overlay_id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Overlay overlay=overlayService.findById(overlay_id);
		overlayService.delete(overlay);
		rs.put(BufferLogic.DESC, MessageManager.DELETE_SUCCESS());
		return rs;
	}

}
