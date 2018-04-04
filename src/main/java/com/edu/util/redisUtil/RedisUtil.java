package com.edu.util.redisUtil;

import redis.clients.jedis.Jedis;

/**
 * @author Tangzhihao
 * @date 2018/2/28
 */

public class RedisUtil {

    public static Jedis getRedisConnect(String url){
        String[] split = url.split(":");
        Jedis jedis = null;
        try{
            jedis = new Jedis(split[0],Integer.valueOf(split[1]));
        }catch (Exception e){
            System.out.println("redis 获取异常！");
        }
        return jedis;
    }

    public static void closeJedis(Jedis jedis){
        jedis.close();
    }

}
