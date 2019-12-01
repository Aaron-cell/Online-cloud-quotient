package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

/**
 * Function:上传图片处理
 * @author Aaron
 * Date：2019.5.16
 */
@Controller
public class PictureController {
	@Autowired
	private PictureService pictureService;
	//ResponseBody自动返回json串
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile) {
		Map result=pictureService.uploadPicture(uploadFile);
		//kindEditor 如果返回json格式的Map 在火狐浏览器不好使 为了保证浏览器的兼容性 返回json格式的字符串
		String json=JsonUtils.objectToJson(result);
		return json;
	}
	
}
