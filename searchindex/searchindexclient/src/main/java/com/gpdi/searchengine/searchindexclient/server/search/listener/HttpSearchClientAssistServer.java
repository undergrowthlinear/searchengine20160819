package com.gpdi.searchengine.searchindexclient.server.search.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.util.ThreadUtils;
import com.gpdi.searchengine.searchindexclient.server.search.external.HttpSearchExternalServer;

/**
 * 
 * @description: TODO(通过netty对外发布搜索接口，将接口数据转换为DocQuery)
 * @author zhangwu
 * @date 2016年8月15日
 * @version 1.0.0
 */
public class HttpSearchClientAssistServer {

	private Logger logger = LoggerFactory
			.getLogger(HttpSearchClientAssistServer.class);
	private HttpSearchExternalServer externalServer;

	public boolean startListener() {
		logger.info("start startListener...");
		ThreadUtils.newSingleThreadExecutor("externalServerThread").submit(
				new ExternalServerThread(externalServer));
		logger.info("start startListener success...");
		return true;
	}

	public void setExternalServer(HttpSearchExternalServer externalServer) {
		this.externalServer = externalServer;
	}

	class ExternalServerThread implements Runnable {

		private Logger logger = LoggerFactory
				.getLogger(ExternalServerThread.class);
		private HttpSearchExternalServer externalServer;
		private int countStart = 0;

		public ExternalServerThread(HttpSearchExternalServer externalServer) {
			// TODO Auto-generated constructor stub
			this.externalServer = externalServer;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				logger.info("used for listener ,countStart:" + countStart++);
				this.externalServer.initServer();
			}
		}

	}

}
