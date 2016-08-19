package com.gpdi.searchengine.searchindexservice.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gpdi.searchengine.commonservice.api.entity.SearchResult;
import com.gpdi.searchengine.searchindexservice.conf.NRTManagerServiceConf;
import com.gpdi.searchengine.searchindexservice.core.NRTManagerService;
import com.gpdi.searchengine.searchindexservice.service.impl.IndexServiceDefaultImpl;
import com.gpdi.searchengine.searchindexservice.service.impl.SearchServiceDefaultImpl;

public class IndexSearchServiceTest {

	IndexServiceDefaultImpl indexService = null;
	SearchServiceDefaultImpl searchSevice = null;
	Properties appProper = null;
	ApplicationContext context = null;

	@Before
	public void before() throws IOException {
		context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		indexService = (IndexServiceDefaultImpl) context
				.getBean("indexService");
		searchSevice = (SearchServiceDefaultImpl) context
				.getBean("searchService");
		assertNotNull("indexService load error,indexService is null",
				indexService);
		assertNotNull("searchSevice load error,searchSevice is null",
				searchSevice);
		appProper = new Properties();
		appProper.load(IndexSearchServiceTest.class.getClassLoader()
				.getResourceAsStream("app.properties"));
	}

	@After
	public void after() {
		for (Map.Entry<String, NRTManagerService> neEntry : indexService
				.getNrtManagerServices().entrySet()) {
			neEntry.getValue().closeIndex();
		}
	}

	@Test
	public void testGetNrtManagerServices() throws InterruptedException {
		// 测试配置
		NRTManagerServiceConf conf = indexService.getNrtConf();
		assertNotNull("conf can't null", conf);
		assertEquals(
				"load areaListRight",
				((String) appProper.get("searchindex.areaCode")).split(",").length,
				conf.getAreaList().length);
		//
		Map<String, NRTManagerService> nrtManagerServices = indexService
				.getNrtManagerServices();
		String[] areaList = ((String) appProper.get("searchindex.areaCode"))
				.split(",");
		assertEquals("load nrtManagerServices", areaList.length,
				nrtManagerServices.size());
		String areaCode = areaList[0];
		// 删除索引
		indexService.deleteIndex(areaCode, new WildcardQuery(new Term("name",
				"蒙自源")));
		// 索引
		Document document = createDocument();
		indexService.addIndex(areaCode, document);
		// 等待重新打开索引文件
		Thread.sleep((long) indexService.getNrtConf().getTargetMaxScaleSec() * 1000);
		// 搜索
		TermQuery query = new TermQuery(new Term("name", "蒙自源"));
		SearchResult searchResult = searchSevice.search(areaCode, query, 100);
		assertNotNull("searchResult can't null", searchResult);
		assertEquals("hit error", 1, searchResult.getSearchResult().size());
		indexService.commitIndex(areaCode);
		Thread.sleep((long) indexService.getNrtConf().getTargetMaxScaleSec() * 1000);
	}

	private Document createDocument() {
		Document document = new Document();
		document.add(new Field("name", "蒙自源", Store.YES, Index.NOT_ANALYZED));
		document.add(new Field("address", "广东省广州市天河区", Store.YES,
				Index.ANALYZED));
		document.add(new Field("phone", "13786788", Store.YES,
				Index.NOT_ANALYZED));
		document.add(new Field("description", "主要经营云南过桥米线", Store.YES,
				Index.ANALYZED));
		return document;
	}

}
