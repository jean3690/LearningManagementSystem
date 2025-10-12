package org.lms.util;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author jean
 *
 */
@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate; // 专门处理字符串

    public RedisUtil(RedisTemplate<String, Object> redisTemplate,
                     StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // ============================= common =============================

    /**
     * 指定缓存失效时间
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取过期时间（秒）
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断 key 是否存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个或多个 key
     */
    public void delete(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(Arrays.asList(keys));
            }
        }
    }

    /**
     * 根据 pattern 批量删除 key（使用 SCAN，安全，适合生产）
     */
    public void deleteByPattern(String pattern) {
        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(100)
                .build();

        try (Cursor<byte[]> cursor = stringRedisTemplate.getConnectionFactory().getConnection().scan(options)) {
            Set<String> keys = new HashSet<>();
            while (cursor.hasNext()) {
                byte[] keyBytes = cursor.next();
                keys.add(new String(keyBytes));
            }
            if (!CollectionUtils.isEmpty(keys)) {
                stringRedisTemplate.delete(keys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================ String =============================

    /**
     * 获取字符串值
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置字符串值 + 过期时间（秒）
     */
    public void setString(String key, String value, long timeoutSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeoutSeconds));
    }
    public void setString(String key, String value, long time,TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value,time,timeUnit);
    }

    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    // ============================ Object =============================

    /**
     * 获取对象（需自行序列化，如 JSON）
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置对象
     */
    public void set(String key, Object value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeoutSeconds));
    }
    public void set(String key,Object value,long time,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,time,timeUnit);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // ============================ Hash =============================

    /**
     * 获取 hash 中的字段值
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 设置 hash 字段
     */
    public void hSet(String key, String field, Object value, long timeoutSeconds) {
        redisTemplate.opsForHash().put(key, field, value);
        redisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
    }

    public void hSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 获取整个 hash
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // ============================ ZSet =============================

    /**
     * 向 ZSet 添加元素（带分数）
     */
    public void zAdd(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 获取 ZSet 范围内元素（倒序）
     */
    public Set<Object> zRevRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key,start,end);
    }

    /**
     * 获取 ZSet 范围内元素（按分数倒序）
     */
    public Set<Object> zRevRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    // ============================ List =============================

    /**
     * 获取 list 范围数据
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 左插入
     */
    public void lPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 右插入
     */
    public void rPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 设置过期时间（通用）
     */
    public void expireAt(String key, long timestamp) {
        redisTemplate.expireAt(key, new Date(timestamp));
    }

    /**
     * 自增
     */
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 自减
     */
    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }
}