package com.gpdi.searchengine.commonservice.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 
 * @description: TODO(这里用一句话描述这个类的作用)
 * from curator
 * @author zhangwu
 * @date 2016年8月12日
 * @version 1.0.0
 */
public class ThreadUtils {
	public static ExecutorService newSingleThreadExecutor(String processName) {
		return Executors.newSingleThreadExecutor(newThreadFactory(processName));
	}

	public static ExecutorService newFixedThreadPool(int qty, String processName) {
		return Executors.newFixedThreadPool(qty, newThreadFactory(processName));
	}

	public static ScheduledExecutorService newSingleThreadScheduledExecutor(
			String processName) {
		return Executors
				.newSingleThreadScheduledExecutor(newThreadFactory(processName));
	}

	public static ScheduledExecutorService newFixedThreadScheduledPool(int qty,
			String processName) {
		return Executors.newScheduledThreadPool(qty,
				newThreadFactory(processName));
	}

	public static ThreadFactory newThreadFactory(String processName) {
		return new ThreadFactoryBuilder().setNameFormat(processName + "-%d")
				.setDaemon(true).build();
	}
	
	private ThreadUtils(){}
}
