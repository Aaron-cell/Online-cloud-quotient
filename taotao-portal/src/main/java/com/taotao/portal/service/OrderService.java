package com.taotao.portal.service;

import org.apache.http.HttpRequest;

import com.taotao.portal.pojo.Order;

public interface OrderService {

	String createOrder(Order order);
}
