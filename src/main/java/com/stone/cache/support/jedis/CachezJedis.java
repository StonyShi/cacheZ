package com.stone.cache.support.jedis;



import com.stone.cache.Cachez;
import com.stone.cache.redis.JedisSentinelTemplate;

import java.util.Set;

/**
 * <p>Created with IntelliJ IDEA. </p>
 * <p>User: Stony </p>
 * <p>Date: 2016/4/27 </p>
 * <p>Time: 13:55 </p>
 * <p>Version: 1.0 </p>
 */
public class CachezJedis implements Cachez {

    JedisSentinelTemplate jedisSentinelTemplate;

    @Override
    public Integer set(String key, Object value) {
        jedisSentinelTemplate.set(key,value);
        return 1;
    }

    @Override
    public Integer set(String key, Object value, int expire) {
        jedisSentinelTemplate.set(key,value,expire);
        return 1;
    }

    @Override
    public Object get(String key) {
        return jedisSentinelTemplate.get(key);
    }

    @Override
    public boolean supportExpire() {
        return true;
    }

    @Override
    public int del(String key) {
        return Integer.valueOf(jedisSentinelTemplate.del(key).toString());
    }

    @Override
    public int watch(String watchKey, String cacheKey) {
        Long re = jedisSentinelTemplate.sadd(watchKey, cacheKey);
        return 0;
    }

    @Override
    public Set<String> unwatch(String key) {
        Set<String> keys = jedisSentinelTemplate.smembers(key);
        jedisSentinelTemplate.del(keys);
        jedisSentinelTemplate.del(key);
        return keys;
    }

    public JedisSentinelTemplate getJedisSentinelTemplate() {
        return jedisSentinelTemplate;
    }

    public void setJedisSentinelTemplate(JedisSentinelTemplate jedisSentinelTemplate) {
        this.jedisSentinelTemplate = jedisSentinelTemplate;
    }
}
