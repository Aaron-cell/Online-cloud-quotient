package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemService;
/**
 * Function:通过id查询tb_item信息
 * @author Aaron
 * Date：2019.5.9
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper; 
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	/**
	 * 通过商品id查询
	 */
	@Override
	public TbItem getItemById(long id) {
		TbItem item=itemMapper.selectByPrimaryKey(id);
		return item;
	}
	
	/**
	 * 商品列表查询
	 */
	@Override
	public EUDateGridResult getItemList(int page, int rows) {
		TbItemExample example=new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list=itemMapper.selectByExample(example);
		//创建EUDateGridResult对象
		EUDateGridResult result=new EUDateGridResult();
		result.setRows(list);
		//取total值
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item,String desc,String itemParam)throws Exception {
		//item补全
		Long itemId=IDUtils.genItemId();
		item.setId(itemId);
		//'商品状态，1-正常，2-下架，3-删除'
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入数据库
		itemMapper.insert(item);
		//插入商品信息
		TaotaoResult result=insertItemDesc(itemId, desc);
		//插入商品规格
		TaotaoResult result1=insertItemParamItem(itemId, itemParam);
		//这里判断result状态码，如果不是200抛出一个异常，springmvc会自动回滚，千万不要加try....catch不然springmvc认为这里没有异常，不会回滚
		if(result.getStatus()!=200 || result1.getStatus()!=200) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	/*
	 * 补全pojo对象信息 插入商品描述
	 */
	public TaotaoResult insertItemDesc(Long itemId,String desc) {
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	
	/*
	 * 补全pojo对象信息 插入商品规格信息
	 */
	public TaotaoResult insertItemParamItem(Long itemId,String itemParam) {
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
		
	}

	/*
	 * 通过itemId查询商品描述 返回TaotaoResult
	 */
	@Override
	public TaotaoResult selectItemDescByItemId(Long itemId) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		return TaotaoResult.ok(itemDesc);
	}

	/*
	 * 通过itemId查询商品模板 返回TaotaoResult
	 */
	@Override
	public TaotaoResult selectItemParamItemByItemId(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list==null || list.size()==0) {
			return TaotaoResult.ok();
		}
		return TaotaoResult.ok(list.get(0));
	}
	
	
	/**
	 * 修改商品信息
	 * 
	 */
	@Override
	public TaotaoResult updateItem(TbItem item, String desc, String itemParam)throws Exception {
		item.setUpdated(new Date());
		//更新商品信息
		itemMapper.updateByPrimaryKeySelective(item);
		TaotaoResult result = updateItemDesc(item.getId(), desc);
		TaotaoResult result2 = updateItemParams(item.getId(), itemParam);
		if(result.getStatus()!=200 || result2.getStatus()!=200) {
			new Exception();
		}
		return TaotaoResult.ok();
	}
	
	/*
	 * 修改商品描述
	 */
	public TaotaoResult updateItemDesc(long itemId,String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		return TaotaoResult.ok();
	}
	/*
	 * 修改商品规格
	 */
	public TaotaoResult updateItemParams(Long itemId,String itemParam) {
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setUpdated(new Date());
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		itemParamItemMapper.updateByExampleSelective(itemParamItem, example);
		return TaotaoResult.ok();
	}

	/*
	 * 删除商品信息 商品描述 以及商品的规格
	 */
	@Override
	public TaotaoResult deleteItem(long[] id)throws Exception {
		//循环删除商品信息
		for(long itemId : id) {
			itemMapper.deleteByPrimaryKey(itemId);
		}
		TaotaoResult result = deleteItemDesc(id);
		TaotaoResult result2 = deleteItemParamItem(id);
		if(result.getStatus()!=200 || result2.getStatus()!=200) {
			new Exception();
		}
		return TaotaoResult.ok();
	}
	/*
	 * 删除商品描述方法
	 */
	public TaotaoResult deleteItemDesc(long[] id) {
		for(long itemId : id) {
			itemDescMapper.deleteByPrimaryKey(itemId);
		}
		return TaotaoResult.ok();
	}
	/*
	 * 删除商品规格
	 */
	public TaotaoResult deleteItemParamItem(long[] id) {
		for(long itemId : id) {
			TbItemParamItemExample example = new TbItemParamItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andItemIdEqualTo(itemId);
			itemParamItemMapper.deleteByExample(example);
		}
		return TaotaoResult.ok();
	}
	
	/*
	 * 商品下架
	 */
	@Override
	public TaotaoResult updateItemStatusInstock(long[] id) {
		//循环将商品下架
		for(long itemId : id) {
			TbItem item = new TbItem();
			item.setId(itemId);
			item.setStatus((byte) 2);
			itemMapper.updateByPrimaryKeySelective(item);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateItemStatusReshelf(long[] id) {
		//循环将商品上架
		for(long itemId : id) {
			TbItem item = new TbItem();
			item.setId(itemId);
			item.setStatus((byte) 1);
			itemMapper.updateByPrimaryKeySelective(item);
		}
		return TaotaoResult.ok();
	}
	

	
	

}
