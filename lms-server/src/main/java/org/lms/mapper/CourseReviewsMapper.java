package org.lms.mapper;


import org.apache.ibatis.annotations.Param;
import org.lms.Enum.OpreationType;
import org.lms.annotation.AutoFill;
import org.lms.dto.CourseModulesDto;
import org.lms.entity.CourseReviews;

import java.util.List;

/**
* @author jeang
* @description 针对表【course_reviews(课程评价表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.CourseReviews
*/
public interface CourseReviewsMapper {

    int deleteByPrimaryKey(Long id);
    @AutoFill({OpreationType.CREATEDAT,OpreationType.UPDATEDAT})
    int insert(CourseReviews record);
    @AutoFill({OpreationType.CREATEDAT,OpreationType.UPDATEDAT})
    int insertSelective(CourseReviews record);

    CourseReviews selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CourseReviews record);

    int updateByPrimaryKey(CourseReviews record);

    List<CourseReviews> list(@Param("ids") List<Long> list);

    List<CourseReviews> findAll(CourseReviews courseReviews);

    List<CourseReviews> search(CourseModulesDto courseModulesDto);

    int deleteBatch(@Param("ids") List<Long> ids);
}
