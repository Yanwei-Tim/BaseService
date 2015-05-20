package com.unistrong.tracker.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.model.Spot;
import com.unistrong.tracker.service.OrbitFilter;

@Service
public class CarOrbitCellFilter extends  CarOrbitFilter{
	
	public boolean valid(List<Spot> orbit) {
		int validPoint = 0;
		Set<String> cellSet = new HashSet<String>();
		for(Spot spot:orbit){
			if(spot.getCell() != null && !"".equals(spot.getCell())){
				String[] cellArray = spot.getCell().split(",");
				if(cellArray == null || cellArray.length<3){
					validPoint++;
				}else{
					if(!cellSet.contains(spot.getCell())){
						cellSet.add(spot.getCell());
						validPoint++;
					}
				}
			}else{
				validPoint++;
			}
		}
		
		//过滤掉小于3个点的轨迹
		if (validPoint > 3) {
			return true;
		}else{
			return false;
		}
		
		
	}
	

	public Integer getOrder() {
		
		return 0;
	}

}
