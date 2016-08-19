package com.gpdi.searchengine.commonservice.api.entity.query;

/**
 * 
* @description: TODO(表示条件and or not)
* @author zhangwu
* @date 2016年8月11日
* @version 1.0.0
 */
public enum DocConditionOccur {

	/**
	 * Use this operator for clauses that <i>must</i> appear in the matching
	 * documents.
	 */
	MUST {
		@Override
		public String toString() {
			return "+";
		}
	},

	/**
	 * Use this operator for clauses that <i>should</i> appear in the matching
	 * documents. For a BooleanQuery with no <code>MUST</code> clauses one or
	 * more <code>SHOULD</code> clauses must match a document for the
	 * BooleanQuery to match.
	 * 
	 * @see BooleanQuery#setMinimumNumberShouldMatch
	 */
	SHOULD {
		@Override
		public String toString() {
			return "";
		}
	},

	/**
	 * Use this operator for clauses that <i>must not</i> appear in the matching
	 * documents. Note that it is not possible to search for queries that only
	 * consist of a <code>MUST_NOT</code> clause.
	 */
	MUST_NOT {
		@Override
		public String toString() {
			return "-";
		}
	};

}
