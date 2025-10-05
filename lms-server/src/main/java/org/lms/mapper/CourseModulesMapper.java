package org.lms.mapper;

import org.apache.ibatis.annotations.Param;
import org.lms.Enum.OpreationType;
import org.lms.annotation.AutoFill;
import org.lms.dto.CourseModulesDto;
import org.lms.entity.CourseModules;

import java.util.List;

/**
* @author jeang
* @description 针对表【course_modules(课程模块/章节表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.CourseModules
*/
public interface CourseModulesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CourseModules record);
    @AutoFill({OpreationType.CREATEDAT,OpreationType.UPDATEDAT})
    int insertSelective(CourseModules record);

    CourseModules selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CourseModules record);

    int updateByPrimaryKey(CourseModules record);

    List<CourseModules> list(@Param("ids") List<Long> list);

    List<CourseModules> findAll();

    List<CourseModules> search(CourseModulesDto courseModulesDto);

    int deleteBatch(List<Long> ids);
}
