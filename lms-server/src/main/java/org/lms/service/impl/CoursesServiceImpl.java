package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.dto.CoursesDto;
import org.lms.entity.*;
import org.lms.exception.deleteFailException;
import org.lms.mapper.*;
import org.lms.response.Result;
import org.lms.service.CoursesService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @author jeang
 * @description 针对表【courses(课程基本信息表)】的数据库操作Service实现
 * @createDate 2025-09-13 22:53:03
 */
@Service
public class CoursesServiceImpl implements CoursesService {
    private CoursesMapper coursesMapper;
    private CategoriesMapper categoriesMapper;
    //跟courses关联的对象
    private CourseModulesMapper courseModulesMapper;
    private CourseReviewsMapper courseReviewsMapper;
    private EnrollmentsMapper enrollmentsMapper;
    private OrderItemsMapper orderItemsMapper;
    //
    private TransactionTemplate transactionTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public CoursesServiceImpl(CoursesMapper coursesMapper,
                              CategoriesMapper categoriesMapper,
                              CourseModulesMapper courseModulesMapper, CourseReviewsMapper courseReviewsMapper,
                              EnrollmentsMapper enrollmentsMapper,
                              OrderItemsMapper orderItemsMapper,
                              TransactionTemplate transactionTemplate,
                              StringRedisTemplate stringRedisTemplate) {
        this.coursesMapper = coursesMapper;
        this.categoriesMapper = categoriesMapper;
        this.courseModulesMapper = courseModulesMapper;
        this.courseReviewsMapper = courseReviewsMapper;
        this.enrollmentsMapper = enrollmentsMapper;
        this.orderItemsMapper = orderItemsMapper;
        this.transactionTemplate = transactionTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, CoursesDto coursesDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<Courses> coursesList = coursesMapper.findAll(coursesDto);
        return Result.success(new PageInfo<>(coursesList));
    }

    @Override
    public Result queryById(Long id) {
        Courses courses = coursesMapper.selectByPrimaryKey(id);
        return Result.success(Optional.ofNullable(courses));
    }

    @Override
    public Result add(CoursesDto coursesDto) {
        Integer categoryId = coursesDto.getCategoryId();
        Categories categories = categoriesMapper.selectByPrimaryKey(Long.valueOf(categoryId));
        if (categories == null) {
            return Result.error();
        }
        Courses courses = new Courses();
        BeanUtils.copyProperties(coursesDto, courses);
        coursesMapper.insertSelective(courses);
        return Result.success();
    }

    @Override
    public Result search(CoursesDto coursesDto) {
        List<CoursesDto> coursesDtos = coursesMapper.search(coursesDto);
        return Result.success(coursesDtos);
    }

    @Override
    public Result update(CoursesDto coursesDto) {
        Categories categories = categoriesMapper.selectByPrimaryKey(Long.valueOf(coursesDto.getCategoryId()));
        if (categories == null) {
            return Result.success();
        }
        Courses courses = new Courses();
        BeanUtils.copyProperties(coursesDto, courses);
        transactionTemplate.execute(status -> {
            try {
                int i = coursesMapper.updateByPrimaryKeySelective(courses);
                return i;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
        return Result.success();
    }

    @Override
    public Result delete(List<Long> ids) {
        transactionTemplate.execute(status -> {
            try {
                List<CourseModules> courseModulesTempList =  courseModulesMapper.list(ids);
                if(courseModulesTempList!=null&&!courseModulesTempList.isEmpty()){
                    throw new deleteFailException("课程模块关联课程id");
                }
                List<CourseReviews> courseReviewsList = courseReviewsMapper.list(ids);
                if(courseReviewsList!=null&&!courseReviewsList.isEmpty()){
                    throw new deleteFailException("课程评价关联课程id");
                }
                List<Enrollments> enrollmentsList = enrollmentsMapper.list(ids);
                if(enrollmentsList!=null&&!enrollmentsList.isEmpty()){
                    throw new deleteFailException("课程报名表关联课程id");
                }
                List<OrderItems> orderItemsList = orderItemsMapper.list(ids);
                if(orderItemsList!=null&&!orderItemsList.isEmpty()){
                    throw new deleteFailException("订单项关联课程id");
                }
                coursesMapper.deleteBatch(ids);
                return 1;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
        return Result.success();
    }
}




