package com.gpdi.searchengine.relayservice.service.impl;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.commonservice.util.PathUtils;
import com.gpdi.searchengine.coordinatorservice.service.CoordinatorService;
import com.gpdi.searchengine.relayservice.service.RelayCoordinatorService;

/**
 * 
 * @description: TODO(负责将增量数据放置在zookeeper节点上)
 * @author zhangwu
 * @date 2016年8月12日
 * @version 1.0.0
 */
public class RelayCoordinatorServiceDefaultImpl implements
		RelayCoordinatorService {

	private Logger logger = LoggerFactory
			.getLogger(RelayCoordinatorServiceDefaultImpl.class);
	private CoordinatorService coordinatorService;

	@Override
	public boolean updateCoordinatorData(String path, String updateData) {
		// TODO Auto-generated method stub
		try {
			if (!PathUtils.validatePath(path)) {
				path = Constants.DEFAULT_ADD_DATA_NODE;
			}
			boolean resu = coordinatorService.setData(path,
					updateData.getBytes(Constants.DEFAULT_CHARSET));
			logger.info("updateCoordinatorData,path:" + path + ",data:"
					+ updateData);
			return resu;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("updateCoordinatorData failed," + e.getMessage());
		}
		return false;
	}

	public void setCoordinatorService(CoordinatorService coordinatorService) {
		this.coordinatorService = coordinatorService;
	}

}
