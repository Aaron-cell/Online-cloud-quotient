package com.taotao.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.service.ContentService;
import com.taotao.portal.service.UserLogoutService;

@Controller
public class IndexController {
	@Autowired
	private UserLogoutService userLogoutService;
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index.html")
	public String showIndex(HttpServletRequest request) {
		String adJson=contentService.getContentList();
		//System.out.println(adJson);
		request.setAttribute("ad1", adJson);
		return "index";
	}
	@RequestMapping("/user/logout/{token}.html")
	public String userLogout(@PathVariable String token) {
		
		TaotaoResult taotaoResult = userLogoutService.userLogoutBytoken(token);
		return "forward:/index.html";
	}
}
