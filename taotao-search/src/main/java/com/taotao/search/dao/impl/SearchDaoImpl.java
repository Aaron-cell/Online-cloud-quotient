package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

/**
 * Function:商品搜索dao
 * @author Aaron
 * Date:2019.7.14
 */
@Repository
public class SearchDaoImpl implements SearchDao {
	@Autowired
	private SolrServer solrServer;
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		//返回值对象
		SearchResult result = new SearchResult();
		//根据查询条件查询索引库
		QueryResponse response = solrServer.query(query);
		//获取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		//获取查询总数量
		result.setRecordCount(solrDocumentList.getNumFound());
		//商品列表
		List<Item> itemlist=new ArrayList<>();
		//取高亮显示
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//获取商品列表
		for(SolrDocument solrDocument : solrDocumentList) {
			//创建一个商品对象
			Item item=new Item();
			item.setId((String) solrDocument.get("id"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title="";
			if(list!=null && list.size()>0) {
				title=list.get(0);
			}else {
				title=(String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			/*
			 * solr中没有存放商品描述 所以这里也无法取出
			 */
			itemlist.add(item);
		}
		result.setItemList(itemlist);
		return result;
	}

}
