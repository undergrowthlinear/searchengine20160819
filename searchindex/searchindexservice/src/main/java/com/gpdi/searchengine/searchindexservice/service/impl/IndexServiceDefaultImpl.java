package com.gpdi.searchengine.searchindexservice.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.common.Constants;
import com.gpdi.searchengine.commonservice.util.StringUtils;
import com.gpdi.searchengine.searchindexservice.conf.NRTManagerServiceConf;
import com.gpdi.searchengine.searchindexservice.core.NRTManagerService;
import com.gpdi.searchengine.searchindexservice.core.SearcherManagerService;
import com.gpdi.searchengine.searchindexservice.service.IndexService;

/**
 * 索引服务，对索引进行管理，管理NRTManagerService，负责索引的创建、索引的更新、索引的删除
 * 
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 11:21:06
 */
public class IndexServiceDefaultImpl implements IndexService {

	private Logger logger = LoggerFactory
			.getLogger(IndexServiceDefaultImpl.class);

	public Map<String, NRTManagerService> nrtManagerServices;
	private NRTManagerServiceConf nrtConf;
	// 分析器
	private Analyzer analyzer;

	public Map<String, NRTManagerService> getNrtManagerServices() {
		return nrtManagerServices;
	}

	public void setNrtManagerServices(
			Map<String, NRTManagerService> nrtManagerServices) {
		this.nrtManagerServices = nrtManagerServices;
	}

	public NRTManagerServiceConf getNrtConf() {
		return nrtConf;
	}

	public void setNrtConf(NRTManagerServiceConf nrtConf) {
		this.nrtConf = nrtConf;
	}

	/**
	 * 初始化的时候 根据区域列表 打开特定目录的索引器和搜索器
	 * 
	 * @return
	 */
	public boolean initIndexSearcher() {
		nrtManagerServices = new HashMap<String, NRTManagerService>();
		logger.info("nrtConf:" + nrtConf);
		for (String areaCode : nrtConf.getAreaList()) {
			NRTManagerService nrtManagerService = new NRTManagerService(
					nrtConf, areaCode, getAnalyzer());
			nrtManagerServices.put(areaCode, nrtManagerService);
		}
		return true;
	}

	/**
	 * 当索引创建后 使用nrtManager创建对应的SearchManager 提供近实时功能
	 * 
	 * @return
	 */
	public Map<String, SearcherManagerService> getSearcherManagerServices() {
		Map<String, SearcherManagerService> searcherManagerServices = new HashMap<String, SearcherManagerService>();
		for (Map.Entry<String, NRTManagerService> areaNrtManager : nrtManagerServices
				.entrySet()) {
			searcherManagerServices.put(areaNrtManager.getKey(),
					new SearcherManagerService(areaNrtManager.getValue()
							.getSearcherManager(), areaNrtManager.getValue()
							.getAnalyzer()));
		}
		return searcherManagerServices;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gpdi.searchengine.searchindexservice.IndexService#addIndex(java
	 * .lang.String,org.apache.lucene.document.Document)
	 */
	@Override
	public boolean addIndex(String areaCode, Document document) {
		// TODO Auto-generated method stub
		if (!getNrtManagerService(areaCode)) {
			return false;
		}
		if (document == null) {
			logger.error("document " + document + " is null");
			return false;
		}
		return nrtManagerServices.get(areaCode).addIndex(document);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gpdi.searchengine.searchindexservice.IndexService#updateIndex(java
	 * .lang.String, org.apache.lucene.index.Term,
	 * org.apache.lucene.document.Document)
	 */
	@Override
	public boolean updateIndex(String areaCode, Term term, Document document) {
		if (!getNrtManagerService(areaCode)) {
			return false;
		}
		if (document == null || term == null) {
			logger.error("document " + document + " is null or term " + term
					+ "is null");
			return false;
		}
		return nrtManagerServices.get(areaCode).updateIndex(term, document);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gpdi.searchengine.searchindexservice.IndexService#deleteIndex(java
	 * .lang.String, org.apache.lucene.search.Query)
	 */
	@Override
	public boolean deleteIndex(String areaCode, Query query) {
		if (!getNrtManagerService(areaCode)) {
			return false;
		}
		if (query == null) {
			logger.error("query " + query + " is null");
			return false;
		}
		return nrtManagerServices.get(areaCode).deleteIndex(query);
	}

	/**
	 * 查找特定区域的索引管理器
	 * 
	 * @param areaCode
	 * @return
	 */
	private boolean getNrtManagerService(String areaCode) {
		if (StringUtils.isEmpty(areaCode)) {
			logger.error("areaCode " + areaCode + " is null");
			return false;
		}
		NRTManagerService nrtManagerService = nrtManagerServices.get(areaCode);
		if (nrtManagerService == null) {
			logger.error("areaCode " + areaCode
					+ " index failed,can't find areaCode");
			return false;
		}
		return true;
	}

	@Override
	public boolean commitIndex(String areaCode) {
		// TODO Auto-generated method stub
		// 支持全部提交索引
		if (!StringUtils.isBlank(areaCode)
				&& Constants.COMMIT_INDEX_ALL_AREA.equals(areaCode)) {
			for (String singleAreaCode : this.getNrtManagerServices().keySet()) {
				commitIndexInternal(singleAreaCode);
			}
		} else {
			commitIndexInternal(areaCode);
		}
		return true;

	}

	/**
	 * 逐个提交索引
	 * 
	 * @param areaCode
	 * @return
	 */
	private boolean commitIndexInternal(String areaCode) {
		if (!getNrtManagerService(areaCode)) {
			return false;
		}
		return nrtManagerServices.get(areaCode).commitIndex();
	}

	public boolean closeIndex(String areaCode) {
		if (!getNrtManagerService(areaCode)) {
			return false;
		}
		return nrtManagerServices.get(areaCode).closeIndex();
	}

}