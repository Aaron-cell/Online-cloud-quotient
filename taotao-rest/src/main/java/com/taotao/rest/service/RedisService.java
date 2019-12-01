package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

public interface RedisService {
	//redis中同步首页内容
	TaotaoResult syncContent(long contentCid);
}
