/*
 * package com.taotao.rest.jedis;
 * 
 * import java.util.HashSet;
 * 
 * import org.junit.Test; import org.springframework.context.ApplicationContext;
 * import org.springframework.context.support.ClassPathXmlApplicationContext;
 * 
 * import redis.clients.jedis.HostAndPort; import redis.clients.jedis.Jedis;
 * import redis.clients.jedis.JedisCluster; import
 * redis.clients.jedis.JedisPool; import redis.clients.util.Pool;
 * 
 * public class JedisTest {
 * 
 * @Test public void testJedisSingle() { //创建一个jedis对象 Jedis jedis = new
 * Jedis("192.168.126.130",6379); //直接调用jedis对象的方法，方法名称和redis命令一致
 * jedis.set("key1", "jedis test"); String string = jedis.get("key1");
 * System.out.println(string); //关闭jedis jedis.close(); }
 *//**
	 * 使用连接池
	 */
/*
 * @Test
 * 
 * @SuppressWarnings("resource") public void TestJedisPool() { //创建jedis连接池
 * JedisPool jedisPool = new JedisPool("192.168.126.130",6379);
 * //从jedis连接池中获取Jedis对象 Jedis jedis = jedisPool.getResource(); String string =
 * jedis.get("key1"); System.out.println(string); //关闭jedis jedis.close();
 * //关闭连接池 jedisPool.close(); }
 * 
 *//**
	 * redis集群测试
	 */
/*
 * @Test public void testJedisCluster() { HashSet<HostAndPort> nodes=new
 * HashSet<>(); nodes.add(new HostAndPort("192.168.126.130",7001));
 * nodes.add(new HostAndPort("192.168.126.130",7002)); nodes.add(new
 * HostAndPort("192.168.126.130",7003)); nodes.add(new
 * HostAndPort("192.168.126.130",7004)); nodes.add(new
 * HostAndPort("192.168.126.130",7005)); nodes.add(new
 * HostAndPort("192.168.126.130",7006)); JedisCluster jedisCluster = new
 * JedisCluster(nodes); String string = jedisCluster.get("key1");
 * System.out.println(string); jedisCluster.close(); }
 * 
 *//**
	 * 单击版测试
	 */
/*
 * @Test public void testSpringsingle() { ApplicationContext
 * applicationContext=new
 * ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
 * JedisPool pool = (JedisPool) applicationContext.getBean("redisClient"); Jedis
 * jedis = pool.getResource(); String string = jedis.get("key1");
 * System.out.println(string); jedis.close(); pool.close(); }
 * 
 *//**
	 * 集群版测试,
	 *//*
		 * @Test public void testSpringJedisCluster() { ApplicationContext
		 * applicationContext=new
		 * ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		 * JedisCluster jedisCluster = (JedisCluster)
		 * applicationContext.getBean("redisClient"); String string =
		 * jedisCluster.get("key1"); System.out.println(string); jedisCluster.close(); }
		 * }
		 */