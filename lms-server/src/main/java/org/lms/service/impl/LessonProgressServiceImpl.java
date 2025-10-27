package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.constant.RedisConstant;
import org.lms.entity.LessonProgress;
import org.lms.entity.Lessons;
import org.lms.event.CommonRedisCacheClearEvent;
import org.lms.mapper.LessonProgressMapper;
import org.lms.mapper.LessonsMapper;
import org.lms.response.Result;
import org.lms.service.LessonProgressService;
import org.lms.utils.JsonUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
* @author jeang
* @description 针对表【lesson_progress(课时学习进度表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class LessonProgressServiceImpl implements LessonProgressService {
    private final LessonProgressMapper lessonProgressMapper;
    private final LessonsMapper lessonsMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public LessonProgressServiceImpl(LessonProgressMapper lessonProgressMapper, LessonsMapper lessonsMapper, StringRedisTemplate stringRedisTemplate, ApplicationEventPublisher eventPublisher) {
        this.lessonProgressMapper = lessonProgressMapper;
        this.lessonsMapper = lessonsMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, LessonProgress lessonProgress) {
        String key;
        if(ObjectUtils.isEmpty(lessonProgress)){
            key = RedisConstant.LESSON_PROGRESS_PAGE + pageNum+":"+pageSize;
        }else {
            String hash = DigestUtils.md5DigestAsHex(JsonUtil.toJson(lessonProgress).getBytes());
            key = RedisConstant.LESSON_PROGRESS_PAGE + pageNum+":"+pageSize+":"+hash;
        }
        String json = stringRedisTemplate.opsForValue().get(key);
        if(json!=null){
            if(json.equals("_NULL_")){
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            List<LessonProgress> lessonProgressList = JsonUtil.jsonToObj(json,List.class,LessonProgress.class);
            if (Optional.ofNullable(lessonProgressList).isEmpty()) {
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            return Result.success(new PageInfo<>(lessonProgressList));
        }
        PageHelper.startPage(pageNum,pageSize);
        List<LessonProgress> lessonProgressList = lessonProgressMapper.findAll(lessonProgress);
        if (ObjectUtils.isEmpty(lessonProgressList)) {
            stringRedisTemplate.opsForValue().set(key,"_NULL_", ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
            return Result.success(Collections.emptyList());
        }
        stringRedisTemplate.opsForValue().set(key,JsonUtil.toJson(lessonProgressList), ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
        return Result.success(new PageInfo<>(lessonProgressList));
    }

    @Override
    public Result add(LessonProgress lessonProgress) {
        Long lessonId = lessonProgress.getLessonId();
        Lessons lessons = lessonsMapper.selectByPrimaryKey(lessonId);
        if(ObjectUtils.isEmpty(lessons)){
            return Result.error();
        }
        lessonProgressMapper.insertSelective(lessonProgress);
        clearPageCache();
        return Result.success();
    }

    private void clearPageCache() {
        String prefix = RedisConstant.LESSON_PROGRESS_PAGE;
        eventPublisher.publishEvent(new CommonRedisCacheClearEvent(prefix));
    }
}




