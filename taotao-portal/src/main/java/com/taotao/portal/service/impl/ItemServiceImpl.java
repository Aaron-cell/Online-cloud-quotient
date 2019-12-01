package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.ItemService;
/**
 * 获取商品信息Service
 * @author 1
 * Date:2019.7.16
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
	@Override
	public ItemInfo getItemById(long itemId) {
		try {
			//调用rest服务查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
			if(!StringUtils.isBlank(json)) {
				TaotaoResult result = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if(result.getStatus()==200) {
					ItemInfo item=(ItemInfo) result.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemDescById(long itemId) {
		try {
			String string = HttpClientUtil.doGet(REST_BASE_URL+ITEM_DESC_URL+itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(string, TbItemDesc.class);
			if(taotaoResult.getStatus()==200) {
				TbItemDesc itemDesc=(TbItemDesc) taotaoResult.getData();
				String result=itemDesc.getItemDesc();
				//System.out.println(result+"在这");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public String getItemParamById(long itemId) {
		try {
			String string = HttpClientUtil.doGet(REST_BASE_URL+ITEM_PARAM_URL+itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(string, TbItemParamItem.class);
			if(taotaoResult.getStatus()==200) {
				TbItemParamItem itemParamItem=(TbItemParamItem) taotaoResult.getData();
				String result=itemParamItem.getParamData();
				/*
				 * [{"group":"主体","params":[{"k":"品牌","v":"小米"},{"k":"型号","v":"小米8"},{"k":"颜色",
				 * "v":"黑色"},
				 * {"k":"上市年份","v":"2018年"}]},{"group":"网络","params":[{"k":"4G网络制式","v":
				 * "4G网络（全网通）"},
				 * {"k":"3G网络制式","v":"3G网络（全网通）"},{"k":"2G网络制式","v":"2G网络（全网通）"}]},
				 * {"group":"存储","params":[{"k":"机身内存","v":"6G+128G"},{"k":"储存卡类型","v":"无存储卡"}]}
				 * ]
				 */
				//生成Html代码
				// 把规格参数json数据转换成java对象
				List<Map> jsonList = JsonUtils.jsonToList(result, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for(Map m1:jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for(Map m2:list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("            <td>"+m2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
