package org.lms.mapper;


import org.lms.entity.CourseReviews;

/**
* @author jeang
* @description 针对表【course_reviews(课程评价表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.CourseReviews
*/
public interface CourseReviewsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CourseReviews record);

    int insertSelective(CourseReviews record);

    CourseReviews selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CourseReviews record);

    int updateByPrimaryKey(CourseReviews record);

}
