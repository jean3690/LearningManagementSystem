package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.dto.CourseModulesDto;
import org.lms.entity.CourseModules;
import org.lms.entity.Courses;
import org.lms.entity.Lessons;
import org.lms.mapper.CourseModulesMapper;
import org.lms.mapper.CoursesMapper;
import org.lms.mapper.LessonsMapper;
import org.lms.response.Result;
import org.lms.service.CourseModulesService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
* @author jeang
* @description 针对表【course_modules(课程模块/章节表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class CourseModulesServiceImpl implements CourseModulesService {
    private CourseModulesMapper courseModulesMapper;
    private CoursesMapper coursesMapper;
    private LessonsMapper lessonsMapper;
    private TransactionTemplate transactionTemplate;

    public CourseModulesServiceImpl(CourseModulesMapper courseModulesMapper, CoursesMapper coursesMapper, LessonsMapper lessonsMapper, TransactionTemplate transactionTemplate) {
        this.courseModulesMapper = courseModulesMapper;
        this.coursesMapper = coursesMapper;
        this.lessonsMapper = lessonsMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CourseModules> courseModulesList = courseModulesMapper.findAll();
        return Result.success(new PageInfo<>(courseModulesList));
    }

    @Override
    public Result search(CourseModulesDto courseModulesDto) {
        List<CourseModules> courseModulesList = courseModulesMapper.search(courseModulesDto);
        return Result.success(courseModulesList);
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
                int count = courseModulesMapper.deleteBatch(ids);
                return count;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
        return Result.success();
    }
}




