package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@Autowired
	private TbContentMapper contentMapper;
	
	/*
	 * 读取分类列表
	 */
	@Override
	public EUDateGridResult getContentList(long categoryId, int page, int rows) {
		 TbContentExample example = new TbContentExample();
		 PageHelper.startPage(page, rows);
		 Criteria criteria = example.createCriteria();
		 criteria.andCategoryIdEqualTo(categoryId);
		 List<TbContent> list = contentMapper.selectByExample(example);
		 EUDateGridResult result = new EUDateGridResult();
		 result.setRows(list);
		 PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		 result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult insertContent(TbContent content) {
		//补全content对象信息
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//添加缓存同步逻辑
		try {
			System.out.println(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		} catch (Exception e) {
			// 如果调用服务失败 可以通过发短信 邮箱的方式通知管理员
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult selectContentById(long id) {
		TbContent tbContent = contentMapper.selectByPrimaryKey(id);
		return TaotaoResult.ok(tbContent);
	}

	@Override
	public TaotaoResult updateContent(TbContent tbContent) {
		tbContent.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(tbContent);
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+tbContent.getCategoryId());
		} catch (Exception e) {
			// 如果调用服务失败 可以通过发短信 邮箱的方式通知管理员
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(long[] id) {
		TbContent tbContent = contentMapper.selectByPrimaryKey(id[0]);
		 for(long ids : id) {
			 contentMapper.deleteByPrimaryKey(ids);
		 }
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+tbContent.getCategoryId());
		} catch (Exception e) {
			// 如果调用服务失败 可以通过发短信 邮箱的方式通知管理员
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

}
