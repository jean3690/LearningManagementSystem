package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.constant.RedisConstant;
import org.lms.entity.CourseReviews;
import org.lms.event.CommonRedisCacheClearEvent;
import org.lms.mapper.CourseReviewsMapper;
import org.lms.response.Result;
import org.lms.service.CourseReviewsService;
import org.lms.utils.JsonUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
* @author jeang
* @description 针对表【course_reviews(课程评价表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class CourseReviewsServiceImpl implements CourseReviewsService {
    private final CourseReviewsMapper courseReviewsMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final TransactionTemplate transactionTemplate;

    public CourseReviewsServiceImpl(CourseReviewsMapper courseReviewsMapper, StringRedisTemplate stringRedisTemplate, ApplicationEventPublisher eventPublisher, TransactionTemplate transactionTemplate) {
        this.courseReviewsMapper = courseReviewsMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.eventPublisher = eventPublisher;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, CourseReviews courseReviews) {
        String key;
        if(ObjectUtils.isEmpty(courseReviews)){
            key = RedisConstant.COURSE_REVIEWS_PAGE + pageNum+":"+pageSize;
        }else {
            String hash = DigestUtils.md5DigestAsHex(JsonUtil.toJson(courseReviews).getBytes());
            key = RedisConstant.COURSE_REVIEWS_PAGE + pageNum + ":"+ pageSize + ":" + hash;
        }
        String json = stringRedisTemplate.opsForValue().get(key);
        if(json!=null){
            if(json.equals("_NULL_")){
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            List<CourseReviews> courseReviewsList = JsonUtil.jsonToObj(json,List.class,CourseReviews.class);
            if (ObjectUtils.isEmpty(courseReviewsList)) {
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            return Result.success(new PageInfo<>(courseReviewsList));
        }
        PageHelper.startPage(pageNum,pageSize);
        List<CourseReviews> courseReviewsList = courseReviewsMapper.findAll(courseReviews);
        if (RedisSaveEmpty(key, ObjectUtils.isEmpty(courseReviewsList), stringRedisTemplate, JsonUtil.toJson(courseReviewsList)))
            return Result.success(new PageInfo<>(Collections.emptyList()));
        return Result.success(new PageInfo<>(courseReviewsList));
    }

    static boolean RedisSaveEmpty(String key, boolean empty, StringRedisTemplate stringRedisTemplate, String json2) {
        if (empty) {
            stringRedisTemplate.opsForValue().set(key,"_NULL_", ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
            return true;
        }
        stringRedisTemplate.opsForValue().set(key, json2,ThreadLocalRandom.current().nextInt(10,30),TimeUnit.MINUTES);
        return false;
    }

    @Override
    public Result delete(List<Long> ids) {
        courseReviewsMapper.deleteBatch(ids);
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result add(CourseReviews courseReviews) {
        courseReviewsMapper.insertSelective(courseReviews);
        clearPageCache();
        return Result.success();
    }

    private void clearPageCache() {
        String prefix = RedisConstant.COURSE_REVIEWS_PAGE;
        eventPublisher.publishEvent(new CommonRedisCacheClearEvent(prefix));
    }
}




