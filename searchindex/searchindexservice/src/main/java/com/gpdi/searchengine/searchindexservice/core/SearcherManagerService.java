package com.gpdi.searchengine.searchindexservice.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gpdi.searchengine.commonservice.api.entity.SearchResult;

/**
 * 负责搜索服务，管理<b>SearcherManager服务，提供IndexSearcher的获取与释放，
 * 同时SearcherManagerService中的Se archerManager来源于NRTManagerService</b>
 * 
 * @author zhangwu
 * @version 1.0
 * @created 08-8月-2016 11:21:06
 */
public class SearcherManagerService {

	private Logger logger = LoggerFactory
			.getLogger(SearcherManagerService.class);

	private SearcherManager searcherManager;
	// 分析器
	private Analyzer analyzer;

	public SearcherManagerService(SearcherManager searcherManager) {
		this.searcherManager = searcherManager;
	}

	public SearcherManagerService(SearcherManager searcherManager,
			Analyzer analyzer) {
		// TODO Auto-generated constructor stub
		this.searcherManager = searcherManager;
		this.analyzer = analyzer;
	}

	public SearchResult search(String fieldName, String searchValue, int num) {
		return search(fieldName, searchValue, num, Sort.RELEVANCE);
	}

	public SearchResult search(Query query, int num) {
		return search(query, num, Sort.RELEVANCE);
	}

	public SearchResult search(String fieldName, String searchValue, int num,
			Sort sort) {
		try {
			// 4.创建Query
			Query query = createQuery(fieldName, searchValue);
			return search(query, num, sort);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("createQuery  error:" + e.getMessage());
		}
		return null;
	}

	public SearchResult search(Query query, int num, Sort sort) {
		return search(query, num, sort, null);
	}

	public SearchResult search(Query query, int num, Sort sort, Filter filter) {
		logQuery(query, num, sort, filter);
		SearchResult searchResult = null;
		IndexSearcher searcher = null;
		try {
			searcher = getIndexSearcher();
			// 5.使用IndexSearcher搜索Query,返回TopDocs
			TopDocs topDocs = searcher.search(query, filter, num, sort);
			searchResult = transScoreDocsToSearchResult(searcher,
					topDocs.scoreDocs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("search  error:" + e.getMessage());
		} finally {
			releaseSearcher(searcher);
		}
		return searchResult;
	}

	private void logQuery(Query query, int num, Sort sort, Filter filter) {
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("query:" + query.toString() + "\t");
		logBuilder.append("num:" + num + "\t");
		if (filter != null) {
			logBuilder.append("filter:" + filter.toString() + "\t");
		}
		if (sort != null) {
			logBuilder.append("sort:" + sort.toString() + "\t");
		}
		logger.info(logBuilder.toString());
	}

	/**
	 * 转换结果集
	 * 
	 * @param searcher
	 * @param scoreDocs
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	private SearchResult transScoreDocsToSearchResult(IndexSearcher searcher,
			ScoreDoc[] scoreDocs) throws CorruptIndexException, IOException {
		// TODO Auto-generated method stub
		if (scoreDocs != null && scoreDocs.length > 0) {
			SearchResult searchResult = new SearchResult();
			Map<String, Map<String, String[]>> mapResult = new HashMap<String, Map<String, String[]>>();
			// 转换结果
			for (ScoreDoc scoreDoc : scoreDocs) {
				Document document = searcher.doc(scoreDoc.doc);
				// 存储文档的所有字段
				Map<String, String[]> mapDocumentField = new HashMap<String, String[]>();
				List<Fieldable> fieldables = document.getFields();
				for (Fieldable fieldable : fieldables) {
					mapDocumentField.put(fieldable.name(),
							document.getValues(fieldable.name()));
				}
				mapResult.put(String.valueOf(scoreDoc.doc), mapDocumentField);
			}
			logger.info("hits " + mapResult.size() + " result");
			searchResult.setSearchResult(mapResult);
			return searchResult;
		}
		logger.info("hits 0 result");
		return null;
	}

	private void releaseSearcher(IndexSearcher searcher) {
		try {
			if (searcher != null) {
				searcherManager.release(searcher);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("releaseSearcher  error:" + e.getMessage());
		}
	}

	public SearchResult searchPage(Query query, int indexPage, int pageNum) {
		logger.info("query:" + query + ",indexPage:" + indexPage + ",pageNum:"
				+ pageNum);
		SearchResult searchResult = null;
		IndexSearcher searcher = null;
		try {
			searcher = getIndexSearcher();
			// 4.创建Query
			// 5.使用IndexSearcher搜索Query,返回TopDocs
			int num = indexPage * pageNum;
			TopDocs topDocs = searcher.search(query, num);
			// 6.显示TopDocs的ScoreDoc
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			int disNum = 0;
			int start = (indexPage - 1) * pageNum;
			if (scoreDocs.length == num) {
				disNum = pageNum;
			} else if (scoreDocs.length > (indexPage - 1) * pageNum
					&& scoreDocs.length < num) {
				disNum = scoreDocs.length - (indexPage - 1) * pageNum;
			} else {
				disNum = 0;
				start = scoreDocs.length;
			}
			ScoreDoc[] pageScoreDocs = new ScoreDoc[disNum];
			int count = 0;
			for (int i = start; i < scoreDocs.length; i++) {
				pageScoreDocs[count++] = scoreDocs[i];
			}
			searchResult = transScoreDocsToSearchResult(searcher,
					topDocs.scoreDocs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("searchPage  error:" + e.getMessage());
		} finally {
			releaseSearcher(searcher);
		}
		return searchResult;
	}

	/**
	 * 支持分页查询
	 * 
	 * @param query
	 * @param indexPage
	 * @param pageNum
	 * @return
	 */
	public SearchResult searchPageAfter(Query query, int indexPage, int pageNum) {
		logger.info("query:" + query + ",indexPage:" + indexPage + ",pageNum:"
				+ pageNum);
		SearchResult searchResult = null;
		IndexSearcher searcher = null;
		try {
			searcher = getIndexSearcher();
			// 4.创建Query
			// 5.使用IndexSearcher搜索Query,返回TopDocs
			ScoreDoc after = getLastScoreDoc(searcher, query, indexPage,
					pageNum);
			ScoreDoc[] scoreDocs = null;
			if (indexPage != 1 && after == null) {// 表示超出了范围
				scoreDocs = null;
			} else {
				TopDocs topDocs = searcher.searchAfter(after, query, pageNum);
				scoreDocs = topDocs.scoreDocs;
			}
			searchResult = transScoreDocsToSearchResult(searcher, scoreDocs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("searchPageAfter  error:" + e.getMessage());
		} finally {
			releaseSearcher(searcher);
		}
		return searchResult;
	}

	private IndexSearcher getIndexSearcher() throws IOException {
		return searcherManager.acquire();
	}

	/**
	 * 查找前一页的最后一个元素
	 * 
	 * @param searcher
	 * @param query
	 * @param indexPage
	 * @param pageNum
	 * @return
	 * @throws IOException
	 */
	private ScoreDoc getLastScoreDoc(IndexSearcher searcher, Query query,
			int indexPage, int pageNum) throws IOException {
		// TODO Auto-generated method stub
		if (indexPage == 1)
			return null;
		int num = (indexPage - 1) * pageNum;
		TopDocs topDocs = searcher.search(query, num);
		if (topDocs.totalHits < num) {
			return null;
		}
		return topDocs.scoreDocs[num - 1];
	}

	private Query createQuery(String fieldName, String searchValue)
			throws ParseException {
		Analyzer a = getAnalyzer();
		QueryParser parser = new QueryParser(Version.LUCENE_35, fieldName, a);
		Query query = parser.parse(searchValue);
		return query;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

}