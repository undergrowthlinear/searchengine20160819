package com.gpdi.searchengine.coordinatorservice.service.skeleton;

import com.gpdi.searchengine.coordinatorservice.callback.ListenerDataNodeCallBack;
import com.gpdi.searchengine.coordinatorservice.service.CoordinatorService;

/**
 * 协调服务，负责节点的创建、节点的删除、节点数据的获取、节点数据的更新
 * 支撑多个不同的实现
 * @description: TODO(这里用一句话描述这个类的作用)
 * @author zhangwu
 * @date 2016年8月12日
 * @version 1.0.0
 */
public abstract class AbstractCoordinatorService implements CoordinatorService {

	protected abstract boolean isConnectZookeeper();

	protected abstract boolean connectZookeeper();

	protected abstract void createNodeInternal(String path, byte[] data);

	protected abstract boolean deleteNodeInternal(String path);

	protected abstract String getDataInternal(String path);

	protected abstract boolean setDataInternal(String path, byte[] data);

	protected abstract void listenerNodeInternal(String path,
			ListenerDataNodeCallBack callBack);

	@Override
	public void createNode(String path, byte[] data) {
		// TODO Auto-generated method stub
		if (!isConnectZookeeper()) {
			connectZookeeper();
		}
		createNodeInternal(path, data);
	}

	@Override
	public boolean deleteNode(String path) {
		// TODO Auto-generated method stub
		if (!isConnectZookeeper()) {
			connectZookeeper();
		}
		return deleteNodeInternal(path);
	}

	@Override
	public String getData(String path) {
		// TODO Auto-generated method stub
		if (!isConnectZookeeper()) {
			connectZookeeper();
		}
		return getDataInternal(path);
	}

	@Override
	public boolean setData(String path, byte[] data) {
		// TODO Auto-generated method stub
		if (!isConnectZookeeper()) {
			connectZookeeper();
		}
		return setDataInternal(path, data);
	}

	@Override
	public void listenerNode(String path, ListenerDataNodeCallBack callBack) {
		// TODO Auto-generated method stub
		if (!isConnectZookeeper()) {
			connectZookeeper();
		}
		listenerNodeInternal(path, callBack);
	}

}
