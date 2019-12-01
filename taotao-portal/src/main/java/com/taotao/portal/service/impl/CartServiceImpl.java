package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
/**
 * 
 * @Title: CartServiceImpl
 * Function:添加购物车商品
 * @author: Aaron
 * @data: 2019年7月23日
 */
@Service
public class CartServiceImpl implements CartService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	@Override
	public TaotaoResult addCartItem(long itemId, int num,
			HttpServletRequest request,HttpServletResponse response) {
		CartItem cartItem = null;
		//取购物车商品列表
		List<CartItem> list = getCartItemList(request);
		//判断购物车商品列表中是否存在此商品
		for(CartItem cItem : list) {
			if(cItem.getId()==itemId) {
				//增加商品数量
				cItem.setNum(cItem.getNum()+num);
				cartItem=cItem;
				break;
			}
		}
		if(cartItem==null) {
			cartItem=new CartItem();
			// 根据商品id查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
			//把json转换为java对象
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
			if(taotaoResult.getStatus()==200) {
				TbItem item=(TbItem) taotaoResult.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setImage(item.getImage()==null?"":item.getImage().split(",")[0]);
				cartItem.setNum(num);
				cartItem.setPrice(item.getPrice());
			}
			//添加到购物车列表
			list.add(cartItem);
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);
		return TaotaoResult.ok(cartItem);
	}
	/**
	 * 
	 * @Description:获取cookie中商品列表
	 * @param: @param request
	 * @param: @return
	 * @return: List<CartItem>
	 * @throws
	 */
	private  List<CartItem> getCartItemList(HttpServletRequest request){
		//从cookie中获取商品列表
		String cookieValue = CookieUtils.getCookieValue(request, "TT_CART", true);
		if(cookieValue==null) {
			return new ArrayList<>();
		}
		try {
			//把json转换成商品列表
			List<CartItem> list = JsonUtils.jsonToList(cookieValue, CartItem.class);
			return list;			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();

	}
	
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> itemList = getCartItemList(request);
		return itemList;
	}
	
	@Override
	public TaotaoResult updateCartItemNum(HttpServletRequest request, HttpServletResponse response, long itemId,
			int num) {
		//取购物车商品列表
		List<CartItem> list = getCartItemList(request);
		//判断购物车商品列表中是否存在此商品
		for(CartItem cItem : list) {
			if(cItem.getId()==itemId) {
				//增加商品数量
				cItem.setNum(num);
				break;
			}
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);
		return TaotaoResult.ok();
	}
	
	/**
	 * 删除商品
	 */
	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		//取购物车商品列表
		List<CartItem> list = getCartItemList(request);
		//判断购物车商品列表中是否存在此商品
		for(CartItem cItem : list) {
			if(cItem.getId()==itemId) {
				//增加商品数量
				list.remove(cItem);
				break;
			}
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);
		return TaotaoResult.ok();
		
	}
	
}
