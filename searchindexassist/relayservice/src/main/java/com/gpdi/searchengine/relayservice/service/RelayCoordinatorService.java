package com.gpdi.searchengine.relayservice.service;

public interface RelayCoordinatorService {


	/**
	 * 将增量数据放入协调器上
	 * @param path
	 * @param updateData
	 * @return
	 */
	public abstract boolean updateCoordinatorData(String path,String updateData);

}