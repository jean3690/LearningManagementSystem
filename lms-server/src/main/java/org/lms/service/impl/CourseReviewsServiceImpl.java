package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.dto.CourseModulesDto;
import org.lms.entity.CourseReviews;
import org.lms.mapper.CourseReviewsMapper;
import org.lms.response.Result;
import org.lms.service.CourseReviewsService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author jeang
* @description 针对表【course_reviews(课程评价表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class CourseReviewsServiceImpl implements CourseReviewsService {
    private final CourseReviewsMapper courseReviewsMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public CourseReviewsServiceImpl(CourseReviewsMapper courseReviewsMapper, StringRedisTemplate stringRedisTemplate) {
        this.courseReviewsMapper = courseReviewsMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, CourseReviews courseReviews) {
        PageHelper.startPage(pageNum,pageSize);
        List<CourseReviews> courseReviewsList = courseReviewsMapper.findAll(courseReviews);
        return Result.success(new PageInfo<>(courseReviewsList));
    }

    @Override
    public Result search(CourseModulesDto courseModulesDto) {
        List<CourseReviews> courseReviewsList = courseReviewsMapper.search(courseModulesDto);
        return Result.success(courseReviewsList);
    }

    @Override
    public Result delete(List<Long> ids) {
        int count = courseReviewsMapper.deleteBatch(ids);
        return Result.success();
    }
}




