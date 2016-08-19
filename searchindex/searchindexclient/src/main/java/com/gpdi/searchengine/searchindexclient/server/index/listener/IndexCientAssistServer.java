package com.gpdi.searchengine.searchindexclient.server.index.listener;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.index.DocObject;
import com.gpdi.searchengine.commonservice.api.service.DocIndexService;
import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.commonservice.util.ThreadUtils;
import com.gpdi.searchengine.coordinatorservice.service.CoordinatorService;
import com.gpdi.searchengine.searchindexclient.server.index.util.StringConvertDocObject;

/**
 * 
 * @description: TODO(负责从Zookeeper获取监听节点的增量数据,将增量数据通过DocIndexService添加到索引服务中)
 * @author zhangwu
 * @date 2016年8月15日
 * @version 1.0.0
 */
public class IndexCientAssistServer {

	private Logger logger = LoggerFactory
			.getLogger(IndexCientAssistServer.class);

	private int defaultCapacity = 10000;
	private int capacity = 0;
	// 用于存放增量数据
	private BlockingQueue<String> updatedDataQueue = new ArrayBlockingQueue<String>(
			getCapacity());
	// 监听数据节点
	private String addDataNode;
	// 协调服务器
	private CoordinatorService coordinatorService;
	// 索引服务器
	private DocIndexService docIndexService;
	// 监听回调
	private IndexClientListenerDataNodeCallBack callBack;

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCapacity() {
		if (capacity == 0) {
			capacity = defaultCapacity;
		}
		return capacity;
	}

	public IndexClientListenerDataNodeCallBack getCallBack() {
		if (callBack == null) {
			callBack = new IndexClientListenerDataNodeCallBack(updatedDataQueue);
		}
		return callBack;
	}

	public String getAddDataNode() {
		if (addDataNode == null) {// 如果未指定监听节点,则使用默认数据节点
			addDataNode = Constants.DEFAULT_ADD_DATA_NODE;
		}
		return addDataNode;
	}

	public void setAddDataNode(String addDataNode) {
		this.addDataNode = addDataNode;
	}

	public void setCoordinatorService(CoordinatorService coordinatorService) {
		this.coordinatorService = coordinatorService;
	}

	public void setDocIndexService(DocIndexService docIndexService) {
		this.docIndexService = docIndexService;
	}

	/**
	 * 初始化服务 启动监听数据节点 启动线程处理增量数据 将增量数据添加到索引服务中
	 * 
	 * @return
	 */
	public boolean startListener() {
		logger.info("startListener...");
		// 监听数据节点
		coordinatorService.listenerNode(getAddDataNode(), getCallBack());
		// 从队列中获取数据执行
		ThreadUtils.newSingleThreadExecutor("UpdataDataIndexer").submit(
				new UpdataDataIndexer());
		logger.info("startListener success...");
		return true;
	}

	// 用于从增量数据队列中获取增量数据，通过docObject发送到索引服务中
	class UpdataDataIndexer implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					try {
						String updateData = updatedDataQueue.take();
						logger.info("get Data from updatedDataQueue,remain updatedDataQueue.size="
								+ updatedDataQueue.size());
						DocObject docObject = StringConvertDocObject
								.stringToDocObject(updateData); 
						if (docObject != null
								&& !Constants.UNKNOWN_AREA_CODE
										.equals(docObject.getAreaCode())) { // 将索引数据发布到索引服务中
							docIndexService.addIndex(docObject.getAreaCode(),
									docObject); 
							logger.info("UpdataDataIndexer get updateData:"
									+ updateData + ",addIndex success");
						} else {
							logger.error("transUpdateData fail,updateData:"
									+ updateData);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						logger.error("UpdataDataIndexer get updateData error,"
								+ e.getMessage());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("UpdataDataIndexer get updateData error,"
							+ e.getMessage());
				}
			}

		}

	}
}
