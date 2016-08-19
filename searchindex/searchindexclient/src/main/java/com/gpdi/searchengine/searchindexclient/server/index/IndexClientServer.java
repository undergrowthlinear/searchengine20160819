package com.gpdi.searchengine.searchindexclient.server.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.searchindexclient.server.ClientServer;
import com.gpdi.searchengine.searchindexclient.server.index.listener.IndexCientAssistServer;

/**
 * 通过curator监听zookeeper的数据节点，获取增量数据，将增量数据转发到dubbo的索引搜索服务
 * 
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 11:21:06
 */
public class IndexClientServer implements ClientServer {

	private Logger logger = LoggerFactory.getLogger(IndexClientServer.class);
	private IndexCientAssistServer indexCientAssistServer;

	public boolean start() {
		logger.info("start IndexClientServer...");
		boolean resut = indexCientAssistServer.startListener();
		logger.info("start IndexClientServer success...");
		return resut;
	}

	public void setIndexCientAssistServer(
			IndexCientAssistServer indexCientAssistServer) {
		this.indexCientAssistServer = indexCientAssistServer;
	}

	
}