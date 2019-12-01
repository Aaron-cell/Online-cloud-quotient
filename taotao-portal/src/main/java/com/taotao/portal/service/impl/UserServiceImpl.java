package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
/**
 * 
 * @Title: UserServiceImpl
 * Function:获取用户信息service
 * @author: Aaron
 * @data: 2019年7月23日
 */
@Service
public class UserServiceImpl implements UserService {
	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_DOMAIN_BASE_URL}")
	public String SSO_DOMAIN_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;
	@Value("${SSO_PAGE_LOGIN}")
	public String SSO_PAGE_LOGIN;
	
	@Override
	public TbUser getUserByToken(String token) {
		try {
			//调用sso系统的服务，根据token获取用户信息
			String string = HttpClientUtil.doGet(SSO_BASE_URL+SSO_USER_TOKEN+token);
			//把json转换成TaotaoResult
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(string, TbUser.class);
			if(taotaoResult.getStatus()==200) {
				TbUser user = (TbUser) taotaoResult.getData();
				return user;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
