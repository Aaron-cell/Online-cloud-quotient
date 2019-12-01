package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @Title: PageController
 * Function:登录，注册页面跳转
 * @author: Aaron
 * @data: 2019年7月19日
 */
@Controller
@RequestMapping("/page")
public class PageController {
	
	@RequestMapping("/register")
	public String showRegister() {
		return "register";
	}
	@RequestMapping("/login")
	public String showLogin(String redirect,Model model) {
		//redirect代表的是域名 例如localhost：8080
		model.addAttribute("redirect", redirect);
		return "login";
	}
}
