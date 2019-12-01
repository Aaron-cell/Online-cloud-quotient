package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.service.UserLogoutService;
@Service
public class UserLogoutServiceImpl implements UserLogoutService {
	
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	
	@Value("${SSO_LOGOUT_URL}")
	private String SSO_LOGOUT_URL;
	
	@Override
	public TaotaoResult userLogoutBytoken(String token) {
		
		//System.out.println(token);
		//System.out.println(SSO_BASE_URL+SSO_LOGOUT_URL+token);
		HttpClientUtil.doGet(SSO_BASE_URL+SSO_LOGOUT_URL+token);
		return TaotaoResult.ok();
	}

}
