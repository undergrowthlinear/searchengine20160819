package com.gpdi.searchengine.searchindexserver.server.external;

import org.apache.lucene.search.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.SearchResult;
import com.gpdi.searchengine.commonservice.api.entity.query.DocQuery;
import com.gpdi.searchengine.commonservice.api.service.DocSearchService;
import com.gpdi.searchengine.searchindexserver.adapta.DocQueryAdaptaQuery;
import com.gpdi.searchengine.searchindexserver.server.Server;
import com.gpdi.searchengine.searchindexservice.service.SearchService;

/**
 * 搜索服务，以dubbo服务化的方式对外提供服务
 * 
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 11:17:22
 */
public class SearchServer implements Server, DocSearchService {

	private Logger logger = LoggerFactory.getLogger(SearchServer.class);
	private SearchService searchService;

	public SearchServer() {

	}

	public boolean start() {
		logger.info("start SearchServer success");
		return true;
	}

	@Override
	public SearchResult search(String areaCode, String fieldName,
			String searchValue, int num) {
		// TODO Auto-generated method stub
		logger.info("search,areaCode:" + areaCode + ",fieldName:" + fieldName
				+ ",searchValue:" + searchValue + ",num:" + num);
		return searchService.search(areaCode, fieldName, searchValue, num);
	}

	@Override
	public SearchResult search(String areaCode, DocQuery docQuery, int num) {
		// TODO Auto-generated method stub
		try {
			Query query = new DocQueryAdaptaQuery().adapta(docQuery);
			return searchService.search(areaCode, query, num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("search," + e.getMessage());
		}
		logger.error("search,areaCode:" + areaCode + ",docQuery:" + docQuery
				+ ",num:" + num + " trans query failed");
		return null;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

}