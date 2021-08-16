package com.ioutime.dao.user;

import com.ioutime.dao.RedisConnPool;
import redis.clients.jedis.Jedis;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 14:53
 */

public class RedisOpt {

    /*存储token*/
    public boolean storage(String key,String value){
        RedisConnPool redisConnPool = new RedisConnPool();
        Jedis jedis = redisConnPool.getRedisConnection();
        String add = redisConnPool.add(jedis, key, value);
        boolean flag = false;
        if("OK".equals(add)){
            flag = true;
        }
        redisConnPool.closeRedis(jedis);
        return flag;
    }

    /*取出token*/
    public String getToken(String key){
        RedisConnPool redisConnPool = new RedisConnPool();
        Jedis jedis = redisConnPool.getRedisConnection();
        String token = jedis.get(key);
        redisConnPool.closeRedis(jedis);
        return token;
    }

    /*删除token*/
    public Long delValue(String key){
        RedisConnPool redisConnPool = new RedisConnPool();
        Jedis jedis = redisConnPool.getRedisConnection();
        Long del = jedis.del(key);
        redisConnPool.closeRedis(jedis);
        return del;
    }

}
