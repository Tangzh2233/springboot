package com.edu.util;

import com.edu.common.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Tangzhihao
 * @date 2018/2/28
 */

public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private static final int MAXIDLE = Integer.valueOf(OpenConfig.getConfig("redis.maxIdle"));
    private static final boolean TESTONBORROW = Boolean.valueOf(OpenConfig.getConfig("redis.testOnBorrow"));
    private static final long SMEIT = Long.valueOf(OpenConfig.getConfig("redis.softMinEvictableIdleTime"));
    private static final int MAXTOTAL = Integer.valueOf(OpenConfig.getConfig("redis.maxTotal"));
    private static final long MAXWATIMILLS = Long.valueOf(OpenConfig.getConfig("redis.maxWaitMillis"));
    private static final String HOST = OpenConfig.getConfig("redis.host");
    private static final int PORT = Integer.valueOf(OpenConfig.getConfig("redis.port"));

    private static final JedisPool pool;

    static {
        JedisPoolConfig config = getJedisPoolConfig();
        pool = new JedisPool(config,HOST,PORT);
    }
    public static JedisPool getPool() {
        return pool;
    }

    private static JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxIdle(MAXIDLE);
        config.setTestOnBorrow(TESTONBORROW);
        config.setSoftMinEvictableIdleTimeMillis(SMEIT);
        config.setMaxTotal(MAXTOTAL);
        config.setMaxWaitMillis(MAXWATIMILLS);

        return config;
    }

    public String ping(){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.ping();
        } catch (Exception e) {
            return null;
        }finally {
            close(jedis);
        }
    }

    public void set(String key,String value){
        Assert.isEmpty(key,"key is null");
        Assert.isEmpty(value,"value is null");

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        }finally {
            close(jedis);
        }
    }

    public static String get(String key){
        Assert.isEmpty(key,"key is null");
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("Redis Exception",e);
        } finally {
            close(jedis);
        }
        return null;
    }

    public static void expire(String key,int s){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.expire(key,s);
        } catch (Exception e) {
            logger.error("Redis Exception",e);
        } finally {
            close(jedis);
        }
    }

    private static void close(Jedis jedis){
        if(jedis!=null){
            jedis.close();
        }
    }

}
