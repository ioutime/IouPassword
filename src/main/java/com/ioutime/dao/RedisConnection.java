package com.ioutime.dao;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 10:52
 */

public class RedisConnection {

    private static String host = null;
    private static int port = 0;
    private static String auth = null;

    static {
        Properties properties = new Properties();
        InputStream stream = MysqlConnection.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        host = properties.getProperty("host");
        port = Integer.valueOf(properties.getProperty("port"));
        auth = properties.getProperty("auth");

    }
    /*连接*/
    public Jedis getRedisConn(){
        Jedis jedis = new Jedis(host,port);
        jedis.auth(auth);
        jedis.select(1);
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
