package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
	/*
	 * 调用mapper查询商品类别cid在TbItemParam中是否有对应的模板 返回TaotaoResult
	 */
	TaotaoResult getItemParamBycid(long cid);
	/*
	 * 调用mapper向TbItemParam插入模板
	 */
	TaotaoResult insertItemParam(TbItemParam itemParam);
}
