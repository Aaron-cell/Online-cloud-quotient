package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 
 * @Title: CartController
 * Function:购物车Controller
 * @author: Aaron
 * @data: 2019年7月23日
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
@Controller
@RequestMapping("/cart")
public class CartController {
	private CartItem cartItem;
	
	private int number;
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable long itemId,@RequestParam(defaultValue = "1")Integer num,
			HttpServletRequest request,HttpServletResponse response,Model model) {
		TaotaoResult taotaoResult = cartService.addCartItem(itemId, num, request, response);
		cartItem=(CartItem) taotaoResult.getData();
		number=num;
		return "redirect:/cart/success.html";
	}
	
	@RequestMapping("/success")
	public String showSuccess(Model model) {
		model.addAttribute("cartItem", cartItem);
		model.addAttribute("num", number);
		return "cartSuccess";
	}
	
	@RequestMapping("/cart.html")
	public String showCart(HttpServletRequest request,HttpServletResponse response,Model model) {
		List<CartItem> itemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", itemList);
		return "cart";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public String updateCartItemNum(@PathVariable long itemId,@PathVariable Integer num,
			HttpServletRequest request,HttpServletResponse response) {
		TaotaoResult taotaoResult = cartService.updateCartItemNum(request, response, itemId, num);
		return null;
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable long itemId,HttpServletRequest request,HttpServletResponse response) {
		cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}
}
