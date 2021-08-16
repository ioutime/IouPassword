package com.ioutime.dao.user;

import com.ioutime.dao.RedisConnection;
import redis.clients.jedis.Jedis;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 14:53
 */

public class RedisOpt {

    /*存储token*/
    public boolean storage(String key,String value){
        RedisConnection redisConnection = new RedisConnection();
        Jedis jedis = redisConnection.getRedisConn();
        String add = redisConnection.add(jedis, key, value);
        boolean flag = false;
        if("OK".equals(add)){
            flag = true;
        }
        redisConnection.closeRedis(jedis);
        return flag;
    }

    /*取出token*/
    public String getToken(String key){
        RedisConnection redisConnection = new RedisConnection();
        Jedis jedis = redisConnection.getRedisConn();
        String token = jedis.get(key);
        redisConnection.closeRedis(jedis);
        return token;
    }

    /*删除token*/
    public Long delValue(String key){
        RedisConnection redisConnection = new RedisConnection();
        Jedis jedis = redisConnection.getRedisConn();
        Long del = jedis.del(key);
        redisConnection.closeRedis(jedis);
        return del;
    }

}
