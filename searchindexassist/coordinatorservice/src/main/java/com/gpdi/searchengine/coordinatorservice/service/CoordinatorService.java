package com.gpdi.searchengine.coordinatorservice.service;

import com.gpdi.searchengine.coordinatorservice.callback.ListenerDataNodeCallBack;

/**
 * 
* @description: TODO(这里用一句话描述这个类的作用)
* 协调服务对外接口
* @author zhangwu
* @date 2016年8月15日
* @version 1.0.0
 */
public interface CoordinatorService {

	/**
	 * 创建指定节点
	 * 
	 * @param path
	 * @param data
	 *            []
	 */
	public  void createNode(String path, byte data[]);

	/**
	 * 删除指定节点
	 * 
	 * @param path
	 * @return
	 */
	public  boolean deleteNode(String path);

	/**
	 * 从指定节点获取数据值
	 * 
	 * @param path
	 * @return
	 */
	public  String getData(String path);

	/**
	 * 设定指定节点的数据
	 * 
	 * @param path
	 * @param data
	 * @return
	 */
	public  boolean setData(String path, byte data[]);
	
	/**
	 * 监听节点 当节点数据改变时 进行回调
	 * @param path
	 * @param callBack
	 */
	public  void listenerNode(String path,ListenerDataNodeCallBack callBack); 

}