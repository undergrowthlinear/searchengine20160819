package com.gpdi.searchengine.commonservice.api.entity.query;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @description: TODO(这里用一句话描述这个类的作用)
 * @author zhangwu
 * @date 2016年8月11日
 * @version 1.0.0
 */
public class DocQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DocCondition> conditions = null;

	public DocQuery(List<DocCondition> conditions) {
		super();
		this.conditions = conditions;
	}

	public List<DocCondition> getConditions() {
		return conditions;
	}

	private String areaCode;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
