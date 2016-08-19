package com.gpdi.searchengine.searchindexserver.adapta;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.index.DocField;
import com.gpdi.searchengine.commonservice.api.entity.index.DocField.DocFieldType;

/**
 * 
 * @description: TODO(将DocField转为Fieldable)
 * @author zhangwu
 * @date 2016年8月11日
 * @version 1.0.0
 */
public class DocFieldAdaptaField implements Adapta<DocField, Fieldable> {

	private Logger logger = LoggerFactory.getLogger(DocFieldAdaptaField.class);

	@Override
	public Fieldable adapta(DocField docField) {
		// TODO Auto-generated method stub
		Fieldable fieldable = null;
		if (docField.getDocFieldType() == DocFieldType.STRING) {
			fieldable = new Field(docField.getFieldName(),
					docField.getFieldValue(), Store.valueOf(docField.getStore()
							.name()), Index.valueOf(docField.getIndex().name()));
		} else if (docField.getDocFieldType() == DocFieldType.NUMERIC) {
			fieldable = new NumericField(docField.getFieldName(),
					Store.valueOf(docField.getStore().name()), docField
							.getIndex().isIndexed()).setDoubleValue(Double
					.valueOf(docField.getFieldValue()));
		}
		logger.info("trans docField to fieldable:" + fieldable);
		return fieldable;
	}
}
