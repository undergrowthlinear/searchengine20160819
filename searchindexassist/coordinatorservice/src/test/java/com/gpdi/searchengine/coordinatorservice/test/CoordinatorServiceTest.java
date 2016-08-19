package com.gpdi.searchengine.coordinatorservice.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.commonservice.util.ConfigUtil;
import com.gpdi.searchengine.commonservice.util.StringUtils;
import com.gpdi.searchengine.coordinatorservice.callback.ListenerDataNodeCallBack;
import com.gpdi.searchengine.coordinatorservice.conf.CoordinatorCientParameter;
import com.gpdi.searchengine.coordinatorservice.service.CoordinatorService;

public class CoordinatorServiceTest {

	ApplicationContext context = null;
	CoordinatorService coordinatorService = null;
	CoordinatorCientParameter clientParam = null;
	Properties appProper = null;
	String data = "name=蒙自源,address=广东省广州市天河区,phone=13786788,description=主要经营云南过桥米线,price=123.4";

	@Before
	public void before() throws IOException {
		context = new ClassPathXmlApplicationContext(
				"classpath:applicationContextTest.xml");
		coordinatorService = (CoordinatorService) context
				.getBean("coordinatorService");
		clientParam = (CoordinatorCientParameter) context
				.getBean("clientParam");
		assertNotNull("coordinatorService  error,coordinatorService is null",
				coordinatorService);
		assertNotNull("clientParam  error,clientParam is null", clientParam);
		appProper = ConfigUtil.loadProperties("app.properties");
	}

	@Test
	public void createNodeTest() throws IOException {
		coordinatorService.createNode(clientParam.getAddDataNode(),
				data.getBytes(Constants.DEFAULT_CHARSET));
		System.in.read();
	}

	@Test
	public void deleteNodeTest() throws IOException {
		assertTrue("delete " + Constants.DEFAULT_ADD_DATA_NODE + " fail",
				coordinatorService.deleteNode(Constants.DEFAULT_ADD_DATA_NODE));
		System.in.read();
	}

	@Test
	public void setDataTest() throws IOException {
		Map<String, String> dataMap = StringUtils.parseKeyValuePair(data, ",");
		dataMap.put("name", "uu");
		data = StringUtils.toQueryString(dataMap);
		assertTrue("setData " + Constants.DEFAULT_ADD_DATA_NODE + " fail",
				coordinatorService.setData(Constants.DEFAULT_ADD_DATA_NODE,
						data.getBytes(Constants.DEFAULT_CHARSET)));
		System.in.read();
	}

	@Test
	public void getDataTest() throws IOException {
		assertNotNull(
				"getData on " + Constants.DEFAULT_ADD_DATA_NODE + " fail",
				coordinatorService.getData(Constants.DEFAULT_ADD_DATA_NODE));
		System.in.read();
	}

	@Test
	public void listenerNodeTest() throws IOException {
		ListenerDataNodeCallBack callBack = new ListenerDataNodeCallBack() {

			@Override
			public void processResult(byte[] resultData) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("listenerNode on "
						+ Constants.DEFAULT_ADD_DATA_NODE
						+ " data changed time is "
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss | SSS")
								.format(new Date(System.currentTimeMillis()))+",data:"+new String(resultData,Constants.DEFAULT_CHARSET));
			}
		};
		coordinatorService.listenerNode(Constants.DEFAULT_ADD_DATA_NODE,
				callBack);
		System.in.read();
	}

}
