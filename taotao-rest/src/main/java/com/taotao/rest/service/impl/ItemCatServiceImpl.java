package com.taotao.rest.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_ITEM_CATEGORY}")
	private String INDEX_ITEM_CATEGORY;

	@Override
	public CatResult getItemCatList() {
		//读取缓存
		try {
			String result = jedisClient.get(INDEX_ITEM_CATEGORY);
			if(!StringUtils.isBlank(result)) {
				CatResult catResult =  JsonUtils.jsonToPojo(result, CatResult.class);
				return catResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 递归查询商品列表
		CatResult catResult = new CatResult();
		//查询分类列表
		catResult.setData(getCatList(0));
		try {
			String string=JsonUtils.objectToJson(catResult);
			jedisClient.set(INDEX_ITEM_CATEGORY, string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return catResult;
	}
	/**
	 * Function:查询分类列表
	 * @param parentId
	 * @return
	 */
	private List<?> getCatList(long parentId){
		
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//返回值
		List resultList=new ArrayList<>();
		/*
		 * 因为首页只能展示14栏 所以我们获取数据要添加限定
		 */
		int count=0;
		//向list中添加节点
		for(TbItemCat tbItemCat : list) {
			//如果是叶子节点
			if(tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if(parentId==0) {
					catNode.setName( "<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				}else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(catNode);
				count++;
				if(count>=14) {
					break;
				}
			}else {
				resultList.add( "/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		return resultList;
	}
}
