package org.lms.mapper;


import org.apache.ibatis.annotations.Param;
import org.lms.Enum.OpreationType;
import org.lms.annotation.AutoFill;
import org.lms.dto.CoursesDto;
import org.lms.entity.Courses;

import java.util.List;

/**
* @author jeang
* @description 针对表【courses(课程基本信息表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.Courses
*/
public interface CoursesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Courses record);
    @AutoFill({OpreationType.CREATEDAT})
    int insertSelective(Courses record);

    Courses selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Courses record);

    int updateByPrimaryKey(Courses record);

    Courses findByCategoryId(@Param("categoryId") Long id);

    List<Courses> findAll(CoursesDto coursesDto);

    List<CoursesDto> search(CoursesDto coursesDto);

    void deleteBatch(@Param("ids") List<Long> ids);

    List<Courses> list(List<Long> ids);
}
