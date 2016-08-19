package com.gpdi.searchengine.searchindexclient;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gpdi.searchengine.searchindexclient.server.ClientServer;

public class SearchClientServerTest {

	ApplicationContext context = null;
	ClientServer clientServer = null;

	@Before
	public void before() throws IOException {
		context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		clientServer = (ClientServer) context.getBean("httpSearchClientServer");
		assertNotNull("clientServer load error,clientServer is null",
				clientServer);
	}

	@Test
	public void testStart() throws IOException {
		System.in.read();
	}

}
