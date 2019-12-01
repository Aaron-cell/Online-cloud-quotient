package com.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
/**
 * 
 * @Title: UserService
 * Function:用户服务接口
 * @author: Aaron
 * @data: 2019年7月18日
 */
public interface UserService {
	TaotaoResult checkData(String content,Integer type);
	
	TaotaoResult createUser(TbUser user);
	
	TaotaoResult userLogin(String username,String password, HttpServletRequest request, HttpServletResponse response);
	
	TaotaoResult getUserByToken(String token);
	
	TaotaoResult userLogoutByToken(String token);
}
