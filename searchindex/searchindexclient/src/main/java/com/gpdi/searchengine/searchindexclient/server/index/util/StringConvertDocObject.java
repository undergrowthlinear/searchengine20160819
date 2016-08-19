package com.gpdi.searchengine.searchindexclient.server.index.util;

import java.util.Map;

import com.gpdi.searchengine.commonservice.api.entity.index.DocField;
import com.gpdi.searchengine.commonservice.api.entity.index.DocObject;
import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.commonservice.util.StringUtils;

/**
 * 
 * @description: TODO(将增量数据转为DocObject)
 * @author zhangwu
 * @date 2016年8月15日
 * @version 1.0.0
 */
public class StringConvertDocObject {

	public static DocObject stringToDocObject(String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		Map<String, String> fieldMap = StringUtils.parseKeyValuePair(data,
				Constants.COMMA_SEPARATOR);
		DocObject docObject = convertDocObject(fieldMap);
		return docObject;
	}

	public static DocObject convertDocObject(Map<String, String> fieldMap) {
		// TODO Auto-generated method stub
		if (fieldMap == null || fieldMap.size() <= 0) {
			return null;
		}
		DocObject docObject = new DocObject();
		for (Map.Entry<String, String> fieldEntry : fieldMap.entrySet()) {
			if (Constants.AREA_CODE.equals(fieldEntry.getKey())) { // 将区域编码存储起来
																	// 方便后续使用
				docObject.setAreaCode(fieldEntry.getValue());
			}
			DocField docField = new DocField(fieldEntry.getKey(),
					fieldEntry.getValue());
			docObject.getDocFields().add(docField);
		}
		return docObject;
	}

}
