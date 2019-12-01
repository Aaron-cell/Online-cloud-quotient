package com.taotao.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;



public class HttpClientTest {
	@Test
	public void doGet() throws Exception{
		//创建一个HttpClient对象
		CloseableHttpClient httpClient=HttpClients.createDefault();
		//创建一个get对象
		HttpGet get = new HttpGet("http://www.baidu.com");
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		//获取响应结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity,"utf-8");
		System.out.println(string);
		//关闭HttpClient
		response.close();
		httpClient.close();
	}
	
	/*
	 * 地址栏携带参数的方法
	 * 1.可以直接在url后面携带参数
	 * 2.下面方法
	 */
	@Test
	public void doGetWithParam() throws Exception{
		//创建一个HttpClient对象
		CloseableHttpClient httpClient=HttpClients.createDefault();
		//创建一个uri对象
		URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
		uriBuilder.addParameter("query", "花千骨");
		HttpGet get = new HttpGet(uriBuilder.build());
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		//获取响应结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity,"utf-8");
		System.out.println(string);
		//关闭HttpClient
		response.close();
		httpClient.close();
	}
}
