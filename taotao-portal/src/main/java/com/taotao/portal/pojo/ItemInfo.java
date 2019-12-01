package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

public class ItemInfo extends TbItem {
	public String[] getImages() {
		String image=this.getImage();
		if(image!=null) {
			String[] images=image.split(",");
			return images;
		}
		return null;	
	}
}
