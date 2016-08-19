package com.gpdi.searchengine.searchindexserver.adapta;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.query.DocCondition;

public class DocConditionAdaptaBooleanClause implements
		Adapta<DocCondition, BooleanClause> {

	private Logger logger = LoggerFactory
			.getLogger(DocConditionAdaptaBooleanClause.class);

	@Override
	public BooleanClause adapta(DocCondition docCondition) {
		// TODO Auto-generated method stub
		if (docCondition != null) {
			// 后续需要改进
			Query query = new TermQuery(new Term(
					docCondition.getDocField().getFieldName(), docCondition.getDocField()
							.getFieldValue()));
			BooleanClause booleanClause = new BooleanClause(query,
					Occur.valueOf(docCondition.getConditionOccur().name()));
			logger.info("DocCondition:" + docCondition + " trans BooleanClause:"
					+ booleanClause + " success");
			return booleanClause;
		}
		logger.error("DocCondition:" + docCondition + " trans BooleanClause failed");
		return null;
	}
}
