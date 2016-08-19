package com.gpdi.searchengine.coordinatorservice.callback;

/**
 * 
 * @description: TODO(监听数据改变回调函数)
 * @author zhangwu
 * @date 2016年8月12日
 * @version 1.0.0
 */
public interface ListenerDataNodeCallBack {
	public void processResult(byte resultData[]) throws Exception;
}
