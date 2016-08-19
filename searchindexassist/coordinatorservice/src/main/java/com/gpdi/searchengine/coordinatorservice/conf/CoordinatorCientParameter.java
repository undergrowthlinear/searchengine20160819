package com.gpdi.searchengine.coordinatorservice.conf;

import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.gpdi.searchengine.commonservice.common.Constants;

/**
 * 
 * @description: TODO(这里用一句话描述这个类的作用) 连接zookeeper的连接参数
 * @author zhangwu
 * @date 2016年8月12日
 * @version 1.0.0
 */
public class CoordinatorCientParameter {

	private String connectString;
	private int sessionTimeoutMs;
	private int connectionTimeoutMs;
	private int baseSleepTimeMs;
	private int maxRetries;
	//重新连接策略
	private RetryPolicy retryPolicy;
	private String addDataNode = Constants.DEFAULT_ADD_DATA_NODE;

	public CoordinatorCientParameter() {
		super();
	}

	public CoordinatorCientParameter(String connectString, int baseSleepTimeMs) {
		this(connectString, Constants.DEFAULT_SESSION_TIMEOUT_MS,
				Constants.DEFAULT_CONNECTION_TIMEOUT_MS, baseSleepTimeMs,
				Constants.MAX_RETRIES_LIMIT);
	}

	public CoordinatorCientParameter(String connectString,
			int sessionTimeoutMs, int connectionTimeoutMs, int baseSleepTimeMs) {
		this(connectString, sessionTimeoutMs, connectionTimeoutMs,
				baseSleepTimeMs, Constants.MAX_RETRIES_LIMIT);
	}

	public CoordinatorCientParameter(String connectString,
			int sessionTimeoutMs, int connectionTimeoutMs, int baseSleepTimeMs,
			int maxRetries) {
		super();
		this.connectString = connectString;
		this.sessionTimeoutMs = sessionTimeoutMs;
		this.connectionTimeoutMs = connectionTimeoutMs;
		this.baseSleepTimeMs = baseSleepTimeMs;
		this.maxRetries = maxRetries;
		this.retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs,
				maxRetries);
	}

	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setSessionTimeoutMs(int sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public int getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	public void setConnectionTimeoutMs(int connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	public int getBaseSleepTimeMs() {
		return baseSleepTimeMs;
	}

	public void setBaseSleepTimeMs(int baseSleepTimeMs) {
		this.baseSleepTimeMs = baseSleepTimeMs;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public RetryPolicy getRetryPolicy() {
		if (retryPolicy == null) {
			retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs,
					maxRetries);
		}
		return retryPolicy;
	}

	public void setRetryPolicy(RetryPolicy retryPolicy) {
		this.retryPolicy = retryPolicy;
	}

	@Override
	public String toString() {
		return "CoordinatorCientParameter [connectString=" + connectString
				+ ", sessionTimeoutMs=" + sessionTimeoutMs
				+ ", connectionTimeoutMs=" + connectionTimeoutMs
				+ ", baseSleepTimeMs=" + baseSleepTimeMs + ", maxRetries="
				+ maxRetries + ", retryPolicy=" + retryPolicy + "]";
	}

	public String getAddDataNode() {
		return addDataNode;
	}

	public void setAddDataNode(String addDataNode) {
		this.addDataNode = addDataNode;
	}

}
