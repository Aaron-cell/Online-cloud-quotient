package com.taotao.portal.service.impl;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
/**
 * 订单服务
 * @Title: OrderServiceImpl
 * Function:
 * @author: Aaron
 * @data: 2019年8月3日
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	@Override
	public String createOrder(Order order) {
		//创建订单服务之前补全用户信息
		//从cookie中获取TT_token 解决方案 可以在拦截器中获取的用户信息 放入request中 这里直接在request中获取
		
		// 调用taotao-order的服务提交订单
		String string = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		//把json转换为taotaoresult
		TaotaoResult taotaoResult = TaotaoResult.format(string);
		if(taotaoResult.getStatus()==200) {
			Object orderId=taotaoResult.getData();
			return orderId.toString();
		}
		return "";
	}

}
