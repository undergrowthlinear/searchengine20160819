package com.gpdi.searchengine.searchindexservice.service;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;

/**
 * 
* @description: TODO(索引服务，新增、修改、删除索引)
* @author zhangwu
* @date 2016年8月10日
* @version 1.0.0
 */
public interface IndexService {

	/**
	 * 添加索引
	 * 
	 * @param areaCode
	 * @param document
	 * @return
	 */
	public  boolean addIndex(String areaCode, Document document) ;
	
	/**
	 * 更新索引
	 * 
	 * @param areaCode
	 * @param term
	 * @param document
	 * @return
	 */
	public  boolean updateIndex(String areaCode, Term term,
			Document document);

	/**
	 * 删除索引
	 * 
	 * @param areaCode
	 * @param query
	 * @return
	 */
	public  boolean deleteIndex(String areaCode, Query query);
	
	/**
	 * 刷新内存索引到磁盘
	 * @param areaCode
	 * @return
	 */
	public boolean commitIndex(String areaCode);

}