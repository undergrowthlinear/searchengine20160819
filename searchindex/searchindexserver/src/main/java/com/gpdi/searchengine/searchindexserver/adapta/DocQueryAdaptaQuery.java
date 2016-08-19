package com.gpdi.searchengine.searchindexserver.adapta;

import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.query.DocCondition;
import com.gpdi.searchengine.commonservice.api.entity.query.DocQuery;

/**
 * 
 * @description: TODO(将DocQuery转为Query)
 * @author zhangwu
 * @date 2016年8月11日
 * @version 1.0.0
 */
public class DocQueryAdaptaQuery implements Adapta<DocQuery, Query> {

	private Logger logger = LoggerFactory.getLogger(DocQueryAdaptaQuery.class);

	@Override
	public Query adapta(DocQuery docQuery) {
		// TODO Auto-generated method stub
		if (docQuery != null) {
			BooleanQuery booleanQuery = new BooleanQuery();
			if (docQuery.getConditions() == null
					|| docQuery.getConditions().size() == 0) {
				logger.info("DocQuery:" + docQuery + " trans Query:"
						+ booleanQuery + " failed,conditions is null");
				return null;
			}
			for (DocCondition docCondition : docQuery.getConditions()) {
				booleanQuery.add(new DocConditionAdaptaBooleanClause()
						.adapta(docCondition));
			}
			logger.info("DocQuery:" + docQuery + " trans Query:" + booleanQuery
					+ " success");
			return booleanQuery;
		}
		logger.error("DocQuery:" + docQuery + " trans Query failed");
		return null;
	}

}
