package org.lms.service;


import org.lms.dto.CourseModulesDto;
import org.lms.entity.CourseReviews;
import org.lms.response.Result;

import java.util.List;

/**
* @author jeang
* @description 针对表【course_reviews(课程评价表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface CourseReviewsService  {

    Result page(Integer pageNum, Integer pageSize, CourseReviews courseReviews);

    Result delete(List<Long> ids);
    Result add(CourseReviews courseReviews);
}
