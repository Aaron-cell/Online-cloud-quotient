package com.taotao.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * Function:图片上传接口
 * @author Aaron
 * Date:2019.5.15
 */
public interface PictureService {
	Map uploadPicture(MultipartFile uploadFile);
}
