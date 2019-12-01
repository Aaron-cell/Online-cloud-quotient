package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.service.ContentCategoryService;

/**
 * Function:内容分类管理
 * @author Aaron
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0") long parentId){
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	//创建子节点
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentcategory(long parentId,String name) {
		TaotaoResult result=contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	
	/*
	 * 删除 商品分类
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteContentCategory(long id) {
		//System.out.println(id+","+parentId);
		TaotaoResult result = contentCategoryService.deleteContentCategory(id);
		return result;
	}
	
	/*
	 * 修改分类
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateContentCategory(TbContentCategory contentCategory) {
		TaotaoResult result = contentCategoryService.updateContentCategory(contentCategory);
		return result;
	}
}
