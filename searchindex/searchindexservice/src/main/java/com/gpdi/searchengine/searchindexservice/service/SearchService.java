package com.gpdi.searchengine.searchindexservice.service;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

import com.gpdi.searchengine.commonservice.api.entity.SearchResult;

public interface SearchService {

	/**
	 * 按照fieldName:searchValue进行搜索
	 * @param areaCode
	 * @param fieldName
	 * @param searchValue
	 * @param num
	 * @return
	 */
	public SearchResult search(String areaCode,String fieldName, String searchValue, int num);

	/**
	 * 按照fieldName:searchValue进行搜索,并排序
	 * @param areaCode
	 * @param fieldName
	 * @param searchValue
	 * @param num
	 * @param sort
	 * @return
	 */
	public SearchResult search(String areaCode,String fieldName, String searchValue, int num,
			Sort sort);
	
	/**
	 * 按照query进行搜索
	 * @param areaCode
	 * @param query
	 * @param num
	 * @return
	 */
	public SearchResult search(String areaCode,Query query, int num);
	
	/**
	 * 按照query进行搜索,并排序
	 * @param areaCode
	 * @param query
	 * @param num
	 * @param sort
	 * @return
	 */
	public SearchResult search(String areaCode,Query query, int num, Sort sort);

	/**
	 * 按照query进行搜索,并排序同时过滤结果集
	 * @param areaCode
	 * @param query
	 * @param num
	 * @param sort
	 * @param filter
	 * @return
	 */
	public SearchResult search(String areaCode,Query query, int num, Sort sort, Filter filter);

	/**
	 * 支持获取当前页记录
	 * @param areaCode
	 * @param query
	 * @param indexPage
	 * @param pageNum
	 * @return
	 */
	public SearchResult searchPage(String areaCode,Query query, int indexPage, int pageNum);

	/**
	 * 支持获取当前页记录
	 * @param areaCode
	 * @param query
	 * @param indexPage
	 * @param pageNum
	 * @return
	 */
	public SearchResult searchPageAfter(String areaCode, Query query,
			int indexPage, int pageNum);

}
