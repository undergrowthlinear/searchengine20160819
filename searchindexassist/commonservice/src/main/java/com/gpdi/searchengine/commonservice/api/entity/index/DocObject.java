package com.gpdi.searchengine.commonservice.api.entity.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gpdi.searchengine.commonservice.common.Constants;

/**
 * 
 * @description: TODO(这里用一句话描述这个类的作用) 用于添加的docObject，添加索引时转换为Document
 * @author zhangwu
 * @date 2016年8月8日
 * @version 1.0.0
 */
public class DocObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DocField> docFields = new ArrayList<DocField>();

	// 用于存放数据所属的区域代码
	private String areaCode = Constants.UNKNOWN_AREA_CODE;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	private float boost = 1.0f;

	public void setBoost(float boost) {
		this.boost = boost;
	}

	public List<DocField> getDocFields() {
		return docFields;
	}

	public void setDocFields(List<DocField> docFields) {
		this.docFields = docFields;
	}

	@Override
	public String toString() {
		return "DocObject [docFields=" + docFields + ",docFields.size="
				+ docFields.size() + ", boost=" + boost + "]";
	}

}
