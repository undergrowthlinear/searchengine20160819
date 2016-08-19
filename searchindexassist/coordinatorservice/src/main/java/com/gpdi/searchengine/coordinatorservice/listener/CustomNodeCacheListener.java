package com.gpdi.searchengine.coordinatorservice.listener;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.coordinatorservice.callback.ListenerDataNodeCallBack;

/**
 * 
 * @description: TODO(当节点数据改变 节点删除、节点新增时 进行回调)
 * @author zhangwu
 * @date 2016年8月12日
 * @version 1.0.0
 */
public class CustomNodeCacheListener implements NodeCacheListener {

	private Logger logger = LoggerFactory
			.getLogger(CustomNodeCacheListener.class);
	private NodeCache nodeCache;
	private ListenerDataNodeCallBack callBack;

	public CustomNodeCacheListener(NodeCache nodeCache,
			ListenerDataNodeCallBack callBack) {
		// TODO Auto-generated constructor stub
		this.nodeCache = nodeCache;
		this.callBack = callBack;
	}

	@Override
	public void nodeChanged() throws Exception {
		// TODO Auto-generated method stub
		if (nodeCache != null && nodeCache.getCurrentData() != null) {
			byte[] resultData = nodeCache.getCurrentData().getData();
			logger.info(Thread.currentThread().getName() + ","
					+ nodeCache.toString() + ","
					+ new String(resultData, Constants.DEFAULT_CHARSET));
			callBack.processResult(resultData);
		}

	}
}
