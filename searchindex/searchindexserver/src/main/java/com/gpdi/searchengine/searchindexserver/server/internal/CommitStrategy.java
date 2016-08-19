package com.gpdi.searchengine.searchindexserver.server.internal;

/**
 * 
* @description: TODO(提交策略 符合策略则提交)
* @author zhangwu
* @date 2016年8月16日
* @version 1.0.0
 */
public interface CommitStrategy {

	/**
	 * 是否符合提交策略
	 * @return
	 */
	public boolean conformStrategy();
	
	
}
