package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
@Service
public class PictureServiceImpl implements PictureService {
	//框架提供的一套读取配置文件的方案 在dao.xml已经将所有配置文件读取到spring容器中了
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		Map resultMap=new HashMap<>();
		//生成一个新的文件名
		//取文件原文件名
		try {
			String oldName=uploadFile.getOriginalFilename();
			//UUID生成不重复的文件名
			//UUID.randomUUID();
			String newName=IDUtils.genImageName();
			//组成新文件名
			newName=newName+oldName.substring(oldName.lastIndexOf("."));
			//图片上传  根据时间生成的路径例如：/2019/05/16 是通过parent Maven包中定义的时间组件joda 
			String imagePath=new DateTime().toString("/yyyy/MM/dd");
			Boolean result=FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, 
					imagePath, newName,uploadFile.getInputStream());
			if(!result) {
				resultMap.put("error",1);
				resultMap.put("message","文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url",IMAGE_BASE_URL+imagePath+"/"+newName);
			return resultMap;
		}catch(Exception e) {
			resultMap.put("error",1);
			resultMap.put("message","上传发生异常");
			return resultMap;
		}
		
	}

}
