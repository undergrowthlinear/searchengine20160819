package com.gpdi.searchengine.searchindexserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gpdi.searchengine.commonservice.api.entity.SearchResult;
import com.gpdi.searchengine.commonservice.api.entity.index.DocField;
import com.gpdi.searchengine.commonservice.api.entity.index.DocField.DocFieldType;
import com.gpdi.searchengine.commonservice.api.entity.index.DocField.Index;
import com.gpdi.searchengine.commonservice.api.entity.index.DocField.Store;
import com.gpdi.searchengine.commonservice.api.entity.index.DocObject;
import com.gpdi.searchengine.commonservice.api.entity.query.DocCondition;
import com.gpdi.searchengine.commonservice.api.entity.query.DocConditionOccur;
import com.gpdi.searchengine.commonservice.api.entity.query.DocQuery;
import com.gpdi.searchengine.commonservice.api.service.DocIndexService;
import com.gpdi.searchengine.commonservice.api.service.DocSearchService;
import com.gpdi.searchengine.commonservice.util.ConfigUtil;

/**
 * 
 * @description: TODO(这里用一句话描述这个类的作用) 服务消费者测试
 * @author zhangwu
 * @date 2016年8月11日
 * @version 1.0.0
 */
public class IndexSearchClientTest {

	ApplicationContext context = null;
	DocIndexService indexServer;
	DocSearchService searchServer;
	Properties appProper = null;

	@Before
	public void before() throws IOException {
		context = new ClassPathXmlApplicationContext(
				"classpath:applicationContextTest.xml");
		indexServer = (DocIndexService) context.getBean("docIndexService");
		searchServer = (DocSearchService) context.getBean("docSearchService");
		assertNotNull("indexServer load error,indexServer is null", indexServer);
		assertNotNull("searchServer load error,searchServer is null",
				searchServer);
		appProper = ConfigUtil.loadProperties("app.properties");
	}

	@Test
	public void testIndexSearch() throws InterruptedException {
		String[] areaList = ((String) appProper.get("searchindex.areaCode"))
				.split(",");
		String areaCode = areaList[0];

		DocQuery query = createDocQuery();
		// 删除索引
		indexServer.deleteIndex(areaCode, query);
		// 索引
		DocObject docObject = createDocObject();
		indexServer.addIndex(areaCode, docObject);
		// 等待重新打开索引文件
		Thread.sleep(5 * 1000);
		// 搜索
		SearchResult searchResult = searchServer.search(areaCode, query, 100);
		assertNotNull("searchResult can't null", searchResult);
		assertEquals("hit error", 1, searchResult.getSearchResult().size());
		Thread.sleep(5 * 1000);
	}

	private DocQuery createDocQuery() {
		List<DocCondition> conditions = new ArrayList<DocCondition>();
		conditions.add(new DocCondition("name", "蒙自源", DocConditionOccur.MUST));
		DocQuery docQuery = new DocQuery(conditions);
		return docQuery;
	}

	private DocObject createDocObject() {
		// TODO Auto-generated method stub
		DocObject docObject = new DocObject();
		List<DocField> docFields = new ArrayList<DocField>();
		docFields.add(new DocField("name", "蒙自源", Store.YES,
				Index.NOT_ANALYZED, DocFieldType.STRING));
		docFields.add(new DocField("address", "广东省广州市天河区", Store.YES,
				Index.ANALYZED, DocFieldType.STRING));
		docFields.add(new DocField("phone", "13786788", Store.YES,
				Index.NOT_ANALYZED, DocFieldType.STRING));
		docFields.add(new DocField("description", "主要经营云南过桥米线", Store.YES,
				Index.ANALYZED, DocFieldType.STRING));
		docFields.add(new DocField("price", "123.4", Store.YES,
				Index.NOT_ANALYZED, DocFieldType.NUMERIC));
		docObject.setDocFields(docFields);
		return docObject;
	}

}
