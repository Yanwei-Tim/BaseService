package com.chinalbs.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unistrong.tracker.model.HandSetToConductor;
import com.unistrong.tracker.service.HandsetToConductorService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:contextData.xml" })
@Component
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class TestHandsetToConductorService {
	
	@Resource
	private HandsetToConductorService hService;
	@Test
	public void testSave() {
		HandSetToConductor handSetToConductor = new HandSetToConductor();
		handSetToConductor.setConductorId("2312312");
		handSetToConductor.setDeviceSn("3213121");
		hService.save(handSetToConductor);
		HandSetToConductor actual = hService.findById(handSetToConductor.getId());
		Assert.assertEquals(handSetToConductor, actual);
	}

}
