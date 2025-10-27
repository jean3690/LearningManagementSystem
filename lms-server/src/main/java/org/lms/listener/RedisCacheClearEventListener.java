package org.lms.listener;

import lombok.extern.slf4j.Slf4j;
import org.lms.event.CommonRedisCacheClearEvent;
import org.lms.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisCacheClearEventListener {
    private final RedisUtil redisUtil;

    public RedisCacheClearEventListener(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @EventListener
    @Async
    public void cacheClear(CommonRedisCacheClearEvent commonRedisCacheClearEvent){
        try {
            String prefix = commonRedisCacheClearEvent.getPrefix();
            redisUtil.deleteByPattern(prefix+"*");
        }catch (Exception e){
            log.info("异步删除缓存失败",e);
        }
    }

}
