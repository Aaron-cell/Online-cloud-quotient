package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
/**
 * 商品信息管理Service
 * @author Aaron
 * date:2019.7.15
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		try {
			//添加缓存逻辑
			//从缓存中取商品信息
			String string = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+"base");
			if(!StringUtils.isBlank(string)) {
				TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
				return TaotaoResult.ok(item);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//根据商品id查询商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		try {
			//把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+"base",JsonUtils.objectToJson(item));
			//设置key的过期时间
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+"base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//使用taotaoResult进行封装
		return TaotaoResult.ok(item);
	}

	@Override
	public TaotaoResult getItemDesc(long itemId) {
		try {
			//添加缓存逻辑
			//从缓存中取商品信息
			String string = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+"desc");
			if(!StringUtils.isBlank(string)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(string, TbItemDesc.class);
				return TaotaoResult.ok(itemDesc);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		try {
			//把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+"desc",JsonUtils.objectToJson(itemDesc));
			//设置key的过期时间
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+"desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TaotaoResult getItemParam(long itemId) {
		try {
			//添加缓存逻辑
			//从缓存中取商品信息
			String string = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+"param");
			if(!StringUtils.isBlank(string)) {
				TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(string, TbItemParamItem.class);
				return TaotaoResult.ok(itemParamItem);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 根据商品id查询规格参数
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list!=null && list.size()>0) {
			TbItemParamItem itemParamItem = list.get(0);
			//添加缓存
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+"param",JsonUtils.objectToJson(itemParamItem));
				//设置key的过期时间
				jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+"param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaotaoResult.ok(itemParamItem);
		}
		return TaotaoResult.build(400, "无此商品规格");
	}

}
