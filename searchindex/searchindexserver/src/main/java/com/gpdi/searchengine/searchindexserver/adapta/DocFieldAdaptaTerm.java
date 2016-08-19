package com.gpdi.searchengine.searchindexserver.adapta;

import org.apache.lucene.index.Term;

import com.gpdi.searchengine.commonservice.api.entity.index.DocField;

/**
 * 
 * @description: TODO(将DocField转为Term)
 * @author zhangwu
 * @date 2016年8月11日
 * @version 1.0.0
 */
public class DocFieldAdaptaTerm implements Adapta<DocField, Term> {

	@Override
	public Term adapta(DocField docField) {
		// TODO Auto-generated method stub
		Term term = new Term(docField.getFieldName(), docField.getFieldValue());
		return term;
	}

}
