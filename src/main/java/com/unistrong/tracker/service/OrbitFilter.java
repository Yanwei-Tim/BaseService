package com.unistrong.tracker.service;

import java.util.List;

import com.unistrong.tracker.model.Spot;
/**
 * 轨迹过滤
 * @author XieHaiSheng
 *
 */
public interface OrbitFilter {
	
	boolean valid(List<Spot> orbit);
	
	Integer getOrder();
	
	
}
