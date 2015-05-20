package com.unistrong.tracker.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Spot;

@Component
public class OrbitFilterFactory implements ApplicationContextAware{
	
	private static Logger logger = LoggerFactory.getLogger(OrbitFilterFactory.class);
	
	
	private static ApplicationContext ctx;
	
	private static List<OrbitFilter> carFilters;
	
	private static List<OrbitFilter> lowSpeedFilter;
	
	public static void carFilter(List<List<Spot>> spotList){
		
		Iterator<List<Spot>> it=spotList.iterator();
		
		while (it.hasNext()) {
			List<Spot> spots=it.next();
			for(OrbitFilter filter:carFilters){
				if(!filter.valid(spots)){
					it.remove();
					break;
				}
			}
			
		}
		
		
	}
	
	
    public static void lowSpeedFilter(List<List<Spot>> spotList){
    	
		
		Iterator<List<Spot>> it=spotList.iterator();
		
		while (it.hasNext()) {
			List<Spot> spots=it.next();
			for(OrbitFilter filter:lowSpeedFilter){
				if(!filter.valid(spots)){
					it.remove();
					break;
				}
			}
			
		}
		
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ctx = applicationContext;
		carFilters=(List) ctx.getBean("carFilters");
		logger.info("carFilters:"+carFilters.size());
		Collections.sort(carFilters, new Comparator<OrbitFilter>(){
            public int compare(OrbitFilter arg0, OrbitFilter arg1) {
                return arg0.getOrder().compareTo(arg1.getOrder());
            }
        });
		
		lowSpeedFilter=(List) ctx.getBean("lowSpeedFilters");
		logger.info("lowSpeedFilter:"+lowSpeedFilter.size());
		Collections.sort(lowSpeedFilter, new Comparator<OrbitFilter>(){
            public int compare(OrbitFilter arg0, OrbitFilter arg1) {
                return arg0.getOrder().compareTo(arg1.getOrder());
            }
        });
		
	}
	
	
}
