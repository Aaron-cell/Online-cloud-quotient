package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;

public interface UserLogoutService {
	TaotaoResult userLogoutBytoken(String token);
}
