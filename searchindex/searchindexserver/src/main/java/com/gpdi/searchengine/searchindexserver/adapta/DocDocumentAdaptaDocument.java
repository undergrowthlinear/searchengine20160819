package com.gpdi.searchengine.searchindexserver.adapta;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.index.DocField;
import com.gpdi.searchengine.commonservice.api.entity.index.DocObject;

/**
 * 
 * @description: TODO(将DocObject转为Document)
 * @author zhangwu
 * @date 2016年8月11日
 * @version 1.0.0
 */
public class DocDocumentAdaptaDocument implements Adapta<DocObject, Document> {

	private Logger logger = LoggerFactory
			.getLogger(DocDocumentAdaptaDocument.class);

	@Override
	public Document adapta(DocObject docObject) {
		// TODO Auto-generated method stub
		if (docObject != null) {
			Document document = new Document();
			if (docObject.getDocFields() == null
					|| docObject.getDocFields().size() == 0) {
				logger.error("trans docObject to document:" + document
						+ " failed,docFields is null");
				return null;
			}
			for (DocField docField : docObject.getDocFields()) {
				Fieldable field = new DocFieldAdaptaField().adapta(docField);
				document.add(field);
			}
			logger.info("trans docObject to document:" + document);
			return document;
		}
		return null;
	}

}
