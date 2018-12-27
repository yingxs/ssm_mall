package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

public class RedisShardedPool {


    private static ShardedJedisPool pool;//jedis连接池
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20")) ;//最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.Idle","20")) ;//连接池中最大空闲个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.Idle","20")) ;//连接池中最小空闲个数
    private static Boolean testOnBoroow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true")) ;//在boroow一个实例的时候，是否进行验证操作，如果true,得到的实例一定是可用的
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true")) ;//在boroow一个实例的时候，是否进行验证操作，如果true,返回的实例一定是可用的

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port")) ;//连接池中最小空闲个数
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port")) ;//连接池中最小空闲个数

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBoroow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽的时候是否阻塞，默认为true，表示阻塞，false就抛出异常

        //pool = new JedisPool(config,redisIp,redisPort,1000*2);

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, 1000 * 2);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port, 1000 * 2);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>(2);
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        pool = new ShardedJedisPool(config,jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);

    }

    static {
        initPool();
        System.out.println();
        System.out.println();
        System.out.println("RedisShardedPool");
        System.out.println();
        System.out.println();

    }



    public static ShardedJedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(ShardedJedis jedis){
        pool.returnBrokenResource(jedis);
    }
    public static void returnResource(ShardedJedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();

        for(int i = 0 ; i < 10 ;i++){
            jedis.set("key"+i,"value"+i);
        }

        returnResource(jedis);
//        pool.destroy();
        System.out.println("test end...");
    }



}
