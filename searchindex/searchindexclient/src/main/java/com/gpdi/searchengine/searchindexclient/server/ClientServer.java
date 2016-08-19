package com.gpdi.searchengine.searchindexclient.server;


/**
 * 客户端服务，负责消费以dubbo方式提供的生产者服务，同时对外提供需要的接口形式，例如Http等
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 11:21:06
 */
public interface ClientServer {

	public boolean start();

}