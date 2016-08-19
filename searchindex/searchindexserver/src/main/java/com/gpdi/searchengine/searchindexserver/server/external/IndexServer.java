package com.gpdi.searchengine.searchindexserver.server.external;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.index.DocField;
import com.gpdi.searchengine.commonservice.api.entity.index.DocObject;
import com.gpdi.searchengine.commonservice.api.entity.query.DocQuery;
import com.gpdi.searchengine.commonservice.api.service.DocIndexService;
import com.gpdi.searchengine.commonservice.util.StringUtils;
import com.gpdi.searchengine.searchindexserver.adapta.DocDocumentAdaptaDocument;
import com.gpdi.searchengine.searchindexserver.adapta.DocFieldAdaptaTerm;
import com.gpdi.searchengine.searchindexserver.adapta.DocQueryAdaptaQuery;
import com.gpdi.searchengine.searchindexserver.server.Server;
import com.gpdi.searchengine.searchindexservice.service.IndexService;

/**
 * 索引服务，实现索引数据的增量添加，以dubbo服务化的方式对外提供服务
 * 
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 11:17:22
 */
public class IndexServer implements Server, DocIndexService {

	private Logger logger = LoggerFactory.getLogger(IndexServer.class);
	private IndexService indexService;

	public boolean start() {
		logger.info("start IndexServer success");
		return true;
	}

	@Override
	public boolean addIndex(String areaCode, DocObject docObject) {
		// TODO Auto-generated method stub
		try {
			if (StringUtils.isEmpty(areaCode)) {
				logger.error("areaCode " + areaCode + " is null");
				return false;
			} else if (docObject == null) {
				logger.error("docObject " + docObject + " is null");
				return false;
			}
			Document document = new DocDocumentAdaptaDocument()
					.adapta(docObject);
			if (document != null) {
				return indexService.addIndex(areaCode, document);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("addIndex," + e.getMessage());
		}
		logger.error("addIndex,areaCode:" + areaCode + ",docObject:"
				+ docObject + " trans document failed");
		return false;
	}

	@Override
	public boolean updateIndex(String areaCode, DocField docField,
			DocObject docObject) {
		// TODO Auto-generated method stub
		try {
			if (StringUtils.isEmpty(areaCode)) {
				logger.error("areaCode " + areaCode + " is null");
				return false;
			} else if (docObject == null || docField == null) {
				logger.error("docObject " + docObject + " is null or docField "
						+ docField + " is null");
				return false;
			}
			Document document = new DocDocumentAdaptaDocument()
					.adapta(docObject);
			Term term = new DocFieldAdaptaTerm().adapta(docField);
			if (document != null && term != null) {
				return indexService.updateIndex(areaCode, term, document);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("updateIndex," + e.getMessage());
		}
		logger.error("updateIndex,areaCode:" + areaCode + ",docField:"
				+ docField + ",docObject:" + docObject
				+ " trans document failed");
		return false;
	}

	@Override
	public boolean deleteIndex(String areaCode, DocQuery docQuery) {
		// TODO Auto-generated method stub
		try {
			if (StringUtils.isEmpty(areaCode)) {
				logger.error("areaCode " + areaCode + " is null");
				return false;
			}
			if (docQuery == null) {
				logger.error("docQuery " + docQuery + " is null");
				return false;
			}
			Query query = new DocQueryAdaptaQuery().adapta(docQuery);
			if (query != null) {
				return indexService.deleteIndex(areaCode, query);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("deleteIndex," + e.getMessage());
		}
		logger.error("deleteIndex,areaCode:" + areaCode + ",query:" + docQuery
				+ " trans query failed");
		return false;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

}