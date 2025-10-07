package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import org.lms.entity.LessonProgress;
import org.lms.mapper.LessonProgressMapper;
import org.lms.response.Result;
import org.lms.service.LessonProgressService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author jeang
* @description 针对表【lesson_progress(课时学习进度表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class LessonProgressServiceImpl implements LessonProgressService {
    private LessonProgressMapper lessonProgressMapper;

    private StringRedisTemplate stringRedisTemplate;

    public LessonProgressServiceImpl(LessonProgressMapper lessonProgressMapper, StringRedisTemplate stringRedisTemplate) {
        this.lessonProgressMapper = lessonProgressMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<LessonProgress> lessonProgressList = lessonProgressMapper.findAll();
        return Result.success(lessonProgressList);
    }

    @Override
    public Result search(LessonProgress lessonProgress) {
        List<LessonProgress> lessonProgressList = lessonProgressMapper.search(lessonProgress);
        return Result.success(lessonProgressList);
    }
}




