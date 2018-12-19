package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

    private static JedisPool pool;//jedis连接池
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20")) ;//最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.Idle","20")) ;//连接池中最大空闲个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.Idle","20")) ;//连接池中最小空闲个数
    private static Boolean testOnBoroow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true")) ;//在boroow一个实例的时候，是否进行验证操作，如果true,得到的实例一定是可用的
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true")) ;//在boroow一个实例的时候，是否进行验证操作，如果true,返回的实例一定是可用的

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port")) ;//连接池中最小空闲个数

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBoroow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽的时候是否阻塞，默认为true，表示阻塞，false就抛出异常

        pool = new JedisPool(config,redisIp,redisPort,1000*2);

    }

    static {
        initPool();
    }



    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }
    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("yingxs_key","yingxs_value");
        returnResource(jedis);
        pool.destroy();
        System.out.println("test end...");
    }






















}
