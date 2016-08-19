package com.gpdi.searchengine.coordinatorservice.service.core;

import java.util.concurrent.ExecutorService;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.commonservice.util.PathUtils;
import com.gpdi.searchengine.commonservice.util.ThreadUtils;
import com.gpdi.searchengine.coordinatorservice.callback.ListenerDataNodeCallBack;
import com.gpdi.searchengine.coordinatorservice.conf.CoordinatorCientParameter;
import com.gpdi.searchengine.coordinatorservice.listener.CustomNodeCacheListener;
import com.gpdi.searchengine.coordinatorservice.service.skeleton.AbstractCoordinatorService;

/**
 * 协调服务，负责节点的创建、节点的删除、节点数据的获取、节点数据的更新
 *  Curator 实现
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 10:31:44
 */
public class CuratorCoordinatorService extends AbstractCoordinatorService {

	private Logger logger = LoggerFactory
			.getLogger(CuratorCoordinatorService.class);
	private CuratorFramework client = null;
	private CoordinatorCientParameter clientParam;

	@Override
	protected boolean isConnectZookeeper() {
		// TODO Auto-generated method stub
		if (client == null
				|| client.getState() != CuratorFrameworkState.STARTED) {
			return false;
		}
		return true;
	}

	@Override
	protected boolean connectZookeeper() {
		// TODO Auto-generated method stub
		if (client == null) {
			client = CuratorFrameworkFactory.newClient(
					clientParam.getConnectString(),
					clientParam.getSessionTimeoutMs(),
					clientParam.getConnectionTimeoutMs(),
					clientParam.getRetryPolicy());
			client.start();
			logger.info("connect to " + clientParam + " successfully");
		}
		return true;
	}

	@Override
	protected void createNodeInternal(String path, byte[] data) {
		// TODO Auto-generated method stub
		try {
			if (PathUtils.validatePath(path)) {
				client.create().creatingParentsIfNeeded()
						.withMode(CreateMode.PERSISTENT).forPath(path, data);
				logger.info("createNode on " + path + " success,");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("createNode on " + path + " fail," + e.getMessage());
		}
	}

	@Override
	protected boolean deleteNodeInternal(String path) {
		// TODO Auto-generated method stub
		try {
			if (PathUtils.validatePath(path)) {
				Stat stat = new Stat();
				client.getData().storingStatIn(stat).forPath(path);
				logger.info("stat:" + stat);
				client.delete().deletingChildrenIfNeeded()
						.withVersion(stat.getVersion()).forPath(path);
				logger.info("deleteNode on " + path + " success,");
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("deleteNode on " + path + " fail," + e.getMessage());
		}
		return false;
	}

	@Override
	protected String getDataInternal(String path) {
		// TODO Auto-generated method stub
		if (PathUtils.validatePath(path)) {
			try {
				String result = new String(client.getData().forPath(path),
						Constants.DEFAULT_CHARSET);
				logger.info("getData on " + path + " data:" + result
						+ " success");
				return result;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("getData on " + path + " fail," + e.getMessage());
			}
		}
		return null;
	}

	@Override
	protected boolean setDataInternal(String path, byte[] data) {
		// TODO Auto-generated method stub
		try {
			if (PathUtils.validatePath(path)) {
				Stat stat = new Stat();
				client.getData().storingStatIn(stat).forPath(path);
				client.setData().withVersion(stat.getVersion())
						.forPath(path, data);
				logger.info("setData on " + path + " data:"
						+ new String(data, Constants.DEFAULT_CHARSET)
						+ " success");
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("setData on " + path + " fail," + e.getMessage());
		}
		return false;
	}

	@Override
	protected void listenerNodeInternal(String path,
			ListenerDataNodeCallBack callBack) {
		// TODO Auto-generated method stub
		if (PathUtils.validatePath(path)) {// 则注册监听节点 获取改变数据
			try {
				ExecutorService executor = ThreadUtils
						.newSingleThreadExecutor("listenerDataNodeThreadPool_"
								+ path + "_" + System.currentTimeMillis());
				NodeCache nodeCache = new NodeCache(client, path, false);
				// 获取初始数据
				nodeCache.start(true);
				nodeCache.getListenable().addListener(
						new CustomNodeCacheListener(nodeCache, callBack),
						executor);
				logger.info("listener on " + path + " callBack:" + callBack);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("listenerNode on " + path + " fail,"
						+ e.getMessage());
			}
		}
	}
	
	public void setClientParam(CoordinatorCientParameter clientParam) {
		this.clientParam = clientParam;
	}

}