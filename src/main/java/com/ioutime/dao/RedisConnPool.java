package com.ioutime.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
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
        jedis.select(1);
        return jedis;
    }

    /*查询*/
    public boolean keys(Jedis jedis,String key){
        Boolean tokenId = jedis.sismember("tokenId", key);
        return tokenId;
    }

    /*添加*/
    public boolean add(Jedis jedis,String value){
        Long id = jedis.sadd("tokenId", value);
        if(id != 0){
            return true;
        }else {
            return false;
        }
    }

    /*删除*/
    public boolean del(Jedis jedis,String value){
        Long id = jedis.srem("tokenId", value);
        if(id != 0){
            return true;
        }else {
            return false;
        }
    }


    /*关闭*/
    public void closeRedis(Jedis jedis){
        jedis.close();
    }


}
