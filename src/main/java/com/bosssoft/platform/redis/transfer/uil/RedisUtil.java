package com.bosssoft.platform.redis.transfer.uil;

import com.bosssoft.platform.common.redis.util.JedisConfigUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class RedisUtil {

	 private String maxConnections=null;
	private JedisPool jedisPool   = null;
	
	public RedisUtil(String hostName,int port){
		connect(hostName, port);
	}
	private void connect(String hostName,int port){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setMaxTotal(Integer.parseInt(getMaxConnections()));
		config.setTestOnBorrow(false);
		jedisPool = new JedisPool(config, hostName, port);
	}

	/**
	 * 扫描
	 * @param cursor
	 * @param param
	 * @return
	 */
	public ScanResult<String> sacn(String cursor,ScanParams param){
		Jedis jedis = jedisPool.getResource();

        try {
            return jedis.scan(cursor, param);
        } finally {
        	if (jedis != null)
        		jedis.close();
        }
	}
	
	/**
	 * 序列化给定 key,并返回被序列化的值
	 * @param key
	 * @return
	 */
	public byte[] dump(String key){
		Jedis jedis = jedisPool.getResource();

        try {
            return jedis.dump(key);
        } finally {
        	if (jedis != null)
        		jedis.close();
        }
	}
	
	public void restore(String key, int ttl, byte[] serializedValue){
		Jedis jedis = jedisPool.getResource();

        try {
            jedis.restore(key, ttl, serializedValue);
        } finally {
        	if (jedis != null)
        		jedis.close();
        }
	}
	
	private String getMaxConnections() {
		
		
		return "500";
	}
	
}
