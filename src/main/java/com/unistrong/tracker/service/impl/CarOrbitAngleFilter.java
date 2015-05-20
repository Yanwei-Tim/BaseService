package com.unistrong.tracker.service.impl;

import java.util.List;

import module.util.DistanceUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.unistrong.tracker.handle.util.SpotToWeb;
import com.unistrong.tracker.model.Spot;
import com.unistrong.tracker.service.OrbitFilter;
import com.unistrong.tracker.service.OrbitFilterFactory;
/**
 * 通过角度过滤
 * 放射状漂移
 * @author XieHaiSheng
 *
 */

public  class CarOrbitAngleFilter implements OrbitFilter {
	
	private static Logger logger = LoggerFactory.getLogger(CarOrbitAngleFilter.class);
	
	
	private int order=1;
	
	private float angle=40F; 
	
	private float minSpeed=0.6F; //unit: m/s
	
	
	/**
	 * 余弦公式法
	 * @param pointO
	 * @param pointS
	 * @param pointM
	 * @return ∠som
	 *   B = Math.acos((a*a + c*c - b*b)/(2*a*c));   
	 */
	private static double angle(Spot pointS,Spot pointO,Spot pointM)
	{
		double om=getDistance(pointO,pointM);
		double sm=getDistance(pointS,pointM);
		double os=getDistance(pointO,pointS);
		double som = Math.acos((om*om + os*os - sm*sm)/(2.0*os*om)); 
		return Math.toDegrees(som);
	}
	
	
	private static double getDistance(Spot uploadPosition, Spot lastPosition) {
		double distanceAdd = DistanceUtil.distance(uploadPosition.getLng(),
				uploadPosition.getLat(), lastPosition.getLng(), lastPosition.getLat());// 里程
		return distanceAdd;
	}
	
	
	public boolean valid(List<Spot> orbit) {
		
		//过滤掉小于4个点的轨迹
		if (orbit.size() <=3) {
			return  false;
		}
	    
		int count=0;
		int illegal=0;
		for(int i=2;i<orbit.size();i++){
			double  d=angle(orbit.get(i-2),orbit.get(i-1),orbit.get(i));
			logger.debug("angle:"+d);
			if(d<angle){
				count++;				
			}
			
		}
		
		logger.debug("angle.count:"+count+",orbit.size:"+orbit.size()+",illegal.size:"+illegal);
		
		
		if(count<orbit.size()/2){
			return  true;
		}else{
			return false;
		}
		
		
	}

	
	public Integer getOrder() {
		return this.order;
	}
	
	
	public void setOrder(int order) {
		this.order = order;
	}

	
	public void setMinSpeed(float minSpeed) {
		this.minSpeed = minSpeed;
	}

	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	
	
}
