package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.constant.RedisConstant;
import org.lms.dto.CourseModulesDto;
import org.lms.entity.CourseModules;
import org.lms.entity.Courses;
import org.lms.entity.Lessons;
import org.lms.event.CommonRedisCacheClearEvent;
import org.lms.mapper.CourseModulesMapper;
import org.lms.mapper.CoursesMapper;
import org.lms.mapper.LessonsMapper;
import org.lms.response.Result;
import org.lms.service.CourseModulesService;
import org.lms.utils.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
* @author jeang
* @description 针对表【course_modules(课程模块/章节表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class CourseModulesServiceImpl implements CourseModulesService {
    private final CourseModulesMapper courseModulesMapper;
    private final CoursesMapper coursesMapper;
    private final LessonsMapper lessonsMapper;
    private final TransactionTemplate transactionTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final StringRedisTemplate stringRedisTemplate;

    public CourseModulesServiceImpl(CourseModulesMapper courseModulesMapper,
                                    CoursesMapper coursesMapper,
                                    LessonsMapper lessonsMapper,
                                    TransactionTemplate transactionTemplate,
                                    ApplicationEventPublisher eventPublisher,
                                    StringRedisTemplate stringRedisTemplate) {
        this.courseModulesMapper = courseModulesMapper;
        this.coursesMapper = coursesMapper;
        this.lessonsMapper = lessonsMapper;
        this.transactionTemplate = transactionTemplate;
        this.eventPublisher = eventPublisher;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, CourseModulesDto courseModulesDto) {
        String key;
        if(ObjectUtils.isEmpty(courseModulesDto)){
            key = RedisConstant.COURSE_MODULES_PAGE + pageNum+":"+pageSize;
        }else {
            String hash = DigestUtils.md5DigestAsHex(JsonUtil.toJson(courseModulesDto).getBytes());
            key = RedisConstant.COURSE_MODULES_PAGE + pageNum+":"+pageSize+":"+hash;
        }
        String json = stringRedisTemplate.opsForValue().get(key);
        if(json!=null){
            if(json.equals("_NULL_")){
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            List<CourseModules> courseModulesList = JsonUtil.jsonToObj(json,List.class,CourseModules.class);
            if (Optional.ofNullable(courseModulesList).isEmpty()) {
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            return Result.success(new PageInfo<>(courseModulesList));
        }
        PageHelper.startPage(pageNum,pageSize);
        List<CourseModules> courseModulesList = courseModulesMapper.findAll(courseModulesDto);
        if(ObjectUtils.isEmpty(courseModulesList)){
            stringRedisTemplate.opsForValue().set(key,"_NULL_",ThreadLocalRandom.current().nextInt(10,30),TimeUnit.MINUTES);
            return Result.success(new PageInfo<>(Collections.emptyList()));
        }
        stringRedisTemplate.opsForValue().set(key,JsonUtil.toJson(courseModulesList),ThreadLocalRandom.current().nextInt(10,30),TimeUnit.MINUTES);
        PageInfo<CourseModules> pageInfo = new PageInfo<>(courseModulesList);
        return Result.success(pageInfo);
    }


    @Override
    public Result add(CourseModulesDto courseModulesDto) {
        Long courseId = courseModulesDto.getCourseId();
        Courses courses = coursesMapper.selectByPrimaryKey(courseId);
        if(courses==null){
            return Result.error();
        }
        CourseModules courseModules = new CourseModules();
        BeanUtils.copyProperties(courseModulesDto,courseModules);
        courseModulesMapper.insertSelective(courseModules);
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result delete(List<Long> ids) {
        transactionTemplate.execute(status -> {
            try {
                List<Lessons> lessonsList = lessonsMapper.list(ids);
                if (lessonsList != null && !lessonsList.isEmpty()) {
                    return Result.error();
                }
                return courseModulesMapper.deleteBatch(ids);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result update(CourseModulesDto courseModulesDto) {
        Courses courses = coursesMapper.selectByPrimaryKey(courseModulesDto.getCourseId());
        if(courses==null){
            return Result.error();
        }
        CourseModules courseModules = new CourseModules();
        BeanUtils.copyProperties(courseModulesDto,courseModules);
        courseModulesMapper.updateByPrimaryKeySelective(courseModules);
        clearPageCache();
        return Result.success();
    }

    private void clearPageCache(){
        String prefix = RedisConstant.COURSE_MODULES_PAGE;
        eventPublisher.publishEvent(new CommonRedisCacheClearEvent(prefix));
    }
}




