package com.taotao.service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * Function:内容管理
 * @author Aaron
 * Date:2019.6.28
 */
public interface ContentService {
	/*
	 * 读取内容列表
	 */
	EUDateGridResult getContentList(long categoryId, int page, int rows);
	/*
	 * 保存新增内容
	 */
	TaotaoResult insertContent(TbContent content);
	/*
	 * 根据id查找内容描述
	 */
	TaotaoResult selectContentById(long id);
	/*
	 * 修改内容信息
	 */
	TaotaoResult updateContent(TbContent tbContent);
	/*
	 * 删除内容
	 */
	TaotaoResult deleteContent(long[] id);
}
