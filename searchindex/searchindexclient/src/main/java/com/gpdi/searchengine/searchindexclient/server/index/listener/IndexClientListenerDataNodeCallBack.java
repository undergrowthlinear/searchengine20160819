package com.gpdi.searchengine.searchindexclient.server.index.listener;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.coordinatorservice.callback.ListenerDataNodeCallBack;

/**
 * 
 * @description: TODO(监听协调数据节点,监听是否有增量数据，有则进行回调，将增量数据通过dubbo索引服务添加到索引中)
 * @author zhangwu
 * @date 2016年8月15日
 * @version 1.0.0
 */
public class IndexClientListenerDataNodeCallBack implements
		ListenerDataNodeCallBack {

	private Logger logger = LoggerFactory
			.getLogger(IndexClientListenerDataNodeCallBack.class);
	private BlockingQueue<String> updatedDataQueue;

	public IndexClientListenerDataNodeCallBack(
			BlockingQueue<String> updatedDataQueue) {
		// TODO Auto-generated constructor stub
		this.updatedDataQueue = updatedDataQueue;
	}

	@Override
	public void processResult(byte[] resultData) throws Exception {
		// TODO Auto-generated method stub
		String result = new String(resultData, Constants.DEFAULT_CHARSET);
		logger.info("get updateData:" + result);
		updatedDataQueue.put(result);
	}

}
