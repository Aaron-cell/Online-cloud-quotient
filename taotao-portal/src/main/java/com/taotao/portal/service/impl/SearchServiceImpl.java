package com.taotao.portal.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
/**
 * Function:商品搜索service
 * @author Aaron
 * Date:2019.7.15
 */
@Service
public class SearchServiceImpl implements SearchService {
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Override
	public SearchResult search(String queryString, int page) {
		// 调用taotao-search服务
		//查询参数
		HashMap<String,String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page+"");
		//调用服务
		try {
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
			if(taotaoResult.getStatus()==200) {
				SearchResult searchResult = (SearchResult) taotaoResult.getData();
				return searchResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
