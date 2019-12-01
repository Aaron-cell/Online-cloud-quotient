package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.taotao.service.ItemParamItemService;

/**
 * Function：展示商品规格页面
 * @author Aaron
 *
 */
@Controller
public class ItemParamItemController {
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/showItem/{itemId}")
	public String showItemParam(@PathVariable Long itemId,Model model) {
		String paramData=itemParamItemService.getItemParamByItemId(itemId);
		model.addAttribute("itemParam",paramData);
		return "item";
	}
}
