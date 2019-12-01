package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;
import com.taotao.service.impl.ItemCatServiceImpl;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	@RequestMapping("/list")
		//@ResponseBody注解 自动将返回结果解析为json串
	@ResponseBody
	//设置id不存在时默认为0
	public List<EUTreeNode> getCatList(@RequestParam(value="id",defaultValue="0") long parentId){
		List<EUTreeNode> list=itemCatService.getCatList(parentId);
		return list;
	}
}
