package com.gpdi.searchengine.searchindexclient.server.search.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gpdi.searchengine.commonservice.api.entity.query.DocCondition;
import com.gpdi.searchengine.commonservice.api.entity.query.DocConditionOccur;
import com.gpdi.searchengine.commonservice.api.entity.query.DocQuery;
import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.commonservice.util.StringUtils;

/**
 * 
 * @description: TODO(将增量数据转为DocQuery)
 * @author zhangwu
 * @date 2016年8月15日
 * @version 1.0.0
 */
public class StringConvertDocQuery {

	public static DocQuery convertDocQuery(Map<String, List<String>> fieldMap) {
		// TODO Auto-generated method stub
		if (fieldMap == null || fieldMap.size() <= 0) {
			return null;
		}
		List<DocCondition> conditions = new ArrayList<DocCondition>();
		DocQuery docQuery = new DocQuery(conditions);
		for (Map.Entry<String, List<String>> fieldEntry : fieldMap.entrySet()) {
			if (Constants.AREA_CODE.equals(fieldEntry.getKey())) { // 将区域编码存储起来
																	// 方便后续使用
				docQuery.setAreaCode(fieldEntry.getValue().get(0));
			}
			DocCondition condition = new DocCondition(fieldEntry.getKey(),
					fieldEntry.getValue().get(0), DocConditionOccur.MUST);
			conditions.add(condition);
		}
		return docQuery;
	}

}
