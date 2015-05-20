package com.unistrong.tracker.handle.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.unistrong.tracker.model.serialize.SpotVo;

public class LngLatUtil {
	
	 /**
     * 根据经纬度，获取两点间的距离
     * 
     * 
     * @param lng1 经度
     * @param lat1 纬度
     * @param lng2 经度
     * @param lat2 纬度
     * @return
     *
     * @date 2014-08-04
     */
    public static double distanceByLngLat(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;

        return s;
    }
    
    /**
     * 判断2个矩形是否有交集
     * @param lngs
     * @param lats
     * @return
     */
    public static boolean isOverlayRectangle(Set<Double> lngs,Set<Double> lats,SpotVo[] f_rectangle,SpotVo[] s_rectangle){
    	List<Double> lngList = new ArrayList<Double>(lngs);
    	List<Double> latList = new ArrayList<Double>(lats);
    	Double[] region = new Double[2];
    	Collections.sort(lngList);
    	Collections.sort(latList);	
    	int lngMiddle = lngs.size()/2;
    	int latMiddle = lats.size()/2;
    	region[0] = (lngList.get(lngMiddle)+lngList.get(lngMiddle+1))/2;
    	region[1] = (latList.get(latMiddle)+latList.get(latMiddle+1))/2;
    	if(isInRec(f_rectangle, region) && isInRec(s_rectangle, region)){
    		return true;
    	}
		return false;
    	
    }
    
 
   public static boolean isInRec(SpotVo[] rectangle, Double[] region){
	   if((rectangle[0].getLng() > region[0] && rectangle[3].getLng() < region[0])
			   && (rectangle[0].getLat()> region[1] && rectangle[1].getLat() < region[1])){
		   return true;
	   }
	return false;
	   
   }
   

}
