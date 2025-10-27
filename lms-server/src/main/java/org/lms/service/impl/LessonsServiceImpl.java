package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.dto.LessonsDto;
import org.lms.entity.CourseModules;
import org.lms.entity.Lessons;
import org.lms.mapper.CourseModulesMapper;
import org.lms.mapper.LessonProgressMapper;
import org.lms.mapper.LessonsMapper;
import org.lms.response.Result;
import org.lms.service.LessonsService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public LessonsServiceImpl(LessonsMapper lessonsMapper, CourseModulesMapper courseModulesMapper, LessonProgressMapper lessonProgressMapper, StringRedisTemplate stringRedisTemplate) {
        this.lessonsMapper = lessonsMapper;
        this.courseModulesMapper = courseModulesMapper;
        this.lessonProgressMapper = lessonProgressMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, LessonsDto lessonsDto) {
        PageHelper.startPage(pageNum,pageSize);
        List<Lessons> lessonsList = lessonsMapper.findAll(lessonsDto);
        return Result.success(new PageInfo<>(lessonsList));
    }

    @Override
    public Result search(Lessons lessons) {
        List<Lessons> lessonsList = lessonsMapper.search(lessons);
        return Result.success(lessonsList);
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
        int i = lessonsMapper.insertSelective(lessons);
        return Result.success();
    }

    @Override
    @Transactional
    public Result update(Lessons lessons) {
        CourseModules courseModules = courseModulesMapper.selectByPrimaryKey(lessons.getModuleId());
        if(courseModules==null){
            return Result.error();
        }
        int update = lessonsMapper.updateByPrimaryKeySelective(lessons);
        return Result.success();
    }

    @Override
    @Transactional
    public Result delete(List<Long> ids) {
        int count1 =  lessonProgressMapper.deleteBatch(ids);
        int count = lessonsMapper.deleteBatch(ids);
        return Result.success();
    }
}




