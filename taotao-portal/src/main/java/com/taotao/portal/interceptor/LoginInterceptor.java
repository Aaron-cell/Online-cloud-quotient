package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserServiceImpl userService;
	/*
	 * 在handler执行之前处理
	 * 返回值决定handler是否执行。true执行 false表示不执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从cookie中获取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//根据token获取用户信息,调用sso接口
		TbUser user = userService.getUserByToken(token);
		if(null==user) {
			//调转到登录页面，把用户请求的url作为参数传递给登录页面
			response.sendRedirect(userService.SSO_DOMAIN_BASE_URL+userService.SSO_PAGE_LOGIN
					+"?redirect="+request.getRequestURI());
			return false;
		}
		//取到用户信息，放行
		request.setAttribute("user", user);
		return true;
	}
	
	/*
	 * 在handler处理之后 返回model之前处理
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * 在所有完成之后处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
