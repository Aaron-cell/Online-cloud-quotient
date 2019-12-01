package com.taotao.search;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {
	@Test
	public void testAddDocument() {
		String zkHost="192.168.126.135:2181,192.168.126.135:2182,192.168.126.135:2183";
		//创建一个链接
		//参数是zookeeper地址列表，用逗号隔开
		CloudSolrServer solrServer=new CloudSolrServer(zkHost);
		//设置默认的collection
		solrServer.setDefaultCollection("collection2");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域
		document.addField("id", "test001");
		document.addField("item_title", "测试商品");
		try {
			//把文档添加到索引库
			solrServer.add(document);
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testDeleteDocument() throws Exception {
		String zkHost="192.168.126.135:2181,192.168.126.135:2182,192.168.126.135:2183";
		//创建一个链接
		//参数是zookeeper地址列表，用逗号隔开
		CloudSolrServer solrServer=new CloudSolrServer(zkHost);
		//设置默认的collection
		solrServer.setDefaultCollection("collection2");
		
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
}
