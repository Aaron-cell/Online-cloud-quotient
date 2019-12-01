package com.taotao.common.pojo;

import java.util.List;
/**
 * Function:EasyUI中dategrid控件要求的数据格式为{total:”2”,rows:[{“id”:”1”,”name”,”张三”},{“id”:”2”,”name”,”李四”}]}
 * @author Aaron
 * Date:2019.5.9 
 */
public class EUDateGridResult {
	private long total;
	private List<?> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
