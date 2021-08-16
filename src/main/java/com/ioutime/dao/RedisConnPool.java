package com.ioutime.dao;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 23:43
 */

public class RedisConnPool {
    private static JedisPool jedisPool = null;

    static {
        Properties properties = new Properties();
        InputStream stream = RedisConnPool.class.getClassLoader().getResourceAsStream("redis.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创建jedis池配置实例
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(properties.getProperty("jedis.pool.maxActive")));
        config.setMaxIdle(Integer.valueOf(properties.getProperty("jedis.pool.maxIdle")));
        config.setMaxWaitMillis(Long.valueOf(properties.getProperty("jedis.pool.maxWait")));
        config.setTestOnBorrow(Boolean.valueOf(properties.getProperty("jedis.pool.testOnBorrow")));
        config.setTestOnReturn(Boolean.valueOf(properties.getProperty("jedis.pool.testOnReturn")));
        jedisPool = new JedisPool(config, properties.getProperty("redis.ip"), Integer.valueOf(properties.getProperty("redis.port")), Integer.valueOf(properties.getProperty("redis.timeout")), properties.getProperty("redis.passWord"));
    }

    /*连接*/
    public Jedis getRedisConnection(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    /*查询*/
    public String keys(Jedis jedis,String key){
        Boolean is = jedis.exists(key);
        String value = "Don't Have";
        if(is){
            value = jedis.get(key);
        }
        return value;
    }

    /*添加*/
    public String add(Jedis jedis,String key,String value){
        Boolean is = jedis.exists(key);
        String s = "False";
        if(!is){
            s = jedis.set(key, value);
        }
        return s;
    }

    /*删除*/
    public Long del(Jedis jedis,String key){
        Boolean is = jedis.exists(key);
        Long del = -1L;
        if(is){
            del = jedis.del(key);
        }
        return del;
    }


    /*关闭*/
    public void closeRedis(Jedis jedis){
        jedis.close();
    }


}
