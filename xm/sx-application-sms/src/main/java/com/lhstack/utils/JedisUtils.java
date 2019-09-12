package com.lhstack.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

public class JedisUtils {


    private static Properties jedisProperties = ClassPathResourceUtils.getProperties("jedis.properties");
    private static ThreadLocal<Jedis> threadLocal = new ThreadLocal<>();
    private static JedisPool jedisPool = null;

    public static Jedis getJedis() {
        Jedis jedis = getJedisConnection();
        String host = jedisProperties.getProperty("host");
        return jedis;
    }

    public static Jedis getJedis(Properties properties) {
        Jedis jedis = getJedisConnection(properties);
        return jedis;
    }

    public static void close() {
        Jedis jedis = threadLocal.get();
        if (jedis != null) {
            jedis.close();
        }
        threadLocal.remove();
    }


    private static Jedis getJedisConnection(Properties properties) {
        Jedis jedis = threadLocal.get();
        if (ObjectUtils.isEmpty(jedis)) {
            threadLocal.set(getDefaultJedis(properties));
        }
        return threadLocal.get();
    }

    private static Jedis getJedisConnection() {
        Jedis jedis = threadLocal.get();
        if (ObjectUtils.isEmpty(jedis)) {
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
