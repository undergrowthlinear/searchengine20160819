package com.gpdi.searchengine.relayservice.service;

public interface RelayCacheService {

	/**
	 * 将增量数据放入redis缓存
	 * 
	 * @param updateData
	 */
	public abstract boolean insertCacheData(String updateData);

}