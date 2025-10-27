package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.constant.RedisConstant;
import org.lms.dto.CoursesDto;
import org.lms.entity.*;
import org.lms.event.CommonRedisCacheClearEvent;
import org.lms.exception.deleteFailException;
import org.lms.mapper.*;
import org.lms.response.Result;
import org.lms.service.CoursesService;
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
 * @description 针对表【courses(课程基本信息表)】的数据库操作Service实现
 * @createDate 2025-09-13 22:53:03
 */
@Service
public class CoursesServiceImpl implements CoursesService {
    private final CoursesMapper coursesMapper;
    private final CategoriesMapper categoriesMapper;
    //跟coures关联的对象
    private final CourseModulesMapper courseModulesMapper;
    private final CourseReviewsMapper courseReviewsMapper;
    private final EnrollmentsMapper enrollmentsMapper;
    private final OrderItemsMapper orderItemsMapper;
    private final TransactionTemplate transactionTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public CoursesServiceImpl(CoursesMapper coursesMapper,
                              CategoriesMapper categoriesMapper,
                              CourseModulesMapper courseModulesMapper,
                              CourseReviewsMapper courseReviewsMapper,
                              EnrollmentsMapper enrollmentsMapper,
                              OrderItemsMapper orderItemsMapper,
                              TransactionTemplate transactionTemplate,
                              StringRedisTemplate stringRedisTemplate,
                              ApplicationEventPublisher eventPublisher) {
        this.coursesMapper = coursesMapper;
        this.categoriesMapper = categoriesMapper;
        this.courseModulesMapper = courseModulesMapper;
        this.courseReviewsMapper = courseReviewsMapper;
        this.enrollmentsMapper = enrollmentsMapper;
        this.orderItemsMapper = orderItemsMapper;
        this.transactionTemplate = transactionTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, CoursesDto coursesDto) {
        String key;
        if (ObjectUtils.isEmpty(coursesDto)) {
            key = RedisConstant.COURSE_PAGE + pageNum + ":" + pageSize;
        } else {
            String hash = DigestUtils.md5DigestAsHex(JsonUtil.toJson(coursesDto).getBytes());
            key = RedisConstant.COUPONS_PAGE + pageNum + ":" + pageSize + ":" + hash;
        }
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json != null) {
            if (json.equals("_NULL_")) {
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            List<Courses> coursesList = JsonUtil.jsonToObj(json, List.class, Courses.class);
            if (ObjectUtils.isEmpty(coursesList)) {
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            return Result.success(new PageInfo<>(coursesList));
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Courses> coursesList = coursesMapper.findAll(coursesDto);
        if (RedisSaveEmpty(key, ObjectUtils.isEmpty(coursesList), stringRedisTemplate, JsonUtil.toJson(coursesList)))
            return Result.success(new PageInfo<>(Collections.emptyList()));
        return Result.success(new PageInfo<>(coursesList));
    }

    static boolean RedisSaveEmpty(String key, boolean empty, StringRedisTemplate stringRedisTemplate, String json2) {
        if (empty) {
            stringRedisTemplate.opsForValue().set(key, "_NULL_", ThreadLocalRandom.current().nextInt(10, 30), TimeUnit.MINUTES);
            return true;
        }
        stringRedisTemplate.opsForValue().set(key, json2, ThreadLocalRandom.current().nextInt(10, 30), TimeUnit.MINUTES);
        return false;
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
        clearPageCache();
        return Result.success();
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
                return coursesMapper.updateByPrimaryKeySelective(courses);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result delete(List<Long> ids) {
        transactionTemplate.execute(status -> {
            try {
                List<CourseModules> courseModulesTempList = courseModulesMapper.list(ids);
                if (courseModulesTempList != null && !courseModulesTempList.isEmpty()) {
                    throw new deleteFailException("课程模块关联课程id");
                }
                List<CourseReviews> courseReviewsList = courseReviewsMapper.list(ids);
                if (courseReviewsList != null && !courseReviewsList.isEmpty()) {
                    throw new deleteFailException("课程评价关联课程id");
                }
                List<Enrollments> enrollmentsList = enrollmentsMapper.list(ids);
                if (enrollmentsList != null && !enrollmentsList.isEmpty()) {
                    throw new deleteFailException("课程报名表关联课程id");
                }
                List<OrderItems> orderItemsList = orderItemsMapper.list(ids);
                if (orderItemsList != null && !orderItemsList.isEmpty()) {
                    throw new deleteFailException("订单项关联课程id");
                }
                coursesMapper.deleteBatch(ids);
                return 1;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
        clearPageCache();
        return Result.success();
    }

    private void clearPageCache() {
        String prefix = RedisConstant.COURSE_PAGE;
        eventPublisher.publishEvent(new CommonRedisCacheClearEvent(prefix));
    }
}




