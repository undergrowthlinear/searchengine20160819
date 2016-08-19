package com.gpdi.searchengine.searchindexclient.server.search.handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.SearchResult;
import com.gpdi.searchengine.commonservice.api.entity.query.DocQuery;
import com.gpdi.searchengine.commonservice.api.service.DocSearchService;
import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.commonservice.util.JsonUtil;

/**
 * 
 * @description: TODO(将用户输入的请求转发给dubbo搜索服务，返回结果集)
 * @author zhangwu
 * @date 2016年8月16日
 * @version 1.0.0
 */
public class SearchQueryHandler {

	private Logger logger = LoggerFactory.getLogger(SearchQueryHandler.class);

	/**
	 * 按照区域进行搜索,除了区域属性，还需至少有一个属性
	 * 
	 * @param queryMap
	 * @return
	 */
	public String searchQuery(Map<String, List<String>> queryMap) {
		DocSearchService docSearchService = GpdiSpringFactory
				.getBean(Constants.SEARCH_SERVER_NAME);
		if (docSearchService == null) { // 此处还需考虑清楚 是否需要抛出异常
			logger.error("can't find DocSearchService...");
			return null;
		}
		if (!queryMap.containsKey(Constants.AREA_CODE)) { // 按照区域进行搜索
			logger.error("can't find areaCode properties...");
			return null;
		}
		if (queryMap.size() == 1) {// 除了areaCode属性 还需至少包含另一个属性s
			logger.error("beside areaCode properties...");
			return null;
		}
		DocQuery docQuery = StringConvertDocQuery.convertDocQuery(queryMap);
		int searchNum = 0;
		if (queryMap.get(Constants.DEFAULT_SEARCH_NAME) == null) {
			searchNum = Constants.DEFAULT_SEARCH_NUM;
		} else {
			searchNum = Integer.valueOf(queryMap.get(
					Constants.DEFAULT_SEARCH_NAME).get(0));
		}
		try {
			//通过dubbo进行查询
			SearchResult searchResult = docSearchService.search(
					docQuery.getAreaCode(), docQuery, searchNum);
			if (searchResult == null) {
				logger.info("can't find search result");
				return null;
			}
			return JsonUtil.serialize(searchResult);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("JsonUtil error on searchResult:");
		}
		return null;
	}

}
