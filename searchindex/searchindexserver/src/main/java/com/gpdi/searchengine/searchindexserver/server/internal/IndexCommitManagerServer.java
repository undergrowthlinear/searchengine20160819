package com.gpdi.searchengine.searchindexserver.server.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.commonservice.util.ThreadUtils;
import com.gpdi.searchengine.searchindexserver.server.Server;
import com.gpdi.searchengine.searchindexservice.service.IndexService;

/**
 * 
 * @description: TODO(此服务负责按照一定的策略 将内存索引提交到硬盘上 不对外发布)
 * @author zhangwu
 * @date 2016年8月16日
 * @version 1.0.0
 */
public class IndexCommitManagerServer implements Server {

	private Logger logger = LoggerFactory
			.getLogger(IndexCommitManagerServer.class);

	private IndexService indexService;
	private CommitStrategy commitStrategy;

	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		logger.info("start IndexCommitManagerServer...");
		ThreadUtils.newSingleThreadExecutor("indexCommitManagerServer").submit(
				new IndexCommitTask(commitStrategy));
		logger.info("start IndexCommitManagerServer success...");
		return false;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public void setCommitStrategy(CommitStrategy commitStrategy) {
		this.commitStrategy = commitStrategy;
	}

	class IndexCommitTask implements Runnable {

		private CommitStrategy commitStrategy;

		public IndexCommitTask(CommitStrategy commitStrategy) {
			// TODO Auto-generated constructor stub
			this.commitStrategy = commitStrategy;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					if (commitStrategy.conformStrategy()) {
						indexService
								.commitIndex(Constants.COMMIT_INDEX_ALL_AREA);
						logger.info("commitIndex success,time is "
								+ System.currentTimeMillis());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("commitIndex error,exception:"
							+ e.getMessage());
				}
			}
		}

	}

}
