package com.gpdi.searchengine.relayservice.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.relayservice.service.RelayCoordinatorService;

public class RelayCoordinateServiceTest {

	ApplicationContext context = null;
	RelayCoordinatorService relayCoordinatorService = null;
	String data = "areaCode=gz,name=蒙自源,address=广东省广州市天河区,phone=13786788,description=主要经营云南过桥米线,price=123.4";
	String path = Constants.DEFAULT_ADD_DATA_NODE;

	@Before
	public void before() throws IOException {
		context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		relayCoordinatorService = (RelayCoordinatorService) context
				.getBean("relayCoordinatorService");
		assertNotNull(
				"relayCoordinatorService  error,relayCoordinatorService is null",
				relayCoordinatorService);
	}

	@Test
	public void relayCoordinateTest() throws IOException {
		relayCoordinatorService.updateCoordinatorData(path, data);
		System.in.read();
	}

}
