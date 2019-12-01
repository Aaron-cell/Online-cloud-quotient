package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl	implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		// 根据parentId查询节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList=new ArrayList<>();
		for(TbContentCategory tbContentCategory: list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			node.setText(tbContentCategory.getName());
			resultList.add(node);
		}
		return resultList;
	}
	
	/**
	 * 插入一条数据
	 * 并返回此条数据自增的id
	 * select last_insert_id()
	 * 取当前事务中最后一次生成的id 
	 * 去maapper中改文件
	 */
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//状态 可选值：1（正常） 2（删除）
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.insert(contentCategory);
		//查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);
	}
	
	/*
	 * 删除分类
	 * 此处要考虑如果直接删除父分类 则子分类也会一并删除
	 */
	@Override
	public TaotaoResult deleteContentCategory(long id) {
		TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		//判断是否为父分类  如果为父分类 则子分类全部删除
		if(tbContentCategory.getIsParent()) {
			TbContentCategoryExample example1 = new TbContentCategoryExample();
			Criteria criteria1 = example1.createCriteria();
			criteria1.andParentIdEqualTo(id);
			contentCategoryMapper.deleteByExample(example1);
		}
		//删除分类
		contentCategoryMapper.deleteByPrimaryKey(id);
		//判断parentId对应的状态 是否为父节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(tbContentCategory.getParentId());
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if(list.size()<=0) {
			TbContentCategory contentCategory = new TbContentCategory();
			contentCategory.setId(tbContentCategory.getParentId());
			contentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		}
		return TaotaoResult.ok();
	}
	
	/*
	 * 修改分类
	 */
	@Override
	public TaotaoResult updateContentCategory(TbContentCategory contentCategory) {
		//更新分类名称
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		
		return TaotaoResult.ok();
	}

}
