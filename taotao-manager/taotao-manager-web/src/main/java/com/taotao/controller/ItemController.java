package com.taotao.controller;

import org.omg.CORBA.LongLongSeqHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * Function:商品管理
 * @author Aaron
 * Date:2019.5.9
 */
@Controller
public class ItemController {
	String category="all";
	String defaultValue=null;
	@Autowired
	private ItemService itemService;
	/*
	 * 显示所有商品列表
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDateGridResult getItemList(Integer page,Integer rows) {
		
		EUDateGridResult result=itemService.getItemList(page, rows);
		return result;
	}
	/*
	 * 存储商品信息
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult creatItem(TbItem item, String desc, String itemParams) throws Exception {
		TaotaoResult taotaoResult=itemService.createItem(item, desc, itemParams);
		return taotaoResult;
	}
	
	/*
	 * 跳转到item-edit页面
	 */
	@RequestMapping("/rest/page/item-edit")
	public String itemEdit() {
		return "item-edit";
	}
	
	/*
	 * 点击编辑 加载商品描述
	 * 返回json格式数据
	 */
	@RequestMapping("/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		TaotaoResult result = itemService.selectItemDescByItemId(itemId);
		return result;	
	}
	
	/*
	 *点击编辑 加载商品规格
	 *返回json格式数据
	 */
	@RequestMapping("/rest/item/param/item/query/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParamItem(@PathVariable Long itemId) {
		TaotaoResult result = itemService.selectItemParamItemByItemId(itemId);
		return result;
	}
	
	/**
	 * 修改商品信息
	 * @throws Exception 
	 * 
	 */
	@RequestMapping(value="/rest/item/update",method =RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItem(TbItem item,String desc, String itemParams) throws Exception {
		TaotaoResult result = itemService.updateItem(item, desc, itemParams);
		return result;
	}
	
	/**
	 * 删除商品信息 描述信息 商品规格信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteItem(String ids) throws Exception {
		/*
		 * 将字符串封装成long型数组
		 */
		String[] arr=ids.split(",");
		long[] id1=new long[arr.length];
		for(int i=0;i<arr.length;i++) {
			long id=Long.parseLong(arr[i]);
			id1[i]=id;
		}
		TaotaoResult result = itemService.deleteItem(id1); 
		return result;	
	}
	
	/**
	 * 商品下架
	 */
	@RequestMapping(value="/rest/item/instock",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult itemInstock(String ids) {
		/*
		 * 将字符串封装成long型数组
		 */
		String[] arr=ids.split(",");
		long[] id1=new long[arr.length];
		for(int i=0;i<arr.length;i++) {
			long id=Long.parseLong(arr[i]);
			id1[i]=id;
		}
		TaotaoResult result = itemService.updateItemStatusInstock(id1);
		return result;
	}
	
	/*
	 * 商品上架
	 */
	@RequestMapping(value="/rest/item/reshelf",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult itemReshelf(String ids) {
		/*
		 * 将字符串封装成long型数组
		 */
		String[] arr=ids.split(",");
		long[] id1=new long[arr.length];
		for(int i=0;i<arr.length;i++) {
			long id=Long.parseLong(arr[i]);
			id1[i]=id;
		}
		TaotaoResult result = itemService.updateItemStatusReshelf(id1);
		return result;
	}
	

}
