package com.lhstack.utils;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;
import java.util.Set;

public class JedisUtils {


    private static Properties jedisProperties = ClassPathResourceUtils.getProperties("jedis.properties");
    private static ThreadLocal<Jedis> threadLocal = new ThreadLocal<>();
    private static JedisPool jedisPool = null;
    private static Log log = LogFactory.get(JedisUtils.class);


    public static void hset(String hashKey,String key,Object value){
        Jedis jedis = getJedis();
        jedis.hset(hashKey,key,GsonUtils.createGson().toJson(value));
    }

    public static void hdel(String hashKey){
        Jedis jedis = getJedis();
        Set<String> hkeys = jedis.hkeys(hashKey);
        if(hkeys.size() > 0){
            hkeys.forEach(key ->{
                jedis.hdel(hashKey,key);
            });
        }
    }

    public static String hget(String hkey,String key){
        Jedis jedis = getJedis();
        return jedis.hget(hkey,key);
    }

    public static Jedis getJedis(){
        Jedis jedis = getJedisConnection();
        log.info("fetch jedis client object is a {}",jedis);
        String host = jedisProperties.getProperty("host");
        return jedis;
    }

    public static Jedis getJedis(Properties properties){
        Jedis jedis = getJedisConnection(properties);
        log.info("fetch jedis client object is a {}",jedis);
        return jedis;
    }


    public static void close(){
        Jedis jedis = threadLocal.get();
        if(jedis != null){
            log.info("close jedis is a {}",jedis);
            jedis.close();
        }
        threadLocal.remove();
    }


    private static Jedis getJedisConnection(Properties properties){
        Jedis jedis = threadLocal.get();
        if(ObjectUtils.isEmpty(jedis)){
            threadLocal.set(getDefaultJedis(properties));
        }
        return threadLocal.get();
    }

    private static Jedis getJedisConnection(){
        Jedis jedis = threadLocal.get();
        if(ObjectUtils.isEmpty(jedis)){
            threadLocal.set(getDefaultJedis(jedisProperties));
        }
        return threadLocal.get();
    }

    private static Jedis getDefaultJedis(Properties properties) {
        if (jedisPool == null) {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle", "8")));
            jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal", "20")));
            jedisPoolConfig.setMinIdle(Integer.parseInt(properties.getProperty("minIdle", "5")));
            String password = properties.getProperty("password", "");
            if (StringUtils.isEmpty(password)) {
                jedisPool = new JedisPool(jedisPoolConfig, properties.getProperty("host", "localhost"),
                        Integer.parseInt(properties.getProperty("port", "6379")),
                        Integer.parseInt(properties.getProperty("connectTimeout", "5000")));
            } else {
                jedisPool = new JedisPool(jedisPoolConfig,
                        properties.getProperty("host", "localhost"),
                        Integer.parseInt(properties.getProperty("port", "6379")),
                        Integer.parseInt(properties.getProperty("connectTimeout", "5000")),
                        password, Integer.parseInt(properties.getProperty("database", "0")));
            }
        }
        return jedisPool.getResource();
    }
}
