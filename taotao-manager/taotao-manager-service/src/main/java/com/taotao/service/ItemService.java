package com.taotao.service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.pojo.TbItem;
/**
 * Function:商品查询
 * @author Aaron
 * Date:2019.5.9
 */
public interface ItemService {
	TbItem getItemById(long id);
	
	EUDateGridResult getItemList(int page,int rows);
	
	/*
	 * 接收item对象，返回taotaoResult
	 */
	TaotaoResult createItem(TbItem item,String desc,String itemParam)throws Exception;
	
	/*
	 * 查询商品描述 返回TaotaoResult
	 */
	TaotaoResult selectItemDescByItemId(Long itemId);
	
	/*
	 * 查询商品对应的模板，返回TaotaoResult
	 */
	TaotaoResult selectItemParamItemByItemId(Long itemId);
	
	/**
	 * 修改商品信息
	 * @throws Exception 
	 */
	TaotaoResult updateItem(TbItem item,String desc,String itemParam) throws Exception;
	/*
	 * 删除商品信息，商品描述，商品规格信息
	 */
	TaotaoResult deleteItem(long id[]) throws Exception;
	/*
	 * 商品下架
	 */
	TaotaoResult updateItemStatusInstock(long id[]);
	/*
	 * 商品上架
	 */
	TaotaoResult updateItemStatusReshelf(long id[]);

}
