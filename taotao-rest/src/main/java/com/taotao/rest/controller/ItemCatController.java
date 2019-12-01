package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Function:商品分类列表
 * @author 1
 * Date:2019.6.24
 */
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	/*
	 * //produces设置返回类型为json 编码为utf-8
	 * 
	 * @RequestMapping(value="/itemcat/list",produces =
	 * MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	 * 
	 * @ResponseBody public String getItemCatList(String callback) { CatResult
	 * catResult = itemCatService.getItemCatList(); //把pojo转换为字符串 String
	 * json=JsonUtils.objectToJson(catResult); //拼装返回值 String
	 * result=callback+"("+json+");"; return result; }
	 */
	/*
	 * 另外一种方法
	 * springmvc4.1及以上才可以使用
	 */
	@RequestMapping("/itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callback) {
		CatResult catResult=itemCatService.getItemCatList();
		MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(catResult);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
}
