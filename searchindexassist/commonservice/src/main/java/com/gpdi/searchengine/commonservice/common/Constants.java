package com.gpdi.searchengine.commonservice.common;

import java.util.regex.Pattern;

/**
 * 
 * @description: TODO(这里用一句话描述这个类的作用)
 * @author zhangwu
 * @date 2016年8月12日
 * @version 1.0.0
 */
public class Constants {

	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String COMMA_SEPARATOR = ",";
	public static final String SEMICOLON_SEPARATOR = ";";
	public static final Pattern COMMA_SPLIT_PATTERN = Pattern
			.compile("\\s*[,]+\\s*");
	// curator 重新连接zookeeper最大次数
	public static final int MAX_RETRIES_LIMIT = 10;
	// curator session超时时间
	public static final int DEFAULT_SESSION_TIMEOUT_MS = 60 * 1000;
	// curator 连接超时时间
	public static final int DEFAULT_CONNECTION_TIMEOUT_MS = 15 * 1000;
	// 默认Zookeeper连接 增量数据节点
	public static final String DEFAULT_ADD_DATA_NODE = "/gpdi/addDataNode";
	// 区域编码名称
	public static final String AREA_CODE = "areaCode";
	public static final String UNKNOWN_AREA_CODE = "unknownAreaCode";
	// 索引服务名称
	public static final String SEARCH_SERVER_NAME = "docSearchService";
	// 默认查询数目
	public static final int DEFAULT_SEARCH_NUM = 15;
	// 默认查询字段
	public static final String DEFAULT_SEARCH_NAME = "searchNum";
	//提交索引的所有区域
	public static final String COMMIT_INDEX_ALL_AREA = "all";

}
