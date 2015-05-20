package com.unistrong.tracker.handle.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import module.util.NumUtil;

import com.unistrong.tracker.model.Spot;
import com.unistrong.tracker.model.serialize.SpotAddrIndoorVo;
import com.unistrong.tracker.model.serialize.SpotAddrVo;
import com.unistrong.tracker.util.GeoMapUtil;
import com.unistrong.tracker.util.Language;
import com.unistrong.tracker.util.LanguageUtil;
import com.unistrong.tracker.util.UsConstants;



public class SpotToVo {
	
	public static List<Map<String, Object>> listSav(List<List<Spot>> rsPart) {
		List<Map<String, Object>> rsPartBeginEnd = new ArrayList<Map<String, Object>>();
		for (List<Spot> list2 : rsPart) {
			Map<String, Object> line = new HashMap<String,Object>();
			List<SpotAddrVo> beginEnd = new ArrayList<SpotAddrVo>();
			SpotAddrVo sav1 = SpotToVo.spot2Vo(list2.get(0));
			beginEnd.add(sav1);
			SpotAddrVo sav2 = SpotToVo.spot2Vo(list2.get(list2.size() - 1));
			beginEnd.add(sav2);
			double distance = SpotToWeb.getPartDistance(list2);
			line.put("distance", NumUtil.round(distance, 1));
			line.put("unit", UsConstants.getI18nMessage(UsConstants.DISTANCE_UNIT));
			line.put("states", beginEnd);
			rsPartBeginEnd.add(line);
		}
		return rsPartBeginEnd;
	}
	
	public static List<List<SpotAddrVo>> listSavOld(List<List<Spot>> rsPart) {
		List<List<SpotAddrVo>> rsPartBeginEnd = new ArrayList<List<SpotAddrVo>>();
		for (List<Spot> list2 : rsPart) {
			List<SpotAddrVo> beginEnd = new ArrayList<SpotAddrVo>();
			SpotAddrVo sav1 = SpotToVo.spot2Vo(list2.get(0));
			beginEnd.add(sav1);
			SpotAddrVo sav2 = SpotToVo.spot2Vo(list2.get(list2.size() - 1));
			beginEnd.add(sav2);
			rsPartBeginEnd.add(beginEnd);
		}
		return rsPartBeginEnd;
	}
	
	public static List<Map<String, Object>> listSavIndoor(List<List<Spot>> rsPart) {
		List<Map<String, Object>> rsPartBeginEnd = new ArrayList<Map<String, Object>>();
		for (List<Spot> list2 : rsPart) {
			Map<String, Object> line = new HashMap<String,Object>();
			List<SpotAddrVo> beginEnd = new ArrayList<SpotAddrVo>();
			SpotAddrVo sav1 = SpotToVo.spot2Vo(list2.get(0));
			beginEnd.add(sav1);
			SpotAddrVo sav2 = SpotToVo.spot2Vo(list2.get(list2.size() - 1));
			beginEnd.add(sav2);
			double distance = SpotToWeb.getPartDistance(list2);
			line.put("distance", NumUtil.round(distance, 1));
			line.put("unit", UsConstants.getI18nMessage(UsConstants.DISTANCE_UNIT));
			line.put("states", beginEnd);
			rsPartBeginEnd.add(line);
		}
		return rsPartBeginEnd;
	}
	public static List<List<SpotAddrIndoorVo>> listSavOldIndoor(List<List<Spot>> rsPart) {
		List<List<SpotAddrIndoorVo>> rsPartBeginEnd = new ArrayList<List<SpotAddrIndoorVo>>();
		for (List<Spot> list2 : rsPart) {
			List<SpotAddrIndoorVo> beginEnd = new ArrayList<SpotAddrIndoorVo>();
			SpotAddrIndoorVo sav1 = SpotToVo.spot2VoIndoor(list2.get(0));
			beginEnd.add(sav1);
			SpotAddrIndoorVo sav2 = SpotToVo.spot2VoIndoor(list2.get(list2.size() - 1));
			beginEnd.add(sav2);
			rsPartBeginEnd.add(beginEnd);
		}
		return rsPartBeginEnd;
	}

	public static List<List<Spot>> listSopt(List<List<Spot>> rsPart) {
		List<List<Spot>> rsPartBeginEnd = new ArrayList<List<Spot>>();
		for (List<Spot> list2 : rsPart) {
			List<Spot> beginEnd = new ArrayList<Spot>();
			beginEnd.add(list2.get(0));
			beginEnd.add(list2.get(list2.size() - 1));
			rsPartBeginEnd.add(beginEnd);
		}
		return rsPartBeginEnd;
	}
	
	public static SpotAddrVo spot2Vo(Spot spot) {
		SpotAddrVo sav = new SpotAddrVo();
		String addr ="";
		if(Language.en_US.equals(LanguageUtil.get())){
			addr = GeoMapUtil.getAddrByGoogle(spot.getLng(),spot.getLat(), null);
		}else{
			addr = GeoMapUtil.getAddrByBaidu(spot.getLng(), spot.getLat());
		}
		sav.setAddr(addr);
		sav.setLat(spot.getLat());
		sav.setLng(spot.getLng());
		sav.setReceive(spot.getReceive());
		return sav;
	}
	
	public static SpotAddrIndoorVo spot2VoIndoor(Spot spot) {
		SpotAddrIndoorVo sav = new SpotAddrIndoorVo();
		String addr ="";
		if(Language.en_US.equals(LanguageUtil.get())){
			addr = GeoMapUtil.getAddrByGoogle(spot.getLng(),spot.getLat(), null);
		}else{
			addr = GeoMapUtil.getAddrByBaidu(spot.getLng(), spot.getLat());
		}
		sav.setAddr(addr);
		sav.setLat(spot.getLat());
		sav.setLng(spot.getLng());
		sav.setReceive(spot.getReceive());
		
		sav.setIndoor(spot.getIndoor());
		sav.setSystem(spot.getSystem());
		sav.setPx(spot.getPx());
		sav.setPy(spot.getPy());
		
		return sav;
	}

}
