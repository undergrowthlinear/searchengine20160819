package com.gpdi.searchengine.dataservice;

import java.lang.String;

/**
 * 数据服务，提供基本的CRUD
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 10:08:58
 */
public interface DataService {

	public String findAlll();

	/**
	 * 
	 * @param data
	 */
	public boolean insert(String data);

}