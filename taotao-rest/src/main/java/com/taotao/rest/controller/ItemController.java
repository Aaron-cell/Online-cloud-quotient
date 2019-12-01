package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 商品信息Controller
 * @author Aaron
 * @date 2019.7.15
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.rest.service.ItemService;
@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	/*
	 * 商品基本信息
	 */
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaotaoResult getItemBaseInfo(@PathVariable long itemId) {
		TaotaoResult taotaoResult = itemService.getItemBaseInfo(itemId);
		return taotaoResult;
	}
	/*
	 * 商品描述
	 */
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable long itemId) {
		TaotaoResult taotaoResult = itemService.getItemDesc(itemId);
		return taotaoResult;
	}
	/*
	 * 商品模板
	 */
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable long itemId) {
		TaotaoResult taotaoResult = itemService.getItemParam(itemId);
		return taotaoResult;
	}
}
