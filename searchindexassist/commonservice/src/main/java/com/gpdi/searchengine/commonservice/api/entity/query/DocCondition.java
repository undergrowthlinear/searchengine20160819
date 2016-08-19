package com.gpdi.searchengine.commonservice.api.entity.query;

import java.io.Serializable;

import com.gpdi.searchengine.commonservice.api.entity.index.DocField;

/**
 * 
 * @description: TODO(表示查询条件)
 * @author zhangwu
 * @date 2016年8月11日
 * @version 1.0.0
 */
public class DocCondition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DocField docField = null;
	private DocConditionOccur conditionOccur = null;

	public DocCondition(String fieldName, String fieldValue,
			DocConditionOccur conditionOccur) {
		// TODO Auto-generated constructor stub
		this.docField = new DocField(fieldName, fieldValue);
		this.conditionOccur = conditionOccur;
	}

	@Override
	public String toString() {
		return "DocCondition [docField=" + docField + ", conditionOccur="
				+ conditionOccur + "]";
	}

	public DocField getDocField() {
		return docField;
	}

	public DocConditionOccur getConditionOccur() {
		return conditionOccur;
	}

}
