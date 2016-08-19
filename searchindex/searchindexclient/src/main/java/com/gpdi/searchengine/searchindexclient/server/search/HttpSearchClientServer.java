package com.gpdi.searchengine.searchindexclient.server.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.searchindexclient.server.ClientServer;
import com.gpdi.searchengine.searchindexclient.server.search.listener.HttpSearchClientAssistServer;

/**
 * 以Http的方式对外提供接口，对内封装以dubbo提供服务的生产者
 * 
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 11:21:06
 */
public class HttpSearchClientServer implements ClientServer {

	private Logger logger = LoggerFactory
			.getLogger(HttpSearchClientServer.class);
	private HttpSearchClientAssistServer assistServer;

	public boolean start() {
		logger.info("start HttpSearchClientServer...");
		boolean res = assistServer.startListener();
		logger.info("start HttpSearchClientServer success");
		return res;
	}

	public void setAssistServer(HttpSearchClientAssistServer assistServer) {
		this.assistServer = assistServer;
	}


}