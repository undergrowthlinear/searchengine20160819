package com.gpdi.searchengine.commonservice.api.service;

import com.gpdi.searchengine.commonservice.api.entity.index.DocField;
import com.gpdi.searchengine.commonservice.api.entity.index.DocObject;
import com.gpdi.searchengine.commonservice.api.entity.query.DocQuery;

/**
 * 
* @description: TODO(索引服务，新增、修改、删除索引)
* @author zhangwu
* @date 2016年8月11日
* @version 1.0.0
 */
public interface DocIndexService {

	/**
	 * 添加索引
	 * 
	 * @param areaCode
	 * @param docObject
	 * @return
	 */
	public boolean addIndex(String areaCode, DocObject docObject);

	/**
	 * 更新索引
	 * 
	 * @param areaCode
	 * @param docField
	 * @param docObject
	 * @return
	 */
	public boolean updateIndex(String areaCode, DocField docField,
			DocObject docObject);

	/**
	 * 删除索引
	 * 
	 * @param areaCode
	 * @param query
	 * @return
	 */
	public boolean deleteIndex(String areaCode, DocQuery query);

}