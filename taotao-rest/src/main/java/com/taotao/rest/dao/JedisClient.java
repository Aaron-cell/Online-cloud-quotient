package com.taotao.rest.dao;

public interface JedisClient {
	
	String get(String key);
	
	String set(String key,String value);
	
	String hget(String hkey,String key);
	
	long hset(String hkey,String key,String value);
	//value自增
	long incr(String key);
	//设置key过期时间
	long expire(String key,int second);
	//查询剩余过期时间
	long ttl(String key);
	//删除key
	long hdel(String hkey,String contentCid);
}
