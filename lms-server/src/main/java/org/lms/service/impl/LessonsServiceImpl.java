package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.constant.RedisConstant;
import org.lms.dto.LessonsDto;
import org.lms.entity.CourseModules;
import org.lms.entity.Lessons;
import org.lms.event.CommonRedisCacheClearEvent;
import org.lms.mapper.CourseModulesMapper;
import org.lms.mapper.LessonProgressMapper;
import org.lms.mapper.LessonsMapper;
import org.lms.response.Result;
import org.lms.service.LessonsService;
import org.lms.utils.JsonUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
* @author jeang
* @description 针对表【lessons(课程课时内容表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class LessonsServiceImpl implements LessonsService {
    private final LessonsMapper lessonsMapper;
    private final CourseModulesMapper courseModulesMapper;
    private final LessonProgressMapper lessonProgressMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final ApplicationEventPublisher eventPublisher;
    public LessonsServiceImpl(LessonsMapper lessonsMapper, CourseModulesMapper courseModulesMapper, LessonProgressMapper lessonProgressMapper, StringRedisTemplate stringRedisTemplate, ApplicationEventPublisher eventPublisher) {
        this.lessonsMapper = lessonsMapper;
        this.courseModulesMapper = courseModulesMapper;
        this.lessonProgressMapper = lessonProgressMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, LessonsDto lessonsDto) {
        String key;
        if(ObjectUtils.isEmpty(lessonsDto)){
            key = RedisConstant.LESSONS_PAGE + pageNum+":"+pageSize;
        }else {
            String hash = DigestUtils.md5DigestAsHex(JsonUtil.toJson(lessonsDto).getBytes());
            key = RedisConstant.LESSONS_PAGE + pageNum+":"+pageSize+":"+hash;
        }
        String json = stringRedisTemplate.opsForValue().get(key);
        if(json!=null){
            if(json.equals("_NULL_")){
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            List<Lessons> lessonsList = JsonUtil.jsonToObj(json,List.class,Lessons.class);
            if (Optional.ofNullable(lessonsList).isEmpty()) {
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            return Result.success(new PageInfo<>(lessonsList));
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Lessons> lessonsList = lessonsMapper.findAll(lessonsDto);
        if (ObjectUtils.isEmpty(lessonsList)) {
            stringRedisTemplate.opsForValue().set(key,"_NULL_", ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
            return Result.success(Collections.emptyList());
        }
        stringRedisTemplate.opsForValue().set(key,JsonUtil.toJson(lessonsList), ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
        return Result.success(new PageInfo<>(lessonsList));
    }

    @Override
    public Result queryById(Long id) {
        Lessons lessons = lessonsMapper.selectByPrimaryKey(id);
        return Result.success(lessons);
    }

    @Override
    public Result add(Lessons lessons) {
        CourseModules courseModules = courseModulesMapper.selectByPrimaryKey(lessons.getModuleId());
        if(courseModules==null){
            return Result.error();
        }
        lessonsMapper.insertSelective(lessons);
        clearPageCache();
        return Result.success();
    }

    @Override
    @Transactional
    public Result update(Lessons lessons) {
        CourseModules courseModules = courseModulesMapper.selectByPrimaryKey(lessons.getModuleId());
        if(courseModules==null){
            return Result.error();
        }
        lessonsMapper.updateByPrimaryKeySelective(lessons);
        clearPageCache();
        return Result.success();
    }

    @Override
    @Transactional
    public Result delete(List<Long> ids) {
        lessonProgressMapper.deleteBatch(ids);
        lessonsMapper.deleteBatch(ids);
        clearPageCache();
        return Result.success();
    }
    private void clearPageCache() {
        String prefix = RedisConstant.LESSONS_PAGE;
        eventPublisher.publishEvent(new CommonRedisCacheClearEvent(prefix));
    }
}




