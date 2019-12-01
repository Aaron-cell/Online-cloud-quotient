package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

public interface ContentCategoryService {
	/*
	 * 获取内容管理列表
	 */
	List<EUTreeNode> getCategoryList(long parentId);
	
	/*
	 * 创建分类
	 */
	TaotaoResult insertContentCategory(long parentId,String name);
	/*
	 * 删除分类
	 * 此处要考虑如果直接删除父分类 则子分类也会一并删除
	 */
	TaotaoResult deleteContentCategory(long id);
	
	/*
	 * 修改分类名称
	 */
	TaotaoResult updateContentCategory(TbContentCategory contentCategory);
}
