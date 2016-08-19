package com.gpdi.searchengine.searchindexserver.server.internal;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @description: TODO(按照时间间隔提交索引策略)
 * @author zhangwu
 * @date 2016年8月16日
 * @version 1.0.0
 */
public class IntervalTimeCommitStrategy implements CommitStrategy {

	private Logger logger = LoggerFactory
			.getLogger(IntervalTimeCommitStrategy.class);
	private int intervalTime;
	private long expireTime;

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
		this.expireTime = getExpireTime();
	}

	private long getExpireTime() {
		return TimeUnit.MILLISECONDS.convert(intervalTime, TimeUnit.SECONDS)
				+ System.currentTimeMillis();
	}

	@Override
	public boolean conformStrategy() {
		// TODO Auto-generated method stub
		if (this.expireTime < System.currentTimeMillis()) {
			this.expireTime = getExpireTime();
			return true;
		}
		try {
			Thread.sleep(TimeUnit.MILLISECONDS.convert(this.intervalTime,
					TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.info("IntervalTimeCommitStrategy InterruptedException error,"
					+ e.getMessage());
		}
		return false;
	}

}
