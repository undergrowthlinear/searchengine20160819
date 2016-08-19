package com.gpdi.searchengine.commonservice.api.service;

import com.gpdi.searchengine.commonservice.api.entity.SearchResult;
import com.gpdi.searchengine.commonservice.api.entity.query.DocQuery;

/**
 * 
 * @description: TODO(搜索服务)
 * @author zhangwu
 * @date 2016年8月11日
 * @version 1.0.0
 */
public interface DocSearchService {

	/**
	 * 按照fieldName:searchValue进行搜索
	 * 
	 * @param areaCode
	 * @param fieldName
	 * @param searchValue
	 * @param num
	 * @return
	 */
	public SearchResult search(String areaCode, String fieldName,
			String searchValue, int num);

	/**
	 * 按照查询条件进行查询
	 * 
	 * @param areaCode
	 * @param docQuery
	 * @return
	 */
	public SearchResult search(String areaCode, DocQuery docQuery, int num);

}
