package com.gpdi.searchengine.searchindexservice.service.impl;

import java.util.Map;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.SearchResult;
import com.gpdi.searchengine.commonservice.util.StringUtils;
import com.gpdi.searchengine.searchindexservice.core.SearcherManagerService;
import com.gpdi.searchengine.searchindexservice.service.SearchService;

/**
 * 负责搜索服务，管理<b>SearcherManagerService服务，提供IndexSearcher的获取与释放，
 * 同时SearcherManagerServ ice中的SearcherManager来源于NRTManagerService</b>
 * 
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 11:21:06
 */
public class SearchServiceDefaultImpl implements SearchService {

	private Logger logger = LoggerFactory
			.getLogger(IndexServiceDefaultImpl.class);
	private Map<String, SearcherManagerService> searcherManagerServices;
	private IndexServiceDefaultImpl indexService;

	public Map<String, SearcherManagerService> getSearcherManagerServices() {
		return searcherManagerServices;
	}

	public void setSearcherManagerServices(
			Map<String, SearcherManagerService> searcherManagerServices) {
		this.searcherManagerServices = searcherManagerServices;
	}

	@Override
	public SearchResult search(String areaCode, String fieldName,
			String searchValue, int num) {
		// TODO Auto-generated method stub
		if (!getNrtManagerService(areaCode)) {
			return null;
		}
		if (StringUtils.isBlank(fieldName) || StringUtils.isBlank(searchValue)) {
			return null;
		}
		return searcherManagerServices.get(areaCode).search(fieldName,
				searchValue, num);
	}

	@Override
	public SearchResult search(String areaCode, String fieldName,
			String searchValue, int num, Sort sort) {
		// TODO Auto-generated method stub
		if (!getNrtManagerService(areaCode)) {
			return null;
		}
		if (StringUtils.isBlank(fieldName) || StringUtils.isBlank(searchValue)) {
			return null;
		}
		return searcherManagerServices.get(areaCode).search(fieldName,
				searchValue, num, sort);
	}

	@Override
	public SearchResult search(String areaCode, Query query, int num) {
		// TODO Auto-generated method stub
		if (!getNrtManagerService(areaCode)) {
			return null;
		}
		return searcherManagerServices.get(areaCode).search(query, num);
	}

	@Override
	public SearchResult search(String areaCode, Query query, int num, Sort sort) {
		// TODO Auto-generated method stub
		if (!getNrtManagerService(areaCode)) {
			return null;
		}
		return searcherManagerServices.get(areaCode).search(query, num, sort);
	}

	@Override
	public SearchResult search(String areaCode, Query query, int num,
			Sort sort, Filter filter) {
		// TODO Auto-generated method stub
		if (!getNrtManagerService(areaCode)) {
			return null;
		}
		return searcherManagerServices.get(areaCode).search(query, num, sort,
				filter);
	}

	@Override
	public SearchResult searchPage(String areaCode, Query query, int indexPage,
			int pageNum) {
		// TODO Auto-generated method stub
		if (!getNrtManagerService(areaCode)) {
			return null;
		}
		return searcherManagerServices.get(areaCode).searchPage(query,
				indexPage, pageNum);
	}

	@Override
	public SearchResult searchPageAfter(String areaCode, Query query,
			int indexPage, int pageNum) {
		// TODO Auto-generated method stub
		if (!getNrtManagerService(areaCode)) {
			return null;
		}
		return searcherManagerServices.get(areaCode).searchPageAfter(query,
				indexPage, pageNum);
	}

	private boolean getNrtManagerService(String areaCode) {
		if (StringUtils.isEmpty(areaCode)) {
			logger.error("areaCode " + areaCode + " is null");
			return false;
		}
		SearcherManagerService searcherManagerService = searcherManagerServices
				.get(areaCode);
		if (searcherManagerService == null) {
			logger.error("areaCode " + areaCode
					+ " search failed,can't find areaCode");
			return false;
		}
		return true;
	}

	public void setIndexService(IndexServiceDefaultImpl indexService) {
		this.indexService = indexService;
		setSearcherManagerServices(this.indexService
				.getSearcherManagerServices());
	}

}