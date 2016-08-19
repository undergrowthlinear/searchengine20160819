package com.gpdi.searchengine.searchindexserver.adapta;

/**
 * 
* @description: TODO(适配器接口)
* @author zhangwu
* @date 2016年8月11日
* @version 1.0.0
* @param <S>
* @param <D>
 */
public interface Adapta<S,D> {

	public D adapta(S s);
	
}
