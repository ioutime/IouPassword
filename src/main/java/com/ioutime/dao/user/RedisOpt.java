package com.ioutime.dao.user;

import com.ioutime.dao.RedisConnPool;
import redis.clients.jedis.Jedis;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 14:53
 */

public class RedisOpt {

    /*存储tokenId*/
    public boolean storage(String value){
        RedisConnPool redisConnPool = new RedisConnPool();
        Jedis jedis = redisConnPool.getRedisConnection();
        boolean add = redisConnPool.add(jedis, value);
        redisConnPool.closeRedis(jedis);
        return add;
    }

    /*删除tokenId*/
    public Long delValue(String key){
        RedisConnPool redisConnPool = new RedisConnPool();
        Jedis jedis = redisConnPool.getRedisConnection();
        Long del = jedis.del(key);
        redisConnPool.closeRedis(jedis);
        return del;
    }

}
