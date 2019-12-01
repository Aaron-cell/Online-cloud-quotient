package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * Function:对内容（Content）进行管理
 * @author Aaron
 * Date:2019.6.28
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	/*
	 * 读取内容列表
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EUDateGridResult getContentList(long categoryId, Integer page, Integer rows) {
		EUDateGridResult result = contentService.getContentList(categoryId, page, rows);
		return result;
	}
	
	/*
	 * 保存新增内容
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}
	
	/*
	 *根据内容id查询内容描述 
	 */
	@RequestMapping("/rest/query/content/{id}")
	@ResponseBody
	public TaotaoResult selectContentById(@PathVariable long id) {
		TaotaoResult result = contentService.selectContentById(id);
		return result;
	}
	
	/*
	 *更新内容信息
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent tbContent) {
		TaotaoResult result = contentService.updateContent(tbContent);
		return result;
	}
	
	/*
	 * 删除内容
	 */
	@RequestMapping(value="/content/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteContent(String ids) {
		/*
		 * 将字符串封装成long型数组
		 */
		String[] arr=ids.split(",");
		long[] id1=new long[arr.length];
		for(int i=0;i<arr.length;i++) {
			long id=Long.parseLong(arr[i]);
			id1[i]=id;
		}
		TaotaoResult result = contentService.deleteContent(id1);
		return result;
	}
}
